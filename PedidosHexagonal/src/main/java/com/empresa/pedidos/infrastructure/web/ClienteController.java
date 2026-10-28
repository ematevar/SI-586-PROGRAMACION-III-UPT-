package com.empresa.pedidos.infrastructure.web;


 import com.empresa.pedidos.infrastructure.web.form.Cliente;
 import com.empresa.pedidos.application.usecase.RegistrarCliente;
import org.springframework.dao.DataIntegrityViolationException;
 import jakarta.validation.Valid;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.validation.BindingResult;
 import org.springframework.web.bind.annotation.*;


 @Controller
 @RequestMapping("/clientes")
 public class ClienteController {
     private final RegistrarCliente casoUso;


     public ClienteController(RegistrarCliente casoUso) {
         this.casoUso = casoUso;
     }


     @GetMapping("/nuevo")
     public String nuevo(Model model) {
         model.addAttribute("cliente", new Cliente());
         return "clientes";
     }


     @PostMapping("/guardar")
     public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente,
                           BindingResult result, Model model) {
         if (result.hasErrors()) return "clientes";


 var value=new com.empresa.pedidos.domain.model.Cliente();
value.setNombre(cliente.getNombre());
value.setDocumento(cliente.getDocumento());
value.setDireccion(cliente.getDireccion());
value.setCorreo(cliente.getCorreo());
try { casoUso.ejecutar(value); }
catch (IllegalArgumentException | DataIntegrityViolationException ex) {
result.rejectValue("documento", "duplicado", "El documento ya se encuentra registrado.");
return "clientes";
}
model.addAttribute("mensaje", "Cliente registrado correctamente.");
         model.addAttribute("cliente", new Cliente());



         return "clientes";
     }
 }
