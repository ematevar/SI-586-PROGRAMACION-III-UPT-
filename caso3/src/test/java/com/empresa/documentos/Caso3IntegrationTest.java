package com.empresa.documentos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(properties={"spring.datasource.url=jdbc:h2:mem:caso3;MODE=MySQL;DB_CLOSE_DELAY=-1","spring.jpa.hibernate.ddl-auto=create-drop","app.mail.mode=demo"})
@AutoConfigureMockMvc
class Caso3IntegrationTest {
 @Autowired MockMvc mvc; @Autowired ObjectMapper json;
 @Test void completeFlow() throws Exception {
 mvc.perform(get("/api/caso3/empleados")).andExpect(status().isUnauthorized());
 MockHttpSession session=new MockHttpSession();
 mvc.perform(post("/api/caso3/login").session(session).contentType("application/json").content("{\"username\":\"admin\",\"password\":\"incorrecta\"}")).andExpect(status().isUnauthorized());
 mvc.perform(post("/api/caso3/login").session(session).contentType("application/json").content("{\"username\":\"admin\",\"password\":\"Admin123!\"}")).andExpect(status().isOk());
 var emp=json.readTree(mvc.perform(get("/api/caso3/empleados").session(session)).andReturn().getResponse().getContentAsString()).get(1);
 String payload="{\"empleadoId\":"+emp.get("id")+",\"tipo\":\"Constancia de Trabajo\",\"plantilla\":\"Constancia Laboral\",\"contenido\":\"\"}";
 var send=json.readTree(mvc.perform(post("/api/caso3/envios").session(session).contentType("application/json").content(payload)).andExpect(status().isOk()).andExpect(jsonPath("$.estado").value("PENDIENTE")).andExpect(jsonPath("$.correoEstado").value("SIMULADO")).andReturn().getResponse().getContentAsString());
 String id=send.get("id").asText(),token=send.get("enlace").asText().split("=")[1];
 mvc.perform(get("/api/caso3/envios/"+id+"/pdf").session(session)).andExpect(status().isOk()).andExpect(content().contentType("application/pdf"));
 mvc.perform(get("/api/caso3/envios").session(session)).andExpect(jsonPath("$[0].estado").value("PENDIENTE"));
 mvc.perform(post("/api/caso3/documentos/"+token+"/abrir").contentType("application/json").content("{\"codigo\":\"EMP999\"}")).andExpect(status().isForbidden());
 mvc.perform(get("/api/caso3/envios").session(session)).andExpect(jsonPath("$[0].estado").value("PENDIENTE"));
 byte[] pdf=mvc.perform(post("/api/caso3/documentos/"+token+"/abrir").contentType("application/json").content("{\"codigo\":\""+emp.get("codigo").asText()+"\"}")).andExpect(status().isOk()).andReturn().getResponse().getContentAsByteArray();
 assertTrue(new String(pdf,0,4).equals("%PDF"));
 var first=json.readTree(mvc.perform(get("/api/caso3/envios").session(session)).andExpect(jsonPath("$[0].estado").value("VISTO")).andReturn().getResponse().getContentAsString()).get(0).get("fechaApertura");
 mvc.perform(post("/api/caso3/documentos/"+token+"/abrir").contentType("application/json").content("{\"codigo\":\""+emp.get("codigo").asText()+"\"}")).andExpect(status().isOk());
 var second=json.readTree(mvc.perform(get("/api/caso3/envios").session(session)).andReturn().getResponse().getContentAsString()).get(0).get("fechaApertura");assertEquals(first,second);
 mvc.perform(post("/api/caso3/documentos/invalido/abrir").contentType("application/json").content("{\"codigo\":\"EMP002\"}")).andExpect(status().isNotFound());
 mvc.perform(post("/api/caso3/envios/"+id+"/reenviar").session(session).contentType("application/json").content("{}")).andExpect(status().isOk()).andExpect(jsonPath("$.estado").value("VISTO"));
 }
}
