package br.com.estanteweb;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class LivroController {

    private LivroRepository repositorioLivro = new LivroRepository();

    @ResponseBody
    @GetMapping("/livros")
    public String livro(){

        List<Livro> livros = repositorioLivro.listarTodos();

        String acervo = "Acervo Estante Web: ";
        for(int i = 0; i < livros.size(); i++) {
            acervo += livros.get(i).getTitulo()
                    + ", de " +  livros.get(i).getAutor()
                    + " (R$ " + livros.get(i).getPreco() + ")"
                    + " | ";
        }
        return acervo;
    }

    @ResponseBody
    @GetMapping("/livros/total")
    public String quantidadeLivros(){
        int total = repositorioLivro.contarLivros();
        return "O acervo tem " + total + " livros.";
    }

    @GetMapping("/livros/html")
    public String livroHtml(Model model){
        model.addAttribute("livros", repositorioLivro.listarTodos());

        return "livros";
    }

    // Missão 1 e 2: busca por preço máximo, com status e cabeçalho próprios
    @GetMapping("/livros/busca")
    public ResponseEntity<String> buscarPorPreco(
            @RequestParam(defaultValue = "999") double precoMaximo){

        List<Livro> encontrados = repositorioLivro.listarAtePreco(precoMaximo);

        if(encontrados.isEmpty()){
            return ResponseEntity
                    .status(404)
                    .body("Nenhum livro encontrado até R$ " + precoMaximo + ".");
        }

        String acervo = "Acervo Estante Web (até R$ " + precoMaximo + "): ";
        for(int i = 0; i < encontrados.size(); i++) {
            acervo += encontrados.get(i).getTitulo()
                    + ", de " + encontrados.get(i).getAutor()
                    + " (R$ " + encontrados.get(i).getPreco() + ")"
                    + " | ";
        }

        return ResponseEntity
                .status(200)
                .header("X-Total-Encontrados", String.valueOf(encontrados.size()))
                .body(acervo);
    }

    // Missão 2.3: lê o cabeçalho User-Agent da requisição
    @ResponseBody
    @GetMapping("/livros/quem-acessa")
    public String quemAcessa(@RequestHeader("User-Agent") String userAgent){
        return "Esse acesso veio de: " + userAgent;
    }
}
