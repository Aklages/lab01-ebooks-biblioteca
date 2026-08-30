package br.edu.pucminas.biblioteca.modelo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Catalogo {

    private final List<Ebook> ebooks = new LinkedList<>();

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

        ebooks.add(new Ebook(titulo, categoria, editora, tipo, formato, dataInicioLicenca, dataFimLicenca));
        return true;
    }

    public List<Ebook> getEbooks() {
        return ebooks;
    }
}
