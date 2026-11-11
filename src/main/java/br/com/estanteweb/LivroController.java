
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

    @Autowired
    private LivroRepository repositorioLivro;

    @ResponseBody
    @GetMapping("/livros")
    public String livro() {

        List<Livro> livros = repositorioLivro.listarTodos();

        String acervo = "Acervo Estante Web: ";

        for (int i = 0; i < livros.size(); i++) {
            acervo += livros.get(i).getTitulo()
                    + ", de " + livros.get(i).getAutor()
                    + " (R$ " + livros.get(i).getPreco() + ")"
                    + " | ";
        }

        return acervo;
    }

    @ResponseBody
    @GetMapping("/livros/total")
    public String quantidadeLivros() {
        int total = repositorioLivro.contarLivros();
        return "O acervo tem " + total + " livros.";
    }

    // Lista os livros, permitindo ordenação
    @GetMapping("/livros/html")
    public String livroHtml(
            @RequestParam(required = false) String ordem,
            Model model) {

        List<Livro> livros;

        if (ordem == null) {
            livros = repositorioLivro.listarTodos();
        } else {
            livros = repositorioLivro.listarOrdenadoPor(ordem);
        }

        model.addAttribute("livros", livros);

        return "livros";
    }

    // Abre o formulário de cadastro
    @GetMapping("/livros/novo")
    public String novo() {
        return "livro-novo";
    }

    // Salva um novo livro
    @PostMapping("/livros")
    public String salvar(
            @RequestParam String titulo,
            @RequestParam String autor,
            @RequestParam int ano,
            @RequestParam double preco,
            Model model) {

        if (titulo.isBlank() || autor.isBlank() || preco <= 0) {
            model.addAttribute("erro",
                    "Informe título e autor e um preço maior que zero.");

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

    // Exibe a ficha de um livro
    @GetMapping("/livros/{id}")
    public String detalhar(
            @PathVariable Long id,
            Model model) {

        Livro livro = repositorioLivro.buscarPorId(id);

        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("livro", livro);

        model.addAttribute("outrosDoAutor",
                repositorioLivro.listarDoMesmoAutor(
                        livro.getAutor(), id));

        return "livro";
    }

    // Busca livros por preço máximo
    @GetMapping("/livros/busca")
    public ResponseEntity<String> buscarPorPreco(
            @RequestParam(defaultValue = "999") double precoMaximo) {

        List<Livro> encontrados =
                repositorioLivro.listarAtePreco(precoMaximo);

        if (encontrados.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body("Nenhum livro encontrado até R$ "
                            + precoMaximo + ".");
        }

        String acervo =
                "Acervo Estante Web (até R$ " + precoMaximo + "): ";

        for (int i = 0; i < encontrados.size(); i++) {
            acervo += encontrados.get(i).getTitulo()
                    + ", de " + encontrados.get(i).getAutor()
                    + " (R$ " + encontrados.get(i).getPreco() + ")"
                    + " | ";
        }

        return ResponseEntity
                .status(200)
                .header("X-Total-Encontrados",
                        String.valueOf(encontrados.size()))
                .body(acervo);
    }

    // Calcula o valor total do acervo
    @ResponseBody
    @GetMapping("/livros/valor-total")
    public String valorTotal() {
        double total = repositorioLivro.somarValorAcervo();

        return "O valor total do acervo é R$ "
                + String.format("%.2f", total) + ".";
    }

    // Identifica quem acessou a aplicação
    @ResponseBody
    @GetMapping("/livros/quem-acessa")
    public String quemAcessa(
            @RequestHeader("User-Agent") String userAgent) {

        return "Esse acesso veio de: " + userAgent;
    }

    // Abre o formulário de edição com os dados atuais
    @GetMapping("/livros/{id}/editar")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Livro livro = repositorioLivro.buscarPorId(id);

        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("livro", livro);

        return "livro-editar";
    }

    // Recebe os dados e atualiza o livro
    @PostMapping("/livros/{id}")
    public String salvarEdicao(
            @PathVariable Long id,
            @RequestParam String titulo,
            @RequestParam String autor,
            @RequestParam String ano,
            @RequestParam String preco,
            Model model) {

        Livro livroExistente = repositorioLivro.buscarPorId(id);

        if (livroExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        int anoConvertido;
        double precoConvertido;

        try {
            anoConvertido = Integer.parseInt(ano);
            precoConvertido = Double.parseDouble(preco);

        } catch (NumberFormatException e) {

            model.addAttribute("erro",
                    "Ano e preço precisam ser números válidos.");

            model.addAttribute("livro", livroExistente);
            model.addAttribute("titulo", titulo);
            model.addAttribute("autor", autor);
            model.addAttribute("ano", ano);
            model.addAttribute("preco", preco);

            return "livro-editar";
        }

        if (titulo.isBlank()
                || autor.isBlank()
                || precoConvertido <= 0) {

            model.addAttribute("erro",
                    "Informe título e autor e um preço maior que zero.");

            model.addAttribute("livro", livroExistente);
            model.addAttribute("titulo", titulo);
            model.addAttribute("autor", autor);
            model.addAttribute("ano", ano);
            model.addAttribute("preco", preco);

            return "livro-editar";
        }

        Livro livro = new Livro();

        livro.setId(id);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno(anoConvertido);
        livro.setPreco(precoConvertido);

        repositorioLivro.atualizar(livro);

        return "redirect:/livros/" + id;
    }

    // Exibe a confirmação de exclusão
    @GetMapping("/livros/{id}/excluir")
    public String confirmarExclusao(
            @PathVariable Long id,
            Model model) {

        Livro livro = repositorioLivro.buscarPorId(id);

        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("livro", livro);

        return "livro-excluir";
    }

    // Exclui o livro após a confirmação
    @PostMapping("/livros/{id}/excluir")
    public String excluir(@PathVariable Long id) {

        Livro livro = repositorioLivro.buscarPorId(id);

        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        repositorioLivro.excluir(id);

        return "redirect:/livros/html";
    }
}
