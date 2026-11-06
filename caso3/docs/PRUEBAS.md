# Validación de la entrega

- Angular: `npm run build` completado correctamente.
- Java: `mvn package` con JDK 21, BUILD SUCCESS.
- JUnit: 3 pruebas ejecutadas, 0 fallos, 0 errores.

La prueba de integración recorre login, autorización, selección de empleado, generación, simulación del correo, vista previa administrativa sin lectura, código incorrecto, código correcto, respuesta PDF, cambio a VISTO, conservación de primera apertura, enlace inválido y reenvío sin alterar la lectura.

Las dos pruebas de servicio comprueban que un fallo SMTP se conserva como ERROR con documento pendiente, y que un fallo al generar PDF no guarda una apertura.

El perfil MySQL y SMTP real requieren servicios y credenciales del usuario; no se probó entrega a buzones externos ni una instancia MySQL en este entorno. La demo y las pruebas utilizan H2 en modo MySQL. No se pudo ejecutar la revisión visual automatizada en Chromium por una restricción del entorno sobre sockets Unix; el código Angular sí se compiló.

## Lista de comprobación manual

1. Ejecutar la demo según README.
2. Crear/editar empleado y comprobar código único, correo y fecha.
3. Generar cada tipo con su plantilla y detalle.
4. Verificar que la vista previa no cambia PENDIENTE.
5. Intentar código incorrecto y luego correcto desde el enlace del correo.
6. Consultar primera fecha de apertura, filtros y exportación.
7. Configurar SMTP real, editar correo de destino y comprobar recepción.
8. Probar fallo SMTP y reenvío después de corregir credenciales.
9. Reiniciar y verificar persistencia de empleados y estados.

Prueba adicional del JAR ejecutable sobre HTTP real: portal Angular servido, login administrativo, generación y acceso PDF y persistencia de VISTO comprobados correctamente.
