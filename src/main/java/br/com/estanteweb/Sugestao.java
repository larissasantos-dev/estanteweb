package br.com.estanteweb;

import java.util.Map;

public class Sugestao {
    private String nomeCliente;
    private String livroSugerido;

    public Sugestao() {
    }

    public Sugestao(String nomeCliente, String livroSugerido){
        this.nomeCliente = nomeCliente;
        this.livroSugerido = livroSugerido;
    }

    // Converte o Map de UMA linha da tabela sugestao em um objeto Sugestao
    public static Sugestao deMap(Map<String, Object> linha) {
        Sugestao sugestao = new Sugestao();
        sugestao.setNomeCliente((String) linha.get("nome_cliente"));
        sugestao.setLivroSugerido((String) linha.get("livro_sugerido"));
        return sugestao;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setLivroSugerido(String livroSugerido) {
        this.livroSugerido = livroSugerido;
    }

    public String getNomeCliente(){
        return nomeCliente;
    }

    public String getLivroSugerido(){
        return livroSugerido;
    }
}
