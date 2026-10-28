# Sistema Web de Pedidos — Laboratorio 04

Minisistema web desarrollado en **Programación III** con **Spring Boot 4, MySQL, Thymeleaf y Spring Security**, usando **arquitectura hexagonal**.

- ✅ Login con Spring Security (usuario en memoria, solo didáctico)
- ✅ CRUD web completo de **Categorías**
- ✅ Reto de ampliación: **consulta de Pedidos** (solo lectura) con filtro opcional por estado

## Requisitos

- Java 21
- MySQL 8+ (y HeidiSQL, opcional)
- No hace falta instalar Maven: se incluye `mvnw`

## Puesta en marcha

1. **Crear la base de datos**: ejecute `sql/sistema_pedidos_web.sql` en HeidiSQL (crea tablas y datos de ejemplo).
2. **Configurar credenciales** (no suba su contraseña real a GitHub). Elija una opción:
   - Variables de entorno: `DB_USER` y `DB_PASSWORD` (opcional `DB_URL`).
   - o copie `src/main/resources/application-local.properties.example` a `application-local.properties` (está en `.gitignore`) y complete sus datos.
3. **Ejecutar**:
   ```bash
   ./mvnw spring-boot:run        # Linux / macOS
   mvnw.cmd spring-boot:run      # Windows
   ```
4. Abrir <http://localhost:8080/login> → usuario `admin`, contraseña `admin123`.

También puede ejecutarse desde NetBeans abriendo `SistemaApplication`.

## Rutas

| Ruta | Método | Descripción |
|------|--------|-------------|
| `/login` | GET | Pantalla de inicio de sesión (pública) |
| `/panel` | GET | Listado de categorías |
| `/categorias/nueva` | GET | Formulario de registro |
| `/categorias/guardar` | POST | Crea la categoría |
| `/categorias/editar/{id}` | GET | Formulario de edición |
| `/categorias/actualizar/{id}` | POST | Actualiza la categoría |
| `/categorias/eliminar/{id}` | POST | Elimina la categoría (con CSRF) |
| `/pedidos?estado=` | GET | Listado de pedidos, filtro opcional |
| `/logout` | POST | Cierra sesión |

## Arquitectura (hexagonal)

```
com.pedidos.sistema
├── categoria
│   ├── domain/model/Categoria
│   ├── domain/ports/in/CategoriaUseCase          (puerto de entrada)
│   ├── domain/ports/out/CategoriaRepositoryPort  (puerto de salida)
│   ├── application/service/CategoriaService
│   └── infrastructure/adapters/out/persistence/  (Entity, SpringData, Adapter)
├── pedido                                        (misma estructura, solo lectura)
└── infrastructure
    ├── security/SecurityConfig
    └── adapters/in/web/WebController
```

Más detalle y el recorrido de una solicitud en [`docs/ARQUITECTURA.md`](docs/ARQUITECTURA.md). Respuestas a las 10 preguntas de comprobación en [`docs/RESPUESTAS.md`](docs/RESPUESTAS.md).

## Pruebas

```bash
./mvnw test
```

Usan H2 en memoria (no necesitan MySQL): contexto, CRUD de categorías, listado/filtro de pedidos y seguridad de rutas.

## Notas de seguridad

- `{noop}admin123` es **solo para el laboratorio**. En producción: `PasswordEncoder` (BCrypt), usuarios desde la tabla `usuario` y autorización por rol.
- La eliminación se hace por **POST** (con token CSRF), no por GET.
- Los roles Cliente/Vendedor/Almacenero están modelados en la BD, pero la autorización diferenciada no está implementada (alcance de la guía).

## Docentes

Ing. Natividad Cáceres · Ing. Elard Rodríguez Marca
