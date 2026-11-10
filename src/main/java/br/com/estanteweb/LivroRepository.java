package br.com.estanteweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Missão 1.1: @Repository torna a classe um bean gerenciado pelo Spring
@Repository
public class LivroRepository {

    // Semana 9, Missão 4.2: colunas que podem aparecer no ORDER BY.
    // O nome da coluna não pode ser enviado como "?" (o ? só vale para valores),
    // então ele é concatenado no SQL. Por isso só entra se estiver nesta lista.
    private static final List<String> COLUNAS_PERMITIDAS = List.of("titulo", "autor", "ano", "preco");

    // Missão 1.1: o Spring injeta o JdbcTemplate que ele mesmo montou
    // a partir do application.properties (Semana 6)
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Missão 3.3: busca o acervo no banco e converte cada linha em Livro
    public List<Livro> listarTodos() {
        String sql = "SELECT * FROM livro";
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);

        List<Livro> livros = new ArrayList<>();
        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }
        return livros;
    }

    // Missão 2.1: a contagem agora é feita pelo próprio banco
    public int contarLivros(){
        String sql = "SELECT COUNT(*) FROM livro";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Missão 4.1: o filtro é feito pelo banco, com parâmetro posicional (?)
    public List<Livro> listarAtePreco(double precoMaximo){
        String sql = "SELECT * FROM livro WHERE preco <= ?";
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql, precoMaximo);

        List<Livro> encontrados = new ArrayList<>();
        for (Map<String, Object> linha : linhas) {
            encontrados.add(Livro.deMap(linha));
        }
        return encontrados;
    }

    // Missão 4.4 (desafio): soma dos preços de todo o acervo
    public double somarValorAcervo(){
        String sql = "SELECT SUM(preco) FROM livro";
        Double total = jdbcTemplate.queryForObject(sql, Double.class);
        // SUM devolve NULL quando a tabela está vazia
        return total != null ? total : 0.0;
    }

    // Semana 8: grava um livro novo com INSERT
    public void salvar(Livro livro) {
        jdbcTemplate.update(
                "INSERT INTO livro (titulo, autor, ano, preco) VALUES (?, ?, ?, ?)",
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAno(),
                livro.getPreco());
    }

    // Semana 9, Missão 1.1: busca UM livro pelo identificador.
    // queryForList aceita zero, uma ou várias linhas sem reclamar;
    // o isEmpty() separa o caso "não existe" do caso "existe".
    public Livro buscarPorId(Long id) {
        String sql = "SELECT * FROM livro WHERE id = ?";
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql, id);

        if (linhas.isEmpty()) {
            return null;
        }
        // id é chave primária: havendo linha, só pode haver uma
        return Livro.deMap(linhas.get(0));
    }

    // Semana 9, Missão 4.2: lista o acervo ordenado por uma coluna permitida.
    // Qualquer valor fora da lista cai na listagem padrão (sem ORDER BY).
    public List<Livro> listarOrdenadoPor(String coluna) {
        if (!COLUNAS_PERMITIDAS.contains(coluna)) {
            return listarTodos();
        }

        // Seguro concatenar: neste ponto, coluna é exatamente um dos quatro nomes da lista
        String sql = "SELECT * FROM livro ORDER BY " + coluna;
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);

        List<Livro> livros = new ArrayList<>();
        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }
        return livros;
    }

    // Semana 9, Missão 4.4 (desafio): outros títulos do mesmo autor, exceto o atual
    public List<Livro> listarDoMesmoAutor(String autor, Long idAtual) {
        String sql = "SELECT * FROM livro WHERE autor = ? AND id <> ?";
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql, autor, idAtual);

        List<Livro> livros = new ArrayList<>();
        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }
        return livros;
    }
}
