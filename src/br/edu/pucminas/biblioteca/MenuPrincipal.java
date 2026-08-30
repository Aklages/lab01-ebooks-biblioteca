package br.edu.pucminas.biblioteca;

import br.edu.pucminas.biblioteca.modelo.Catalogo;
import br.edu.pucminas.biblioteca.modelo.ECategoria;
import br.edu.pucminas.biblioteca.modelo.EFormato;
import br.edu.pucminas.biblioteca.modelo.EPerfil;
import br.edu.pucminas.biblioteca.modelo.ETipo;
import br.edu.pucminas.biblioteca.modelo.Editora;
import br.edu.pucminas.biblioteca.modelo.PeriodoDeAcesso;
import br.edu.pucminas.biblioteca.modelo.RepositorioEditoras;
import br.edu.pucminas.biblioteca.modelo.RepositorioUsuarios;
import br.edu.pucminas.biblioteca.modelo.Usuario;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    static RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios("data\\usuarios.csv");
    static RepositorioEditoras repositorioEditoras = new RepositorioEditoras("data\\editoras.csv");

    static Catalogo catalogo = new Catalogo();
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

    static LocalDate lerData(String mensagem) {
        System.out.print(mensagem + " (dd/MM/yyyy): ");
        try {
            return LocalDate.parse(teclado.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            return null;
        }
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
        repositorioUsuarios.carregar();
        repositorioEditoras.carregar();
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
            
            encontrado = repositorioUsuarios.buscar(tipoPerfil, login);

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
                    case 1 -> cadastrarUsuario();
                    case 2 -> cadastrarEbook();
                    case 3 -> System.out.println("TODO: Cadastrar Editora");
                    case 4 -> System.out.println("TODO: Renovar Licença do Ebook");
                    case 5 -> System.out.println("TODO: Cadastrar Periodo de Acesso");
                }
            }
        }
    }

    static void cadastrarUsuario(){
        int opcao = -1;
        boolean duplicado = true;

        cabecalho();
        while (opcao < 0 || opcao > 2) { 
            System.out.println("Escolha o tipo de perfil que deseja cadstrar: ");
            System.out.println("1 - Aluno");
            System.out.println("2 - Bibliotecario");
            System.out.println("0 - Voltar");
            opcao = lerInteiro("Opcao");
        }

        EPerfil perfil = mapearIdentificadorParaPerfil(opcao);

        String matricula = null;

        while(duplicado){
            matricula = lerString("Matricula");
            if(repositorioUsuarios.existeMatricula(matricula))
                System.out.println("Matricula duplicada.");
            else
                duplicado = false;
        }

        String senha = lerString("Senha");

        switch (perfil) {
            case EPerfil.Aluno -> repositorioUsuarios.cadastrarAluno(matricula, senha);
            case EPerfil.Bibliotecario -> repositorioUsuarios.cadastrarBibliotecario(matricula, senha);
        }
    }

    static void cadastrarEbook(){
        cabecalho();

        String titulo = lerString("Titulo");

        String nomeEditora = lerString("Editora");
        Editora editora = repositorioEditoras.buscar(nomeEditora);
        if (editora == null) {
            System.out.println("Editora não cadastrada.");
            return;
        }

        int opcaoFormato = -1;
        while (opcaoFormato < 1 || opcaoFormato > 2) {
            System.out.println("1 - PDF");
            System.out.println("2 - EPUB");
            opcaoFormato = lerInteiro("Formato");
        }
        EFormato formato = opcaoFormato == 1 ? EFormato.PDF : EFormato.EPUB;

        int opcaoCategoria = -1;
        while (opcaoCategoria < 1 || opcaoCategoria > 3) {
            System.out.println("1 - Literatura");
            System.out.println("2 - Tecnico");
            System.out.println("3 - Periodico");
            opcaoCategoria = lerInteiro("Categoria");
        }
        ECategoria categoria = switch (opcaoCategoria) {
            case 1 -> ECategoria.literatura;
            case 2 -> ECategoria.tecnico;
            default -> ECategoria.periodico;
        };

        int opcaoTipo = -1;
        while (opcaoTipo < 1 || opcaoTipo > 2) {
            System.out.println("1 - Obrigatoria");
            System.out.println("2 - Livre");
            opcaoTipo = lerInteiro("Tipo de leitura");
        }
        ETipo tipo = opcaoTipo == 1 ? ETipo.OBRIGATORIO : ETipo.LIVRE;

        LocalDate dataInicio = lerData("Data de inicio da licenca");
        LocalDate dataFim = lerData("Data de fim da licenca");

        if (catalogo.cadastrarEbook(titulo, editora, formato, categoria, tipo, dataInicio, dataFim)) {
            System.out.println("Ebook cadastrado com sucesso.");
        } else {
            System.out.println("Não foi possível cadastrar o eBook. Verifique os dados informados.");
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