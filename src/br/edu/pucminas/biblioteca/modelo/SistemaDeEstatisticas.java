package br.edu.pucminas.biblioteca.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SistemaDeEstatisticas {

    private static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // HU14
    public void notificar(Ebook ebook, Aluno aluno, LocalDateTime dataHora) {
        if (ebook == null || aluno == null || dataHora == null) {
            throw new IllegalArgumentException("A notificacao precisa identificar o ebook, o aluno e a data/hora.");
        }

        System.out.println("[estatisticas] " + dataHora.format(FORMATO_DATA_HORA)
                + " | aluno: " + aluno.getMatricula()
                + " | ebook: " + ebook.getTitulo());
    }
}
