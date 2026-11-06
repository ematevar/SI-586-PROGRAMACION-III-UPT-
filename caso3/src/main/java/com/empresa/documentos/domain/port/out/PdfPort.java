package com.empresa.documentos.domain.port.out;
import com.empresa.documentos.domain.model.Envio;
public interface PdfPort { byte[] generar(Envio e); }
