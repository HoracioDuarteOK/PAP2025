#!/bin/bash

# Script para ejecutar el servicio web de Biblioteca Comunitaria PAP2025
# Este script compila y ejecuta el publicador del servicio web

echo "🚀 Iniciando Servicio Web - Biblioteca Comunitaria PAP2025"

# Verificar que estamos en el directorio correcto
if [ ! -f "app/build.gradle" ]; then
    echo "❌ Error: No se encontró el archivo build.gradle"
    echo "   Ejecuta este script desde el directorio PAP2025/PAP2025"
    exit 1
fi

# Compilar el proyecto
echo "📦 Compilando el proyecto..."
./gradlew build

if [ $? -ne 0 ]; then
    echo "❌ Error en la compilación"
    exit 1
fi

echo "✅ Compilación exitosa"

# Ejecutar el servicio web
echo "🌐 Iniciando servicio web..."
echo "   El servicio estará disponible en: http://localhost:8080/bibliotecaWS"
echo "   Para detener el servicio, presiona Ctrl+C"
echo ""

java -cp "app/build/classes/java/main:app/build/resources/main" pap2025.Publicadores.PublicadorServicioWeb
