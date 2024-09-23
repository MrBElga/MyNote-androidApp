package com.example.appmynote.entidades;

public class Note {
    private final String titulo;
    private String prioridade;
    private String conteudo;

    public Note(String titulo, String prioridade, String conteudo) {
        this.titulo = titulo;
        this.prioridade = prioridade;
        this.conteudo = conteudo;
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
}
