package cadastrobd;

import cadastro.model.util.ConectorBD;
import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaJuridicaDAO;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class CadastroBD {

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        try (Connection connection = ConectorBD.getConnection()) {
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO(connection);
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO(connection);

            try (Scanner scanner = new Scanner(System.in)) {
                int opcao;
                do {
                    System.out.println("==============================");
                    System.out.println("Escolha uma opção:");
                    System.out.println("1 - Incluir Pessoa");
                    System.out.println("2 - Alterar Pessoa");
                    System.out.println("3 - Excluir Pessoa");
                    System.out.println("4 - Buscar pelo ID");
                    System.out.println("5 - Exibir todos");
                    System.out.println("0 - Finalizar Programa");
                    System.out.println("==============================");

                    // Captura da opção escolhida pelo usuário
                    opcao = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha

                    // Execução da opção escolhida
                    switch (opcao) {
                        case 1:
                            incluirPessoa(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                            break;
                        case 2:
                            alterarPessoa(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                            break;
                        case 3:
                            excluirPessoa(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                            break;
                        case 4:
                            buscarPeloId(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                            break;
                        case 5:
                            exibirTodos(pessoaFisicaDAO, pessoaJuridicaDAO);
                            break;
                        case 0:
                            System.out.println("Programa finalizado.");
                            scanner.close();
                            return;
                        default:
                            System.out.println("***** Opção inválida. Tente novamente. *****");
                            break;
                    }
                } while (opcao != 0);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Erro durante a execução do sistema.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao conectar ao banco de dados.");
        }
    }

    //Incluir Pessoa
    private static void incluirPessoa(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        String tipo = "";
        while (!tipo.equals("F") && !tipo.equals("J")) {
            System.out.println("Escolha o tipo de pessoa:");
            System.out.printf("F - Pessoa Física | J - Pessoa Jurídica: ");
            tipo = scanner.nextLine().toUpperCase();
            if (!tipo.equals("F") && !tipo.equals("J")) {
                System.out.println("Opção inválida. Por favor, escolha F ou J.");
            }
        }

        try {
            if (tipo.equals("F")) {
                // Lógica para incluir Pessoa Física
                System.out.println("Incluir Pessoa Física");

                // Dados para inserção de pessoa física
                PessoaFisica pessoaFisicaParaInserir = new PessoaFisica();
                System.out.print("Nome: ");
                pessoaFisicaParaInserir.setNome(scanner.nextLine());
                System.out.print("Logradouro: ");
                pessoaFisicaParaInserir.setLogradouro(scanner.nextLine());
                System.out.print("Cidade: ");
                pessoaFisicaParaInserir.setCidade(scanner.nextLine());
                System.out.print("Estado: ");
                pessoaFisicaParaInserir.setEstado(scanner.nextLine());
                System.out.print("Telefone: ");
                pessoaFisicaParaInserir.setTelefone(scanner.nextLine());
                System.out.print("E-mail: ");
                pessoaFisicaParaInserir.setEmail(scanner.nextLine());
                System.out.print("CPF: ");
                pessoaFisicaParaInserir.setCpf(scanner.nextLine());

                // Inserir pessoa física
                pessoaFisicaDAO.incluirPessoaFisica(pessoaFisicaParaInserir);

            } else if (tipo.equals("J")) {
                // Lógica para incluir Pessoa Jurídica
                System.out.println("Incluir Pessoa Jurídica");

                // Dados para inserção de pessoa jurídica
                PessoaJuridica pessoaJuridicaParaInserir = new PessoaJuridica();
                System.out.print("Nome: ");
                pessoaJuridicaParaInserir.setNome(scanner.nextLine());
                System.out.print("Logradouro: ");
                pessoaJuridicaParaInserir.setLogradouro(scanner.nextLine());
                System.out.print("Cidade: ");
                pessoaJuridicaParaInserir.setCidade(scanner.nextLine());
                System.out.print("Estado: ");
                pessoaJuridicaParaInserir.setEstado(scanner.nextLine());
                System.out.print("Telefone: ");
                pessoaJuridicaParaInserir.setTelefone(scanner.nextLine());
                System.out.print("e-mail: ");
                pessoaJuridicaParaInserir.setEmail(scanner.nextLine());
                System.out.print("CNPJ: ");
                pessoaJuridicaParaInserir.setCnpj(scanner.nextLine());

                // Inserir pessoa jurídica
                pessoaJuridicaDAO.incluirPessoaJuridica(pessoaJuridicaParaInserir);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao incluir Pessoa no banco de dados.");
        }
    }

    //Alterar Pessoa
    private static void alterarPessoa(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        String tipo = "";
        while (!tipo.equals("F") && !tipo.equals("J")) {
            System.out.println("Escolha o tipo de pessoa:");
            System.out.printf("F - Pessoa Física | J - Pessoa Jurídica: ");
            tipo = scanner.nextLine().toUpperCase();
            if (!tipo.equals("F") && !tipo.equals("J")) {
                System.out.println("Opção inválida. Por favor, escolha F ou J.");
            }
        }

        if (tipo.equals("F")) {
            // Método para alterar uma Pessoa Física no banco de dados
            System.out.println("Alterar Pessoa Física");

            System.out.print("ID: ");
            int idParaAlterarPF = scanner.nextInt(); // Defina o ID da pessoa que deseja alterar
            scanner.nextLine(); // Consumir a quebra de linha
            try {
                // Verifica se a pessoa com o ID especificado existe no banco
                if (pessoaFisicaDAO.pessoaExiste(idParaAlterarPF)) {
                    // Dados para alteração de pessoa física
                    PessoaFisica pessoaFisicaParaAlterar = new PessoaFisica();
                    pessoaFisicaParaAlterar.setId(idParaAlterarPF); // Define o ID da pessoa a ser alterada
                    System.out.println("Preencha com os novos dados");
                    System.out.print("Nome: ");
                    pessoaFisicaParaAlterar.setNome(scanner.nextLine());
                    System.out.print("Logradouro: ");
                    pessoaFisicaParaAlterar.setLogradouro(scanner.nextLine());
                    System.out.print("Cidade: ");
                    pessoaFisicaParaAlterar.setCidade(scanner.nextLine());
                    System.out.print("Estado: ");
                    pessoaFisicaParaAlterar.setEstado(scanner.nextLine());
                    System.out.print("Telefone: ");
                    pessoaFisicaParaAlterar.setTelefone(scanner.nextLine());
                    System.out.print("E-mail: ");
                    pessoaFisicaParaAlterar.setEmail(scanner.nextLine());
                    System.out.print("CPF: ");
                    pessoaFisicaParaAlterar.setCpf(scanner.nextLine());

                    // Alterar pessoa física no banco
                    pessoaFisicaDAO.alterarPessoaFisica(pessoaFisicaParaAlterar);
                    System.out.println("\n*Dados Alterados de Pessoa Física ID(" + idParaAlterarPF + ")*");
                    System.out.println();

                } else {
                    System.out.println("Pessoa com o ID especificado não encontrada.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao alterar Pessoa Física no banco de dados: " + e.getMessage());
            }

        } else if (tipo.equals("J")) {
            // Método para alterar uma Pessoa Jurídica no banco de dados
            System.out.println("Alterar Pessoa Jurídica");

            System.out.print("ID: ");
            int idParaAlterarPJ = scanner.nextInt(); // Defina o ID da pessoa que deseja alterar
            scanner.nextLine(); // Consumir a quebra de linha
            try {
                // Verifica se a pessoa com o ID especificado existe no banco
                if (pessoaFisicaDAO.pessoaExiste(idParaAlterarPJ)) {
                    // Dados para alteração de pessoa jurídica
                    PessoaJuridica pessoaJuridicaParaAlterar = new PessoaJuridica();
                    pessoaJuridicaParaAlterar.setId(idParaAlterarPJ); // Define o ID da pessoa a ser alterada
                    System.out.println("Preencha com os novos dados");
                    System.out.print("Nome: ");
                    pessoaJuridicaParaAlterar.setNome(scanner.nextLine());
                    System.out.print("Logradouro: ");
                    pessoaJuridicaParaAlterar.setLogradouro(scanner.nextLine());
                    System.out.print("Cidade: ");
                    pessoaJuridicaParaAlterar.setCidade(scanner.nextLine());
                    System.out.print("Estado: ");
                    pessoaJuridicaParaAlterar.setEstado(scanner.nextLine());
                    System.out.print("Telefone: ");
                    pessoaJuridicaParaAlterar.setTelefone(scanner.nextLine());
                    System.out.print("E-mail: ");
                    pessoaJuridicaParaAlterar.setEmail(scanner.nextLine());
                    System.out.print("CNPJ: ");
                    pessoaJuridicaParaAlterar.setCnpj(scanner.nextLine());

                    // Alterar pessoa juridica no banco
                    pessoaJuridicaDAO.alterarPessoaJuridica(pessoaJuridicaParaAlterar);
                    System.out.println("\n*Dados Alterados de Pessoa Física ID(" + idParaAlterarPJ + ")*");
                    System.out.println();

                } else {
                    System.out.println("Pessoa com o ID especificado não encontrada.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao alterar Pessoa Física no banco de dados: " + e.getMessage());
            }

        }
    }

    //Excluir Pessoa
    private static void excluirPessoa(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        String tipo = "";
        while (!tipo.equals("F") && !tipo.equals("J")) {
            System.out.println("Escolha o tipo de pessoa:");
            System.out.printf("F - Pessoa Física | J - Pessoa Jurídica: ");
            tipo = scanner.nextLine().toUpperCase();
            if (!tipo.equals("F") && !tipo.equals("J")) {
                System.out.println("Opção inválida. Por favor, escolha F ou J.");
            }
        }

        if (tipo.equals("F")) {
            // Método para excluir uma Pessoa Física no banco de dados
            System.out.println("Excluir Pessoa Física");

            System.out.print("ID: ");
            int idParaExcluirPF = scanner.nextInt(); // Defina o ID da pessoa que deseja excluir
            scanner.nextLine(); // Consumir a quebra de linha
            try {
                // Verifica se a pessoa com o ID especificado existe no banco
                if (pessoaFisicaDAO.pessoaExiste(idParaExcluirPF)) {
                    pessoaFisicaDAO.excluirPessoaFisica(idParaExcluirPF);
                    System.out.println("\n*Cadastro de Pessoa Fisica Excluido com Sucesso*");

                } else {
                    System.out.println("Pessoa com o ID especificado não encontrada.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao excluir Pessoa Física no banco de dados: " + e.getMessage());
            }

        } else if (tipo.equals("J")) {
            // Método para alterar uma Pessoa Jurídica no banco de dados
            System.out.println("Excluir Pessoa Jurídica");

            System.out.print("ID: ");
            int idParaExcluirPJ = scanner.nextInt(); // Defina o ID da pessoa que deseja excluir
            scanner.nextLine(); // Consumir a quebra de linha
            try {
                // Verifica se a pessoa com o ID especificado existe no banco
                if (pessoaJuridicaDAO.pessoaExiste(idParaExcluirPJ)) {
                    pessoaJuridicaDAO.excluirPessoaJuridica(idParaExcluirPJ);
                    System.out.println("\n*Cadastro de Pessoa Jurídica Excluido com Sucesso*");
                } else {
                    System.out.println("Pessoa com o ID especificado não encontrada.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao excluir Pessoa Jurídica no banco de dados: " + e.getMessage());
            }

        }
    }

    //Buscar por ID
    private static void buscarPeloId(Scanner scanner, PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        String tipo = "";
        while (!tipo.equals("F") && !tipo.equals("J")) {
            System.out.println("Escolha o tipo de pessoa:");
            System.out.printf("F - Pessoa Física | J - Pessoa Jurídica: ");
            tipo = scanner.nextLine().toUpperCase();
            if (!tipo.equals("F") && !tipo.equals("J")) {
                System.out.println("Opção inválida. Por favor, escolha F ou J.");
            }
        }

        System.out.print("Digite o ID da pessoa que deseja procurar: ");
        int buscarPeloId = scanner.nextInt(); // Defina o ID da pessoa que deseja buscar
        scanner.nextLine(); // Consumir a quebra de linha

        try {
            if (tipo.equals("F")) {
                PessoaFisica pessoaFisicaEncontrada = pessoaFisicaDAO.buscarPessoaFisicaPorId(buscarPeloId);
                if (pessoaFisicaEncontrada != null) {
                    System.out.println("\n---------- Detalhes da Pessoa Física ----------");
                    System.out.println(pessoaFisicaEncontrada);
                } else {
                    System.out.println("Pessoa Física com o ID especificado não encontrada.");
                }
            } else if (tipo.equals("J")) {
                PessoaJuridica pessoaJuridicaEncontrada = pessoaJuridicaDAO.buscarPessoaJuridicaPorId(buscarPeloId);
                if (pessoaJuridicaEncontrada != null) {
                    System.out.println("\n---------- Detalhes da Pessoa Jurídica ----------");
                    System.out.println(pessoaJuridicaEncontrada);
                } else {
                    System.out.println("Pessoa Jurídica com o ID especificado não encontrada.");
                }
            } else {
                System.out.println("Opção inválida.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar pelo ID: " + e.getMessage());
        }
    }
    // Exibir todos os cadastros              
    private static void exibirTodos(PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO) {
        System.out.println("---------- Cadastros de Pessoa Física ----------");
        pessoaFisicaDAO.exibirTodosFisica().forEach(System.out::println);
        // Exibir todos os cadastros de pessoa jurídica
        System.out.println("\n---------- Cadastros de Pessoa Jurídica ----------");
        pessoaJuridicaDAO.exibirTodasJuridicas().forEach(System.out::println);

    }

}
