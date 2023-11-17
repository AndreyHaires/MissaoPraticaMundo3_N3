package cadastrobd.model;

/**
 *
 * @author Andrey H Aires
 */
public class PessoaJuridica extends Pessoa {
    
    private String cnpj;

    // Construtor padrão
    public PessoaJuridica() {
        super(); // Chama o construtor da classe pai (Pessoa)
    }

    // Construtor completo para PessoaJuridica
    public PessoaJuridica(int id, String nome_pessoa, String logradouro_pessoa, String cidade_pessoa, String estado_pessoa, String telefone_pessoa, String email_pessoa, String cnpj) {
        super(id, nome_pessoa, logradouro_pessoa, cidade_pessoa, estado_pessoa, telefone_pessoa, email_pessoa);
        this.cnpj = cnpj;
    }

    // Getter e setter para CNPJ
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    // Retorna uma representação de string formatada do objeto.
    @Override
    public String toString() {
        return super.toString() + "CNPJ: " + cnpj;
    }
}
