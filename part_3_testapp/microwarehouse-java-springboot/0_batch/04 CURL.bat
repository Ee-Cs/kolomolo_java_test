@echo on
@set SITE=http://localhost:8081
@set CURL=c:\tools\curl-7.58.0\bin\curl.exe
@set CURL=%CURL% -g -i -H "Accept: application/json" -H "Content-Type: application/json"
@set HR_YELLOW=@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
@set HR_RED=@powershell    -Command Write-Host "----------------------------------------------------------------------" -foreground "Red"

:present_spark_product
%HR_YELLOW%
@powershell -Command Write-Host "PRESENT SPARK PRODUCT" -foreground "Green"
%CURL% -X GET %SITE%/products
@echo.

:finish
%HR_RED%
@powershell -Command Write-Host "FINISH" -foreground "Red"
pause