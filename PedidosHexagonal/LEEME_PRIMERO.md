# Laboratorio 04 - Sistema Pedidos

Proyecto Maven para NetBeans. Java 17 o superior, Spring Boot 3.4.5 y MySQL.

## Inicio en Windows
1. Extraer TODO el ZIP. No ejecutar desde la vista comprimida.
2. Iniciar MySQL en XAMPP (o el servicio MySQL).
3. Importar Base_Datos_Pedidos_10_Registros.sql en HeidiSQL o phpMyAdmin. Crea Pedidos y carga 10 empleados y 10 clientes. Importarlo una sola vez para evitar duplicados.
4. Abrir src/main/resources/application.properties. Se entrega para MySQL local, puerto 3306, usuario root y contraseña vacía (XAMPP). Si tu MySQL tiene clave, cambiar spring.datasource.password. También puedes definir DB_URL, DB_USER y DB_PASSWORD como variables de entorno.
5. NetBeans: Archivo > Abrir proyecto > seleccionar la carpeta que contiene pom.xml. Configurar JDK 17 o superior y ejecutar PedidosApplication.java.
6. Alternativa: doble clic en INICIAR_WINDOWS.bat. Requiere Java en PATH e Internet para descargar Maven y las dependencias la primera vez.
7. Abrir http://localhost:8080/login

Usuario: admin
Contraseña: Admin123*

El administrador se crea al iniciar, solo si no existe. Su contraseña se guarda como hash BCrypt; no se almacena en texto plano. Crear un empleado en el formulario registra sus datos personales: no crea automáticamente una cuenta de acceso, tal como la guía distingue ambas tablas.

## Funciones de la guía
- Login válido e inválido con Spring Security.
- Menú privado y cierre de sesión POST con invalidación y eliminación de JSESSIONID.
- Sesiones de 15 minutos; protección contra fijación de sesión; cookie HttpOnly y SameSite=Lax.
- ADMIN registra empleados; ADMIN y EMPLEADO registran clientes.
- Formularios Thymeleaf con token CSRF automático, validación del servidor y mensajes de éxito/error.
- Documento único, normalización de espacios y restricción UNIQUE en MySQL.
- Tablas separadas usuarios_app, empleados_app y clientes_app.
- Conserva los nombres de campos y rutas de la guía.

## Retos del punto 16 resueltos
- domain/model: modelos sin dependencias de Spring/JPA.
- domain/port/out: UsuarioPort, EmpleadoPort y ClientePort.
- application/usecase: RegistrarEmpleado y RegistrarCliente, con las reglas de documento.
- infrastructure/persistence/adapter: adaptadores JPA que implementan los puertos.
- infrastructure/config: composición de casos de uso.
- infrastructure/web/form: formularios validados, separados de entidades JPA.
- Los controladores llaman casos de uso y no repositorios JPA. La seguridad usa UsuarioPort.

## Pruebas y empaquetado
Desde una terminal en esta carpeta:

    mvnw.cmd clean test
    mvnw.cmd clean package
    java -jar target/PedidosHexagonal-1.0.0.jar

Las pruebas usan H2 en memoria: no modifican la base MySQL. Cubren credenciales, BCrypt, roles, acceso privado, renderizado de vistas, registros, duplicados, validación, CSRF y logout.

Estado de verificación de esta entrega: se revisaron estructura, código y configuración. Se intentó ejecutar Maven, pero este entorno no puede resolver repo.maven.apache.org. Por ello no se afirma compilación exitosa ni pruebas aprobadas y no se incluye un JAR sin verificar. Ejecutar los comandos anteriores con Internet en tu PC.

Para llevarlo a otra PC: llevar este proyecto, importar el SQL e iniciar MySQL. Si ya generaste el JAR, puedes llevar también target/PedidosHexagonal-1.0.0.jar y ejecutarlo con Java 17 o superior.

## Comprobación manual (punto 15)
1. Ir a /menu sin sesión: debe redirigir al login.
2. Probar clave incorrecta: debe mostrar error.
3. Entrar con admin y registrar cliente y empleado con documentos nuevos.
4. Consultar SELECT * FROM Pedidos.clientes_app y SELECT * FROM Pedidos.empleados_app.
5. Repetir un documento: debe mostrar error sin duplicar registros.
6. Probar nombre vacío o correo inválido: no debe guardar.
7. Cerrar sesión y volver a una ruta privada: debe pedir autenticación.

La guía original se incluye como Guia_Laboratorio04.pdf. Las credenciales y ddl-auto=update son los valores didácticos de la guía para práctica local.
