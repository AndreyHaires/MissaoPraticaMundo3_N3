package cadastrobd;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaFisicaDAO;
import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.PessoaJuridicaDAO;
import cadastro.model.util.ConectorBD;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

public class CadastroBDTeste {

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        try (Connection connection = ConectorBD.getConnection()) {
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO(connection);
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO(connection);

            // Dados para inserção de pessoa física
            PessoaFisica pessoaFisicaParaInserir = new PessoaFisica();
            pessoaFisicaParaInserir.setNome("João da Silva");
            pessoaFisicaParaInserir.setLogradouro("Rua 12, casa 3, Quitanda");
            pessoaFisicaParaInserir.setCidade("Riacho do Sul");
            pessoaFisicaParaInserir.setEstado("PA");
            pessoaFisicaParaInserir.setTelefone("47-32552311");
            pessoaFisicaParaInserir.setEmail("joao@riacho.com");
            pessoaFisicaParaInserir.setCpf("841.329.470-33");

            // Inserir pessoa física
            pessoaFisicaDAO.incluirPessoaFisica(pessoaFisicaParaInserir);
            System.out.println("\n*Cadastro de Pessoa Física Realizado com Sucesso*");
            
            // Alterar uma Pessoa Física no banco de dados
            alterarPessoaFisica(connection, pessoaFisicaDAO);                           
            System.out.println("\n*Dados de Pessoa Física Alterados*");
            
            // Exibir todos os cadastros de pessoa física
            System.out.println("\n---------- Exibindo Cadastros de Pessoa Física ----------");
            pessoaFisicaDAO.exibirTodosFisica().forEach(System.out::println);
            
            // Excluir cadastro de Pessoa Física pelo ID
            int idParaExcluirPF = 7; // Substitua pelo ID da Pessoa Física que deseja excluir
            pessoaFisicaDAO.excluirPessoaFisica(idParaExcluirPF);
            System.out.println("\n*Cadastro de Pessoa Fisica Excluido com Sucesso*");
           
            // Dados para inserção de pessoa jurídica
            PessoaJuridica pessoaJuridicaParaInserir = new PessoaJuridica();
            pessoaJuridicaParaInserir.setNome("Microsoft Informatica Ltda");
            pessoaJuridicaParaInserir.setLogradouro("Av. Presidente Juscelino Kubitscheck, 1.909 - Torre Sul - 16º andar");
            pessoaJuridicaParaInserir.setCidade("São Paulo");
            pessoaJuridicaParaInserir.setEstado("SP");
            pessoaJuridicaParaInserir.setTelefone("48-32431335");
            pessoaJuridicaParaInserir.setEmail("microsoft@microsoft.com");
            pessoaJuridicaParaInserir.setCnpj("46.857.039/0001-20");

            // Inserir Pessoa Jurídica
            pessoaJuridicaDAO.incluirPessoaJuridica(pessoaJuridicaParaInserir);
            System.out.println("\n*Cadastro de Pessoa Jurídica Realizado com Sucesso*");
            
            // Alterar uma Pessoa Jurídica no banco de dados
            alterarPessoaJuridica(connection, pessoaJuridicaDAO);                           
            System.out.println("\n*Dados de Pessoa jurídica Alterados*");            
                      
            //Exibir todos os cadastros de pessoa jurídica
            System.out.println("\n---------- Exibindo Cadastros de Pessoa Jurídica ----------");
            pessoaJuridicaDAO.exibirTodasJuridicas().forEach(System.out::println);
            
            
            // Excluir cadastro de Pessoa Jurídica pelo ID
            int idParaExcluirPJ = 8; // Substitua pelo ID da Pessoa Juridica que deseja excluir
            pessoaJuridicaDAO.excluirPessoaJuridica(idParaExcluirPJ);
            System.out.println("\n*Cadastro de Pessoa Jurídica Excluido com Sucesso*");
            
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    // Método para alterar uma Pessoa Física no banco de dados
    private static void alterarPessoaFisica(Connection connection, PessoaFisicaDAO pessoaFisicaDAO) {       
        int idParaAlterar = 7; // Defina o ID da pessoa que deseja alterar

        try {
            // Verifica se a pessoa com o ID especificado existe no banco
            if (pessoaFisicaDAO.pessoaExiste(idParaAlterar)) {
                // Dados para alteração de pessoa física
                PessoaFisica pessoaFisicaParaAlterar = new PessoaFisica();
                pessoaFisicaParaAlterar.setId(idParaAlterar); // Define o ID da pessoa a ser alterada
                pessoaFisicaParaAlterar.setNome("Maria dos Santos");
                pessoaFisicaParaAlterar.setLogradouro("Rua 15 de Setembro, 45, Centro");
                pessoaFisicaParaAlterar.setCidade("Curitiba");
                pessoaFisicaParaAlterar.setEstado("PR");
                pessoaFisicaParaAlterar.setTelefone("41-32771598");
                pessoaFisicaParaAlterar.setEmail("maria@hotmail.com");
                pessoaFisicaParaAlterar.setCpf("44254189729");

                // Alterar pessoa física no banco
                pessoaFisicaDAO.alterarPessoaFisica(pessoaFisicaParaAlterar);
              
            } else {
                System.out.println("Pessoa com o ID especificado não encontrada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao alterar Pessoa Física no banco de dados: " + e.getMessage());
        }
    }
    
    // Método para alterar uma Pessoa Jurídica no banco de dados
    private static void alterarPessoaJuridica(Connection connection, PessoaJuridicaDAO pessoaJuridicaDAO) {
        int idParaAlterar = 8; // Defina o ID da pessoa jurídica que deseja alterar

        try {
            // Verifica se a pessoa jurídica com o ID especificado existe no banco
            if (pessoaJuridicaDAO.pessoaExiste(idParaAlterar)) {
                // Dados para alteração de pessoa jurídica
                PessoaJuridica pessoaJuridicaParaAlterar = new PessoaJuridica();
                pessoaJuridicaParaAlterar.setId(idParaAlterar); // Define o ID da pessoa jurídica a ser alterada
                pessoaJuridicaParaAlterar.setNome("IBM");
                pessoaJuridicaParaAlterar.setLogradouro("Rua Nova Veneza, 127, Bela Vista");
                pessoaJuridicaParaAlterar.setCidade("Ipiranga");
                pessoaJuridicaParaAlterar.setEstado("RS");
                pessoaJuridicaParaAlterar.setTelefone("11-98765432");
                pessoaJuridicaParaAlterar.setEmail("contato@ibm.com");
                pessoaJuridicaParaAlterar.setCnpj("70.673.504/0001-58");

                // Alterar pessoa jurídica no banco
                pessoaJuridicaDAO.alterarPessoaJuridica(pessoaJuridicaParaAlterar);


            } else {
                System.out.println("Pessoa Jurídica com o ID especificado não encontrada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao alterar Pessoa Jurídica no banco de dados: " + e.getMessage());
        }
    }

}
