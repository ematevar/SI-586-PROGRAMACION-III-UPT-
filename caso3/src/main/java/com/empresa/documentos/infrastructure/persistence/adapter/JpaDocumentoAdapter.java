package com.empresa.documentos.infrastructure.persistence.adapter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import com.empresa.documentos.infrastructure.persistence.entity.*;
import com.empresa.documentos.infrastructure.persistence.repository.*;
import com.empresa.documentos.domain.port.in.*; import com.empresa.documentos.domain.port.out.*;
import com.empresa.documentos.domain.model.*;
@Repository @Transactional
public class JpaDocumentoAdapter implements DocumentoRepository {
 private final EmpleadoJpa empleados; private final EnvioJpa envios;
 public JpaDocumentoAdapter(EmpleadoJpa a,EnvioJpa b){empleados=a;envios=b;}
 private Empleado map(EmpleadoEntity e){return new Empleado(e.id,e.codigo,e.nombre,e.correo,e.cargo,e.ingreso);}
 private Envio map(EnvioEntity e){return new Envio(e.id,e.empleadoId,e.empleado,e.codigo,e.correo,e.tipo,e.plantilla,e.contenido,e.token,e.fechaEnvio,e.fechaApertura,e.correoEstado,e.correoError);}
 public List<Empleado> empleados(){return empleados.findAll().stream().map(this::map).toList();}
 public Optional<Empleado> empleado(Long id){return empleados.findById(id).map(this::map);}
 public Empleado guardar(Empleado e){EmpleadoEntity x=e.id()==null?new EmpleadoEntity():empleados.findById(e.id()).orElseThrow();x.codigo=e.codigo();x.nombre=e.nombre();x.correo=e.correo();x.cargo=e.cargo();x.ingreso=e.ingreso();return map(empleados.save(x));}
 public List<Envio> envios(){return envios.findAll().stream().map(this::map).sorted(Comparator.comparing(Envio::id).reversed()).toList();}
 public Optional<Envio> envio(Long id){return envios.findById(id).map(this::map);}
 public Optional<Envio> porToken(String token){return envios.findByToken(token).map(this::map);}
 public Envio guardar(Envio e){EnvioEntity x=e.id()==null?new EnvioEntity():envios.findById(e.id()).orElseThrow();x.empleadoId=e.empleadoId();x.empleado=e.empleado();x.codigo=e.codigo();x.correo=e.correo();x.tipo=e.tipo();x.plantilla=e.plantilla();x.contenido=e.contenido();x.token=e.token();x.fechaEnvio=e.fechaEnvio();if(x.fechaApertura==null)x.fechaApertura=e.fechaApertura();x.correoEstado=e.correoEstado();x.correoError=e.correoError();return map(envios.save(x));}
}
