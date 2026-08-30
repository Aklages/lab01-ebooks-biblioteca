package br.edu.pucminas.biblioteca.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class RepositorioUsuarios {

    private final String caminhoArquivo;

    private final Map<String, Aluno> alunos = new HashMap<>();
    private final Map<String, Bibliotecario> bibliotecarios = new HashMap<>();
    private final Map<String, EquipeDaBiblioteca> equipe = new HashMap<>();

    public RepositorioUsuarios(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public void carregar() {
        alunos.clear();
        bibliotecarios.clear();
        equipe.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                try {
                    String[] dados = linha.split(";");
                    int perfil = Integer.parseInt(dados[0]);
                    String matricula = dados[1];
                    String senha = dados[2];

                    switch (perfil) {
                        case 1 -> alunos.put(matricula, new Aluno(matricula, senha));
                        case 2 -> bibliotecarios.put(matricula, new Bibliotecario(matricula, senha));
                        case 3 -> equipe.put(matricula, new EquipeDaBiblioteca(matricula, senha));
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Linha ignorada em " + caminhoArquivo + ": " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    public Map<String, Aluno> getAlunos() {
        return alunos;
    }

    public Map<String, Bibliotecario> getBibliotecarios() {
        return bibliotecarios;
    }

    public Map<String, EquipeDaBiblioteca> getEquipe() {
        return equipe;
    }

    public Usuario buscar(EPerfil perfil, String matricula) {
        return switch (perfil) {
            case Aluno -> alunos.get(matricula);
            case Bibliotecario -> bibliotecarios.get(matricula);
            case EquipeDaBiblioteca -> equipe.get(matricula);
        };
    }

    public boolean existeMatricula(EPerfil perfil, String matricula) {
        return switch(perfil) {
            case Aluno -> alunos.containsKey(matricula);
            case Bibliotecario -> bibliotecarios.containsKey(matricula);
            case EquipeDaBiblioteca -> equipe.containsKey(matricula);
        };
    }

    // HU03
    public boolean cadastrarAluno(String matricula, String senha) {
        if (existeMatricula(EPerfil.Aluno, matricula)) {
            return false;
        }

        alunos.put(matricula, new Aluno(matricula, senha));
        persistir(1, matricula, senha);
        return true;
    }

    // HU03
    public boolean cadastrarBibliotecario(String matricula, String senha) {
        if (existeMatricula(EPerfil.Bibliotecario, matricula)) {
            return false;
        }

        bibliotecarios.put(matricula, new Bibliotecario(matricula, senha));
        persistir(2, matricula, senha);
        return true;
    }

    // HU03
    public boolean cadastrarEquipe(String matricula, String senha) {
        if (existeMatricula(EPerfil.EquipeDaBiblioteca, matricula)) {
            return false;
        }

        equipe.put(matricula, new EquipeDaBiblioteca(matricula, senha));
        persistir(3, matricula, senha);
        return true;
    }

    private void persistir(int perfil, String matricula, String senha) {
        try (FileWriter fw = new FileWriter(caminhoArquivo, true)) {
            if (precisaQuebrarLinhaAntesDeEscrever()) {
                fw.write(System.lineSeparator());
            }
            fw.write(perfil + ";" + matricula + ";" + senha + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuario: " + e.getMessage());
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
