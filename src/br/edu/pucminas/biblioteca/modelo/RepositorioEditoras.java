package br.edu.pucminas.biblioteca.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RepositorioEditoras {

    private final String caminhoArquivo;

    private final Map<String, Editora> editoras = new HashMap<>();

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
}
