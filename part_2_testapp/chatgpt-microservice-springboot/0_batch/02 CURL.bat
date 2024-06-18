@echo on
@set SITE=http://localhost:8500/api/v1
@set FILE=
@set CURL=c:\tools\curl-7.58.0\bin\curl.exe
@set CURL=%CURL% -g -i -H "Accept: application/json"
@set HR_YELLOW=@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
@set HR_RED=@powershell    -Command Write-Host "----------------------------------------------------------------------" -foreground "Red"

:chat
%HR_YELLOW%
@powershell -Command Write-Host "POST chat completion" -foreground "Green"
%CURL% -H "Content-Type: application/json" -X POST ^
    -d "{\"question\":\"Say this is a test!\"}" "%SITE%/chat"
@echo.

:transcription
%HR_YELLOW%
@powershell -Command Write-Host "POST transcription" -foreground "Green"
%CURL% -H "Content-Type: multipart/form-data" -X POST ^
-F "file=@sample.mp3;type=audio/mpeg3" ^
"%SITE%/transcription"
@echo.

:finish
%HR_RED%
@powershell -Command Write-Host "FINISH" -foreground "Red"
pause