#!/bin/bash

# Script de despliegue para PAP2025 Web Client - Biblioteca Comunitaria
# Este script automatiza el proceso de despliegue en Tomcat

echo "🚀 Iniciando despliegue de PAP2025 Web Client..."

# Variables de configuración
TOMCAT_HOME="/opt/tomcat"  # Cambiar por la ruta de tu Tomcat
APP_NAME="bibliotecaWeb"
WAR_NAME="pap2025WebClientWS.war"

# Verificar que Tomcat esté instalado
if [ ! -d "$TOMCAT_HOME" ]; then
    echo "❌ Error: Tomcat no encontrado en $TOMCAT_HOME"
    echo "   Por favor, actualiza la variable TOMCAT_HOME en este script"
    exit 1
fi

# Verificar que el directorio del proyecto existe
if [ ! -d "WebContent" ]; then
    echo "❌ Error: No se encontró el directorio WebContent"
    echo "   Ejecuta este script desde la raíz del proyecto pap2025WebClientWS"
    exit 1
fi

echo "📦 Preparando archivos para despliegue..."

# Crear directorio temporal para el WAR
TEMP_DIR="/tmp/${APP_NAME}_deploy"
rm -rf "$TEMP_DIR"
mkdir -p "$TEMP_DIR"

# Copiar contenido de WebContent al directorio temporal
cp -r WebContent/* "$TEMP_DIR/"

# Copiar archivos compilados (si existen)
if [ -d "build/classes" ]; then
    mkdir -p "$TEMP_DIR/WEB-INF/classes"
    cp -r build/classes/* "$TEMP_DIR/WEB-INF/classes/"
    echo "✅ Archivos compilados copiados"
fi

# Crear archivo WAR
echo "📦 Creando archivo WAR..."
cd "$TEMP_DIR"
jar -cf "${WAR_NAME}" *
cd - > /dev/null

# Detener Tomcat
echo "🛑 Deteniendo Tomcat..."
"$TOMCAT_HOME/bin/shutdown.sh" 2>/dev/null || echo "   Tomcat no estaba ejecutándose"

# Esperar a que se detenga completamente
sleep 5

# Limpiar aplicación anterior
echo "🧹 Limpiando aplicación anterior..."
rm -rf "$TOMCAT_HOME/webapps/$APP_NAME"
rm -f "$TOMCAT_HOME/webapps/${APP_NAME}.war"

# Copiar nuevo WAR
echo "📤 Desplegando nueva versión..."
cp "$TEMP_DIR/${WAR_NAME}" "$TOMCAT_HOME/webapps/"

# Iniciar Tomcat
echo "🚀 Iniciando Tomcat..."
"$TOMCAT_HOME/bin/startup.sh"

# Limpiar archivos temporales
rm -rf "$TEMP_DIR"

# Esperar a que la aplicación se despliegue
echo "⏳ Esperando a que la aplicación se despliegue..."
sleep 10

# Verificar que la aplicación esté funcionando
echo "🔍 Verificando despliegue..."
if curl -s -o /dev/null -w "%{http_code}" "http://localhost:8080/$APP_NAME" | grep -q "200"; then
    echo "✅ ¡Despliegue exitoso!"
    echo "🌐 La aplicación está disponible en: http://localhost:8080/$APP_NAME"
else
    echo "⚠️  La aplicación se desplegó pero puede no estar completamente lista"
    echo "🌐 Intenta acceder a: http://localhost:8080/$APP_NAME"
    echo "📋 Revisa los logs de Tomcat en: $TOMCAT_HOME/logs/"
fi

echo ""
echo "📋 Información del despliegue:"
echo "   - Aplicación: $APP_NAME"
echo "   - URL: http://localhost:8080/$APP_NAME"
echo "   - Logs: $TOMCAT_HOME/logs/"
echo "   - Directorio: $TOMCAT_HOME/webapps/$APP_NAME"
echo ""
echo "🎯 Para verificar que todo funciona:"
echo "   1. Abre http://localhost:8080/$APP_NAME en tu navegador"
echo "   2. Verifica que puedes registrar usuarios y hacer login"
echo "   3. Revisa los logs si hay algún problema"
