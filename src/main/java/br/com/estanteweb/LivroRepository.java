
package br.com.estanteweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class LivroRepository {

    private static final List<String> COLUNAS_PERMITIDAS =
            List.of("titulo", "autor", "ano", "preco");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Lista todos os livros
    public List<Livro> listarTodos() {

        String sql = "SELECT * FROM livro";

        List<Map<String, Object>> linhas =
                jdbcTemplate.queryForList(sql);

        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }

        return livros;
    }

    // Conta os livros cadastrados
    public int contarLivros() {

        String sql = "SELECT COUNT(*) FROM livro";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Lista livros até determinado preço
    public List<Livro> listarAtePreco(double precoMaximo) {

        String sql = "SELECT * FROM livro WHERE preco <= ?";

        List<Map<String, Object>> linhas =
                jdbcTemplate.queryForList(sql, precoMaximo);

        List<Livro> encontrados = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            encontrados.add(Livro.deMap(linha));
        }

        return encontrados;
    }

    // Calcula o valor total do acervo
    public double somarValorAcervo() {

        String sql = "SELECT SUM(preco) FROM livro";

        Double total =
                jdbcTemplate.queryForObject(sql, Double.class);

        return total != null ? total : 0.0;
    }

    // Salva um livro novo
    public void salvar(Livro livro) {

        String sql = "INSERT INTO livro "
                + "(titulo, autor, ano, preco) "
                + "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAno(),
                livro.getPreco()
        );
    }

    // Busca um livro pelo ID
    public Livro buscarPorId(Long id) {

        String sql = "SELECT * FROM livro WHERE id = ?";

        List<Map<String, Object>> linhas =
                jdbcTemplate.queryForList(sql, id);

        if (linhas.isEmpty()) {
            return null;
        }

        return Livro.deMap(linhas.get(0));
    }

    // Lista os livros ordenados por uma coluna permitida
    public List<Livro> listarOrdenadoPor(String coluna) {

        if (!COLUNAS_PERMITIDAS.contains(coluna)) {
            return listarTodos();
        }

        String sql = "SELECT * FROM livro ORDER BY " + coluna;

        List<Map<String, Object>> linhas =
                jdbcTemplate.queryForList(sql);

        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }

        return livros;
    }

    // Lista outros livros do mesmo autor
    public List<Livro> listarDoMesmoAutor(
            String autor,
            Long idAtual) {

        String sql =
                "SELECT * FROM livro WHERE autor = ? AND id <> ?";

        List<Map<String, Object>> linhas =
                jdbcTemplate.queryForList(sql, autor, idAtual);

        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }

        return livros;
    }

    // Atualiza os dados de um livro específico
    public void atualizar(Livro livro) {

        String sql = "UPDATE livro SET "
                + "titulo = ?, autor = ?, ano = ?, preco = ? "
                + "WHERE id = ?";

        jdbcTemplate.update(
                sql,
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAno(),
                livro.getPreco(),
                livro.getId()
        );
    }

    // Exclui um livro específico
    public void excluir(Long id) {

        String sql = "DELETE FROM livro WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
