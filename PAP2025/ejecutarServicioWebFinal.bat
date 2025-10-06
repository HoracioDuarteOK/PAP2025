@echo off
REM Script para ejecutar el servicio web simple de Biblioteca Comunitaria

echo === SERVICIO WEB BIBLIOTECA COMUNITARIA PAP2025 ===
echo Usando Java 17 desde: C:\Program Files\Java\jdk-17
echo.

REM Definir la ruta de Java 17
set JAVA_HOME=C:\Program Files\Java\jdk-17
set JAVA_EXE=%JAVA_HOME%\bin\java.exe
set JAVAC_EXE=%JAVA_HOME%\bin\javac.exe

REM Verificar que Java 17 existe
if not exist "%JAVA_EXE%" (
    echo Error: No se encontro Java 17 en %JAVA_HOME%
    pause
    exit /b 1
)

REM Crear directorio de clases si no existe
if not exist "app\build\classes" mkdir "app\build\classes"

echo Compilando servicio web simple...

REM Compilar solo las clases del servicio web simple
"%JAVAC_EXE%" -cp "." -d "app\build\classes" app\src\main\java\pap2025\Publicadores\ServicioWebSimple.java
if %ERRORLEVEL% neq 0 (
    echo Error compilando ServicioWebSimple
    pause
    exit /b 1
)

"%JAVAC_EXE%" -cp "app\build\classes" -d "app\build\classes" app\src\main\java\pap2025\Publicadores\EjecutarServicioWeb.java
if %ERRORLEVEL% neq 0 (
    echo Error compilando EjecutarServicioWeb
    pause
    exit /b 1
)

echo Compilacion exitosa!
echo.
echo Iniciando servicio web...
echo El servicio estara disponible en: http://localhost:8080/bibliotecaWS
echo Para detener el servicio, presiona Ctrl+C
echo.

REM Ejecutar el servicio web
"%JAVA_EXE%" -cp "app\build\classes" pap2025.Publicadores.EjecutarServicioWeb

pause
