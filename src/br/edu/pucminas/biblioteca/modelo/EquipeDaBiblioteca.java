package br.edu.pucminas.biblioteca.modelo;

import java.time.LocalDate;

public class EquipeDaBiblioteca extends Usuario {

    // HU02
    public boolean cadastrarEbook(String titulo, Editora editora, EFormato formato, ECategoria categoria, ETipo tipo, LocalDate dataInicioLicenca, LocalDate dataFimLicenca) {
        // TODO: implementar na Sprint 3
        return false;
    }

    // HU03
    public boolean cadastrarUsuario(String matricula, String senha, String perfil) {
        // TODO: implementar na Sprint 3
        return false;
    }

    // HU05
    public boolean cadastrarCategoria(String nome) {
        // TODO: implementar na Sprint 3
        return false;
    }

    // HU04
    public boolean cadastrarEditora(String nome) {
        // TODO: implementar na Sprint 3
        return false;
    }

    // HU06
    public boolean cadastrarPeriodoDeAcesso(String semestre, LocalDate dataInicio, LocalDate dataFim) {
        // TODO: implementar na Sprint 3
        return false;
    }

    // HU07
    public boolean renovarLicenca(Ebook ebook, LocalDate novaDataFimLicenca) {
        // TODO: implementar na Sprint 3
        return false;
    }
}
