package br.edu.pucminas.biblioteca.modelo;

import java.util.Collection;
import java.util.List;

public class Bibliotecario extends Usuario {

    // HU08
    public List<Ebook> consultarCatalogo(ECategoria categoria, String titulo) {
        // TODO: implementar na Sprint 3
        return null;
    }

    // HU09
    public List<Aluno> consultarAlunosComEbook(Ebook ebook) {
        Collection<Aluno> consultarAlunos = null;
        return consultarAlunos.stream()
                .filter(aluno -> aluno.getEstante().getEbooks().contains(ebook))
                .toList();
    }
}
