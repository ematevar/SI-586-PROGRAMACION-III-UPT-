package com.empresa.documentos.infrastructure.config;
import org.springframework.stereotype.Component;import org.springframework.boot.CommandLineRunner;
import com.empresa.documentos.domain.port.out.DocumentoRepository;import com.empresa.documentos.domain.model.Empleado;
@Component public class Seed implements CommandLineRunner {
 private final DocumentoRepository r; public Seed(DocumentoRepository r){this.r=r;}
 public void run(String... args){if(r.empleados().isEmpty()){String[] names={"Carlos Pérez","Ana Torres","Luis Gómez","María Díaz"};for(int i=0;i<4;i++)r.guardar(new Empleado(null,"EMP00"+(i+1),names[i],new String[]{"carlos","ana","luis","maria"}[i]+"@empresa.com","Analista","2023-01-03"));}}
}
