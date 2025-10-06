@echo off
REM Script de despliegue para PAP2025 Web Client - Biblioteca Comunitaria
REM Este script automatiza el proceso de despliegue en Tomcat (Windows)

echo 🚀 Iniciando despliegue de PAP2025 Web Client...

REM Variables de configuración
set TOMCAT_HOME=C:\apache-tomcat-9.0.xx
set APP_NAME=bibliotecaWeb
set WAR_NAME=pap2025WebClientWS.war

REM Verificar que Tomcat esté instalado
if not exist "%TOMCAT_HOME%" (
    echo ❌ Error: Tomcat no encontrado en %TOMCAT_HOME%
    echo    Por favor, actualiza la variable TOMCAT_HOME en este script
    pause
    exit /b 1
)

REM Verificar que el directorio del proyecto existe
if not exist "WebContent" (
    echo ❌ Error: No se encontró el directorio WebContent
    echo    Ejecuta este script desde la raíz del proyecto pap2025WebClientWS
    pause
    exit /b 1
)

echo 📦 Preparando archivos para despliegue...

REM Crear directorio temporal para el WAR
set TEMP_DIR=%TEMP%\%APP_NAME%_deploy
if exist "%TEMP_DIR%" rmdir /s /q "%TEMP_DIR%"
mkdir "%TEMP_DIR%"

REM Copiar contenido de WebContent al directorio temporal
xcopy /E /I /Y WebContent "%TEMP_DIR%"

REM Copiar archivos compilados (si existen)
if exist "build\classes" (
    mkdir "%TEMP_DIR%\WEB-INF\classes" 2>nul
    xcopy /E /I /Y "build\classes" "%TEMP_DIR%\WEB-INF\classes\"
    echo ✅ Archivos compilados copiados
)

REM Crear archivo WAR
echo 📦 Creando archivo WAR...
cd /d "%TEMP_DIR%"
jar -cf "%WAR_NAME%" *
cd /d "%~dp0"

REM Detener Tomcat
echo 🛑 Deteniendo Tomcat...
call "%TOMCAT_HOME%\bin\shutdown.bat" 2>nul || echo    Tomcat no estaba ejecutándose

REM Esperar a que se detenga completamente
timeout /t 5 /nobreak >nul

REM Limpiar aplicación anterior
echo 🧹 Limpiando aplicación anterior...
if exist "%TOMCAT_HOME%\webapps\%APP_NAME%" rmdir /s /q "%TOMCAT_HOME%\webapps\%APP_NAME%"
if exist "%TOMCAT_HOME%\webapps\%APP_NAME%.war" del "%TOMCAT_HOME%\webapps\%APP_NAME%.war"

REM Copiar nuevo WAR
echo 📤 Desplegando nueva versión...
copy "%TEMP_DIR%\%WAR_NAME%" "%TOMCAT_HOME%\webapps\"

REM Iniciar Tomcat
echo 🚀 Iniciando Tomcat...
call "%TOMCAT_HOME%\bin\startup.bat"

REM Limpiar archivos temporales
rmdir /s /q "%TEMP_DIR%"

REM Esperar a que la aplicación se despliegue
echo ⏳ Esperando a que la aplicación se despliegue...
timeout /t 10 /nobreak >nul

echo ✅ ¡Despliegue completado!
echo 🌐 La aplicación debería estar disponible en: http://localhost:8080/%APP_NAME%
echo.
echo 📋 Información del despliegue:
echo    - Aplicación: %APP_NAME%
echo    - URL: http://localhost:8080/%APP_NAME%
echo    - Logs: %TOMCAT_HOME%\logs\
echo    - Directorio: %TOMCAT_HOME%\webapps\%APP_NAME%
echo.
echo 🎯 Para verificar que todo funciona:
echo    1. Abre http://localhost:8080/%APP_NAME% en tu navegador
echo    2. Verifica que puedes registrar usuarios y hacer login
echo    3. Revisa los logs si hay algún problema
echo.
pause
