package com.pedidos.sistema; // Paquete raíz de pruebas.

import static org.junit.jupiter.api.Assertions.*; // Aserciones.

import com.pedidos.sistema.categoria.domain.model.Categoria; // Dominio de categoría.
import com.pedidos.sistema.categoria.domain.ports.in.CategoriaUseCase; // Puerto de entrada de categoría.
import com.pedidos.sistema.pedido.domain.ports.in.PedidoUseCase; // Puerto de entrada de pedidos.
import com.pedidos.sistema.pedido.infrastructure.adapters.out.persistence.PedidoEntity; // Entidad para preparar datos.
import com.pedidos.sistema.pedido.infrastructure.adapters.out.persistence.SpringDataPedidoRepository; // Repositorio JPA.
import java.math.BigDecimal; // Importes.
import java.time.LocalDateTime; // Fechas.
import org.junit.jupiter.api.Test; // Anotación de prueba.
import org.springframework.beans.factory.annotation.Autowired; // Inyección.
import org.springframework.boot.test.context.SpringBootTest; // Contexto completo.
import org.springframework.transaction.annotation.Transactional; // Revierte los datos de cada prueba.

/** Prueba los casos de uso de punta a punta (servicio -> adaptador -> JPA -> H2). */
@SpringBootTest
@Transactional
class CategoriaYPedidoUseCaseTests {

    @Autowired CategoriaUseCase categoriaUseCase;
    @Autowired PedidoUseCase pedidoUseCase;
    @Autowired SpringDataPedidoRepository pedidoRepository;

    @Test
    void crudDeCategoria() {
        Categoria creada = categoriaUseCase.crearCategoria(new Categoria(null, "Bebidas", "Jugos y agua"));
        assertNotNull(creada.getId()); // Se generó el ID.

        categoriaUseCase.actualizarCategoria(creada.getId(), new Categoria(null, "Bebidas frías", "Actualizada"));
        assertEquals("Bebidas frías", categoriaUseCase.obtenerCategoriaPorId(creada.getId()).orElseThrow().getNombre());

        assertTrue(categoriaUseCase.eliminarCategoria(creada.getId())); // Elimina.
        assertTrue(categoriaUseCase.obtenerCategoriaPorId(creada.getId()).isEmpty()); // Ya no existe.
        assertFalse(categoriaUseCase.eliminarCategoria(creada.getId())); // Eliminar de nuevo devuelve false.
    }

    @Test
    void listaYFiltraPedidos() {
        guardar("PENDIENTE", "85.50", LocalDateTime.of(2026, 10, 23, 10, 0));
        guardar("APROBADO", "42.00", LocalDateTime.of(2026, 10, 23, 11, 0));

        assertEquals(2, pedidoUseCase.listarPedidos().size());
        assertEquals("APROBADO", pedidoUseCase.listarPedidos().get(0).getEstado()); // Más reciente primero.
        assertEquals(1, pedidoUseCase.listarPedidosPorEstado("pendiente").size()); // Filtro sin distinguir mayúsculas.
        assertEquals(2, pedidoUseCase.listarPedidosPorEstado("").size()); // Sin filtro = todos.
    }

    private void guardar(String estado, String total, LocalDateTime fecha) {
        PedidoEntity e = new PedidoEntity();
        e.setEstado(estado);
        e.setTotal(new BigDecimal(total));
        e.setFecha(fecha);
        pedidoRepository.save(e);
    }
}
