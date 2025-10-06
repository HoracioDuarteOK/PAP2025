@echo off
REM Script para ejecutar el servicio web de Biblioteca Comunitaria PAP2025 (Windows)
REM Este script compila y ejecuta el publicador del servicio web

echo 🚀 Iniciando Servicio Web - Biblioteca Comunitaria PAP2025

REM Verificar que estamos en el directorio correcto
if not exist "app\build.gradle" (
    echo ❌ Error: No se encontró el archivo build.gradle
    echo    Ejecuta este script desde el directorio PAP2025\PAP2025
    pause
    exit /b 1
)

REM Compilar el proyecto
echo 📦 Compilando el proyecto...
call gradlew build

if %ERRORLEVEL% neq 0 (
    echo ❌ Error en la compilación
    pause
    exit /b 1
)

echo ✅ Compilación exitosa

REM Ejecutar el servicio web
echo 🌐 Iniciando servicio web...
echo    El servicio estará disponible en: http://localhost:8080/bibliotecaWS
echo    Para detener el servicio, presiona Ctrl+C
echo.

java -cp "app\build\classes\java\main;app\build\resources\main" pap2025.Publicadores.PublicadorServicioWeb

pause
