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


public static Sugestao deMap(Map<String, Object> linha) {
    Sugestao sugestao = new Sugestao();
    sugestao.setNomeCliente((String) linha.get("nome_cliente"));
    sugestao.setLivroSugerido((String) linha.get("livro_sugerido"));
    return sugestao;
}


repositorio.salvar(sugestao);
