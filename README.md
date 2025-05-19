# 💻 Proyecto Java JDBC - Inserción y Gestión de Usuarios con Arquitectura por Capas

Este proyecto implementa un flujo completo para insertar y gestionar usuarios en una base de datos relacional utilizando **Java + JDBC**, aplicando principios sólidos de arquitectura y diseño en una estructura por capas.

---

## 🎯 Objetivo de la actividad

📌 **Implementar un flujo completo para insertar un usuario en una base de datos relacional 🗃️ utilizando JDBC con una arquitectura por capas bien definida:**

**main ➡ service ➡ dao ➡ db**

🧩 A lo largo del desarrollo se demuestra cómo:

- Crear un objeto `Usuario` (DTO).
- Validar sus datos desde el **Service**.
- Ejecutar un **INSERT** desde el **DAO**.
- Utilizar un pool de conexiones con **HikariCP**.
- Aplicar buenas prácticas de diseño y separación de responsabilidades.

---

## 🧱 Estructura del proyecto

src/
├── config/
│ └── DBConfig.java # Configuración del pool de conexiones con HikariCP
├── model/
│ └── Usuario.java # DTO con atributos y métodos de usuario
├── dao/
│ ├── DaoGenerico.java # Interface genérica para operaciones CRUD
│ └── DaoUsuarioImpl.java # Implementación concreta para la entidad Usuario
├── service/
│ └── UsuarioService.java # Lógica de negocio, validaciones y reglas del dominio
└── Main.java # Clase de prueba o entrada


---

## 🧩 Base de datos utilizada

- **Motor**: MySQL
- **Nombre de la base**: `jdbc_ejemplo`
- **Tabla**: `usuario`

```sql
CREATE TABLE usuario (
    idUsuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    email VARCHAR(100)
);

✅ Funcionalidades implementadas

    📥 Insertar nuevos usuarios (previa validación de datos y duplicados)

    📋 Listar todos los usuarios

    🔍 Buscar usuario por ID

    ✏️ Actualizar datos de un usuario

    🗑️ Eliminar un usuario por ID

    🔒 Validaciones en UsuarioService:

        Campos obligatorios

        Formato de email

        Prevención de duplicados

⚙️ Dependencias clave

    JDK 17+

    JDBC

    HikariCP (para gestión de conexiones eficiente)

    MySQL (como base de datos)

🚀 ¿A quién está dirigido este proyecto?

Este proyecto es ideal para:

    Estudiantes que desean comprender cómo estructurar aplicaciones Java con acceso a bases de datos.

    Desarrolladores que quieran aplicar principios como separación de responsabilidades, validación centralizada y arquitectura limpia.

    Quienes deseen ver un ejemplo real de implementación de CRUD con buenas prácticas.
