package cadastrobd.model;

/**
 * Classe que interage com o banco de dados para operações relacionadas à Pessoa Física.
 * Fornece métodos para exibir todos os registros de Pessoa Física e incluir novos registros.
 * Utiliza a tabela Pessoa para dados gerais e a tabela pessoa_fisica para dados específicos de Pessoa Física.
 * Cada registro é formatado como uma string para fácil exibição.
 * 
 * @author Andrey H Aires
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {

    private final Connection connection;

    // Construtor da classe PessoaFisicaDAO. 
    public PessoaFisicaDAO(Connection connection) { // Conexão com o banco de dados.
        this.connection = connection; 
    }
    
    // Obtém todos os registros de Pessoa Física do banco de dados e os retorna como uma lista de strings formatadas.
    public List<String> exibirTodosFisica() {
        List<String> cadastros = new ArrayList<>();
        String sql = "SELECT p.id_pessoa, p.nome_pessoa, p.logradouro_pessoa, p.cidade_pessoa, p.estado_pessoa, p.telefone_pessoa, p.email_pessoa, pf.cpf " +
                "FROM Pessoa p " +
                "JOIN pessoa_fisica pf ON p.id_pessoa = pf.id_pessoa";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String cadastro = String.format("ID: %d%nNome: %s%nLogradouro: %s%nCidade: %s%nEstado: %s%nTelefone: %s%nEmail: %s%nCPF: %s%n",
                        resultSet.getInt("id_pessoa"),
                        resultSet.getString("nome_pessoa"),
                        resultSet.getString("logradouro_pessoa"),
                        resultSet.getString("cidade_pessoa"),
                        resultSet.getString("estado_pessoa"),
                        resultSet.getString("telefone_pessoa"),
                        resultSet.getString("email_pessoa"),
                        resultSet.getString("cpf"));
                
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

    // Inclui um novo registro de Pessoa Física no banco de dados.
    public void incluirPessoaFisica(PessoaFisica pessoaFisica) throws SQLException {
        String sqlPessoa = "INSERT INTO Pessoa (nome_pessoa, logradouro_pessoa, cidade_pessoa, estado_pessoa, telefone_pessoa, email_pessoa) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPessoaFisica = "INSERT INTO pessoa_fisica (id_pessoa, cpf) VALUES (?, ?)";

        try (PreparedStatement preparedStatementPessoa = connection.prepareStatement(sqlPessoa, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatementPessoaFisica = connection.prepareStatement(sqlPessoaFisica)) {

            // Verifica se o nome não é nulo antes de inserir
            if (pessoaFisica.getNome() != null) {
                // Iniciar uma transação
                connection.setAutoCommit(false);

                try {
                    // Inserir na tabela Pessoa
                    preparedStatementPessoa.setString(1, pessoaFisica.getNome());
                    preparedStatementPessoa.setString(2, pessoaFisica.getLogradouro());
                    preparedStatementPessoa.setString(3, pessoaFisica.getCidade());
                    preparedStatementPessoa.setString(4, pessoaFisica.getEstado());
                    preparedStatementPessoa.setString(5, pessoaFisica.getTelefone());
                    preparedStatementPessoa.setString(6, pessoaFisica.getEmail());
                    preparedStatementPessoa.executeUpdate();

                    // Obter o ID gerado
                    try (ResultSet generatedKeys = preparedStatementPessoa.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);

                            // Inserir na tabela PessoaFisica
                            preparedStatementPessoaFisica.setInt(1, id);
                            preparedStatementPessoaFisica.setString(2, pessoaFisica.getCpf());
                            preparedStatementPessoaFisica.executeUpdate();
                        }
                    }

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
                System.out.println("Nome da pessoa não pode ser nulo. A inserção foi ignorada.");
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    
    // Altera um registro de Pessoa Física pelo ID no banco de dados.
    public void alterarPessoaFisica(PessoaFisica pessoa) throws SQLException {
        String sqlPessoa = "UPDATE Pessoa SET nome_pessoa=?, logradouro_pessoa=?, cidade_pessoa=?, estado_pessoa=?, telefone_pessoa=?, email_pessoa=? WHERE id_pessoa=?";
        String sqlPessoaFisica = "UPDATE pessoa_fisica SET cpf=? WHERE id_pessoa=?";

        try (PreparedStatement preparedStatementPessoa = connection.prepareStatement(sqlPessoa);
             PreparedStatement preparedStatementPessoaFisica = connection.prepareStatement(sqlPessoaFisica)) {

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

                    // Atualizar na tabela PessoaFisica
                    preparedStatementPessoaFisica.setString(1, pessoa.getCpf());
                    preparedStatementPessoaFisica.setInt(2, pessoa.getId());
                    preparedStatementPessoaFisica.executeUpdate();

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

    // Exclui um registro de Pessoa Física pelo ID no banco de dados.
    public void excluirPessoaFisica(int id) throws SQLException {
        String sqlPessoaFisica = "DELETE FROM pessoa_fisica WHERE id_pessoa = ?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE id_pessoa = ?";

        try (PreparedStatement preparedStatementPessoaFisica = connection.prepareStatement(sqlPessoaFisica);
             PreparedStatement preparedStatementPessoa = connection.prepareStatement(sqlPessoa)) {

            // Iniciar uma transação
            connection.setAutoCommit(false);

            try {
                // Excluir da tabela PessoaFisica
                preparedStatementPessoaFisica.setInt(1, id);
                preparedStatementPessoaFisica.executeUpdate();

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
    
    // Faz a busca de Pessoa Fisica por ID.

public PessoaFisica buscarPessoaFisicaPorId(int id) {
    String sql = "SELECT p.id_pessoa, p.nome_pessoa, p.logradouro_pessoa, p.cidade_pessoa, p.estado_pessoa, p.telefone_pessoa, p.email_pessoa, pf.cpf " +
            "FROM Pessoa p " +
            "JOIN pessoa_fisica pf ON p.id_pessoa = pf.id_pessoa " +
            "WHERE p.id_pessoa = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setInt(1, id);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                PessoaFisica pessoaFisica = new PessoaFisica();
                pessoaFisica.setId(resultSet.getInt("id_pessoa"));
                pessoaFisica.setNome(resultSet.getString("nome_pessoa"));
                pessoaFisica.setLogradouro(resultSet.getString("logradouro_pessoa"));
                pessoaFisica.setCidade(resultSet.getString("cidade_pessoa"));
                pessoaFisica.setEstado(resultSet.getString("estado_pessoa"));
                pessoaFisica.setTelefone(resultSet.getString("telefone_pessoa"));
                pessoaFisica.setEmail(resultSet.getString("email_pessoa"));
                pessoaFisica.setCpf(resultSet.getString("cpf"));
                
                return pessoaFisica;
            }
        }
    } catch (SQLException e) {
        handleSQLException(e);
    }

    return null; // Retorna null se não encontrar a pessoa com o ID especificado
}

    
    // Trata exceções de SQL, imprimindo o rastreamento de pilha.
    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Erro na execução da consulta SQL", e);
    }
}
