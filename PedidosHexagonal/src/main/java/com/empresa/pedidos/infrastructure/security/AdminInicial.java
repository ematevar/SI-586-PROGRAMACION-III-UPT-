package com.empresa.pedidos.infrastructure.security;


 import com.empresa.pedidos.domain.model.Usuario;
 import com.empresa.pedidos.domain.port.out.UsuarioPort;
 import org.springframework.boot.CommandLineRunner;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.security.crypto.password.PasswordEncoder;





  @Configuration
  public class AdminInicial {
      @Bean
      CommandLineRunner crearAdmin(UsuarioPort repository,
                                   PasswordEncoder encoder) {
          return args -> {
              if (repository.findByUsername("admin").isEmpty()) {
                  Usuario u = new Usuario();
                  u.setUsername("admin");
                  u.setPasswordHash(encoder.encode("Admin123*"));
                  u.setRol("ADMIN");
                  u.setActivo(true);
                  repository.save(u);
              }
          };
      }
  }
