package br.edu.pucminas.biblioteca.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LeitorUsuarios {

    private final String caminhoArquivo;

    public LeitorUsuarios(String caminhoArquivo){
        this.caminhoArquivo = caminhoArquivo;
    }

    public Map<String, Aluno> lerAlunos(){
        Map<String, Aluno> alunos = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }
                
                String[] dadosAluno = linha.split(";");
                
                int perfil = Integer.parseInt(dadosAluno[0]);
    
                String matricula;
                String senha;

                if (perfil == 1) {
                    matricula = dadosAluno[1];
                    senha = dadosAluno[2];

                    alunos.put(matricula, new Aluno(matricula, senha));
                }
            }
            
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            return new HashMap<>();
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter número: " + e.getMessage());
            return new HashMap<>();
        }
        
        return alunos;
    }

    public Map<String, Bibliotecario> lerBibliotecarios(){
        Map<String, Bibliotecario> bibliotecarios = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }
                
                String[] dadosBibliotecario = linha.split(";");
                
                int perfil = Integer.parseInt(dadosBibliotecario[0]);
    
                String matricula;
                String senha;

                if (perfil == 2) {
                    matricula = dadosBibliotecario[1];
                    senha = dadosBibliotecario[2];

                    bibliotecarios.put(matricula, new Bibliotecario(matricula, senha));
                }
            }
            
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            return new HashMap<>();
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter número: " + e.getMessage());
            return new HashMap<>();
        }
        
        return bibliotecarios;
    }

    public Map<String, EquipeDaBiblioteca> lerEquipe(){
        Map<String, EquipeDaBiblioteca> equipe = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }
                
                String[] dadosEquipe = linha.split(";");
                
                int perfil = Integer.parseInt(dadosEquipe[0]);
    
                String matricula;
                String senha;

                if (perfil == 3) {
                    matricula = dadosEquipe[1];
                    senha = dadosEquipe[2];

                    equipe.put(matricula, new EquipeDaBiblioteca(matricula, senha));
                }
            }
            
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            return new HashMap<>();
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter número: " + e.getMessage());
            return new HashMap<>();
        }
        
        return equipe;
    }
}
