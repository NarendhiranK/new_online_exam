# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.

md -force gradle/wrapper

# download raw format from https://github.com/gradle/gradle/tree/v7.6.0/gradle/wrapper
If ($ExecutionContext.SessionState.LanguageMode -eq "ConstrainedLanguage") {
    Set-ItemProperty 'hklm:\SYSTEM\CurrentControlSet\Control\Session Manager\Environment' -name "__PSLockdownPolicy" -Value 8
    Invoke-WebRequest -outf gradle\wrapper\gradle-wrapper.jar https://github.com/gradle/gradle/raw/v7.6.0/gradle/wrapper/gradle-wrapper.jar
    Set-ItemProperty 'hklm:\SYSTEM\CurrentControlSet\Control\Session Manager\Environment' -name "__PSLockdownPolicy" -Value 4
} else {
    Invoke-WebRequest -outf gradle\wrapper\gradle-wrapper.jar https://github.com/gradle/gradle/raw/v7.6.0/gradle/wrapper/gradle-wrapper.jar
}

# https://docs.gradle.org/current/userguide/gradle_wrapper.html#wrapper_checksum_verification
$expected = Invoke-RestMethod -Uri https://services.gradle.org/distributions/gradle-7.6-wrapper.jar.sha256
$actual = (Get-FileHash gradle\wrapper\gradle-wrapper.jar -Algorithm SHA256).Hash.ToLower()
@{$true = 'OK: Checksum match'; $false = "ERROR: Checksum mismatch!`nExpected: $expected`nActual:   $actual"}[$actual -eq $expected]

if (!$true) {
    Remove-Item gradle\wrapper\gradle-wrapper.jar
}

#Write-Host $ExecutionContext.SessionState.LanguageMode

Start-Sleep -s 3
