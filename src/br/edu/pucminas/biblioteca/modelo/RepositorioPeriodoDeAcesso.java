package br.edu.pucminas.biblioteca.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class RepositorioPeriodoDeAcesso {

    private final String caminhoArquivo;

    private final List<PeriodoDeAcesso> periodos = new LinkedList<>();

    public RepositorioPeriodoDeAcesso(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public void carregar() {
        periodos.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] dados = linha.split(";");
                String semestre = dados[0];
                LocalDate dataInicio = LocalDate.parse(dados[1]);
                LocalDate dataFim = LocalDate.parse(dados[2]);

                periodos.add(new PeriodoDeAcesso(semestre, dataInicio, dataFim));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao converter dado do periodo de acesso: " + e.getMessage());
        }
    }

    public List<PeriodoDeAcesso> getPeriodos() {
        return periodos;
    }

    // HU10
    public boolean existePeriodoVigente() {
        return periodos.stream().anyMatch(PeriodoDeAcesso::estaVigente);
    }
}
