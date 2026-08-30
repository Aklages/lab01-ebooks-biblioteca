package br.edu.pucminas.biblioteca.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RepositorioEstantes {

    private final String caminhoArquivo;

    private final RepositorioUsuarios repositorioUsuarios;
    private final Catalogo catalogo;

    public RepositorioEstantes(String caminhoArquivo, RepositorioUsuarios repositorioUsuarios, Catalogo catalogo) {
        this.caminhoArquivo = caminhoArquivo;
        this.repositorioUsuarios = repositorioUsuarios;
        this.catalogo = catalogo;
    }

    // HU12
    public void carregar() {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] dados = linha.split(";");
                String matricula = dados[0];
                String tituloEbook = dados[1];

                Aluno aluno = repositorioUsuarios.getAlunos().get(matricula);
                Ebook ebook = catalogo.buscarPorTitulo(tituloEbook);

                if (aluno != null && ebook != null) {
                    aluno.getEstante().carregarEbook(ebook);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    // HU10
    public boolean adicionar(Aluno aluno, Ebook ebook, boolean periodoDeAcessoVigente) {
        boolean adicionado = aluno.adicionarEbookEstante(ebook, periodoDeAcessoVigente);
        if (adicionado) {
            persistir(aluno, ebook);
        }
        return adicionado;
    }

    // HU11
    public boolean remover(Aluno aluno, Ebook ebook, boolean periodoDeAcessoVigente) {
        boolean removido = aluno.removerEbookEstante(ebook, periodoDeAcessoVigente);
        if (removido) {
            reescrever();
        }
        return removido;
    }

    private void reescrever() {
        try (FileWriter fw = new FileWriter(caminhoArquivo, false)) {
            for (Aluno aluno : repositorioUsuarios.getAlunos().values()) {
                for (Ebook ebook : aluno.getEstante().getEbooks()) {
                    fw.write(aluno.getMatricula() + ";" + ebook.getTitulo() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar estante: " + e.getMessage());
        }
    }

    private void persistir(Aluno aluno, Ebook ebook) {
        try (FileWriter fw = new FileWriter(caminhoArquivo, true)) {
            if (precisaQuebrarLinhaAntesDeEscrever()) {
                fw.write(System.lineSeparator());
            }
            fw.write(aluno.getMatricula() + ";" + ebook.getTitulo() + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Erro ao salvar estante: " + e.getMessage());
        }
    }

    // Evita concatenar a nova linha na última linha existente quando o arquivo não termina com quebra de linha
    private boolean precisaQuebrarLinhaAntesDeEscrever() throws IOException {
        Path caminho = Path.of(caminhoArquivo);
        if (!Files.exists(caminho) || Files.size(caminho) == 0) {
            return false;
        }

        byte[] conteudo = Files.readAllBytes(caminho);
        byte ultimoByte = conteudo[conteudo.length - 1];
        return ultimoByte != '\n' && ultimoByte != '\r';
    }
}
