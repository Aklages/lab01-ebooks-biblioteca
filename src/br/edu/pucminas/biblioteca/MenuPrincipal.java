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

    static int exibirMenuPrincipal() {
        cabecalho();
        switch (usuarioLogado.getPerfil()) {
            case EPerfil.Aluno -> {
                System.out.println("1 - Consultar Catalógo");
                System.out.println("2 - Consultar Estante");
                System.out.println("0 - Deslogar");
            }
            case EPerfil.Bibliotecario -> {
                System.out.println("1 - Consultar Catalógo");
                System.out.println("2 - Consultar Alunos com Ebook");
                System.out.println("0 - Deslogar");
            }
            case EPerfil.EquipeDaBiblioteca -> {
                System.out.println("1 - Cadastrar Periodo de Acesso");
                System.out.println("2 - Renovar Licença do Ebook");
                System.out.println("3 - Cadastrar Ebook");
                System.out.println("4 - Cadastrar Usuario");
                System.out.println("5 - Cadastrar Editora");
                System.out.println("0 - Deslogar");
            }   
        }
        return lerInteiro("Digite sua escolha");
    }

    static boolean realizarLogin() {
        boolean loginOuSenhaEhInvalido = true;
        boolean encerrar = false;
        int opcaoPerfil;
        EPerfil tipoPerfil = EPerfil.Aluno;
        String login;
        String senha;
        Usuario encontrado = null;

        cabecalho();
        while(loginOuSenhaEhInvalido && !encerrar){
            System.out.println("Escolha o tipo de perfil que deseja logar: ");
            System.out.println("1 - Aluno");
            System.out.println("2 - Bibliotecario");
            System.out.println("3 - Equipe da bliblioteca");
            System.out.println("0 - Encerrar");
            opcaoPerfil = lerInteiro("Opcao");
            switch (opcaoPerfil) {
                case 1 -> tipoPerfil = EPerfil.Aluno;
                case 2 -> tipoPerfil = EPerfil.Bibliotecario;
                case 3 -> tipoPerfil = EPerfil.EquipeDaBiblioteca;
                case 0 -> encerrar = true;
            }
            if(!encerrar){
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
                    loginOuSenhaEhInvalido = false;
                }
            }
        }

        return !encerrar;
    }

    public static void main(String[] args) {
        teclado = new Scanner(System.in);
        
        boolean continuar = true;
        int opcao = 1;

        config();

        while(continuar){
            continuar = realizarLogin();
            while(opcao != 0 && continuar){
                opcao = exibirMenuPrincipal();
            }
            opcao = 1;
        }
    }
}