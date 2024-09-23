package com.example.appmynote.entidades;

public class Note {
    private final String titulo;
    private String prioridade;
    private String conteudo;
    private String  caminho;


    public Note(String titulo, String prioridade, String conteudo, String caminho) {
        this.titulo = titulo;
        this.prioridade = prioridade;
        this.conteudo = conteudo;
        this.caminho = caminho;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }


    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
