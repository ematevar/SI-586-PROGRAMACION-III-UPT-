@echo off
cd /d "%~dp0frontend"
call npm ci
if errorlevel 1 exit /b 1
call npm run build
if errorlevel 1 exit /b 1
xcopy /E /I /Y dist\portal\browser ..\src\main\resources\static
cd ..
call mvnw.cmd clean package
if errorlevel 1 exit /b 1
copy /Y target\DocumentosHexagonal-1.0.0.jar caso3-documentos.jar
pause
