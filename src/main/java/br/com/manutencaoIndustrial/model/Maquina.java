package br.com.manutencaoIndustrial.model;

import br.com.manutencaoIndustrial.enums.StatusMaquina;

public class Maquina {
    private int id;
    private String nome;
    private String setor;
    private String status;

    public Maquina(int id, String nome, String setor, String status) {
        this.id = id;
        this.nome = nome;
        this.setor = setor;
        this.status = status;
    }

    public Maquina(String nome, String setor, String status) {
        this.nome = nome;
        this.setor = setor;
        this.status = status;
    }

    public Maquina(int id, String nome, String setor) {
        this.id = id;
        this.nome = nome;
        this.setor = setor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toStringBasico() {
        return "\nID: " + getId() + "\nNOME: " + getNome() + "\nSETOR: " + getSetor() + "\n<>------------------<>";
    }
}
