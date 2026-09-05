package br.com.estanteweb;

public class Livro {
    private String titulo;
    private String autor;
    private int ano;
    private double preco;

    public Livro(String titulo, String autor, double preco){
        this.titulo = titulo;
        this.autor = autor;
        this.preco = preco;
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