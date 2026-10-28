package com.empresa.pedidos.infrastructure.web;


 import com.empresa.pedidos.infrastructure.web.form.Empleado;
 import com.empresa.pedidos.application.usecase.RegistrarEmpleado;
import org.springframework.dao.DataIntegrityViolationException;
 import jakarta.validation.Valid;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.Model;
 import org.springframework.validation.BindingResult;
 import org.springframework.web.bind.annotation.*;


 @Controller
 @RequestMapping("/empleados")
 public class EmpleadoController {
     private final RegistrarEmpleado casoUso;





     public EmpleadoController(RegistrarEmpleado casoUso) {
         this.casoUso = casoUso;
     }


     @GetMapping("/nuevo")
     public String nuevo(Model model) {
         model.addAttribute("empleado", new Empleado());
         return "empleados";
     }


     @PostMapping("/guardar")
     public String guardar(@Valid @ModelAttribute("empleado") Empleado empleado,
                           BindingResult result, Model model) {
         if (result.hasErrors()) return "empleados";


 var value=new com.empresa.pedidos.domain.model.Empleado();
value.setNombres(empleado.getNombres());
value.setApellidos(empleado.getApellidos());
value.setDocumento(empleado.getDocumento());
value.setCorreo(empleado.getCorreo());
try { casoUso.ejecutar(value); }
catch (IllegalArgumentException | DataIntegrityViolationException ex) {
result.rejectValue("documento", "duplicado", "El documento ya se encuentra registrado.");
return "empleados";
}
model.addAttribute("mensaje", "Empleado registrado correctamente.");
         model.addAttribute("empleado", new Empleado());
         return "empleados";
     }
 }
