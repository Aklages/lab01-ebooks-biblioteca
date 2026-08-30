package br.edu.pucminas.biblioteca;

import br.edu.pucminas.biblioteca.modelo.Aluno;
import br.edu.pucminas.biblioteca.modelo.Bibliotecario;
import br.edu.pucminas.biblioteca.modelo.EPerfil;
import br.edu.pucminas.biblioteca.modelo.Ebook;
import br.edu.pucminas.biblioteca.modelo.Editora;
import br.edu.pucminas.biblioteca.modelo.EquipeDaBiblioteca;
import br.edu.pucminas.biblioteca.modelo.LeitorUsuarios;
import br.edu.pucminas.biblioteca.modelo.PeriodoDeAcesso;
import br.edu.pucminas.biblioteca.modelo.Usuario;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuPrincipal {
    static List<Aluno> alunosCadastradosLista = new LinkedList<>();
    static Map<String, Aluno> alunosCadastradosMap = new HashMap<>();
    static Map<String, Bibliotecario> bibliotecariosCadastrados = new HashMap<>();
    static Map<String, EquipeDaBiblioteca> pessoasEquipeCadastrados = new HashMap<>();

    static List<Editora> editoras = new LinkedList<>();
    static List<Ebook> ebooks = new LinkedList<>();
    static List<PeriodoDeAcesso> periodos = new LinkedList<>();

    static Usuario usuarioLogado;

    static Scanner teclado;

    static int lerInteiro(String mensagem) {
        System.out.print(mensagem + ": ");
        try {
            return Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException nException) {
            return -1;
        }
    }

    static double lerDouble(String mensagem) {
        System.out.print(mensagem + ": ");
        try {
            return Double.parseDouble(teclado.nextLine());
        } catch (NumberFormatException nException) {
            return Double.NEGATIVE_INFINITY;
        }
    }

    static String lerString(String mensagem) {
        System.out.print(mensagem + ": ");
        return teclado.nextLine();
    }

    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Tecle Enter para continuar.");
        teclado.nextLine();
    }

    static void cabecalho() {
        limparTela();
        System.out.println("Sistema Biblioteca Xulambs\n==========================");
    }

    static void config(){
        LeitorUsuarios leitorUsuarios = new LeitorUsuarios("data\\usuarios.csv");
        alunosCadastradosMap = leitorUsuarios.lerAlunos();
        bibliotecariosCadastrados = leitorUsuarios.lerBibliotecarios();
        pessoasEquipeCadastrados = leitorUsuarios.lerEquipe();
    }

    static int exibirMenuPrincipal(){
        int opcao = -1;
        
        cabecalho();
        while(opcao < 0 || opcao > 1){
            System.out.println("1 - Realizar login");
            System.out.println("0 - Encerrar sistema");
            opcao = lerInteiro("Digite sua escolha");
        }

        return opcao;
    }

    static int menuOpcoesPerfil(){
        int opcao = -1;

        cabecalho();
        while (opcao < 0 || opcao > 3) { 
            System.out.println("Escolha o tipo de perfil que deseja logar: ");
            System.out.println("1 - Aluno");
            System.out.println("2 - Bibliotecario");
            System.out.println("3 - Equipe da bliblioteca");
            System.out.println("0 - Voltar");
            opcao = lerInteiro("Opcao");
        }

        return opcao;
    }

    static EPerfil mapearIdentificadorParaPerfil(int opcao){
        EPerfil perfilMapeado = null;
        
        switch (opcao) {
            case 1 -> perfilMapeado = EPerfil.Aluno;
            case 2 -> perfilMapeado = EPerfil.Bibliotecario;
            case 3 -> perfilMapeado = EPerfil.EquipeDaBiblioteca;
        }

        if(perfilMapeado == null)
            throw new IllegalArgumentException("Opção de perfil inválida, não foi possivel mapear.");

        return perfilMapeado;
    }

    //Realiza login do usuario, tornando o usuario como variavel global para facilidade de uso
    static boolean realizarLogin() {
        boolean desejaLogar = true;

        int opcao;
        EPerfil tipoPerfil;

        String login;
        String senha;

        Usuario encontrado = null;

        while(desejaLogar){
            opcao = menuOpcoesPerfil();

            // Opcao 0 significa que o usuario deseja voltar ao menu principal
            if(opcao == 0){
                desejaLogar = false;
                break;
            }

            try {
                tipoPerfil = mapearIdentificadorParaPerfil(opcao);                
            } catch (Exception ile) {
                System.out.println("Opção de perfil inválida.");
                break;
            }

            login = lerString("Login");
            senha = lerString("Senha");
            
            switch (tipoPerfil) {
                case EPerfil.Aluno -> encontrado = alunosCadastradosMap.get(login);
                case EPerfil.Bibliotecario -> encontrado = bibliotecariosCadastrados.get(login);
                case EPerfil.EquipeDaBiblioteca -> encontrado = pessoasEquipeCadastrados.get(login);
            }

            if(encontrado == null || !encontrado.login(senha)){
                System.out.print("Usuario ou Senha incorreto. Tente novamente\n\n");
            }
            else{
                usuarioLogado = encontrado;
                break;
            }
        }

        return desejaLogar;
    }

    static int exibirMenuLogado() {
        int opcao = -1;
        int numOpcoes = 0;

        cabecalho();
        while (opcao < 0 || opcao > numOpcoes) {
            switch (usuarioLogado.getPerfil()) {
                case EPerfil.Aluno -> {
                    System.out.println("1 - Consultar Catalógo");
                    System.out.println("2 - Consultar Estante");
                    System.out.println("0 - Deslogar");
                    numOpcoes = 2;
                }
                case EPerfil.Bibliotecario -> {
                    System.out.println("1 - Consultar Catalógo");
                    System.out.println("2 - Consultar Alunos com Ebook");
                    System.out.println("0 - Deslogar");
                    numOpcoes = 2;
                }
                case EPerfil.EquipeDaBiblioteca -> {
                    System.out.println("1 - Cadastrar Usuario");
                    System.out.println("2 - Cadastrar Ebook");
                    System.out.println("3 - Cadastrar Editora");
                    System.out.println("4 - Renovar Licença do Ebook");
                    System.out.println("5 - Cadastrar Periodo de Acesso");
                    System.out.println("0 - Deslogar");
                    numOpcoes = 5;
                }
            }
            opcao = lerInteiro("Digite sua escolha");
        }
        return opcao;
    }

    static void realizarOperacaoEscolhida(int opcao){
        switch (usuarioLogado.getPerfil()) {
            case EPerfil.Aluno -> {
                switch (opcao) {
                    case 1 -> System.out.print("TODO: Consultar Catalógo");
                    case 2 -> System.out.print("TODO: Consultar Estante");
                }
            }
            case EPerfil.Bibliotecario -> {
                switch (opcao) {
                    case 1 -> System.out.print("TODO: Consultar Catalógo");
                    case 2 -> System.out.print("TODO: Consultar Alunos com Ebook");
                }
            }
            case EPerfil.EquipeDaBiblioteca -> {
                switch (opcao) {
                    case 1 -> System.out.println("TODO: Cadastrar Usuario");
                    case 2 -> System.out.println("TODO: Cadastrar Ebook");
                    case 3 -> System.out.println("TODO: Cadastrar Editora");
                    case 4 -> System.out.println("TODO: Renovar Licença do Ebook");
                    case 5 -> System.out.println("TODO: Cadastrar Periodo de Acesso");
                }
            }
        }
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        config();
        
        boolean usuarioDesejaContinuar = true;
        boolean usuarioEstahLogado = false;
        int opcao;

        while(usuarioDesejaContinuar){
            opcao = exibirMenuPrincipal();
            switch (opcao) {
                case 1 -> usuarioEstahLogado = realizarLogin();
                case 0 -> usuarioDesejaContinuar = false;
            }
            if(usuarioEstahLogado){
                opcao = -1;
                while(opcao != 0){
                    opcao = exibirMenuLogado();
                    realizarOperacaoEscolhida(opcao);
                }
                usuarioEstahLogado = false;
            }
        }
    }
}