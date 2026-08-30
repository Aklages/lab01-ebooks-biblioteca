package br.edu.pucminas.biblioteca.modelo;

import java.time.LocalDate;

public class PeriodoDeAcesso {

    private String semestre;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public PeriodoDeAcesso(String semestre, LocalDate dataInicio, LocalDate dataFim) {
        this.semestre = semestre;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public String getSemestre() {
        return semestre;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    // HU06
    public boolean estaVigente() {
        LocalDate hoje = LocalDate.now();
        return !hoje.isBefore(dataInicio) && !hoje.isAfter(dataFim);
    }
}
