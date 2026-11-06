package com.empresa.documentos;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import com.empresa.documentos.application.usecase.DocumentoService;
import com.empresa.documentos.domain.port.in.*; import com.empresa.documentos.domain.port.out.*;
import com.empresa.documentos.domain.model.*;
class DocumentoServiceTest {
 @Test void smtpFailureRemainsVisibleAndPending(){
 DocumentoRepository repo=mock(DocumentoRepository.class);PdfPort pdf=mock(PdfPort.class);CorreoPort mail=mock(CorreoPort.class);
 when(repo.empleado(2L)).thenReturn(Optional.of(new Empleado(2L,"EMP002","Ana Torres","ana@example.com","Analista","2023-01-03")));
 when(repo.guardar(any(Envio.class))).thenAnswer(i->{Envio e=i.getArgument(0);return new Envio(10L,e.empleadoId(),e.empleado(),e.codigo(),e.correo(),e.tipo(),e.plantilla(),e.contenido(),e.token(),e.fechaEnvio(),e.fechaApertura(),e.correoEstado(),e.correoError());});
 when(mail.enviar(any())).thenThrow(new IllegalStateException("SMTP unavailable"));
 Envio result=new DocumentoService(repo,pdf,mail).enviar(2L,"Constancia de Trabajo","Constancia Laboral","");
 assertEquals("ERROR",result.correoEstado());assertEquals("PENDIENTE",result.estado());assertNotNull(result.correoError());assertEquals(10L,result.id());
 }
 @Test void pdfFailureDoesNotPersistOpening(){
 DocumentoRepository repo=mock(DocumentoRepository.class);PdfPort pdf=mock(PdfPort.class);CorreoPort mail=mock(CorreoPort.class);
 Envio e=new Envio(1L,2L,"Ana","EMP002","ana@example.com","Constancia de Trabajo","Constancia Laboral","Texto","token",java.time.LocalDateTime.now(),null,"SIMULADO",null);
 when(repo.porToken("token")).thenReturn(Optional.of(e));when(pdf.generar(e)).thenThrow(new IllegalStateException("PDF unavailable"));
 assertThrows(IllegalStateException.class,()->new DocumentoService(repo,pdf,mail).abrir("token","EMP002"));verify(repo,never()).guardar(any(Envio.class));
 }
}
