package br.edu.pucminas.biblioteca.modelo;

import java.time.LocalDate;

public class Ebook {

    public static final int LIMITE = 60;

    private String titulo;
    private ECategoria categoria;
    private Editora editora;
    private ETipo tipo;
    private EFormato formato;
    private int acessosAtivos;
    private LocalDate dataInicioLicenca;
    private LocalDate dataFimLicenca;

    public Ebook(String titulo, ECategoria categoria, Editora editora, ETipo tipo, EFormato formato,
            LocalDate dataInicioLicenca, LocalDate dataFimLicenca) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.editora = editora;
        this.tipo = tipo;
        this.formato = formato;
        this.dataInicioLicenca = dataInicioLicenca;
        this.dataFimLicenca = dataFimLicenca;
        this.acessosAtivos = 0;
    }

    public String getTitulo() {
        return titulo;
    }

    public ECategoria getCategoria() {
        return categoria;
    }

    public Editora getEditora() {
        return editora;
    }

    public ETipo getTipo() {
        return tipo;
    }

    public EFormato getFormato() {
        return formato;
    }

    public LocalDate getDataInicioLicenca() {
        return dataInicioLicenca;
    }

    public LocalDate getDataFimLicenca() {
        return dataFimLicenca;
    }

    // HU08
    public boolean licencaVigente() {
        LocalDate hoje = LocalDate.now();
        return !hoje.isBefore(dataInicioLicenca) && !hoje.isAfter(dataFimLicenca);
    }

    // HU13
    public boolean possuiLicencaDisponivel() {
        return acessosAtivos < LIMITE;
    }

    // HU13
    public void ocuparLicenca() {
        acessosAtivos++;
    }

    // HU13
    public void liberarLicenca() {
        if (acessosAtivos > 0) {
            acessosAtivos--;
        }
    }

    public LocalDate getDataFimLicenca() {
        return dataFimLicenca;
    }
    
}
