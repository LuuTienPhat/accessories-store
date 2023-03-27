@echo off
setlocal EnableExtensions EnableDelayedExpansion
set "Counter=1"
for /F "delims=" %%I in ('dir "C:\Users\Phat\Documents\eclipse-workspace\spring-security-jwt\uploads\products\*" /AD /B /ON 2^>nul') do ren "C:\Users\Phat\Documents\eclipse-workspace\spring-security-jwt\uploads\products\%%I" "!Counter!" & set /A Counter+=1
endlocal
pause