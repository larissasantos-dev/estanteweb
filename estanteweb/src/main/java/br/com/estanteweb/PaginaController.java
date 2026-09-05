package br.com.estanteweb;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PaginaController {

    @ResponseBody
    @GetMapping("/")
    public String boasVindas(){
        return "EstanteWeb: livros usados com história";
    }

    @ResponseBody
    @GetMapping("/sobre")
    public String sobrenos(){
        return "Somos uma livraria online que vende livros usados!";
    }

    @GetMapping("/contato" )
    public String paginaContato(Model model){
        return "contato";
    }

    @GetMapping("/contato/sucesso")
    public String confirmada(@RequestParam(required = false) String nome, Model model){
        model.addAttribute("nome", nome);
        return "contato-sucesso";
    }

    @PostMapping("/contato")
    public String receberContato(Model model,
            @RequestParam String nome, @RequestParam String mensagem){

        if(mensagem.isBlank()){
            model.addAttribute("erro", "Obrigatório informar uma mensagem");
            model.addAttribute("nome", nome);
            return "contato";
        }

        return "redirect:/contato/sucesso?nome=" + nome;
    }

}