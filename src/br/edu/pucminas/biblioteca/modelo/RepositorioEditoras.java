package br.edu.pucminas.biblioteca.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RepositorioEditoras {

    private final String caminhoArquivo;

    private final Map<String, Editora> editoras = new LinkedHashMap<>();

    public RepositorioEditoras(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public void carregar() {
        editoras.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String nome = linha.trim();
                if (nome.isEmpty()) {
                    continue;
                }

                editoras.put(nome, new Editora(nome));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    public Map<String, Editora> getEditoras() {
        return editoras;
    }

    public Editora buscar(String nome) {
        return editoras.get(nome);
    }

    public boolean existeEditora(String nome) {
        return editoras.containsKey(nome);
    }

    // HU04
    public boolean cadastrar(String nome) {
        if (nome == null || nome.isBlank() || existeEditora(nome.trim())) {
            return false;
        }

        String nomeNormalizado = nome.trim();
        editoras.put(nomeNormalizado, new Editora(nomeNormalizado));
        persistir(nomeNormalizado);
        return true;
    }

    // HU04
    public boolean remover(String nome, List<Ebook> ebooks) {
        if (nome == null || !existeEditora(nome) || possuiEbooksAssociados(nome, ebooks)) {
            return false;
        }

        editoras.remove(nome);
        reescrever();
        return true;
    }

    // HU04
    public boolean possuiEbooksAssociados(String nome, List<Ebook> ebooks) {
        if (ebooks == null) {
            return false;
        }

        return ebooks.stream()
                .anyMatch(ebook -> ebook.getEditora() != null && ebook.getEditora().getNome().equals(nome));
    }

    private void persistir(String nome) {
        try (FileWriter fw = new FileWriter(caminhoArquivo, true)) {
            if (precisaQuebrarLinhaAntesDeEscrever()) {
                fw.write(System.lineSeparator());
            }
            fw.write(nome + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Erro ao salvar editora: " + e.getMessage());
        }
    }

    private void reescrever() {
        try (FileWriter fw = new FileWriter(caminhoArquivo, false)) {
            for (Editora editora : editoras.values()) {
                fw.write(editora.getNome() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar editora: " + e.getMessage());
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

    @Override
    public String toString() {
        return editoras.values().stream()
            .map(Editora::getNome)
            .collect(Collectors.joining("\n"));
    }
}
