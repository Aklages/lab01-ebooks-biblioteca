package br.edu.pucminas.biblioteca.modelo;

import java.time.LocalDate;

public class Ebook {

    public static final int LIMITE = 60;

    private String titulo;
    private Categoria categoria;
    private Editora editora;
    private ETipo tipo;
    private EFormato formato;
    private int acessosAtivos;
    private LocalDate dataInicioLicenca;
    private LocalDate dataFimLicenca;

    // HU08
    public boolean licencaVigente() {
        // TODO: implementar na Sprint 3
        return false;
    }

    // HU13
    public boolean possuiLicencaDisponivel() {
        // TODO: implementar na Sprint 3
        return false;
    }

    // HU13
    public void ocuparLicenca() {
        // TODO: implementar na Sprint 3
    }

    // HU13
    public void liberarLicenca() {
        // TODO: implementar na Sprint 3
    }
}
