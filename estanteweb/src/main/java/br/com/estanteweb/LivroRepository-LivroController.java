public void salvar(Livro livro) {
    jdbcTemplate.update(
            "INSERT INTO livro (titulo, autor, ano, preco) VALUES (?, ?, ?, ?)",
            livro.getTitulo(),
            livro.getAutor(),
            livro.getAno(),
            livro.getPreco());
}


@GetMapping("/livros/novo")
public String novo() {
    return "livro-novo";
}

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
    repositorio.salvar(livro);

    return "redirect:/livros/html";
}
