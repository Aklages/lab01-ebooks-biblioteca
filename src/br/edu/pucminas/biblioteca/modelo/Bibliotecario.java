package br.edu.pucminas.biblioteca.modelo;

import java.util.Collection;
import java.util.List;

public class Bibliotecario extends Usuario {

    public Bibliotecario(String matricula, String senha){
        super(matricula, senha, EPerfil.Bibliotecario);
    }
    // HU08
    public List<Ebook> consultarCatalogo(Catalogo catalogo, ECategoria categoria, String titulo) {
        if (catalogo == null) {
            return List.of();
        }

        return catalogo.consultarCatalogo(categoria, titulo);
    }

    // HU09
    public List<Aluno> consultarAlunosComEbook(Ebook ebook, Collection<Aluno> alunos) {
        if (ebook == null || alunos == null) {
            return List.of();
        }

        return alunos.stream()
                .filter(aluno -> aluno.getEstante().contem(ebook))
                .toList();
    }
}
