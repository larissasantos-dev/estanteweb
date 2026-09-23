package br.com.estanteweb;

import java.util.ArrayList;
import java.util.List;

public class LivroRepository {

    private List<Livro> listaLivros= new ArrayList<>();

    public LivroRepository(){
        listaLivros.add(new Livro("O Ladrão de Raios", "Rick Riordan", 15.00));
        listaLivros.add(new Livro("O Mar de Monstros", "Rick Riordan", 14.50));
        listaLivros.add(new Livro("A Maldição do Titã", "Rick Riordan", 16.00));
        listaLivros.add(new Livro("A Batalha do Labirinto", "Rick Riordan", 18.00));
        listaLivros.add(new Livro("O Último Olimpiano", "Rick Riordan", 19.90));
        listaLivros.add(new Livro("A <b>Marca de Atena</b>", "Rick Riordan", 22.00));
    }

    public List<Livro> listarTodos() {
        return listaLivros;
    }

    public int contarLivros(){
        int qtd = 0;
        for(int i = 0; i < listaLivros.size(); i++){
            qtd += 1;
        }
        return qtd;
    }

    public List<Livro> listarAtePreco(double precoMaximo){
        List<Livro> encontrados = new ArrayList<>();
        for(int i = 0; i < listaLivros.size(); i++){
            if(listaLivros.get(i).getPreco() <= precoMaximo){
                encontrados.add(listaLivros.get(i));
            }
        }
        return encontrados;
    }
}