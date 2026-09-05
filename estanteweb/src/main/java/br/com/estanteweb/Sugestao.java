package br.com.estanteweb;

public class Sugestao {
    private String nomeCliente;
    private String livroSugerido;

    public Sugestao(String nomeCliente, String livroSugerido){
        this.nomeCliente = nomeCliente;
        this.livroSugerido = livroSugerido;
    }

    public String getNomeCliente(){
        return nomeCliente;
    }

    public String getLivroSugerido(){
        return livroSugerido;
    }
}
