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
}
