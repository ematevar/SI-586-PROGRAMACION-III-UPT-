package com.empresa.documentos.infrastructure.pdf;
import org.springframework.stereotype.Component;
import com.lowagie.text.*; import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import com.empresa.documentos.domain.port.out.PdfPort;
import com.empresa.documentos.domain.model.Envio;
@Component
public class OpenPdfAdapter implements PdfPort {
 public byte[] generar(Envio e){try{ByteArrayOutputStream out=new ByteArrayOutputStream();Document d=new Document(PageSize.A4,56,56,60,60);PdfWriter.getInstance(d,out);d.open();
 Font title=FontFactory.getFont(FontFactory.HELVETICA_BOLD,20);Font body=FontFactory.getFont(FontFactory.HELVETICA,12);
 d.add(new Paragraph("EMPRESA S.A.C.",title));d.add(new Paragraph("Recursos Humanos • Documento personal",body));d.add(new Paragraph("\n"+e.tipo().toUpperCase()+"\n\n",title));
 d.add(new Paragraph("Empleado: "+e.empleado()+" | Código: "+e.codigo(),body));d.add(new Paragraph("Fecha de emisión: "+e.fechaEnvio().toLocalDate()+"\n\n",body));
 d.add(new Paragraph(e.contenido(),body));d.add(new Paragraph("\n\n____________________________\nRecursos Humanos\n"+e.plantilla(),body));d.close();return out.toByteArray();}catch(Exception ex){throw new IllegalStateException("No se pudo generar PDF",ex);}}
}
