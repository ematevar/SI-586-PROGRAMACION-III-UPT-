package com.empresa.pedidos.infrastructure.security;
 import com.empresa.pedidos.domain.model.Usuario;
 import com.empresa.pedidos.domain.port.out.UsuarioPort;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.security.core.userdetails.*;
 import org.springframework.security.core.userdetails.User;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.security.web.SecurityFilterChain;
 import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.config.http.SessionCreationPolicy;

 @Configuration
 public class SecurityConfig {
   @Bean
   PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   UserDetailsService userDetailsService(UsuarioPort repository) {
     return username -> repository.findByUsername(username)
        .filter(u -> u.isActivo())
        .map(u -> User.withUsername(u.getUsername())
            .password(u.getPasswordHash())
            .roles(u.getRol())
            .build())
        .orElseThrow(() ->
            new UsernameNotFoundException("Usuario no válido"));


     }

     @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
          .authorizeHttpRequests(auth -> auth
             .requestMatchers("/login", "/error").permitAll()
             .requestMatchers("/empleados/**").hasRole("ADMIN")
             .requestMatchers("/clientes/**").hasAnyRole("ADMIN", "EMPLEADO")
             .anyRequest().authenticated()
          )
          .formLogin(form -> form
             .loginPage("/login")
             .defaultSuccessUrl("/menu", true)
             .failureUrl("/login?error")
             .permitAll()
          )
          .logout(logout -> logout
             .logoutUrl("/logout")
             .logoutSuccessUrl("/login?logout")
             .invalidateHttpSession(true)
             .clearAuthentication(true)
             .deleteCookies("JSESSIONID")
             .permitAll()
          )
          .sessionManagement(session -> session
             .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
             .sessionFixation(fixation -> fixation.changeSessionId())
          );
       return http.build();
     }
 }
