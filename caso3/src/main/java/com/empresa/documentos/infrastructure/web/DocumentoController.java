package com.empresa.documentos.infrastructure.web;
import org.springframework.web.bind.annotation.*;import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.*;import jakarta.validation.Valid;import jakarta.validation.constraints.*;
import java.util.*;import java.time.Instant;
import com.empresa.documentos.domain.port.in.DocumentoUseCase;
import com.empresa.documentos.domain.model.*;
@RestController @RequestMapping("/api/caso3")
public class DocumentoController {
 private final DocumentoUseCase use;private final String user,password;
 public DocumentoController(DocumentoUseCase u,@Value("${app.admin.user}")String a,@Value("${app.admin.password}")String p){use=u;user=a;password=p;}
 record Login(@NotBlank String username,@NotBlank String password){}
 record EmpleadoRequest(Long id,@Pattern(regexp="EMP[0-9]{3,8}") @NotBlank String codigo,@NotBlank @Size(max=120) String nombre,@Email @NotBlank String correo,@NotBlank @Size(max=100) String cargo,@NotBlank @Pattern(regexp="[0-9]{4}-[0-9]{2}-[0-9]{2}") String ingreso){}
 record Enviar(@NotNull Long empleadoId,@NotBlank String tipo,@NotBlank String plantilla,@Size(max=10000) String contenido){}
 record Acceso(@NotBlank @Size(max=30) String codigo){}
 private void admin(HttpSession s){if(!Boolean.TRUE.equals(s.getAttribute("admin")))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Inicie sesión de administrador");}
 private void throttle(HttpSession s,String prefix,int max){long now=Instant.now().getEpochSecond();Long start=(Long)s.getAttribute(prefix+"start");if(start==null||now-start>300){s.setAttribute(prefix+"start",now);s.setAttribute(prefix+"tries",0);}int n=(int)s.getAttribute(prefix+"tries");if(n>=max)throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,"Demasiados intentos. Espere cinco minutos");s.setAttribute(prefix+"tries",n+1);}
 @PostMapping("/login") public Map<String,Object> login(@Valid @RequestBody Login r,HttpServletRequest request){HttpSession s=request.getSession();throttle(s,"login",10);if(!user.equals(r.username())||!java.security.MessageDigest.isEqual(password.getBytes(java.nio.charset.StandardCharsets.UTF_8),r.password().getBytes(java.nio.charset.StandardCharsets.UTF_8)))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Credenciales incorrectas");request.changeSessionId();s.setAttribute("admin",true);s.removeAttribute("logintries");s.removeAttribute("loginstart");return Map.of("ok",true);}
 @GetMapping("/session") public Map<String,Object> session(HttpSession s){return Map.of("admin",Boolean.TRUE.equals(s.getAttribute("admin")));}
 @PostMapping("/logout") public void logout(HttpSession s){s.invalidate();}
 @GetMapping("/empleados") public List<Empleado> empleados(HttpSession s){admin(s);return use.empleados();}
 @PostMapping("/empleados") public Empleado guardar(@Valid @RequestBody EmpleadoRequest r,HttpSession s){admin(s);try{java.time.LocalDate.parse(r.ingreso());return use.guardar(new Empleado(r.id(),r.codigo(),r.nombre(),r.correo(),r.cargo(),r.ingreso()));}catch(org.springframework.dao.DataIntegrityViolationException ex){throw new ResponseStatusException(HttpStatus.CONFLICT,"Código duplicado");}catch(java.time.DateTimeException ex){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Fecha inválida");}}
 @GetMapping("/envios") public List<Map<String,Object>> envios(HttpSession s){admin(s);return use.envios().stream().map(this::view).toList();}
 private Map<String,Object> view(Envio e){Map<String,Object> m=new LinkedHashMap<>();m.put("id",e.id());m.put("empleado",e.empleado());m.put("codigo",e.codigo());m.put("correo",e.correo());m.put("tipo",e.tipo());m.put("fechaEnvio",e.fechaEnvio());m.put("fechaApertura",e.fechaApertura());m.put("estado",e.estado());m.put("correoEstado",e.correoEstado());m.put("correoError",e.correoError());m.put("enlace","/?documento="+e.token());return m;}
 @PostMapping("/envios") public Map<String,Object> enviar(@Valid @RequestBody Enviar r,HttpSession s){admin(s);return view(use.enviar(r.empleadoId(),r.tipo(),r.plantilla(),r.contenido()));}
 @PostMapping("/envios/{id}/reenviar") public Map<String,Object> reenviar(@PathVariable Long id,HttpSession s){admin(s);return view(use.reenviar(id));}
 @GetMapping("/envios/{id}/pdf") public ResponseEntity<byte[]> preview(@PathVariable Long id,HttpSession s){admin(s);return response(use.preview(id));}
 @PostMapping("/documentos/{token}/abrir") public ResponseEntity<byte[]> abrir(@PathVariable String token,@Valid @RequestBody Acceso r,HttpSession s){throttle(s,"pdf",20);return response(use.abrir(token,r.codigo()));}
 private ResponseEntity<byte[]> response(byte[] bytes){return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).header("Content-Disposition","inline; filename=documento.pdf").header("Cache-Control","no-store").header("X-Content-Type-Options","nosniff").body(bytes);}
}
