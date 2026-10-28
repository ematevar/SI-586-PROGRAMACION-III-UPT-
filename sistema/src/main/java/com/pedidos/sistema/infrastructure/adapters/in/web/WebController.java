package com.pedidos.sistema.infrastructure.adapters.in.web; // Paquete del controlador web.

import com.pedidos.sistema.categoria.domain.model.Categoria; // Modelo de categoría.
import com.pedidos.sistema.categoria.domain.ports.in.CategoriaUseCase; // Casos de uso de categoría.
import com.pedidos.sistema.pedido.domain.ports.in.PedidoUseCase; // Casos de uso de pedidos.
import org.springframework.stereotype.Controller; // Controlador MVC (devuelve vistas).
import org.springframework.ui.Model; // Envía datos a la vista.
import org.springframework.web.bind.annotation.*; // Anotaciones de rutas y parámetros.
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Mensajes tras redirección.
import java.util.List; // Lista de estados.

/** Adaptador de entrada: recibe HTTP, llama a los casos de uso y elige la vista. No contiene lógica de persistencia. */
@Controller
public class WebController {

    private static final List<String> ESTADOS = List.of("PENDIENTE", "APROBADO", "DESPACHADO", "RECHAZADO");

    private final CategoriaUseCase categoriaUseCase; // Casos de uso de categoría.
    private final PedidoUseCase pedidoUseCase; // Casos de uso de pedidos.

    public WebController(CategoriaUseCase categoriaUseCase, PedidoUseCase pedidoUseCase) {
        this.categoriaUseCase = categoriaUseCase;
        this.pedidoUseCase = pedidoUseCase;
    }

    @GetMapping("/login") // GET /login.
    public String login() { return "login"; } // templates/login.html

    @GetMapping({"/", "/panel"}) // GET /panel (y la raíz redirige lógicamente al mismo panel).
    public String panel(Model model) {
        model.addAttribute("categorias", categoriaUseCase.obtenerTodas()); // Lista con nombre "categorias".
        return "panel"; // templates/panel.html
    }

    @GetMapping("/categorias/nueva") // Formulario de creación.
    public String nuevaCategoria(Model model) {
        model.addAttribute("categoria", new Categoria()); // Objeto vacío para el formulario.
        return "formulario-categoria";
    }

    @PostMapping("/categorias/guardar") // Envío del formulario de creación.
    public String guardarCategoria(@ModelAttribute Categoria categoria, RedirectAttributes ra) {
        categoriaUseCase.crearCategoria(categoria); // Caso de uso de creación.
        ra.addFlashAttribute("mensaje", "Categoría registrada correctamente.");
        return "redirect:/panel"; // Evita reenviar el formulario al recargar.
    }

    @GetMapping("/categorias/editar/{id}") // Edición usando el ID de la URL.
    public String editarCategoria(@PathVariable Long id, Model model) {
        return categoriaUseCase.obtenerCategoriaPorId(id).map(categoria -> { // Si el ID existe...
            model.addAttribute("categoria", categoria); // ...envía la categoría encontrada.
            return "formulario-categoria"; // Reutiliza el formulario.
        }).orElse("redirect:/panel"); // Si no existe, vuelve al panel.
    }

    @PostMapping("/categorias/actualizar/{id}") // Envío de actualización.
    public String actualizarCategoria(@PathVariable Long id, @ModelAttribute Categoria categoria, RedirectAttributes ra) {
        boolean ok = categoriaUseCase.actualizarCategoria(id, categoria).isPresent(); // Ejecuta la actualización.
        if (ok) ra.addFlashAttribute("mensaje", "Categoría actualizada correctamente.");
        else ra.addFlashAttribute("error", "La categoría no existe.");
        return "redirect:/panel";
    }

    /** Mejora recomendada por la guía: eliminar por POST (con CSRF) y no por GET. */
    @PostMapping("/categorias/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id, RedirectAttributes ra) {
        if (categoriaUseCase.eliminarCategoria(id)) ra.addFlashAttribute("mensaje", "Categoría eliminada.");
        else ra.addFlashAttribute("error", "No se pudo eliminar (no existe o tiene productos asociados).");
        return "redirect:/panel";
    }

    @GetMapping("/pedidos") // Reto de ampliación: listado de pedidos (solo consulta).
    public String pedidos(@RequestParam(required = false) String estado, Model model) {
        model.addAttribute("pedidos", pedidoUseCase.listarPedidosPorEstado(estado)); // Sin estado = todos.
        model.addAttribute("estados", ESTADOS); // Opciones del filtro.
        model.addAttribute("estadoSeleccionado", estado); // Para marcar la opción activa.
        return "pedidos"; // templates/pedidos.html
    }
}
