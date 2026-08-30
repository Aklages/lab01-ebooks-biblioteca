package br.edu.pucminas.biblioteca.modelo;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Estante {

    public static final int LIVRES = 2;
    public static final int OBRIGATORIOS = 4;

    private final List<Ebook> ebooks = new LinkedList<>();

    // HU10
    public boolean add(Ebook ebook) {
        if (ebook == null || ebooks.contains(ebook)) {
            return false;
        }

        if (vagasRestantes(ebook.getTipo()) <= 0) {
            return false;
        }

        ebooks.add(ebook);
        return true;
    }

    // HU10
    public boolean contem(Ebook ebook) {
        return ebooks.contains(ebook);
    }

    // HU11
    public boolean remove(Ebook ebook) {
        return ebook != null && ebooks.remove(ebook);
    }

    // HU11
    public int vagasRestantes(ETipo tipo) {
        int limite = tipo == ETipo.OBRIGATORIO ? OBRIGATORIOS : LIVRES;
        long ocupadas = ebooks.stream().filter(ebook -> ebook.getTipo() == tipo).count();
        return limite - (int) ocupadas;
    }

    // HU12
    public Map<ETipo, List<Ebook>> consultar() {
        Map<ETipo, List<Ebook>> agrupado = new EnumMap<>(ETipo.class);
        for (ETipo tipo : ETipo.values()) {
            agrupado.put(tipo, new LinkedList<>());
        }
        for (Ebook ebook : ebooks) {
            agrupado.get(ebook.getTipo()).add(ebook);
        }
        return agrupado;
    }

    // Usado pelo RepositorioEstantes para restaurar a estante persistida, sem passar pelas validações da HU10
    void carregarEbook(Ebook ebook) {
        ebooks.add(ebook);
    }

    public List<Ebook> getEbooks() {
        return ebooks;
    }
}
