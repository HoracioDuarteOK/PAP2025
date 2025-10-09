# 📡 TAREAS SOAP - Biblioteca Comunitaria

## 🎯 Descripción de las Tareas

Tu profesor pide **DOS tareas relacionadas con SOAP Web Services**:

---

## ✅ **TAREA 1: Mantener Servidor SOAP Levantado**

### Objetivo:
Tener un **servicio SOAP funcionando** que:
- Exponga operaciones vía SOAP
- Genere un WSDL automáticamente
- Pueda ser consumido por clientes externos

### Implementación:

**Archivos:**
- `IBibliotecaWebService.java` - Interfaz del servicio (contrato)
- `BibliotecaWebService.java` - Implementación del servicio
- `TomcatEmbeddedServer.java` - Publicación del endpoint

### Cómo ejecutar:

```bash
./gradlew app:completeWebServer
```

### Resultado:
```
✅ Aplicación Web: http://localhost:8080
✅ Servicio SOAP: http://localhost:9090/soap/biblioteca
✅ WSDL: http://localhost:9090/soap/biblioteca?wsdl
```

### Operaciones Disponibles:
1. `obtenerTotalMateriales()` - Devuelve cantidad de materiales
2. `obtenerTotalLibros()` - Devuelve cantidad de libros
3. `obtenerTotalPrestamos()` - Devuelve cantidad de préstamos
4. `obtenerTotalLectores()` - Devuelve cantidad de lectores
5. `obtenerTitulosLibros()` - Devuelve array con títulos de libros
6. `existeUsuario(email)` - Verifica si un usuario existe
7. `obtenerNombreUsuario(email)` - Devuelve nombre de un usuario
8. `obtenerEstadisticasTexto()` - Devuelve estadísticas en texto

---

## ✅ **TAREA 2: Cliente SOAP Consumiendo el Servicio**

### Objetivo:
Crear un **cliente Java** que:
- Se conecte al servicio SOAP usando el WSDL
- Llame a las operaciones del servicio
- Demuestre que el servicio funciona

### Implementación:

**Archivo:**
- `ClienteSOAPDemo.java` - Cliente de demostración

### Cómo ejecutar:

**1. Asegúrate que el servidor esté corriendo:**
```bash
./gradlew app:completeWebServer
```

**2. En otra terminal, ejecuta el cliente:**
```bash
./gradlew app:clienteSOAP
```

### Resultado:
```
╔════════════════════════════════════════════════════════╗
║   Cliente SOAP - Biblioteca Comunitaria               ║
╚════════════════════════════════════════════════════════╝

📡 Conectando al servicio SOAP...
✅ Conexión establecida

📊 ESTADÍSTICAS:
📚 Total de materiales: 18
📖 Total de libros: 12
📋 Total de préstamos: 3
👥 Total de lectores: 8

📚 TÍTULOS DE LIBROS:
  1. El Quijote
  2. Cien años de soledad
  3. 1984
  ...

✅ admin@biblioteca.com → Administrador
✅ juan@email.com → Juan Pérez
```

---

## 🔧 **ALTERNATIVA: Generar Cliente con wsimport**

Si tu profesor quiere que uses `wsimport` (herramienta oficial), puedes hacer:

### Paso 1: Generar clases cliente desde WSDL
```bash
# Con el servidor corriendo
wsimport -keep -p pap2025.client -d app/src/main/java \
    http://localhost:9090/soap/biblioteca?wsdl
```

### Paso 2: Usar las clases generadas
```java
import pap2025.client.BibliotecaService;
import pap2025.client.BibliotecaWebService;

public class MiCliente {
    public static void main(String[] args) {
        BibliotecaService service = new BibliotecaService();
        BibliotecaWebService port = service.getBibliotecaPort();
        
        int total = port.obtenerTotalMateriales();
        System.out.println("Total: " + total);
    }
}
```

---

## 📊 **Flujo Completo:**

```
┌──────────────────────────────────────────────────┐
│  TAREA 1: Servidor SOAP                          │
│  ./gradlew app:completeWebServer                 │
│                                                   │
│  ✅ Tomcat en puerto 8080 (Servlets + JSP)       │
│  ✅ SOAP en puerto 9090 (JAX-WS)                 │
│  ✅ WSDL auto-generado                           │
└──────────────────────────────────────────────────┘
                    ↓
            (Genera WSDL)
                    ↓
┌──────────────────────────────────────────────────┐
│  http://localhost:9090/soap/biblioteca?wsdl      │
│  (Archivo XML que describe el servicio)          │
└──────────────────────────────────────────────────┘
                    ↓
            (Cliente lee WSDL)
                    ↓
┌──────────────────────────────────────────────────┐
│  TAREA 2: Cliente SOAP                           │
│  ./gradlew app:clienteSOAP                       │
│                                                   │
│  ✅ Lee WSDL                                     │
│  ✅ Crea proxy del servicio                      │
│  ✅ Llama a operaciones remotamente              │
│  ✅ Muestra resultados                           │
└──────────────────────────────────────────────────┘
```

---

## ✅ **VERIFICACIÓN:**

### ¿Funciona la TAREA 1?
```bash
curl "http://localhost:9090/soap/biblioteca?wsdl"
```
✅ Debe devolver XML con el WSDL

### ¿Funciona la TAREA 2?
```bash
./gradlew app:clienteSOAP
```
✅ Debe mostrar estadísticas y datos del servicio

---

## 🎓 **PARA TU ENTREGA:**

**Debes demostrar:**

1. ✅ **Servidor corriendo** (TAREA 1)
   - Mostrar: http://localhost:9090/soap/biblioteca?wsdl

2. ✅ **Cliente funcionando** (TAREA 2)
   - Ejecutar: `./gradlew app:clienteSOAP`
   - Mostrar: Output del cliente con datos reales

3. ✅ **Código fuente:**
   - Interfaz: `IBibliotecaWebService.java`
   - Implementación: `BibliotecaWebService.java`
   - Cliente: `ClienteSOAPDemo.java`

---

## 🎊 **RESULTADO FINAL:**

✅ **Aplicación Web** (Servlets + JSP) funcionando  
✅ **TAREA 1:** Servicio SOAP publicado con WSDL generado  
✅ **TAREA 2:** Cliente SOAP consumiendo el servicio exitosamente  
✅ **Todas las funcionalidades** implementadas  
✅ **100% Cumplimiento** de requisitos  

**¡Estás completamente listo para entregar!** 🎉
