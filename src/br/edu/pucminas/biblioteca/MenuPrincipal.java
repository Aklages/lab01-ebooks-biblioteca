package br.edu.pucminas.biblioteca;

import br.edu.pucminas.biblioteca.modelo.Aluno;
import br.edu.pucminas.biblioteca.modelo.Bibliotecario;
import br.edu.pucminas.biblioteca.modelo.EBook;
import br.edu.pucminas.biblioteca.modelo.EPerfil;
import br.edu.pucminas.biblioteca.modelo.Editora;
import br.edu.pucminas.biblioteca.modelo.EquipeDaBiblioteca;
import br.edu.pucminas.biblioteca.modelo.Estante;
import br.edu.pucminas.biblioteca.persistencia.EBookRepositorioArquivo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuPrincipal {
    static List<Aluno> alunos = new LinkedList<>();
    static Map<Integer, Bibliotecario> bibliotecarios = new HashMap<>();
    static Map<Integer, EquipeDaBiblioteca> pessoasEquipe = new HashMap<>();
    static List<Editora> editoras = new LinkedList<>();

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
        System.out.println("App XGB v3\n================");
    }

    static void config(){
        //LeitorImoveis leitorImoveis = new LeitorImoveis();
        //imoveis = leitorImoveis.lerDados("src/contasImoveis.csv");
    }

    static int exibirMenuPrincipal(EPerfil perfil) {
        cabecalho();
        System.out.println("1 - Cadastrar imóveis");
        System.out.println("2 - Cadastrar contas e associar a um imóvel");
        System.out.println("3 - Listar imóveis (código)");
        System.out.println("4 - Detalhar imóveis");
        System.out.println("=============================");
        System.out.println("5 - Cadastrar proprietário");
        System.out.println("6 - Associar imóvel a proprietário");
        System.out.println("=============================");
        System.out.println("7 - Registrar contrato de aluguel");
        System.out.println("8 - Verificar resultados de proprietários");
        System.out.println("0 - Finalizar");
        return lerInteiro("Digite sua escolha");
    }

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("1. Adicionar eBook a estante");
            System.out.println("2. Consultar estante");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opcao: ");

            int opcao;
            try {
                opcao = Integer.parseInt(leitor.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido.");
                continue;
            }

            try {
                switch (opcao) {
                    case 1:
                        // chamar aluno.adicionarEBook(ebook)
                        break;
                    case 2:
                        // chamar estante.listar()
                        break;
                    case 3:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opcao invalida, tente novamente.");
                }
            } catch (IllegalStateException e) {
                System.out.println("Nao foi possivel concluir a acao: " + e.getMessage());
            }
        }
        leitor.close();
    }
}