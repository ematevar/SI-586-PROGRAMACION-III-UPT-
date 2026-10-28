package com.empresa.pedidos.infrastructure.web;
import com.empresa.pedidos.infrastructure.web.form.Cliente;
import com.empresa.pedidos.application.usecase.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/clientes")
public class ClienteController {
private final RegistrarCliente registrar;
private final ListarClientes listar;
public ClienteController(RegistrarCliente registrar,ListarClientes listar){this.registrar=registrar;this.listar=listar;}
@ModelAttribute("registros")
public java.util.List<com.empresa.pedidos.domain.model.Cliente> registros(){return listar.ejecutar();}
@GetMapping({"", "/nuevo"})
public String nuevo(Model model){model.addAttribute("cliente",new Cliente());return "clientes";}
@PostMapping("/guardar")
public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result, RedirectAttributes flash){
if(result.hasErrors()) return "clientes";
var value=new com.empresa.pedidos.domain.model.Cliente();
value.setNombre(cliente.getNombre());
value.setDocumento(cliente.getDocumento());
value.setDireccion(cliente.getDireccion());
value.setCorreo(cliente.getCorreo());
try{registrar.ejecutar(value);}
catch(IllegalArgumentException ex){result.rejectValue("documento","duplicado",ex.getMessage());return "clientes";}
catch(DataIntegrityViolationException ex){result.rejectValue("documento","duplicado","No se pudo guardar. Comprueba que el documento no esté registrado.");return "clientes";}
flash.addFlashAttribute("mensaje","Cliente registrado correctamente.");
return "redirect:/clientes";
}
}
