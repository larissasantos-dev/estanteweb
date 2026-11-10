package br.com.estanteweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TesteController {
    @GetMapping("/ola")
    public String ola(Model model){
        String nome = "EstanteWeb";
        int ano = 1998;

        model.addAttribute("nomeLoja", nome);
        model.addAttribute("anoFundacao", ano);

        return "ola";
    }
}