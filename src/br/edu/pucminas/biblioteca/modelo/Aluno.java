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
    public List<Ebook> consultarCatalogo(Catalogo catalogo, ECategoria categoria, String titulo) {
        if (catalogo == null) {
            return List.of();
        }

        return catalogo.consultarCatalogo(categoria, titulo);
    }

    // HU10
    public boolean adicionarEbookEstante(Ebook ebook, boolean periodoDeAcessoVigente) {
        if (!periodoDeAcessoVigente) {
            return false;
        }

        boolean adicionado = estante.add(ebook);
        if (adicionado) {
            try {
                sistemaDeEstatisticas.notificar(ebook, this, LocalDateTime.now());
            } catch (RuntimeException e) {
                System.err.println("Falha ao notificar o sistema de estatisticas: " + e.getMessage());
            }
        }

        return adicionado;
    }

    // HU11
    public boolean removerEbookEstante(Ebook ebook, boolean periodoDeAcessoVigente) {
        if (!periodoDeAcessoVigente) {
            return false;
        }

        return estante.remove(ebook);
    }

    // HU12
    public Map<ETipo, List<Ebook>> consultarEstante() {
        return estante.consultar();
    }

    // HU13
    public boolean acessarEbook(Ebook ebook) {
        if (ebook == null || !estante.contem(ebook) || !ebook.possuiLicencaDisponivel()) {
            return false;
        }

        ebook.ocuparLicenca();
        return true;
    }

    // HU13
    public void encerrarLeitura(Ebook ebook) {
        if (ebook != null) {
            ebook.liberarLicenca();
        }
    }
}
