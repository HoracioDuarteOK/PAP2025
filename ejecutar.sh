#!/bin/bash

echo "Compilando proyecto..."
./gradlew compileJava

if [ $? -eq 0 ]; then
    echo "Compilación exitosa! Ejecutando aplicación..."
    cd app/build/classes/java/main
    java pap2025.presentacion.VentanaPrincipal
else
    echo "Error en la compilación. Revisar errores."
fi
