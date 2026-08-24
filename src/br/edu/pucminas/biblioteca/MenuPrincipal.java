package br.edu.pucminas.biblioteca;

import br.edu.pucminas.biblioteca.modelo.Aluno;
import br.edu.pucminas.biblioteca.modelo.EBook;
import br.edu.pucminas.biblioteca.modelo.Estante;
import br.edu.pucminas.biblioteca.persistencia.EBookRepositorioArquivo;
import java.util.Scanner;

public class MenuPrincipal {
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