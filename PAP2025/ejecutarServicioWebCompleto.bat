@echo off
REM Script mejorado para ejecutar el servicio web con todas las dependencias

echo 🚀 Iniciando Servicio Web - Biblioteca Comunitaria PAP2025
echo 📋 Usando Java 17 desde: C:\Program Files\Java\jdk-17

REM Definir la ruta de Java 17
set JAVA_HOME=C:\Program Files\Java\jdk-17
set JAVA_EXE=%JAVA_HOME%\bin\java.exe
set JAVAC_EXE=%JAVA_HOME%\bin\javac.exe

REM Verificar que Java 17 existe
if not exist "%JAVA_EXE%" (
    echo ❌ Error: No se encontró Java 17 en %JAVA_HOME%
    pause
    exit /b 1
)

REM Crear directorio para librerías si no existe
if not exist "lib" mkdir "lib"

REM Descargar dependencias JPA si no existen
if not exist "lib\javax.persistence-api-2.2.jar" (
    echo 📦 Descargando dependencias JPA...
    echo    Esto puede tomar unos minutos...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/javax/persistence/javax.persistence-api/2.2/javax.persistence-api-2.2.jar' -OutFile 'lib\javax.persistence-api-2.2.jar'"
)

REM Crear directorio de clases si no existe
if not exist "app\build\classes" mkdir "app\build\classes"

echo 📦 Compilando archivos Java con Java 17 y dependencias JPA...

REM Compilar con dependencias JPA
"%JAVAC_EXE%" -cp "lib\javax.persistence-api-2.2.jar;." -d "app\build\classes" app\src\main\java\pap2025\datatypes\*.java
if %ERRORLEVEL% neq 0 (
    echo ❌ Error compilando datatypes
    pause
    exit /b 1
)

"%JAVAC_EXE%" -cp "lib\javax.persistence-api-2.2.jar;app\build\classes" -d "app\build\classes" app\src\main\java\pap2025\logica\*.java
if %ERRORLEVEL% neq 0 (
    echo ❌ Error compilando logica
    pause
    exit /b 1
)

"%JAVAC_EXE%" -cp "lib\javax.persistence-api-2.2.jar;app\build\classes" -d "app\build\classes" app\src\main\java\pap2025\interfaz\*.java
if %ERRORLEVEL% neq 0 (
    echo ❌ Error compilando interfaz
    pause
    exit /b 1
)

"%JAVAC_EXE%" -cp "lib\javax.persistence-api-2.2.jar;app\build\classes" -d "app\build\classes" app\src\main\java\pap2025\fabrica\*.java
if %ERRORLEVEL% neq 0 (
    echo ❌ Error compilando fabrica
    pause
    exit /b 1
)

"%JAVAC_EXE%" -cp "lib\javax.persistence-api-2.2.jar;app\build\classes" -d "app\build\classes" app\src\main\java\pap2025\persistencia\*.java
if %ERRORLEVEL% neq 0 (
    echo ❌ Error compilando persistencia
    pause
    exit /b 1
)

"%JAVAC_EXE%" -cp "lib\javax.persistence-api-2.2.jar;app\build\classes" -d "app\build\classes" app\src\main\java\pap2025\Publicadores\*.java
if %ERRORLEVEL% neq 0 (
    echo ❌ Error compilando publicadores
    pause
    exit /b 1
)

echo ✅ Compilación exitosa con Java 17

REM Ejecutar el servicio web con Java 17
echo 🌐 Iniciando servicio web con Java 17...
echo    El servicio estará disponible en: http://localhost:8080/bibliotecaWS
echo    Para detener el servicio, presiona Ctrl+C
echo.

"%JAVA_EXE%" -cp "lib\javax.persistence-api-2.2.jar;app\build\classes" pap2025.Publicadores.PublicadorServicioWeb

pause
