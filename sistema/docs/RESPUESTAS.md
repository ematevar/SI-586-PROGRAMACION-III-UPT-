# Preguntas de comprobación — Laboratorio 04

1. **¿Qué responsabilidad tiene `Categoria` y por qué no depende de JPA?**
   Representa el concepto de negocio "categoría" (id, nombre, descripción). No depende de JPA para que las reglas del negocio no queden atadas a una tecnología de persistencia: es el núcleo del hexágono y puede probarse o reutilizarse sin base de datos.

2. **Diferencia entre `CategoriaUseCase` y `CategoriaRepositoryPort`.**
   `CategoriaUseCase` es el puerto de **entrada**: lo que la aplicación ofrece al exterior (crear, listar, actualizar, eliminar). `CategoriaRepositoryPort` es el puerto de **salida**: lo que la aplicación necesita de la infraestructura para persistir datos.

3. **¿Qué función cumple `@Service` en `CategoriaService`?**
   Registra la clase como bean de Spring (componente de la capa de aplicación), de modo que Spring la crea e inyecta donde se necesite, por ejemplo en `WebController` a través de la interfaz `CategoriaUseCase`.

4. **¿Por qué el adaptador convierte `Categoria` a `CategoriaEntity` antes de guardar?**
   Porque JPA trabaja con entidades anotadas (`@Entity`), mientras que el dominio es Java puro. El adaptador traduce entre ambos (`toEntity` / `toModel`) y así el dominio queda aislado de la persistencia.

5. **¿Qué operaciones CRUD hereda `SpringDataCategoriaRepository`?**
   De `JpaRepository`: `save` (crear y actualizar), `findById`, `findAll`, `deleteById`, `count`, `existsById`, entre otras, sin escribir SQL.

6. **¿Qué hace `@GetMapping("/panel")` y cómo llega la lista a `panel.html`?**
   Atiende las solicitudes GET a `/panel`. El método obtiene las categorías con `categoriaUseCase.obtenerTodas()`, las guarda en el `Model` con el nombre `categorias` y devuelve la vista `panel`; Thymeleaf lee ese atributo con `${categorias}`.

7. **¿Para qué sirve `th:each`?**
   Repite el elemento HTML (aquí, una fila `<tr>`) por cada elemento de la colección, imprimiendo id, nombre y descripción de cada categoría.

8. **Diferencia entre `@ModelAttribute` y `@PathVariable`.**
   `@PathVariable` toma un valor de la **URL** (por ejemplo el `{id}` de `/categorias/editar/{id}`). `@ModelAttribute` construye un **objeto** (`Categoria`) con los campos enviados en el formulario.

9. **Riesgo de `{noop}admin123`.**
   La contraseña está en texto plano en el código (visible en el repositorio) y sin hash, y es un solo usuario fijo. Quien acceda al código o al repositorio conoce las credenciales. En producción se usa `PasswordEncoder` (BCrypt), usuarios en base de datos y contraseñas fuera del código.

10. **¿Qué falta para que la tabla `pedido` sea un módulo web funcional?**
    Modelo de dominio `Pedido`, puertos de entrada/salida, servicio, entidad JPA, repositorio Spring Data y adaptador de persistencia, además de una ruta en el controlador y la vista `pedidos.html` (ya incluidos en este proyecto como solo consulta). Para un flujo completo faltaría crear pedidos con sus detalles, manejar stock, estados, autorización por rol y autenticación desde la tabla `usuario`.
