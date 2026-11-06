# Caso 3 — Envío y control de documentos con acceso seguro

Proyecto desarrollado sobre el ZIP auth2fa-hexagonal-completo-Nayeli Quispe. Se conserva el código de autenticación base en com.empresa.documentos.auth2fa, Spring Boot 3.2.5, las capas y el módulo 2FA original. Se incorpora el caso 3 en el paquete com.empresa.documentos y un portal Angular real, ya compilado dentro del backend. La interfaz 2FA original queda en referencia-2fa/index.html como referencia; el portal de documentos utiliza su propia sesión administrativa.

## Inicio rápido en Windows

1. Instalar **JDK 17 o 21** y comprobar `java -version`.
2. Descomprimir todo el ZIP en una carpeta.
3. Ejecutar **INICIAR_DEMO.bat** y mantener la consola abierta.
4. Abrir **http://localhost:8080**.
5. Ingresar con usuario **admin** y contraseña **Admin123!**.

El JAR incluye backend, Angular compilado y todas las dependencias Java. No necesita MySQL, Node o Maven para ejecutar la demo. Los datos se guardan en data; los correos de demostración en data/correos-demo. La demo NO envía correos reales. Para reiniciarla, detener el proceso y eliminar data (esto borra sus registros).

## Demostración completa del caso 3

1. En Empleados, seleccionar Ana Torres, EMP002.
2. Seleccionar Constancia de Trabajo / Constancia Laboral. Dejar el contenido vacío para generar el texto con empleado, cargo y fecha de ingreso. Para Boleta de Pago y Contrato Actualizado, escribir el detalle contractual o de remuneraciones en Contenido: el sistema no calcula planillas ni reemplaza asesoría laboral.
3. Pulsar Generar PDF y enviar correo. Se genera el documento y su enlace aleatorio. El estado inicial es PENDIENTE; el correo demo se identifica como SIMULADO.
4. Ver el correo guardado o utilizar Abrir enlace del empleado. El enlace no contiene el código de empleado.
5. Intentar con EMP001: debe denegar el acceso y mantener PENDIENTE.
6. Ingresar EMP002: entrega el PDF al visor y registra VISTO y la primera fecha de apertura.
7. Regresar al panel Envíos y lecturas. Se actualiza cada diez segundos o con Actualizar. Probar filtros Todos/Visto/Pendiente y exportación CSV.
8. Abrir nuevamente: la fecha inicial no cambia. La vista previa administrativa no marca lectura.

Se registra la entrega del PDF al visor tras una validación correcta; no es posible demostrar que una persona haya leído su contenido. No se entrega un PDF sin validar el código. El código se envía en JSON por POST, no en la URL. Los documentos se reconstruyen desde una instantánea de los datos guardados al enviarlos, por lo que editar al empleado no modifica documentos anteriores.

## MySQL y correo real

1. Iniciar MySQL (por ejemplo XAMPP). Importar sql/caso3_mysql.sql en HeidiSQL si desea crear el esquema manualmente. JPA también puede crearlo al arrancar; no importar el esquema authdb del caso 2FA para este módulo.
2. Editar INICIAR_MYSQL_SMTP_EJEMPLO.bat con usuario/clave MySQL y credenciales SMTP propias. En Gmail utilizar una contraseña de aplicación de la cuenta configurada.
3. Cambiar los correos de los empleados por buzones reales desde Editar.
4. Ejecutar el archivo editado. MAIL_MODE=smtp activa JavaMail. ENVIADO significa que el servidor SMTP aceptó el mensaje; no garantiza llegada a la bandeja de entrada. ERROR conserva el documento y permite Reenviar.
5. PUBLIC_URL debe ser la URL accesible para el empleado, por ejemplo http://IP_DEL_SERVIDOR:8080 para una red local. localhost solo sirve en la misma PC. En despliegue externo utilizar HTTPS y contraseña administrativa propia.

Las variables admitidas están en src/main/resources/application.properties. No hay credenciales reales incluidas. MAIL_MODE=demo siempre simula aunque se seleccione MySQL.

## Abrir y editar en NetBeans

Abrir **DocumentosHexagonal**, la carpeta que contiene el pom.xml, como proyecto Maven. Seleccionar JDK 17/21. La clase principal es com.empresa.documentos.DocumentosApplication. Puede ejecutar con `mvn spring-boot:run`. Si modifica Java, ejecutar `mvn clean package` y copiar target/DocumentosHexagonal-1.0.0.jar a caso3-documentos.jar.

Para modificar Angular se requiere Node 20.11+ y npm: en frontend ejecutar `npm ci`, luego `npm run build`. Copiar frontend/dist/portal/browser/* a src/main/resources/static y empaquetar nuevamente. RECONSTRUIR.bat automatiza el proceso en Windows mediante Maven Wrapper. Desarrollo: backend en 8080 y `npm start` en frontend (4200), con proxy /api. Para probar los enlaces generados de correo se recomienda la versión integrada en 8080.

## Estructura

- src/main/java/com/empresa/documentos/auth2fa: proyecto original de autenticación.
- src/main/java/com/empresa/documentos/domain/model: Empleado y Envio sin dependencias del framework.
- domain/port/in y domain/port/out: caso de uso y puertos de repositorio, PDF y correo.
- application/usecase/DocumentoService: reglas de generación, envío y primera apertura.
- infrastructure: adaptadores REST, JPA, OpenPDF y JavaMail.
- frontend: portal Angular con código fuente y package-lock.json.
- sql: esquema y consultas MySQL.
- postman: colección importable para probar la API.
- docs: trazabilidad, arquitectura y pruebas.

## Acceso y límites

El administrador necesita sesión HTTP para listar empleados, ver documentos, reportes, reenviar y editar. El empleado necesita un enlace aleatorio de 72 caracteres más su código. El acceso tiene un límite de intentos por sesión de cinco minutos; no es una defensa distribuida frente a ataques. La contraseña administrativa de ejemplo debe cambiarse usando ADMIN_PASSWORD. El acceso del empleado reproduce lo solicitado por el caso; el código de empleado no sustituye una contraseña fuerte ni autentica por sí solo su identidad. No publicar como sistema de recursos humanos productivo sin endurecimiento, políticas de expiración y gestión de usuarios.

El módulo 2FA original se conserva como base separada; no se presenta como protección de las rutas del caso 3. Los documentos no caducan ni se revocan en esta versión. La firma del PDF es un pie de Recursos Humanos, no una firma digital certificada.

## Abrir esta entrega en NetBeans

Archivo → Abrir proyecto → seleccionar DocumentosHexagonal (donde está pom.xml). Elegir JDK 17 o 21, ejecutar Clean and Build y luego Run Project (F6). nbactions.xml configura spring-boot:run. El primer build necesita conexión a Internet para descargar dependencias. También puede ejecutar `mvnw.cmd spring-boot:run` desde esta carpeta. No abra frontend como proyecto Java.
