package br.edu.pucminas.biblioteca.modelo;

public abstract class Usuario {

    private String matricula;
    private String senha;
    private  EPerfil perfil;

    public Usuario(String matricula, String senha, EPerfil perfil){
        this.matricula = matricula;
        this.senha = senha;
        this.perfil = perfil;
    }

    public EPerfil getPerfil(){
        return perfil;
    }

    // HU01
    public boolean login(String senha) {
        return this.senha.equals(senha);
    }
}
