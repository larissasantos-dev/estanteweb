package br.com.estanteweb;

import java.util.ArrayList;
import java.util.List;

public class SugestaoRepository {

    private List<Sugestao> listaSugestoes = new ArrayList<>();

    public void adicionar(Sugestao s){
        listaSugestoes.add(s);
    }

    public List<Sugestao> listarTodas(){
        return listaSugestoes;
    }

    public int contarSugestoes(){
        return listaSugestoes.size();
    }

    public void salvar(Sugestao sugestao) {
    }
}
