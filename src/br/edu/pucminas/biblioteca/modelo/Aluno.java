package br.edu.pucminas.biblioteca.modelo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Aluno extends Usuario {

    private final Estante estante = new Estante();
    private final SistemaDeEstatisticas sistemaDeEstatisticas = new SistemaDeEstatisticas();

    public Aluno(String matricula, String senha){
        super(matricula, senha, EPerfil.Aluno);
    }

    public Estante getEstante() {
        return estante;
    }

    // HU08
    public List<Ebook> consultarCatalogo(ECategoria categoria, String titulo) {
        // TODO: implementar na Sprint 3
        return null;
    }

    // HU10
    public boolean adicionarEbookEstante(Ebook ebook, boolean periodoDeAcessoVigente) {
        if (!periodoDeAcessoVigente) {
            return false;
        }

        boolean adicionado = estante.add(ebook);
        if (adicionado) {
            sistemaDeEstatisticas.notificar(ebook, this, LocalDateTime.now());
        }

        return adicionado;
    }

    // HU11
    public boolean removerEbookEstante(Ebook ebook) {
        // TODO: implementar na Sprint 3
        return false;
    }

    // HU12
    public Map<ETipo, List<Ebook>> consultarEstante() {
        // TODO: implementar na Sprint 3
        return null;
    }

    // HU13
    public boolean acessarEbook(Ebook ebook) {
        // TODO: implementar na Sprint 3
        return false;
    }

    // HU13
    public void encerrarLeitura(Ebook ebook) {
        // TODO: implementar na Sprint 3
    }
}
