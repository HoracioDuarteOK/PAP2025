#!/bin/bash

# Script para ejecutar el servidor web
export PATH="/opt/homebrew/opt/openjdk@11/bin:$PATH"

echo "🚀 Iniciando servidor web..."
echo "📁 Directorio actual: $(pwd)"
echo "☕ Java version: $(java -version 2>&1 | head -1)"

cd /Users/lucasalpuin/Desktop/PAP2025-main/PAP2025

echo "🔧 Ejecutando gradlew webServer..."
./gradlew webServer
