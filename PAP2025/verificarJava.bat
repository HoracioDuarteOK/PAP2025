@echo off
REM Script para verificar la versión de Java y guiar la instalación

echo 🔍 Verificando versión de Java instalada...
echo.

java -version 2>&1 | findstr "version"
if %ERRORLEVEL% neq 0 (
    echo ❌ Java no está instalado o no está en el PATH
    echo.
    echo 📋 Pasos para instalar Java 17:
    echo    1. Desinstalar Java 24 desde Panel de Control
    echo    2. Descargar Java 17 desde: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
    echo    3. Instalar Java 17
    echo    4. Configurar JAVA_HOME y PATH
    echo.
    pause
    exit /b 1
)

echo.
echo ✅ Java está instalado
echo.
echo 📋 Si ves Java 24, necesitas:
echo    1. Desinstalar Java 24 desde Panel de Control
echo    2. Descargar Java 17 desde Oracle o OpenJDK
echo    3. Instalar Java 17
echo    4. Reiniciar la terminal
echo.
echo 🌐 Enlaces útiles:
echo    - Oracle JDK 17: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
echo    - OpenJDK 17: https://adoptium.net/temurin/releases/?version=17
echo.

pause
