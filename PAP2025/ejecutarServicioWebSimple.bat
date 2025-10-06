@echo off
REM Script simple para ejecutar el servicio web de Biblioteca Comunitaria PAP2025
REM Este script ejecuta directamente el servicio web usando Java 17

echo 🚀 Iniciando Servicio Web - Biblioteca Comunitaria PAP2025
echo 📋 Usando Java 17 desde: C:\Program Files\Java\jdk-17

REM Definir la ruta de Java 17
set JAVA_HOME=C:\Program Files\Java\jdk-17
set JAVA_EXE=%JAVA_HOME%\bin\java.exe
set JAVAC_EXE=%JAVA_HOME%\bin\javac.exe

REM Verificar que Java 17 existe
if not exist "%JAVA_EXE%" (
    echo ❌ Error: No se encontró Java 17 en %JAVA_HOME%
    echo    Verifica que Java 17 esté instalado correctamente
    pause
    exit /b 1
)

REM Verificar que estamos en el directorio correcto
if not exist "app\src\main\java\pap2025\Publicadores\PublicadorServicioWeb.java" (
    echo ❌ Error: No se encontró el archivo PublicadorServicioWeb.java
    echo    Ejecuta este script desde el directorio PAP2025\PAP2025
    pause
    exit /b 1
)

echo 📦 Compilando archivos Java con Java 17...

REM Crear directorio de clases si no existe
if not exist "app\build\classes" mkdir "app\build\classes"

REM Compilar archivos Java directamente con Java 17
"%JAVAC_EXE%" -cp "." -d "app\build\classes" app\src\main\java\pap2025\datatypes\*.java
"%JAVAC_EXE%" -cp "." -d "app\build\classes" app\src\main\java\pap2025\logica\*.java
"%JAVAC_EXE%" -cp "." -d "app\build\classes" app\src\main\java\pap2025\interfaz\*.java
"%JAVAC_EXE%" -cp "." -d "app\build\classes" app\src\main\java\pap2025\fabrica\*.java
"%JAVAC_EXE%" -cp "." -d "app\build\classes" app\src\main\java\pap2025\persistencia\*.java
"%JAVAC_EXE%" -cp "." -d "app\build\classes" app\src\main\java\pap2025\Publicadores\*.java

if %ERRORLEVEL% neq 0 (
    echo ❌ Error en la compilación
    echo    Verifica que todas las dependencias estén disponibles
    pause
    exit /b 1
)

echo ✅ Compilación exitosa con Java 17

REM Ejecutar el servicio web con Java 17
echo 🌐 Iniciando servicio web con Java 17...
echo    El servicio estará disponible en: http://localhost:8080/bibliotecaWS
echo    Para detener el servicio, presiona Ctrl+C
echo.

"%JAVA_EXE%" -cp "app\build\classes" pap2025.Publicadores.PublicadorServicioWeb

pause
