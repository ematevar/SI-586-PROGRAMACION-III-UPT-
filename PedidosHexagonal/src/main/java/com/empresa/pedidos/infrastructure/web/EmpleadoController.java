package com.empresa.pedidos.infrastructure.web;
import com.empresa.pedidos.infrastructure.web.form.Empleado;
import com.empresa.pedidos.application.usecase.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/empleados")
public class EmpleadoController {
private final RegistrarEmpleado registrar;
private final ListarEmpleados listar;
public EmpleadoController(RegistrarEmpleado registrar,ListarEmpleados listar){this.registrar=registrar;this.listar=listar;}
@ModelAttribute("registros")
public java.util.List<com.empresa.pedidos.domain.model.Empleado> registros(){return listar.ejecutar();}
@GetMapping({"", "/nuevo"})
public String nuevo(Model model){model.addAttribute("empleado",new Empleado());return "empleados";}
@PostMapping("/guardar")
public String guardar(@Valid @ModelAttribute("empleado") Empleado empleado, BindingResult result, RedirectAttributes flash){
if(result.hasErrors()) return "empleados";
var value=new com.empresa.pedidos.domain.model.Empleado();
value.setNombres(empleado.getNombres());
value.setApellidos(empleado.getApellidos());
value.setDocumento(empleado.getDocumento());
value.setCorreo(empleado.getCorreo());
try{registrar.ejecutar(value);}
catch(IllegalArgumentException ex){result.rejectValue("documento","duplicado",ex.getMessage());return "empleados";}
catch(DataIntegrityViolationException ex){result.rejectValue("documento","duplicado","No se pudo guardar. Comprueba que el documento no esté registrado.");return "empleados";}
flash.addFlashAttribute("mensaje","Empleado registrado correctamente.");
return "redirect:/empleados";
}
}
