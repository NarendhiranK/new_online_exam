<div class="screenlet-body">
  <form id="createOfbizDemoEvent" method="post" action="<@ofbizUrl>createOfbizDemoEvent</@ofbizUrl>">
    <input type="hidden" name="addOfbizDemoFromFtl" value="Y"/>
    <fieldset>
      <div>
        <span class="label">${uiLabelMap.OfbizDemoType}</span>
        <select name="ofbizDemoTypeId" class='required'>
          <#list ofbizDemoTypes as demoType>
            <option value='${demoType.ofbizDemoTypeId}'>${demoType.description}</option>
          </#list>
        </select>*
      </div>
      <div>
        <span class="label">${uiLabelMap.OfbizDemoFirstName}</span>
        <input type="text" name="firstName" id="firstName" class='required' maxlength="20" />*
      </div>
      <div>
        <span class="label">${uiLabelMap.OfbizDemoLastName}</span>
        <input type="text" name="lastName" id="lastName" class='required' maxlength="20" />*
      </div>
      <div>
        <span class="label">${uiLabelMap.OfbizDemoComment}</span>
        <input type="text" name="comments" id="comments" class='inputbox' size="60" maxlength="255" />
      </div>
    </fieldset>
    <input type="submit" value="${uiLabelMap.CommonAdd}" />
  </form>
</div>