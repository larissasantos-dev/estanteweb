package br.com.estanteweb;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SugestaoController {

    private SugestaoRepository repositorioSugestao = new SugestaoRepository();

    @GetMapping("/sugestoes/nova")
    public String paginaNovaSugestao(@RequestParam(required = false) String nomeCliente, Model model){
        model.addAttribute("nomeCliente", nomeCliente);
        return "sugestao-nova";
    }

    @PostMapping("/sugestoes")
    public String receberSugestao(Model model,
            @RequestParam String nomeCliente, @RequestParam String livroSugerido){

        if(nomeCliente.isBlank() || livroSugerido.isBlank()){
            model.addAttribute("erro", "Obrigatório informar nome e livro");
            model.addAttribute("nomeCliente", nomeCliente);
            model.addAttribute("livroSugerido", livroSugerido);
            return "sugestao-nova";
        }

        repositorioSugestao.salvar(new Sugestao(nomeCliente, livroSugerido));

        return "redirect:/sugestoes?enviada=true";
    }

    @GetMapping("/sugestoes")
    public String listarSugestoes(@RequestParam(required = false) Boolean enviada, Model model){
        model.addAttribute("sugestoes", repositorioSugestao.listarTodas());
        model.addAttribute("enviada", enviada);
        return "sugestoes";
    }
}
