package com.empresa.pedidos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.empresa.pedidos.infrastructure.persistence.repository.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc
class PedidosApplicationTests {
@Autowired MockMvc mvc;
@Autowired ClienteJpaRepository clientes;
@Autowired EmpleadoJpaRepository empleados;
@Autowired UsuarioJpaRepository usuarios;
@Autowired PasswordEncoder encoder;
@Test void rutasPrivadas() throws Exception {mvc.perform(get("/menu")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrlPattern("**/login"));}
@Test void credencialesYHash() throws Exception {
assertTrue(encoder.matches("Admin123*",usuarios.findByUsername("admin").orElseThrow().getPasswordHash()));
mvc.perform(formLogin("/login").user("admin").password("Admin123*")).andExpect(redirectedUrl("/menu"));
mvc.perform(formLogin("/login").user("admin").password("incorrecta")).andExpect(redirectedUrl("/login?error"));}
@Test @WithMockUser(roles="EMPLEADO") void permisosEmpleado() throws Exception {mvc.perform(get("/empleados/nuevo")).andExpect(status().isForbidden());mvc.perform(get("/clientes/nuevo")).andExpect(status().isOk());mvc.perform(get("/menu")).andExpect(status().isOk());}
@Test @WithMockUser(roles="ADMIN") void vistas() throws Exception {for(String url:new String[]{"/menu","/empleados/nuevo","/clientes/nuevo","/login"})mvc.perform(get(url)).andExpect(status().isOk());}
@Test @WithMockUser(roles="ADMIN") void registroValidacionDuplicadoYCsrf() throws Exception {
mvc.perform(post("/clientes/guardar").param("nombre","Cliente prueba").param("documento","TEST-01")).andExpect(status().isForbidden());
mvc.perform(post("/clientes/guardar").with(csrf()).param("nombre","Cliente prueba").param("documento"," TEST-01 ").param("correo","cliente@example.com")).andExpect(redirectedUrl("/clientes"));
assertTrue(clientes.existsByDocumento("TEST-01"));
mvc.perform(get("/clientes")).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString("TEST-01")));long total=clientes.count();
mvc.perform(post("/clientes/guardar").with(csrf()).param("nombre","Duplicado").param("documento","TEST-01")).andExpect(model().attributeHasFieldErrors("cliente","documento"));assertEquals(total,clientes.count());
mvc.perform(post("/clientes/guardar").with(csrf()).param("nombre","").param("documento","OTRO")).andExpect(model().attributeHasFieldErrors("cliente","nombre"));
mvc.perform(post("/empleados/guardar").with(csrf()).param("nombres","Ana").param("apellidos","Torres").param("documento","EMP-TEST")).andExpect(redirectedUrl("/empleados"));assertTrue(empleados.existsByDocumento("EMP-TEST"));mvc.perform(get("/empleados")).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString("EMP-TEST")));}
@Test @WithMockUser void logout() throws Exception {mvc.perform(post("/logout").with(csrf())).andExpect(redirectedUrl("/login?logout"));}
}
