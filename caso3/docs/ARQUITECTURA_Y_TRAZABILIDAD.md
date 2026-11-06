# Arquitectura y correspondencia con el enunciado

| Paso de la imagen | Implementación |
|---|---|
| 1. Lista administrativa | Sesión administrativa, búsqueda, alta/edición y selección de empleado |
| 2. Generar/enviar | Tres tipos de documento y plantilla compatible, OpenPDF y JavaMail |
| 3. Correo recibido | Destinatario del empleado, asunto, saludo y enlace protegido; SMTP o demo explícita |
| 4. Validación | Página de documento protegido, enlace aleatorio y código del empleado por POST |
| 5. Visualización | PDF A4 en visor del navegador; descarga después de validar |
| 6. Registro automático | Primera apertura persistida en hora America/Lima, estado VISTO |
| 7. Control | Tabla, filtros, fechas, estados, estado separado del correo y exportación CSV |
| Arquitectura hexagonal | Dominio → puertos de aplicación → adaptadores primarios/secundarios |
| Tecnologías | Spring Boot, MySQL, OpenPDF, JavaMail y Angular |

## Dependencias

REST (adaptador primario) invoca DocumentoUseCase. DocumentoService implementa ese puerto y usa DocumentoRepository, PdfPort y CorreoPort. JpaDocumentoAdapter, OpenPdfAdapter y SmtpCorreoAdapter implementan los puertos de salida. El dominio no importa JPA, Spring o JavaMail. Las entidades JPA se convierten a registros de dominio en el adaptador.

La tabla de envíos almacena instantánea de empleado, contenido y plantilla. El token aleatorio identifica el enlace; no es una ruta pública al fichero. No se adjunta un PDF libre al correo porque evitaría el control de acceso y la trazabilidad del flujo indicado.

## API

| Método | Ruta /api/caso3 | Acceso |
|---|---|---|
| POST | /login | Credenciales admin |
| GET | /session | Estado de sesión |
| POST | /logout | Cierra sesión |
| GET/POST | /empleados | Administrador |
| GET/POST | /envios | Administrador |
| GET | /envios/{id}/pdf | Administrador; no registra lectura |
| POST | /envios/{id}/reenviar | Administrador |
| POST | /documentos/{token}/abrir | Enlace más código; devuelve PDF y registra apertura |

Generar un documento verifica previamente la generación PDF. Cada envío permanece registrado aunque SMTP falle. Reenviar conserva la primera fecha de generación y el estado de lectura, y vuelve a intentar la entrega del mismo enlace. La apertura preserva la primera fecha, incluso en aperturas posteriores. Los errores de código y enlaces inválidos no registran lectura. Un error al guardar la apertura impide devolver una respuesta satisfactoria.
