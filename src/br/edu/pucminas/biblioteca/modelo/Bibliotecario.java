package br.edu.pucminas.biblioteca.modelo;

import java.util.List;

public class Bibliotecario extends Usuario {

    public Bibliotecario(String matricula, String senha){
        super(matricula, senha, EPerfil.Bibliotecario);
    }
    // HU08
    public List<Ebook> consultarCatalogo(ECategoria categoria, String titulo) {
        // TODO: implementar na Sprint 3
        return null;
    }

    // HU09
    public List<Aluno> consultarAlunosComEbook(Ebook ebook) {
        // TODO: implementar na Sprint 3
        return null;
    }
}
