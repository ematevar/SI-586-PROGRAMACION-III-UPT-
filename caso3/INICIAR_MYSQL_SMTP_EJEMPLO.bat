@echo off
cd /d "%~dp0"
set APP_PROFILE=mysql
set DB_USER=root
set DB_PASSWORD=
set MAIL_MODE=smtp
set SMTP_HOST=smtp.gmail.com
set SMTP_PORT=587
set SMTP_USER=TU_CORREO@gmail.com
set SMTP_PASSWORD=TU_CLAVE_DE_APLICACION
set SMTP_AUTH=true
set SMTP_TLS=true
set MAIL_FROM=TU_CORREO@gmail.com
set PUBLIC_URL=http://localhost:8080
set ADMIN_PASSWORD=CAMBIA_ESTA_CLAVE
java -jar caso3-documentos.jar
pause
