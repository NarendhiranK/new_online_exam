/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
*/
package org.apache.ofbiz.bi

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

import org.apache.ofbiz.base.util.UtilDateTime
import org.apache.ofbiz.base.util.UtilProperties
import org.apache.ofbiz.base.util.UtilValidate
import org.apache.ofbiz.entity.GenericValue
import org.apache.ofbiz.entity.condition.EntityCondition
import org.apache.ofbiz.entity.condition.EntityOperator
import org.apache.ofbiz.order.order.OrderReadHelper
import org.apache.ofbiz.service.ServiceUtil

Map loadSalesInvoiceFact() {
    GenericValue invoice = from('Invoice').where(parameters).queryOne()
    if (!invoice) {
        String errorMessage = UtilProperties.getMessage('AccountingUiLabels', 'AccountingInvoiceDoesNotExists', parameters.locale)
        logError(errorMessage)
        return error(errorMessage)
    }
    if (invoice.invoiceTypeId == 'SALES_INVOICE') {
        List invoiceItems = delegator.getRelated('InvoiceItem', null, null, invoice, false)
        for (GenericValue invoiceItem : invoiceItems) {
            Map inMap = [invoice: invoice, invoiceItem: invoiceItem]
            run service: 'loadSalesInvoiceItemFact', with: inMap
        }
    }
    return success()
}

Map loadSalesInvoiceItemFact() {
    GenericValue invoice = parameters.invoice
    GenericValue invoiceItem = parameters.invoiceItem
    invoice = invoice ?: from('Invoice').where(parameters).queryOne()
    if (UtilValidate.isEmpty(invoiceItem)) {
        invoiceItem = from('InvoiceItem').where(parameters).queryOne()
    }
    if (!invoice) {
        String errorMessage = UtilProperties.getMessage('AccountingUiLabels', 'AccountingInvoiceDoesNotExists', parameters.locale)
        logError(errorMessage)
        return error(errorMessage)
    }
    if (!invoiceItem) {
        String errorMessage = UtilProperties.getMessage('AccountingUiLabels', 'AccountingInvoiceItemDoesNotExists', parameters.locale)
        logError(errorMessage)
        return error(errorMessage)
    }

    if (invoice.invoiceTypeId == 'SALES_INVOICE') {
        GenericValue fact = from('SalesInvoiceItemFact')
            .where(invoiceId: invoiceItem.invoiceId, invoiceItemSeqId: invoiceItem.invoiceItemSeqId)
            .queryOne()
    // key handling
        if (!fact) {
            Map inMap
            Map naturalKeyFields
            Map serviceResult
            fact = makeValue('SalesInvoiceItemFact')
            fact.invoiceId = invoice.invoiceId
            fact.invoiceItemSeqId = invoiceItem.invoiceItemSeqId
            // conversion of the invoice date
            if (invoice.invoiceDate) {
                inMap = [:]
                naturalKeyFields = [:]
                inMap.dimensionEntityName = 'DateDimension'
                Date invoiceDate = new Date(invoice.invoiceDate.getTime())
                naturalKeyFields.dateValue = invoiceDate
                inMap.naturalKeyFields = naturalKeyFields
                serviceResult = run service: 'getDimensionIdFromNaturalKey', with: inMap
                fact.invoiceDateDimId = serviceResult.dimensionId
                fact.invoiceDateDimId = fact.invoiceDateDimId ?: '_NF_'
            } else {
                fact.invoiceDateDimId = '_NA_'
            }

            // conversion of the product id
            if (invoiceItem.productId) {
                inMap = [:]
                naturalKeyFields = [:]
                inMap.dimensionEntityName = 'ProductDimension'
                naturalKeyFields.productId = invoiceItem.productId
                inMap.naturalKeyFields = naturalKeyFields
                serviceResult = run service: 'getDimensionIdFromNaturalKey', with: inMap
                fact.productDimId = serviceResult.dimensionId
                fact.productDimId = fact.productDimId ?: '_NF_'
            } else {
                fact.productDimId = '_NA_'
            }

            // conversion of the invoice currency
            if (invoice.currencyUomId) {
                inMap = [:]
                naturalKeyFields = [:]
                inMap.dimensionEntityName = 'CurrencyDimension'
                naturalKeyFields.currencyId = invoice.currencyUomId
                inMap.naturalKeyFields = naturalKeyFields
                serviceResult = run service: 'getDimensionIdFromNaturalKey', with: inMap
                fact.origCurrencyDimId = serviceResult.dimensionId
                fact.origCurrencyDimId = fact.origCurrencyDimId ?: '_NF_'
            } else {
                fact.origCurrencyDimId = '_NA_'
            }

            // TODO
            fact.orderId = '_NA_'
            fact.billToCustomerDimId = '_NA_'
            fact.create()
        }
        /*
         * facts handling
         */
        fact.quantity = invoiceItem.quantity as BigDecimal
        fact.extGrossAmount = 0 as BigDecimal
        fact.extDiscountAmount = 0 as BigDecimal
        fact.extTaxAmount = 0 as BigDecimal
        fact.extNetAmount = 0 as BigDecimal

        if (invoiceItem.quantity && invoiceItem.amount) {
            fact.extGrossAmount = invoiceItem.quantity * invoiceItem.amount
        }

        // taxes
        List taxes = delegator.getRelated('ChildrenInvoiceItem', null, null, invoiceItem, false)
        for (GenericValue tax : taxes) {
            if (tax.amount) {
                fact.extTaxAmount = fact.extTaxAmount + tax.amount
            }
        }
        // discounts
        List discounts = delegator.getRelated('ChildrenInvoiceItem', null, null, invoiceItem, false)
        for (GenericValue discount : discounts) {
            if (discount.amount) {
                fact.extDiscountAmount = fact.extDiscountAmount - discount.amount
            }
        }
        fact.extNetAmount = fact.extGrossAmount - fact.extDiscountAmount
        // TODO: prorate invoice header discounts and shipping charges
        // TODO: costs
        fact.extManFixedCost = 0 as BigDecimal
        fact.extManVarCost = 0 as BigDecimal
        fact.extStorageCost = 0 as BigDecimal
        fact.extDistributionCost = 0 as BigDecimal

        BigDecimal costs = fact.extManFixedCost + fact.extManVarCost + fact.extStorageCost + fact.extDistributionCost
        fact.contributionAmount = fact.extNetAmount - costs

        fact.store()
    }
    return success()
}

Map loadSalesOrderFact() {
    GenericValue orderHeader = from('OrderHeader').where(parameters).queryOne()
    if (!orderHeader) {
        String errorMessage = UtilProperties.getMessage('OrderErrorUiLabels', 'OrderOrderIdDoesNotExists', parameters.locale)
        logError(errorMessage)
        return error(errorMessage)
    }
    if (orderHeader.orderTypeId == 'SALES_ORDER') {
        if (orderHeader.statusId == 'ORDER_APPROVED') {
            List orderItems = from('OrderItem')
                .where('orderId', orderHeader.orderId, 'orderItemTypeId', 'PRODUCT_ORDER_ITEM')
                .queryList()

            for (GenericValue orderItem : orderItems) {
                Map inMap = [:]
                inMap.orderHeader = orderHeader
                inMap.orderItem = orderItem
                inMap.orderAdjustment = null
                run service: 'loadSalesOrderItemFact', with: inMap
            }
        }
    }
    return success()
}

Map loadSalesOrderItemFact() {
    Map inMap
    Map naturalKeyFields
    Map serviceResult
    GenericValue orderHeader = parameters.orderHeader
    GenericValue orderItem = parameters.orderItem
    GenericValue orderAdjustment = parameters.orderAdjustment

    List orderAdjustments
    GenericValue orderStatus

    orderHeader ?: from('OrderHeader').where(parameters).queryOne()
    orderItem ?: this.from('OrderItem').where(parameters).queryOne()
    if (!orderAdjustment) {
        orderAdjustments = from('OrderAdjustment').where('orderId': orderItem.orderId).queryList()
    }
    if (!orderHeader) {
        String errorMessage = UtilProperties.getMessage('OrderErrorUiLabels', 'OrderOrderIdDoesNotExists', parameters.locale)
        logError(errorMessage)
        return error(errorMessage)
    }
    if (!orderItem) {
        String errorMessage = UtilProperties.getMessage('OrderErrorUiLabels', 'OrderOrderItemIdDoesNotExists', parameters.locale)
        logError(errorMessage)
        return error(errorMessage)
    }

    if (orderHeader.statusId == 'ORDER_APPROVED') {
        GenericValue fact = from('SalesOrderItemFact').where(orderId: orderItem.orderId, orderItemSeqId: orderItem.orderItemSeqId).queryOne()
        // key handling
        if (!fact) {
            fact = makeValue('SalesOrderItemFact')
            fact.orderId = orderHeader.orderId
            fact.orderItemSeqId = orderItem.orderItemSeqId
            fact.productStoreId = orderHeader.productStoreId
            fact.salesChannelEnumId = orderHeader.salesChannelEnumId
            fact.statusId = orderItem.statusId

            // account
            if (orderHeader.productStoreId) {
                GenericValue account =  from('ProductStore').where(productStoreId: orderHeader.productStoreId).queryOne()
                fact.account = account.storeName
            }

            // pod
            if (orderHeader.currencyUom == 'EUR') {
                fact.pod = 'Latin'
            } else {
                fact.pod = 'Engish'
            }

            // brand
            if (orderHeader.salesChannelEnumId) {
                GenericValue brand = from('Enumeration').where(enumId: orderHeader.salesChannelEnumId).queryOne()
                fact.brand = brand.description
            }

            // conversion of the order date
            orderStatus = from('OrderStatus')
                .where(orderId: orderHeader.orderId, statusId: 'ORDER_APPROVED')
                .orderBy('-statusDatetime')
                .queryFirst()
            if (orderStatus.statusDatetime) {
                inMap = [:]
                naturalKeyFields = [:]
                inMap.dimensionEntityName = 'DateDimension'
                Date statusDatetime = new Date(orderStatus.statusDatetime.getTime())
                naturalKeyFields.dateValue = statusDatetime
                inMap.naturalKeyFields = naturalKeyFields
                serviceResult = run service: 'getDimensionIdFromNaturalKey', with: inMap
                fact.orderDateDimId = serviceResult.dimensionId
                fact.orderDateDimId = fact.orderDateDimId ?: '_NF_'
            } else {
                fact.orderDateDimId = '_NA_'
            }

            // conversion of the product id
            if (UtilValidate.isNotEmpty(orderItem.productId)) {
                inMap = [:]
                naturalKeyFields = [:]
                inMap.dimensionEntityName = 'ProductDimension'
                naturalKeyFields.productId = orderItem.productId
                inMap.naturalKeyFields = naturalKeyFields
                serviceResult = run service: 'getDimensionIdFromNaturalKey', with: inMap
                fact.productDimId = serviceResult.dimensionId
                fact.orderDateDimId = fact.orderDateDimId ?: '_NF_'
            } else {
                fact.productDimId = '_NA_'
            }

            // conversion of the order currency
            if (orderHeader.currencyUom) {
                inMap = [:]
                naturalKeyFields = [:]
                inMap.dimensionEntityName = 'CurrencyDimension'
                naturalKeyFields.currencyId = orderHeader.currencyUom
                inMap.naturalKeyFields = naturalKeyFields
                serviceResult = run service: 'getDimensionIdFromNaturalKey', with: inMap
                fact.origCurrencyDimId = serviceResult.dimensionId
                fact.orderDateDimId = fact.orderDateDimId ?: '_NF_'
            } else {
                fact.origCurrencyDimId = '_NA_'
            }

            // productCategoryId
            GenericValue productCategoryMember = from('ProductCategoryMember').where(productId: orderItem.productId, thruDate: null).queryFirst()
            if (productCategoryMember) {
                fact.productCategoryId = productCategoryMember.productCategoryId
            }

            // TODO
            fact.billToCustomerDimId = '_NA_'

            fact.create()
        }
        /*
         * facts handling
         */
        Map partyAccountingPreferencesCallMap = [:]

        OrderReadHelper orderReadHelper = new OrderReadHelper(orderHeader)
        Map billFromParty = orderReadHelper.getBillFromParty()
        partyAccountingPreferencesCallMap.organizationPartyId = billFromParty.partyId
        Map accountResult = run service: 'getPartyAccountingPreferences', with: partyAccountingPreferencesCallMap
        GenericValue accPref = accountResult.partyAccountingPreference

        fact.with {
            quantity = orderItem.quantity as BigDecimal
            extGrossAmount = 0 as BigDecimal
            extGrossCost = 0 as BigDecimal
            extDiscountAmount = 0 as BigDecimal
            extNetAmount = 0 as BigDecimal
            extShippingAmount = 0 as BigDecimal
            extTaxAmount = 0 as BigDecimal

            GS = 0 as BigDecimal
            GMS = 0 as BigDecimal
            GMP = 0 as BigDecimal
            GSS = 0 as BigDecimal
            GSC = 0 as BigDecimal
            GSP = 0 as BigDecimal
            GP = 0 as BigDecimal
        }

        countOrder = 0 as BigDecimal

        // extGrossAmount
        Map convertUomCurrencyMap = [:]
        convertUomCurrencyMap.uomId = orderHeader.currencyUom
        convertUomCurrencyMap.uomIdTo = accPref.baseCurrencyUomId
        if (UtilValidate.isNotEmpty(orderStatus)) {
            convertUomCurrencyMap.nowDate = orderStatus.statusDatetime
        }
        Map convertResult = run service: 'convertUomCurrency', with: convertUomCurrencyMap
        BigDecimal exchangeRate = convertResult.conversionFactor

        if (exchangeRate) {
            BigDecimal unitPrice = orderItem.unitPrice * exchangeRate

            fact.extGrossAmount = fact.quantity * unitPrice
        }

        // extGrossCost
        GenericValue cost = from('SupplierProduct')
            .where(productId: orderItem.productId, availableThruDate: null, minimumOrderQuantity: 0 as BigDecimal)
            .queryFirst()
        if (cost) {
            convertUomCurrencyMap.uomId = cost.currencyUomId
            convertUomCurrencyMap.uomIdTo = accPref.baseCurrencyUomId
            if (orderStatus) {
                convertUomCurrencyMap.nowDate = orderStatus.statusDatetime
            }
            Map grossCostResult = run service: 'convertUomCurrency', with: convertUomCurrencyMap
            exchangeRate = grossCostResult.conversionFactor

            if (exchangeRate) {
                BigDecimal costPrice = cost.lastPrice * exchangeRate
                fact.extGrossCost = fact.quantity * costPrice
            }
        }

        // extShippingAmount
        for (GenericValue shipping : orderAdjustments) {
            if (shipping.orderAdjustmentTypeId == 'SHIPPING_CHARGES') {
                fact.extShippingAmount = fact.extShippingAmount + shipping.amount
            }
        }

        // extTaxAmount
        for (GenericValue tax : orderAdjustments) {
            if (tax.orderAdjustmentTypeId == 'SALES_TAX') {
                fact.extTaxAmount = fact.extTaxAmount + tax.amount
            }
        }

        // extDiscountAmount
        for (GenericValue discount : orderAdjustments) {
            if (discount.orderAdjustmentTypeId == 'PROMOTION_ADJUSTMENT') {
                fact.extDiscountAmount = fact.extDiscountAmount + discount.amount
                // product promo code
                GenericValue productPromoCode = from('ProductPromoCode').where(productPromoId: discount.productPromoId).queryFirst()
                if (productPromoCode) {
                    fact.productPromoCode = productPromoCode.productPromoCodeId
                } else {
                    fact.productPromoCode = 'Not require code'
                }
            }
        }

        // extNetAmount
        fact.extNetAmount = fact.extGrossAmount - fact.extDiscountAmount

        // GS
        BigDecimal countGS = 0 as BigDecimal
        List checkGSList = from('SalesOrderItemFact').where(orderId: orderHeader.orderId).queryList()
        for (GenericValue checkGS : checkGSList) {
            if (checkGS.GS) {
                if (0 != checkGS.GS) {
                    countGS = 1
                }
            }
        }
        if (countGS == 0) {
            convertUomCurrencyMap.uomId = orderHeader.currencyUom
            convertUomCurrencyMap.uomIdTo = accPref.baseCurrencyUomId
            if (orderStatus) {
                convertUomCurrencyMap.nowDate = orderStatus.statusDatetime
            }
            Map gsResult = run service: 'convertUomCurrency', with: convertUomCurrencyMap
            exchangeRate = gsResult.conversionFactor

            if (exchangeRate) {
                fact.GS = orderHeader.grandTotal * exchangeRate
            }
        }

        // GMS
        fact.GMS = fact.GMS + fact.extGrossAmount

        // GMP
        fact.GMP = fact.GMS - fact.extGrossCost

        // GSP
        BigDecimal countGSP = 0 as BigDecimal
        List checkGSPList = from('SalesOrderItemFact').where(orderId: orderHeader.orderId).queryList()
        for (GenericValue checkGSP : checkGSPList) {
            if (checkGSP.GSP) {
                if (checkGSP.GSP != 0) {
                    countGSP = 1
                }
            }
        }
        if (countGSP == 0) {
            BigDecimal warrantyPrice = 0 as BigDecimal
            for (GenericValue warranty : orderAdjustments) {
                if (warranty.orderAdjustmentTypeId == 'WARRANTY_ADJUSTMENT') {
                    warrantyPrice = warrantyPrice + warranty.amount
                }
            }
            BigDecimal gss = fact.extShippingAmount + warrantyPrice

            convertUomCurrencyMap.uomId = orderHeader.currencyUom
            convertUomCurrencyMap.uomIdTo = accPref.baseCurrencyUomId
            if (orderStatus) {
                convertUomCurrencyMap.nowDate = orderStatus.statusDatetime
            }
            Map gspResult = run service: 'convertUomCurrency', with: convertUomCurrencyMap
            exchangeRate = gspResult.conversionFactor

            if (exchangeRate) {
                gss = gss * exchangeRate
            }
            fact.GSS = gss
            fact.GSP = gss as BigDecimal
        }

        // GP
        fact.GP = fact.GMP + fact.GSP

        // countOrder
        BigDecimal countOrder = 0 as BigDecimal
        List checkCountOrderList = from('SalesOrderItemFact').where(orderId: orderHeader.orderId).queryList()
        for (GenericValue checkCountOrder : checkCountOrderList) {
            if (checkCountOrder.countOrder) {
                if (checkCountOrder.countOrder != 0) {
                    countOrder = 1
                }
            }
        }
        if (countOrder == 0) {
            fact.countOrder = 1 as BigDecimal
        }
        fact.store()
    }
    return success()
}

/**
 * Load Sales Order Data Daily
 */
Map loadSalesOrderDataDaily() {
    Timestamp nowTimestamp = UtilDateTime.nowTimestamp()
    Date nowDate = new Date(nowTimestamp.getTime())
    SimpleDateFormat sdf = new SimpleDateFormat('yyyy-MM-dd 07:00:00.000', parameters.locale)
    String today = sdf.format(nowDate)
    Date yesterdayDate = new Date(nowTimestamp.getTime() - 86400000)
    String yesterday = sdf.format(yesterdayDate)

    Map inMap = [:]
    inMap.fromDate = yesterday
    inMap.thruDate = today

    run service: 'importSalesOrderData', with: inMap
    return success()
}

/**
 * Import Sales Order Data
 */
Map importSalesOrderData() {
    Map inMap = [fromDate: parameters.fromDate, thruDate: parameters.thruDate]

    Map res = run service: 'loadDateDimension', with: inMap
    if (!ServiceUtil.isSuccess(res)) {
        return res
    }
    EntityCondition condition = EntityCondition.makeCondition(
        EntityCondition.makeCondition('statusId', 'ORDER_APPROVED'),
        EntityCondition.makeCondition('statusDatetime', EntityOperator.GREATER_THAN_EQUAL_TO, parameters.fromDate),
        EntityCondition.makeCondition('statusDatetime', EntityOperator.LESS_THAN, parameters.thruDate)
        )
    List orderStatusList = from('OrderStatus').where(condition).queryList()
    for (GenericValue orderHeader : orderStatusList) {
        inMap = [:]
        inMap.orderId = orderHeader.orderId
        res = run service: 'loadSalesOrderFact', with: inMap
        if (!ServiceUtil.isSuccess(res)) {
            return res
        }
    }
    return success()
}

/**
 * Convert Uom Currency from UomConversionDated entity
 */
Map convertUomCurrency() {
    if (!parameters.nowDate) {
        Timestamp now = UtilDateTime.nowTimestamp()
        parameters.nowDate = now
    }
    Map result = success()
    EntityCondition condition = EntityCondition.makeCondition(
        EntityCondition.makeCondition('uomId', parameters.uomId),
        EntityCondition.makeCondition('uomIdTo', parameters.uomIdTo),
        EntityCondition.makeCondition('fromDate', EntityOperator.LESS_THAN_EQUAL_TO, parameters.nowDate),
        EntityCondition.makeCondition('thruDate', EntityOperator.GREATER_THAN, parameters.nowDate)
        )
    GenericValue uomConversion = from('UomConversionDated').where(condition).orderBy('-fromDate').queryFirst()
    if (uomConversion) {
        result.conversionFactor = uomConversion.conversionFactor
    } else {
        GenericValue uomConversionLastest = from('UomConversionDated')
            .where(uomId: parameters.uomId, uomIdTo: parameters.uomIdTo, thruDate: null)
            .queryFirst()
        if (uomConversionLastest) {
            result.conversionFactor = uomConversionLastest.conversionFactor
        }
    }
    return result
}

Map loadInventoryFact() {
    GenericValue inventory = from('InventoryItem').where(inventoryItemId: parameters.inventoryItemId).queryOne()
    GenericValue fact = from('InventoryItemFact').where(inventoryItemId: parameters.inventoryItemId).queryOne()

    Map inMap = [:]
    Map naturalKeyFields = [:]
    Map serviceResult
    if (!fact) {
        fact = makeValue('InventoryItemFact')
        fact.inventoryItemId = inventory.inventoryItemId
        // conversion of the inventory date
        if (inventory?.createdStamp) {
            inMap = [:]
            naturalKeyFields = [:]
            inMap.dimensionEntityName = 'DateDimension'
            Date createdStampDatetime = new Date(inventory.createdStamp.getTime())
            naturalKeyFields.dateValue = createdStampDatetime
            inMap.naturalKeyFields = naturalKeyFields
            serviceResult = run service: 'getDimensionIdFromNaturalKey', with: inMap
            fact.inventoryDateDimId = serviceResult.dimensionId
            fact.inventoryDateDimId = fact.inventoryDateDimId ?: '_NF_'
        } else {
            fact.inventoryDateDimId = '_NA_'
        }
        // conversion of the productId
        if (inventory?.productId) {
            inMap = [:]
            naturalKeyFields = [:]
            inMap.dimensionEntityName = 'ProductDimension'
            naturalKeyFields.productId = inventory.productId
            inMap.naturalKeyFields = naturalKeyFields
            serviceResult = run service: 'getDimensionIdFromNaturalKey', with: inMap
            fact.productDimId = serviceResult.dimensionId
            fact.productDimId = fact.productDimId ?: '_NF_'
        } else {
            fact.productDimId = '_NA_'
        }
        // conversion of the order currency
        if (inventory?.currencyUomId) {
            inMap = [:]
            naturalKeyFields = [:]
            inMap.dimensionEntityName = 'CurrencyDimension'
            naturalKeyFields.currencyId = inventory.currencyUomId
            inMap.naturalKeyFields = naturalKeyFields
            serviceResult = run service: 'getDimensionIdFromNaturalKey', with: inMap
            fact.origCurrencyDimId = serviceResult.dimensionId
            fact.origCurrencyDimId = fact.origCurrencyDimId ?: '_NF_'
        } else {
            fact.origCurrencyDimId = '_NA_'
        }
        fact.create()
    }

    fact.facilityId = inventory.facilityId
    fact.inventoryItemId = inventory.inventoryItemId
    fact.quantityOnHandTotal = inventory.quantityOnHandTotal
    fact.availableToPromiseTotal = inventory.availableToPromiseTotal
    fact.unitCost = inventory.unitCost

    // calculate sold out amount
    fact.soldoutAmount = inventory.quantityOnHandTotal - inventory.availableToPromiseTotal

    fact.store()
    return success()
}
