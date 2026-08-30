package br.edu.pucminas.biblioteca.modelo;

import java.time.LocalDate;
import java.util.Collection;

public class EquipeDaBiblioteca extends Usuario {

    public static final int MINIMO_ALUNOS_RENOVACAO = 3;

    public EquipeDaBiblioteca(String matricula, String senha){
        super(matricula, senha, EPerfil.EquipeDaBiblioteca);
    }
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
    public boolean cadastrarEditora(String nome, RepositorioEditoras repositorioEditoras) {
        if (repositorioEditoras == null) {
            return false;
        }

        return repositorioEditoras.cadastrar(nome);
    }

    // HU06
    public boolean cadastrarPeriodoDeAcesso(String semestre, LocalDate dataInicio, LocalDate dataFim, RepositorioPeriodoDeAcesso repositorioPeriodoDeAcesso) {
        if (repositorioPeriodoDeAcesso == null) {
            return false;
        }

        return repositorioPeriodoDeAcesso.cadastrar(semestre, dataInicio, dataFim);
    }

    // HU07
    public boolean renovarLicenca(Ebook ebook, LocalDate novaDataFimLicenca, Collection<Aluno> alunos, RepositorioPeriodoDeAcesso repositorioPeriodoDeAcesso) {
        if (ebook == null
                || novaDataFimLicenca == null
                || alunos == null
                || repositorioPeriodoDeAcesso == null
                || repositorioPeriodoDeAcesso.existePeriodoVigente()
                || !novaDataFimLicenca.isAfter(ebook.getDataFimLicenca())
                || contarAlunosComEbook(ebook, alunos) < MINIMO_ALUNOS_RENOVACAO) {
            return false;
        }

        ebook.renovarLicenca(novaDataFimLicenca);
        return true;
    }

    // HU07
    public int contarAlunosComEbook(Ebook ebook, Collection<Aluno> alunos) {
        return (int) alunos.stream()
                .filter(aluno -> aluno.getEstante().contem(ebook))
                .count();
    }
}
