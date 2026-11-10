package br.com.estanteweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Controller
public class LivroController {

    // Missão 1.2: o repositório é injetado pelo Spring, sem "new"
    @Autowired
    private LivroRepository repositorioLivro;

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

    // Semana 9, Missão 4.1: "ordem" vem da QUERY STRING (/livros/html?ordem=preco).
    // É opcional: sem ela, a rota continua sendo a mesma e lista sem ordenação.
    @GetMapping("/livros/html")
    public String livroHtml(@RequestParam(required = false) String ordem, Model model){
        List<Livro> livros;

        if (ordem == null) {
            livros = repositorioLivro.listarTodos();
        } else {
            livros = repositorioLivro.listarOrdenadoPor(ordem);
        }

        model.addAttribute("livros", livros);

        return "livros";
    }

    // Semana 8: exibe o formulário de cadastro
    @GetMapping("/livros/novo")
    public String novo() {
        return "livro-novo";
    }

    // Semana 8: grava o livro, com validação
    @PostMapping("/livros")
    public String salvar(@RequestParam String titulo,
                         @RequestParam String autor,
                         @RequestParam int ano,
                         @RequestParam double preco,
                         Model model) {

        if (titulo.isBlank() || autor.isBlank() || preco <= 0) {
            model.addAttribute("erro", "Informe título e autor e um preço maior que zero.");
            model.addAttribute("titulo", titulo);
            model.addAttribute("autor", autor);
            model.addAttribute("ano", ano);
            model.addAttribute("preco", preco);
            return "livro-novo";
        }

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno(ano);
        livro.setPreco(preco);
        repositorioLivro.salvar(livro);

        return "redirect:/livros/html";
    }

    // Semana 9, Missões 1 e 2: a ficha de UM livro.
    // {id} marca o trecho variável do caminho; o @PathVariable entrega esse trecho já
    // convertido para Long. Se não for número (/livros/abc), o Spring responde 400
    // ANTES de executar o corpo do método.
    @GetMapping("/livros/{id}")
    public String detalhar(@PathVariable Long id, Model model) {
        Livro livro = repositorioLivro.buscarPorId(id);

        // Missão 2.2: id válido na forma, mas sem linha no banco -> 404.
        // A verificação vem antes de qualquer uso do objeto.
        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("livro", livro);

        // Missão 4.4 (desafio): outros títulos do mesmo autor
        model.addAttribute("outrosDoAutor",
                repositorioLivro.listarDoMesmoAutor(livro.getAutor(), id));

        return "livro";
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

    // Semana 7, Missão 4.4 (desafio): valor total do acervo, calculado pelo banco
    @ResponseBody
    @GetMapping("/livros/valor-total")
    public String valorTotal(){
        double total = repositorioLivro.somarValorAcervo();
        return "O valor total do acervo é R$ " + String.format("%.2f", total) + ".";
    }

    // Missão 2.3: lê o cabeçalho User-Agent da requisição
    @ResponseBody
    @GetMapping("/livros/quem-acessa")
    public String quemAcessa(@RequestHeader("User-Agent") String userAgent){
        return "Esse acesso veio de: " + userAgent;
    }
}
