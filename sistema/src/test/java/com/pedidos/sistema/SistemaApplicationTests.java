package com.pedidos.sistema; // Paquete raíz de pruebas.

import org.junit.jupiter.api.Test; // Anotación de prueba.
import org.springframework.boot.test.context.SpringBootTest; // Levanta el contexto completo.

@SpringBootTest // Usa H2 en memoria (src/test/resources/application.properties).
class SistemaApplicationTests {

    @Test
    void contextLoads() { // Verifica que todos los beans (puertos, servicios, adaptadores) se crean bien.
    }
}
