package br.com.estanteweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FreeController {
    @GetMapping("/free")
    public String free(Model container) {
        String nome = "Larissa";
        String sobrenome = "Santos";

        container.addAttribute("nome", nome);
        container.addAttribute("sobrenome", sobrenome);

        // Adicione as duas chaves que o seu HTML atual está pedindo:
        container.addAttribute("chave1", "Larissa");
        container.addAttribute("chave2", "Santos");

        return "ola";
    }


}
