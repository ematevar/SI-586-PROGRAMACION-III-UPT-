package com.empresa.pedidos.infrastructure.web;


 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.GetMapping;


 @Controller
 public class LoginController {
     @GetMapping("/")
 public String inicio(){return "redirect:/menu";}
 @GetMapping("/login")
     public String login() { return "login"; }


         @GetMapping("/menu")
         public String menu() { return "menu"; }
 }
