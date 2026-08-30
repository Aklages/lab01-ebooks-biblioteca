package br.edu.pucminas.biblioteca;

import br.edu.pucminas.biblioteca.modelo.Aluno;
import br.edu.pucminas.biblioteca.modelo.Catalogo;
import br.edu.pucminas.biblioteca.modelo.ECategoria;
import br.edu.pucminas.biblioteca.modelo.EFormato;
import br.edu.pucminas.biblioteca.modelo.EPerfil;
import br.edu.pucminas.biblioteca.modelo.ETipo;
import br.edu.pucminas.biblioteca.modelo.Ebook;
import br.edu.pucminas.biblioteca.modelo.Editora;
import br.edu.pucminas.biblioteca.modelo.Estante;
import br.edu.pucminas.biblioteca.modelo.RepositorioEditoras;
import br.edu.pucminas.biblioteca.modelo.RepositorioEstantes;
import br.edu.pucminas.biblioteca.modelo.RepositorioPeriodoDeAcesso;
import br.edu.pucminas.biblioteca.modelo.RepositorioUsuarios;
import br.edu.pucminas.biblioteca.modelo.Usuario;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuPrincipal {
    static RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios("data\\usuarios.csv");
    static RepositorioEditoras repositorioEditoras = new RepositorioEditoras("data\\editoras.csv");

    static Catalogo catalogo = new Catalogo("data\\ebooks.csv", repositorioEditoras);
    static RepositorioEstantes repositorioEstantes = new RepositorioEstantes("data\\estantes.csv", repositorioUsuarios, catalogo);
    static RepositorioPeriodoDeAcesso repositorioPeriodoDeAcesso = new RepositorioPeriodoDeAcesso("data\\periodos.csv");

    static Usuario usuarioLogado;

    static Scanner teclado;

    static final String CONTEUDO_LEITURA = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor
            incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis
            nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.

            Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu
            fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
            culpa qui officia deserunt mollit anim id est laborum.

            Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium
            doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore
            veritatis et quasi architecto beatae vitae dicta sunt explicabo.
            """;

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
        catalogo.carregar();
        repositorioEstantes.carregar();
        repositorioPeriodoDeAcesso.carregar();
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
                    System.out.println("3 - Adicionar eBook à Estante");
                    System.out.println("4 - Acessar eBook");
                    System.out.println("0 - Deslogar");
                    numOpcoes = 4;
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
                    case 1 -> consultarCatalogo();
                    case 2 -> consultarEstante();
                    case 3 -> adicionarEbookEstante();
                    case 4 -> acessarEbook();
                }
            }
            case EPerfil.Bibliotecario -> {
                switch (opcao) {
                    case 1 -> consultarCatalogo();
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
            if(repositorioUsuarios.existeMatricula(perfil, matricula))
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

        String nomeEditora;
        Editora editora = null;

        while(editora == null){
            nomeEditora = lerString("Editora");
            if(nomeEditora.equals("0"))
                return;
            editora = repositorioEditoras.buscar(nomeEditora);
            if (editora == null) {
                System.out.println("Editora não cadastrada.");
                System.out.println("Editoras cadastradas:");
                System.out.println(repositorioEditoras.toString());
                System.out.println("(digite 0 para sair)");
            }
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

        while(dataInicio == null || dataFim == null || dataInicio.isAfter(dataFim)){
            System.out.println("Data de inicio ou Data de fim da licencao não pode ser nulo.\nData de inicio não pode ser posterior a Data de fim.");
            dataInicio = lerData("Data de inicio da licenca");
            dataFim = lerData("Data de fim da licenca");
        }

        if (catalogo.cadastrarEbook(titulo, editora, formato, categoria, tipo, dataInicio, dataFim)) {
            System.out.println("Ebook cadastrado com sucesso.");
        } else {
            System.out.println("Não foi possível cadastrar o eBook. Verifique os dados informados.");
        }
    }

    static void consultarCatalogo(){
        cabecalho();

        int opcaoCategoria = -1;
        while (opcaoCategoria < 0 || opcaoCategoria > 3) {
            System.out.println("0 - Todas");
            System.out.println("1 - Literatura");
            System.out.println("2 - Tecnico");
            System.out.println("3 - Periodico");
            opcaoCategoria = lerInteiro("Categoria");
        }
        ECategoria categoria = switch (opcaoCategoria) {
            case 1 -> ECategoria.literatura;
            case 2 -> ECategoria.tecnico;
            case 3 -> ECategoria.periodico;
            default -> null;
        };

        String tituloBusca = lerString("Buscar por titulo (deixe em branco para nao filtrar)");

        List<Ebook> resultado = catalogo.consultarCatalogo(categoria, tituloBusca);

        cabecalho();
        if (resultado.isEmpty()) {
            System.out.println("Nenhum eBook encontrado no catálogo.");
        } else {
            for (Ebook ebook : resultado) {
                System.out.println(ebook.getTitulo() + " | " + ebook.getEditora().getNome() + " | "
                        + ebook.getFormato() + " | " + ebook.getCategoria());
            }
        }

        pausa();
    }

    static void consultarEstante(){
        cabecalho();

        Aluno aluno = (Aluno) usuarioLogado;
        Estante estante = aluno.getEstante();
        Map<ETipo, List<Ebook>> ebooksPorTipo = estante.consultar();

        for (ETipo tipo : ETipo.values()) {
            int limite = tipo == ETipo.OBRIGATORIO ? Estante.OBRIGATORIOS : Estante.LIVRES;
            int vagasRestantes = estante.vagasRestantes(tipo);
            List<Ebook> ebooksDoTipo = ebooksPorTipo.get(tipo);

            System.out.println(tipo + " (" + (limite - vagasRestantes) + " de " + limite + "):");
            if (ebooksDoTipo.isEmpty()) {
                System.out.println("  Nenhum eBook adicionado.");
            } else {
                for (Ebook ebook : ebooksDoTipo) {
                    System.out.println("  " + ebook.getTitulo() + " | " + ebook.getEditora().getNome() + " | "
                            + ebook.getFormato());
                }
            }
        }

        pausa();
    }

    static void adicionarEbookEstante(){
        cabecalho();

        Aluno aluno = (Aluno) usuarioLogado;

        List<Ebook> ebooksDisponiveis = catalogo.consultarCatalogo(null, null);
        if (ebooksDisponiveis.isEmpty()) {
            System.out.println("Nenhum eBook disponível no catálogo.");
            pausa();
            return;
        }

        for (Ebook ebook : ebooksDisponiveis) {
            System.out.println(ebook.getTitulo() + " | " + ebook.getEditora().getNome() + " | "
                    + ebook.getFormato() + " | " + ebook.getCategoria() + " | " + ebook.getTipo());
        }

        String tituloEscolhido = lerString("Titulo do eBook (deixe em branco para cancelar)");
        if (tituloEscolhido.isBlank()) {
            return;
        }

        Ebook ebook = catalogo.buscarPorTitulo(tituloEscolhido);
        if (ebook == null || !ebook.licencaVigente()) {
            System.out.println("eBook não encontrado no catálogo.");
            pausa();
            return;
        }

        boolean periodoVigente = repositorioPeriodoDeAcesso.existePeriodoVigente();
        if (!periodoVigente) {
            System.out.println("Fora do período de acesso vigente. Não é possível adicionar eBooks à estante.");
            pausa();
            return;
        }

        if (aluno.getEstante().contem(ebook)) {
            System.out.println("Este eBook já está na sua estante.");
            pausa();
            return;
        }

        if (aluno.getEstante().vagasRestantes(ebook.getTipo()) <= 0) {
            int limite = ebook.getTipo() == ETipo.OBRIGATORIO ? Estante.OBRIGATORIOS : Estante.LIVRES;
            System.out.println("Limite de " + limite + " eBooks de leitura " + ebook.getTipo() + " atingido.");
            pausa();
            return;
        }

        if (repositorioEstantes.adicionar(aluno, ebook, periodoVigente)) {
            System.out.println("eBook adicionado à estante com sucesso.");
        } else {
            System.out.println("Não foi possível adicionar o eBook à estante.");
        }

        pausa();
    }

    static void acessarEbook(){
        cabecalho();

        Aluno aluno = (Aluno) usuarioLogado;
        Estante estante = aluno.getEstante();
        Map<ETipo, List<Ebook>> ebooksPorTipo = estante.consultar();

        boolean estanteVazia = ebooksPorTipo.values().stream().allMatch(List::isEmpty);
        if (estanteVazia) {
            System.out.println("Sua estante está vazia.");
            pausa();
            return;
        }

        for (List<Ebook> ebooksDoTipo : ebooksPorTipo.values()) {
            for (Ebook ebook : ebooksDoTipo) {
                System.out.println(ebook.getTitulo() + " | " + ebook.getEditora().getNome() + " | "
                        + ebook.getFormato() + " | " + ebook.getTipo());
            }
        }

        String tituloEscolhido = lerString("Titulo do eBook que deseja acessar (deixe em branco para cancelar)");
        if (tituloEscolhido.isBlank()) {
            return;
        }

        Ebook ebook = catalogo.buscarPorTitulo(tituloEscolhido);
        if (ebook == null || !estante.contem(ebook)) {
            System.out.println("Este eBook não está na sua estante.");
            pausa();
            return;
        }

        if (!ebook.possuiLicencaDisponivel()) {
            System.out.println("Todas as licenças de uso simultâneo deste eBook estão ocupadas. Tente novamente mais tarde.");
            pausa();
            return;
        }

        if (!aluno.acessarEbook(ebook)) {
            System.out.println("Não foi possível acessar o eBook.");
            pausa();
            return;
        }

        lerEbook(ebook);

        aluno.encerrarLeitura(ebook);
    }

    static void lerEbook(Ebook ebook){
        cabecalho();
        System.out.println(ebook.getTitulo() + " | " + ebook.getEditora().getNome());
        System.out.println("==========================\n");
        System.out.println(CONTEUDO_LEITURA);
        System.out.println("Tecle Enter para fechar o eBook.");
        teclado.nextLine();
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