package com.pedidos.sistema; // Paquete raíz de pruebas.

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf; // CSRF de prueba.
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user; // Usuario de prueba.
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity; // Integra la seguridad.
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // GET.
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // POST.
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Estado HTTP.
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view; // Nombre de vista.

import org.junit.jupiter.api.BeforeEach; // Preparación.
import org.junit.jupiter.api.Test; // Prueba.
import org.springframework.beans.factory.annotation.Autowired; // Inyección.
import org.springframework.boot.test.context.SpringBootTest; // Contexto completo.
import org.springframework.test.web.servlet.MockMvc; // Cliente HTTP simulado.
import org.springframework.test.web.servlet.setup.MockMvcBuilders; // Constructor de MockMvc.
import org.springframework.web.context.WebApplicationContext; // Contexto web.

/** Comprueba los criterios de aceptación: las rutas exigen login y las vistas se resuelven. */
@SpringBootTest
class WebSecurityTests {

    @Autowired WebApplicationContext context;
    MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    void rutasProtegidasRedirigenAlLogin() throws Exception {
        mvc.perform(get("/panel")).andExpect(status().is3xxRedirection());
        mvc.perform(get("/pedidos")).andExpect(status().is3xxRedirection()); // Criterio: /pedidos requiere sesión.
    }

    @Test
    void loginEsPublico() throws Exception {
        mvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("login"));
    }

    @Test
    void usuarioAutenticadoVePanelYPedidos() throws Exception {
        mvc.perform(get("/panel").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk()).andExpect(view().name("panel"));
        mvc.perform(get("/pedidos").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk()).andExpect(view().name("pedidos"));
    }

    @Test
    void eliminarPorPostExigeCsrf() throws Exception {
        mvc.perform(post("/categorias/eliminar/999").with(user("admin").roles("ADMIN")))
                .andExpect(status().isForbidden()); // Sin token CSRF se rechaza.
        mvc.perform(post("/categorias/eliminar/999").with(user("admin").roles("ADMIN")).with(csrf()))
                .andExpect(status().is3xxRedirection()); // Con token: redirige al panel.
    }
}
