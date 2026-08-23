package br.edu.pucminas.biblioteca.modelo;

public class Ebook {

    public static final int LIMITE = 60;

    private String titulo;
    private Categoria categoria;
    private Editora editora;
    private ETipo tipo;
    private EFormato formato;
    private int acessosAtivos;
    private boolean licencaRenovada;
}
