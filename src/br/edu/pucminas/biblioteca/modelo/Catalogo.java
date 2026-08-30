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

public class Catalogo {

    private final String caminhoArquivo;

    private final RepositorioEditoras repositorioEditoras;

    private final List<Ebook> ebooks = new LinkedList<>();

    public Catalogo(String caminhoArquivo, RepositorioEditoras repositorioEditoras) {
        this.caminhoArquivo = caminhoArquivo;
        this.repositorioEditoras = repositorioEditoras;
    }

    public void carregar() {
        ebooks.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                try {
                    String[] dados = linha.split(";");
                    String titulo = dados[0];
                    ECategoria categoria = ECategoria.valueOf(dados[1]);
                    Editora editora = repositorioEditoras.buscar(dados[2]);
                    ETipo tipo = ETipo.valueOf(dados[3]);
                    EFormato formato = EFormato.valueOf(dados[4]);
                    LocalDate dataInicioLicenca = LocalDate.parse(dados[5]);
                    LocalDate dataFimLicenca = LocalDate.parse(dados[6]);

                    ebooks.add(new Ebook(titulo, categoria, editora, tipo, formato, dataInicioLicenca, dataFimLicenca));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Linha ignorada em " + caminhoArquivo + ": " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    // HU02
    public boolean cadastrarEbook(String titulo, Editora editora, EFormato formato, ECategoria categoria,
            ETipo tipo, LocalDate dataInicioLicenca, LocalDate dataFimLicenca) {
        if (titulo == null || titulo.isBlank()
                || editora == null
                || formato == null
                || categoria == null
                || tipo == null
                || dataInicioLicenca == null
                || dataFimLicenca == null
                || !dataFimLicenca.isAfter(dataInicioLicenca)) {
            return false;
        }

        Ebook ebook = new Ebook(titulo, categoria, editora, tipo, formato, dataInicioLicenca, dataFimLicenca);
        ebooks.add(ebook);
        persistir(ebook);
        return true;
    }

    public List<Ebook> getEbooks() {
        return ebooks;
    }

    public Ebook buscarPorTitulo(String titulo) {
        return ebooks.stream()
                .filter(ebook -> ebook.getTitulo().equals(titulo))
                .findFirst()
                .orElse(null);
    }

    // HU08
    public List<Ebook> consultarCatalogo(ECategoria categoriaFiltro, String tituloBusca) {
        return ebooks.stream()
                .filter(Ebook::licencaVigente)
                .filter(ebook -> categoriaFiltro == null || ebook.getCategoria() == categoriaFiltro)
                .filter(ebook -> tituloBusca == null || tituloBusca.isBlank()
                        || ebook.getTitulo().toLowerCase().contains(tituloBusca.toLowerCase()))
                .toList();
    }

    // HU07
    public void salvar() {
        try (FileWriter fw = new FileWriter(caminhoArquivo, false)) {
            for (Ebook ebook : ebooks) {
                fw.write(ebook.getTitulo() + ";" + ebook.getCategoria() + ";" + ebook.getEditora().getNome() + ";"
                        + ebook.getTipo() + ";" + ebook.getFormato() + ";" + ebook.getDataInicioLicenca() + ";"
                        + ebook.getDataFimLicenca() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar ebook: " + e.getMessage());
        }
    }

    private void persistir(Ebook ebook) {
        try (FileWriter fw = new FileWriter(caminhoArquivo, true)) {
            if (precisaQuebrarLinhaAntesDeEscrever()) {
                fw.write(System.lineSeparator());
            }
            fw.write(ebook.getTitulo() + ";" + ebook.getCategoria() + ";" + ebook.getEditora().getNome() + ";"
                    + ebook.getTipo() + ";" + ebook.getFormato() + ";" + ebook.getDataInicioLicenca() + ";"
                    + ebook.getDataFimLicenca() + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Erro ao salvar ebook: " + e.getMessage());
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
