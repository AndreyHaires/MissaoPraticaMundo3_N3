package cadastrobd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe para acessar e manipular dados de Pessoa Jurídica no banco de dados.
 * 
 * Autor: Andrey H. Aires
 */
public class PessoaJuridicaDAO {

    private final Connection connection;

    // Construtor que recebe uma conexão como parâmetro.
    public PessoaJuridicaDAO(Connection connection) {
        this.connection = connection;
    }

    // Exibir todos os cadastros de Pessoa Jurídica.
    public List<String> exibirTodasJuridicas() {
        List<String> cadastros = new ArrayList<>();
        String sql = "SELECT p.id_pessoa, p.nome_pessoa, p.logradouro_pessoa, p.cidade_pessoa, p.estado_pessoa, " +
                     "p.telefone_pessoa, p.email_pessoa, pj.cnpj " +
                     "FROM Pessoa p JOIN pessoa_juridica pj ON p.id_pessoa = pj.id_pessoa";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String cadastro = String.format("ID: %d%nNome: %s%nLogradouro: %s%nCidade: %s%nEstado: %s%n" +
                        "Telefone: %s%nEmail: %s%nCNPJ: %s%n",
                        resultSet.getInt("id_pessoa"),
                        resultSet.getString("nome_pessoa"),
                        resultSet.getString("logradouro_pessoa"),
                        resultSet.getString("cidade_pessoa"),
                        resultSet.getString("estado_pessoa"),
                        resultSet.getString("telefone_pessoa"),
                        resultSet.getString("email_pessoa"),
                        resultSet.getString("cnpj"));

                cadastros.add(cadastro);
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }

        return cadastros;
    }
    
    // Verifica se a pessoa com o mesmo ID já existe
    public boolean pessoaExiste(int id) throws SQLException {
        String sql = "SELECT id_pessoa FROM Pessoa WHERE id_pessoa = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    // Incluir um novo cadastro de Pessoa Jurídica no banco de dados.
    public void incluirPessoaJuridica(PessoaJuridica pessoaJuridica) throws SQLException {
        String sqlPessoa = "INSERT INTO Pessoa (nome_pessoa, logradouro_pessoa, cidade_pessoa, estado_pessoa, " +
                           "telefone_pessoa, email_pessoa) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPessoaJuridica = "INSERT INTO pessoa_juridica (id_pessoa, cnpj) VALUES (?, ?)";

        try (PreparedStatement preparedStatementPessoa = connection.prepareStatement(sqlPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatementPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica)) {

            if (pessoaJuridica.getNome() != null) {
                connection.setAutoCommit(false);

                try {
                    preparedStatementPessoa.setString(1, pessoaJuridica.getNome());
                    preparedStatementPessoa.setString(2, pessoaJuridica.getLogradouro());
                    preparedStatementPessoa.setString(3, pessoaJuridica.getCidade());
                    preparedStatementPessoa.setString(4, pessoaJuridica.getEstado());
                    preparedStatementPessoa.setString(5, pessoaJuridica.getTelefone());
                    preparedStatementPessoa.setString(6, pessoaJuridica.getEmail());
                    preparedStatementPessoa.executeUpdate();

                    try (ResultSet generatedKeys = preparedStatementPessoa.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            preparedStatementPessoaJuridica.setInt(1, id);
                            preparedStatementPessoaJuridica.setString(2, pessoaJuridica.getCnpj());
                            preparedStatementPessoaJuridica.executeUpdate();
                        }
                    }

                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                    throw e;
                } finally {
                    connection.setAutoCommit(true);
                }
            } else {
                System.out.println("Nome da pessoa não pode ser nulo. A inserção foi ignorada.");
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Altera um registro de Pessoa Jurídica pelo ID no banco de dados.
    public void alterarPessoaJuridica(PessoaJuridica pessoa) throws SQLException {
        String sqlPessoa = "UPDATE Pessoa SET nome_pessoa=?, logradouro_pessoa=?, cidade_pessoa=?, estado_pessoa=?, telefone_pessoa=?, email_pessoa=? WHERE id_pessoa=?";
        String sqlPessoaJuridica = "UPDATE pessoa_juridica SET cnpj=? WHERE id_pessoa=?";

        try (PreparedStatement preparedStatementPessoa = connection.prepareStatement(sqlPessoa);
             PreparedStatement preparedStatementPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica)) {

            // Verifica se o nome não é nulo antes de atualizar
            if (pessoa.getNome() != null) {
                // Iniciar uma transação
                connection.setAutoCommit(false);

                try {
                    // Atualizar na tabela Pessoa
                    preparedStatementPessoa.setString(1, pessoa.getNome());
                    preparedStatementPessoa.setString(2, pessoa.getLogradouro());
                    preparedStatementPessoa.setString(3, pessoa.getCidade());
                    preparedStatementPessoa.setString(4, pessoa.getEstado());
                    preparedStatementPessoa.setString(5, pessoa.getTelefone());
                    preparedStatementPessoa.setString(6, pessoa.getEmail());
                    preparedStatementPessoa.setInt(7, pessoa.getId());
                    preparedStatementPessoa.executeUpdate();

                    // Atualizar na tabela PessoaJuridica
                    preparedStatementPessoaJuridica.setString(1, pessoa.getCnpj());
                    preparedStatementPessoaJuridica.setInt(2, pessoa.getId());
                    preparedStatementPessoaJuridica.executeUpdate();

                    // Commit da transação
                    connection.commit();
                } catch (SQLException e) {
                    // Rollback em caso de exceção
                    connection.rollback();
                    throw e;
                } finally {
                    // Restaurar o modo de autocommit
                    connection.setAutoCommit(true);
                }
            } else {
                System.out.println("Nome da pessoa não pode ser nulo. A atualização foi ignorada.");
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // Exclui um registro de Pessoa Jurídica pelo ID no banco de dados.
    public void excluirPessoaJuridica(int id) throws SQLException {
        String sqlPessoaJuridica = "DELETE FROM pessoa_juridica WHERE id_pessoa = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE id_pessoa = ?";

        try (PreparedStatement preparedStatementPessoaJuridica = connection.prepareStatement(sqlPessoaJuridica);
             PreparedStatement preparedStatementPessoa = connection.prepareStatement(sqlPessoa)) {

            // Iniciar uma transação
            connection.setAutoCommit(false);

            try {
                // Excluir da tabela PessoaJuridica
                preparedStatementPessoaJuridica.setInt(1, id);
                preparedStatementPessoaJuridica.executeUpdate();

                // Excluir da tabela Pessoa
                preparedStatementPessoa.setInt(1, id);
                preparedStatementPessoa.executeUpdate();

                // Commit da transação
                connection.commit();
            } catch (SQLException e) {
                // Rollback em caso de exceção
                connection.rollback();
                throw e;
            } finally {
                // Restaurar o modo de autocommit
                connection.setAutoCommit(true);
            }
        }
    }

    // Busca um registro de pessoa Jurídica pelo ID.
    // Faz a busca de Pessoa Jurídica por ID.
public PessoaJuridica buscarPessoaJuridicaPorId(int id) {
    String sql = "SELECT p.id_pessoa, p.nome_pessoa, p.logradouro_pessoa, p.cidade_pessoa, p.estado_pessoa, p.telefone_pessoa, p.email_pessoa, pj.cnpj " +
            "FROM Pessoa p " +
            "JOIN pessoa_juridica pj ON p.id_pessoa = pj.id_pessoa " +
            "WHERE p.id_pessoa = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setInt(1, id);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                PessoaJuridica pessoaJuridica = new PessoaJuridica();
                pessoaJuridica.setId(resultSet.getInt("id_pessoa"));
                pessoaJuridica.setNome(resultSet.getString("nome_pessoa"));
                pessoaJuridica.setLogradouro(resultSet.getString("logradouro_pessoa"));
                pessoaJuridica.setCidade(resultSet.getString("cidade_pessoa"));
                pessoaJuridica.setEstado(resultSet.getString("estado_pessoa"));
                pessoaJuridica.setTelefone(resultSet.getString("telefone_pessoa"));
                pessoaJuridica.setEmail(resultSet.getString("email_pessoa"));
                pessoaJuridica.setCnpj(resultSet.getString("cnpj"));

                return pessoaJuridica;
            }
        }
    } catch (SQLException e) {
        handleSQLException(e);
    }

    return null; // Retorna null se não encontrar a pessoa com o ID especificado
}
  
    // Tratar exceções relacionadas a consultas SQL.
    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Erro na execução da consulta SQL", e);
    }
}
