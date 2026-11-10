package br.com.estanteweb;

import java.util.Map;

public class Livro {
    private long id;
    private String titulo;
    private String autor;
    private int ano;
    private double preco;

    // Missão 3.1: construtor vazio; o objeto é preenchido campo a campo pelos setters
    public Livro() {
    }

    // Missão 3.2: converte o Map de UMA linha da tabela livro em um objeto Livro.
    // Valores numéricos passam primeiro por Number, porque o driver pode entregar
    // Long, Integer ou BigDecimal (a coluna preco é DECIMAL).
    public static Livro deMap(Map<String, Object> linha) {
        Livro livro = new Livro();
        livro.setId(((Number) linha.get("id")).longValue());
        livro.setTitulo((String) linha.get("titulo"));
        livro.setAutor((String) linha.get("autor"));
        livro.setAno(((Number) linha.get("ano")).intValue());
        livro.setPreco(((Number) linha.get("preco")).doubleValue());
        return livro;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setAutor(String autor){
        this.autor = autor;
    }

    public void setAno(int ano){
        this.ano = ano;
    }

    public void setPreco(double preco){
        this.preco = preco;
    }

    public long getId(){
        return id;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getAutor(){
        return autor;
    }

    public int getAno(){
        return ano;
    }

    public double getPreco() {
        return preco;
    }
}
