package com.empresa.documentos.application.usecase;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.time.*; import java.util.*;
import com.empresa.documentos.domain.port.in.*; import com.empresa.documentos.domain.port.out.*;
import com.empresa.documentos.domain.model.*;
@Service
public class DocumentoService implements DocumentoUseCase {
 private final DocumentoRepository repo; private final PdfPort pdf; private final CorreoPort correo;
 public DocumentoService(DocumentoRepository r,PdfPort p,CorreoPort c){repo=r;pdf=p;correo=c;}
 public List<Empleado> empleados(){return repo.empleados();}
 public Empleado guardar(Empleado e){return repo.guardar(e);}
 public List<Envio> envios(){return repo.envios();}
 private Envio require(Long id){return repo.envio(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Envío inexistente"));}
 public Envio enviar(Long id,String tipo,String plantilla,String contenido){
 Empleado e=repo.empleado(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Empleado inexistente"));
 if(!List.of("Constancia de Trabajo","Boleta de Pago","Contrato Actualizado").contains(tipo))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tipo no permitido");
 String expected=switch(tipo){case "Boleta de Pago"->"Boleta Mensual";case "Contrato Actualizado"->"Contrato Laboral";default->"Constancia Laboral";};
 if(!expected.equals(plantilla))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Plantilla incompatible");
 String text=contenido==null?"":contenido.trim();
 if(text.isEmpty())text="Se deja constancia de que "+e.nombre()+" (código "+e.codigo()+") labora en nuestra empresa como "+e.cargo()+" desde el "+e.ingreso()+" a la fecha.";
 if(!tipo.equals("Constancia de Trabajo")&&(contenido==null||contenido.isBlank()))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ingrese el detalle del documento");
 Envio n=new Envio(null,e.id(),e.nombre(),e.codigo(),e.correo(),tipo,plantilla,text,UUID.randomUUID().toString()+UUID.randomUUID().toString(),LocalDateTime.now(ZoneId.of("America/Lima")),null,"PENDIENTE",null);
 pdf.generar(n);return entregar(repo.guardar(n));
 }
 private Envio entregar(Envio e){String estado;String error=null;try{estado=correo.enviar(e);}catch(Exception ex){estado="ERROR";error="No se pudo entregar por SMTP. Revise configuración y reintente.";}
 return repo.guardar(new Envio(e.id(),e.empleadoId(),e.empleado(),e.codigo(),e.correo(),e.tipo(),e.plantilla(),e.contenido(),e.token(),e.fechaEnvio(),e.fechaApertura(),estado,error));}
 public Envio reenviar(Long id){return entregar(require(id));}
 public synchronized byte[] abrir(String token,String codigo){Envio e=repo.porToken(token).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Enlace inválido"));
 if(!e.codigo().equals(codigo.trim().toUpperCase(Locale.ROOT)))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Código de empleado incorrecto");
 byte[] bytes=pdf.generar(e);
 if(e.fechaApertura()==null)repo.guardar(new Envio(e.id(),e.empleadoId(),e.empleado(),e.codigo(),e.correo(),e.tipo(),e.plantilla(),e.contenido(),e.token(),e.fechaEnvio(),LocalDateTime.now(ZoneId.of("America/Lima")),e.correoEstado(),e.correoError()));
 return bytes;}
 public byte[] preview(Long id){return pdf.generar(require(id));}
}
