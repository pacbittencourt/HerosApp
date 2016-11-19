package ufjf.heros;

import java.util.List;

/**
 * Created by Alex on 19/11/2016.
 */

public class Hero {
    private String nome;
    private String descricao;
    private List<String> poderes;

    public Hero() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getPoderes() {
        return poderes;
    }

    public void setPoderes(List<String> poderes) {
        this.poderes = poderes;
    }
}
