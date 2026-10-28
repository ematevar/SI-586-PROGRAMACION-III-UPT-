package com.pedidos.sistema.infrastructure.security; // Paquete de configuración de seguridad.

import org.springframework.context.annotation.Bean; // Declara objetos administrados por Spring.
import org.springframework.context.annotation.Configuration; // Marca la clase de configuración.
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Configuración HTTP.
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Activa la seguridad web.
import org.springframework.security.core.userdetails.User; // Constructor de usuarios.
import org.springframework.security.core.userdetails.UserDetails; // Contrato de usuario.
import org.springframework.security.core.userdetails.UserDetailsService; // Servicio que carga usuarios.
import org.springframework.security.provisioning.InMemoryUserDetailsManager; // Usuarios en memoria.
import org.springframework.security.web.SecurityFilterChain; // Cadena de filtros.

/**
 * Seguridad del laboratorio: un único usuario en memoria con rol ADMIN.
 * SOLO DIDÁCTICO: en producción usar PasswordEncoder (BCrypt) y usuarios desde la tabla usuario.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth // Reglas para las solicitudes.
                        .requestMatchers("/css/**", "/js/**").permitAll() // Estáticos sin sesión.
                        .anyRequest().authenticated() // El resto (incluye /panel, /categorias/**, /pedidos) exige login.
                )
                .formLogin(form -> form.loginPage("/login") // Vista personalizada de login.
                        .defaultSuccessUrl("/panel", true) // Tras autenticar, va al panel.
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/logout") // Ruta de cierre de sesión (POST).
                        .logoutSuccessUrl("/login?logout").permitAll()); // Vuelve al login con ?logout.
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin123") // Sin hash: solo laboratorio.
                .roles("ADMIN") // ROLE_ADMIN.
                .build();
        return new InMemoryUserDetailsManager(admin); // Guardado en memoria mientras corre la app.
    }
}
