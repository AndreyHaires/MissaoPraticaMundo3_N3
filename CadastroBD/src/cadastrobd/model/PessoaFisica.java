package cadastrobd.model;

/**
 *
 * author Andrey H Aires
 */
public class PessoaFisica extends Pessoa {

    private String cpf;

    // Construtor padrão
    public PessoaFisica() {
        super();
    }

    // Construtor completo
    public PessoaFisica(int id_pessoa, String nome_pessoa, String logradouro_pessoa, String cidade_pessoa, String estado_pessoa, String telefone_pessoa, String email_pessoa, String cpf) {
        super(id_pessoa, nome_pessoa, logradouro_pessoa, cidade_pessoa, estado_pessoa, telefone_pessoa, email_pessoa);
        this.cpf = cpf;
    }

    // Getter e setter
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // Retorna uma representação de string formatada do objeto.
    @Override
    public String toString() {
        return super.toString() + "CPF: " + getCpf();
    }
}
