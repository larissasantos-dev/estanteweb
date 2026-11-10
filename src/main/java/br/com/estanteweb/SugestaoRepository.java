package br.com.estanteweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SugestaoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void salvar(Sugestao sugestao) {
        jdbcTemplate.update(
                "INSERT INTO sugestao (nome_cliente, livro_sugerido) VALUES (?, ?)",
                sugestao.getNomeCliente(),
                sugestao.getLivroSugerido());
    }

    public List<Sugestao> listarTodas() {
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList("SELECT * FROM sugestao");
        List<Sugestao> sugestoes = new ArrayList<>();
        for (Map<String, Object> linha : linhas) {
            sugestoes.add(Sugestao.deMap(linha));
        }
        return sugestoes;
    }
}
