package br.edu.pucminas.biblioteca.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    // HU06
    public boolean cadastrar(String semestre, LocalDate dataInicio, LocalDate dataFim) {
        if (semestre == null || semestre.isBlank()
                || dataInicio == null
                || dataFim == null
                || !dataFim.isAfter(dataInicio)
                || existeSobreposicao(semestre, dataInicio, dataFim)) {
            return false;
        }

        PeriodoDeAcesso periodo = new PeriodoDeAcesso(semestre, dataInicio, dataFim);
        periodos.add(periodo);
        persistir(periodo);
        return true;
    }

    // HU06
    public boolean existeSobreposicao(String semestre, LocalDate dataInicio, LocalDate dataFim) {
        return periodos.stream().anyMatch(periodo -> periodo.sobrepoe(semestre, dataInicio, dataFim));
    }

    private void persistir(PeriodoDeAcesso periodo) {
        try (FileWriter fw = new FileWriter(caminhoArquivo, true)) {
            if (precisaQuebrarLinhaAntesDeEscrever()) {
                fw.write(System.lineSeparator());
            }
            fw.write(periodo.getSemestre() + ";" + periodo.getDataInicio() + ";" + periodo.getDataFim()
                    + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Erro ao salvar periodo de acesso: " + e.getMessage());
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
