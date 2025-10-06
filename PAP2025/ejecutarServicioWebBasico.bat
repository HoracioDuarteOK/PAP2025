@echo off
REM Script simplificado para compilar solo las clases del servicio web

echo 🚀 Compilando Servicio Web - Biblioteca Comunitaria PAP2025
echo 📋 Usando Java 17 desde: C:\Program Files\Java\jdk-17

REM Definir la ruta de Java 17
set JAVA_HOME=C:\Program Files\Java\jdk-17
set JAVA_EXE=%JAVA_HOME%\bin\java.exe
set JAVAC_EXE=%JAVA_HOME%\bin\javac.exe

REM Crear directorio de clases si no existe
if not exist "app\build\classes" mkdir "app\build\classes"

echo 📦 Compilando solo las clases del servicio web...

REM Compilar solo las clases básicas sin JPA
"%JAVAC_EXE%" -cp "." -d "app\build\classes" app\src\main\java\pap2025\datatypes\DTLector.java
"%JAVAC_EXE%" -cp "." -d "app\build\classes" app\src\main\java\pap2025\datatypes\DTBibliotecario.java
"%JAVAC_EXE%" -cp "." -d "app\build\classes" app\src\main\java\pap2025\datatypes\DTMaterial.java
"%JAVAC_EXE%" -cp "." -d "app\build\classes" app\src\main\java\pap2025\datatypes\DTPrestamo.java

if %ERRORLEVEL% neq 0 (
    echo ❌ Error compilando DTOs básicos
    pause
    exit /b 1
)

echo ✅ DTOs básicos compilados exitosamente

REM Crear una versión simplificada del servicio web
echo 📝 Creando servicio web simplificado...

echo package pap2025.Publicadores; > app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo. >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo import javax.jws.WebMethod; >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo import javax.jws.WebService; >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo import javax.jws.soap.SOAPBinding; >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo import javax.jws.soap.SOAPBinding.ParameterStyle; >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo import javax.jws.soap.SOAPBinding.Style; >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo import javax.xml.ws.Endpoint; >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo. >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo @WebService >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo @SOAPBinding(style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED) >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo public class ServicioWebSimple { >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo. >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo     private Endpoint endpoint; >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo. >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo     public void publicar() { >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo         endpoint = Endpoint.publish("http://localhost:8080/bibliotecaWS", this); >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo         System.out.println("Servicio web publicado en: http://localhost:8080/bibliotecaWS"); >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo     } >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo. >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo     @WebMethod >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo     public String saludar(String nombre) { >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo         return "Hola " + nombre + " desde el servicio web de Biblioteca Comunitaria!"; >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo     } >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo. >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo     @WebMethod >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo     public boolean testServicio() { >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo         return true; >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo     } >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java
echo } >> app\build\classes\pap2025\Publicadores\ServicioWebSimple.java

REM Compilar el servicio simplificado
"%JAVAC_EXE%" -cp "." -d "app\build\classes" app\build\classes\pap2025\Publicadores\ServicioWebSimple.java

if %ERRORLEVEL% neq 0 (
    echo ❌ Error compilando servicio simplificado
    pause
    exit /b 1
)

echo ✅ Servicio web simplificado compilado exitosamente

REM Ejecutar el servicio web simplificado
echo 🌐 Iniciando servicio web simplificado...
echo    El servicio estará disponible en: http://localhost:8080/bibliotecaWS
echo    Para detener el servicio, presiona Ctrl+C
echo.

"%JAVA_EXE%" -cp "app\build\classes" pap2025.Publicadores.ServicioWebSimple

pause
