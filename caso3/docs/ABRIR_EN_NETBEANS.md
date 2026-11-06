# Abrir y ejecutar en NetBeans

1. Descomprimir el ZIP.
2. En NetBeans, Archivo → Abrir proyecto.
3. Seleccionar la carpeta **DocumentosHexagonal**, donde están pom.xml, mvnw.cmd y src.
4. Verificar que el proyecto tenga JDK 17 o 21 (Propiedades → Build → Compile si su versión de NetBeans muestra esa sección).
5. Clean and Build. La primera compilación descarga las dependencias Maven.
6. Run Project (F6). El archivo nbactions.xml ejecuta spring-boot:run.
7. Abrir http://localhost:8080. Usuario admin, contraseña Admin123!.

Alternativa desde terminal dentro de DocumentosHexagonal: `mvnw.cmd spring-boot:run`.
La clase principal es `com.empresa.documentos.DocumentosApplication`.
Para ejecutar sin compilar, usar INICIAR_DEMO.bat.

## Estructura equivalente al proyecto de referencia

| Elemento | Ubicación |
|---|---|
| Definición Maven | pom.xml |
| Maven Wrapper | mvnw, mvnw.cmd y .mvn/wrapper |
| Arranque Spring Boot | src/main/java/com/empresa/documentos/DocumentosApplication.java |
| Casos de uso | application/usecase |
| Modelos | domain/model |
| Puertos de entrada y salida | domain/port/in y domain/port/out |
| Adaptadores de base de datos | infrastructure/persistence/adapter |
| Entidades JPA | infrastructure/persistence/entity |
| Repositorios JPA | infrastructure/persistence/repository |
| Controladores | infrastructure/web |
| Configuración | infrastructure/config y src/main/resources |
| Portal Angular compilado | src/main/resources/static |
| Fuente Angular | frontend |

Las rutas de paquetes de la tabla parten de src/main/java/com/empresa/documentos.
Spring Boot conserva la versión 3.2.5 de la primera base; se tomó de PedidosHexagonal la organización del proyecto y sus ejecutables Maven. La interfaz del caso 3 continúa siendo Angular.
