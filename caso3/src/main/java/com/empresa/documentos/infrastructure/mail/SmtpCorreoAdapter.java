package com.empresa.documentos.infrastructure.mail;
import org.springframework.stereotype.Component; import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;import org.springframework.mail.SimpleMailMessage;
import com.empresa.documentos.domain.port.out.CorreoPort;
import com.empresa.documentos.domain.model.Envio;
import java.nio.file.*;import java.nio.charset.StandardCharsets;
@Component
public class SmtpCorreoAdapter implements CorreoPort {
 private final JavaMailSender sender; private final String mode,from,url;
 public SmtpCorreoAdapter(JavaMailSender s,@Value("${app.mail.mode}")String m,@Value("${app.mail.from}")String f,@Value("${app.public-url}")String u){sender=s;mode=m;from=f;url=u;}
 public String enviar(Envio e){String body="Hola "+e.empleado()+",\nSe le ha enviado un documento personal: "+e.tipo()+".\nPara visualizarlo visite:\n"+url+"/?documento="+e.token()+"\nIngrese su código de empleado.\nRecursos Humanos";
 if(mode.equals("demo")){try{Files.createDirectories(Path.of("data/correos-demo"));Files.writeString(Path.of("data/correos-demo/envio-"+e.id()+".txt"),"Para: "+e.correo()+"\nAsunto: Documento personal - "+e.tipo()+"\n\n"+body,StandardCharsets.UTF_8);return "SIMULADO";}catch(Exception ex){throw new IllegalStateException(ex);}}
 SimpleMailMessage msg=new SimpleMailMessage();msg.setFrom(from);msg.setTo(e.correo());msg.setSubject("Documento personal - "+e.tipo());msg.setText(body);sender.send(msg);return "ENVIADO";}
}
