package com.empresa.documentos.domain.port.out;
import java.util.*;
import com.empresa.documentos.domain.model.*;
public interface DocumentoRepository {
 List<Empleado> empleados(); Empleado guardar(Empleado e); Optional<Empleado> empleado(Long id);
 List<Envio> envios(); Optional<Envio> envio(Long id); Optional<Envio> porToken(String token);
 Envio guardar(Envio e);
}
