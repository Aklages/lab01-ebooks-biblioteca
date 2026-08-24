package br.edu.pucminas.biblioteca.modelo;

import java.time.LocalDate;
import java.util.List;

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


    // HU04
    public boolean cadastrarEditora(String nome, List<Editora> editorasCadastradas) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome da editora não pode ser nulo ou vazio.");
        } else if (editorasCadastradas.stream().anyMatch(e -> e.getNome().equals(nome))) {
            throw new IllegalArgumentException("A editora já está cadastrada.");
        }
        return true;
       
    }

    // HU06
    public boolean cadastrarPeriodoDeAcesso(String semestre, LocalDate dataInicio, LocalDate dataFim) {
        if (semestre == null || semestre.isEmpty()) {
            throw new IllegalArgumentException("O semestre não pode ser nulo ou vazio.");
        } else if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("As datas de início e fim não podem ser nulas.");
        } else if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data de fim.");
        }
        return true;
    }

    // HU07
    public boolean renovarLicenca(Ebook ebook, LocalDate novaDataFimLicenca) {
        if (ebook == null) {
            throw new IllegalArgumentException("O ebook não pode ser nulo.");
        } else if (novaDataFimLicenca == null) {
            throw new IllegalArgumentException("A nova data de fim de licença não pode ser nula.");
        } else if (novaDataFimLicenca.isBefore(ebook.getDataFimLicenca())) {
            throw new IllegalArgumentException("A nova data de fim de licença não pode ser anterior à data de fim de licença atual.");
        }
        return true;
    }
}
