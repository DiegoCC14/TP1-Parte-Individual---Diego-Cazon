# Ferretería TodoCode - Spring Boot + Thymeleaf + JPA

Sistema de gestión de productos y herramientas para ferretería desarrollado en **Java** con **Spring Boot**, **Spring Data JPA** y **Thymeleaf**, correspondiente a la actividad práctica integradora del [Curso de Thymeleaf de TodoCode](https://www.youtube.com/playlist?list=PLQxX2eiEaqbxC-DDkMMiAlvh4hhVpaHw8).

---

## 🛠️ Tecnologías y Arquitectura

- **Lenguaje**: Java 21 / 24
- **Framework**: Spring Boot 3.4.3
  - `spring-boot-starter-web` (Controladores REST y MVC)
  - `spring-boot-starter-thymeleaf` (Motor de plantillas dinámico)
  - `spring-boot-starter-data-jpa` (Persistencia relacional)
- **Base de Datos**: 
  - **SQLite3** por defecto (`ferreteria.db`), persistente y sin necesidad de instalar servicios externos.
  - Soporte listo para **MySQL** con configuración comentada en `application.properties`.
- **Estructura por Capas**:
  - `model`: Entidad JPA `Producto`
  - `repository`: `IProductoRepository` (Spring Data JPA)
  - `service`: `IProductoService` y `ProductoService` (Lógica de negocio y validaciones)
  - `controller`: `ProductoRestController` (API REST) y `ProductoWebController` (Vistas Thymeleaf)
  - `config`: `DataInitializer` (Carga automática de productos iniciales al iniciar)
  - `templates`: Vistas HTML dinámicas con Thymeleaf y fragmentos (`layout.html`, `lista.html`, `formulario.html`)

---

## 🚀 Cómo Ejecutar el Proyecto

### Opción 1: En IntelliJ IDEA (Recomendada)
1. Abrir **IntelliJ IDEA**.
2. Seleccionar **Open** y elegir la carpeta `4 - Thymeleaf`.
3. Esperar que IntelliJ sincronice las dependencias de `pom.xml`.
4. Ejecutar la clase principal `FerreteriaApplication.java` (`Run 'FerreteriaApplication'`).

### Opción 2: Con el script directo de Windows
Hacer doble clic en `run.bat` o ejecutarlo en la consola:
```powershell
.\run.bat
```

### Opción 3: Con Maven Wrapper desde la terminal
```powershell
.\mvnw.cmd spring-boot:run
```

---

## 🌐 Interfaz Gráfica Web (Thymeleaf)

Una vez iniciado el servidor, abrir el navegador en:

- **Catálogo Principal**: [http://localhost:8080/productos](http://localhost:8080/productos)
  - Listado completo en tabla dinámica.
  - Indicadores visuales de stock (*Agotado*, *Bajo stock*, *En stock*).
  - Formato de moneda en pesos/dólares.
  - Botones de **Editar** y **Eliminar** (con modal de confirmación).
- **Formulario de Registro / Edición**: [http://localhost:8080/productos/nuevo](http://localhost:8080/productos/nuevo)
  - Validación de campos obligatorios, precio mayor a cero y stock no negativo.
  - Mensajes de error contextuales.
- **Fragmentos Reutilizables**:
  - Encabezado con navegación, barra superior de métricas y pie de página compartidos mediante `th:replace`.

---

## ⚡ API REST (`/api/productos`)

La API responde y recibe datos en formato JSON:

| Método | Endpoint | Descripción | Código Éxito |
|---|---|---|---|
| `GET` | `/api/productos` | Obtener todos los productos | `200 OK` |
| `GET` | `/api/productos/{id}` | Obtener producto por código | `200 OK` / `404 Not Found` |
| `POST` | `/api/productos` | Crear nuevo producto | `201 Created` / `400 Bad Request` |
| `PUT` | `/api/productos/{id}` | Modificar producto existente | `200 OK` / `400 Bad Request` |
| `DELETE` | `/api/productos/{id}` | Eliminar producto por código | `200 OK` / `404 Not Found` |

### Ejemplo de JSON para `POST` / `PUT`:
```json
{
  "nombre": "Taladro Percutor 650W",
  "marca": "Bosch",
  "categoria": "Herramientas Eléctricas",
  "precio": 89.99,
  "stock": 15,
  "descripcion": "Taladro percutor ergonómico con mandril de 13mm y velocidad variable."
}
```

---

## 📮 Pruebas con Postman

Se incluye en la raíz del proyecto el archivo:
```
Ferreteria.postman_collection.json
```
Para usarlo:
1. Abrir Postman.
2. Hacer clic en **Import**.
3. Arrastrar el archivo `Ferreteria.postman_collection.json`.
4. Ejecutar las peticiones prediseñadas para listar, crear, consultar, actualizar y eliminar.
