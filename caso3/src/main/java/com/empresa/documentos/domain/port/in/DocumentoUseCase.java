package com.empresa.documentos.domain.port.in;
import java.util.List;
import com.empresa.documentos.domain.model.*;
public interface DocumentoUseCase {
 List<Empleado> empleados(); Empleado guardar(Empleado e); List<Envio> envios();
 Envio enviar(Long empleadoId,String tipo,String plantilla,String contenido); Envio reenviar(Long id);
 byte[] abrir(String token,String codigo); byte[] preview(Long id);
}
