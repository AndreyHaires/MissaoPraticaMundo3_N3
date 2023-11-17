package cadastrobd.model;
/**
 *
 * @author Andrey H Aires
 */
public class Pessoa {
    // Campos da classe
    private int id_pessoa;
    private String nome_pessoa;
    private String logradouro_pessoa;
    private String cidade_pessoa;
    private String estado_pessoa;
    private String telefone_pessoa;
    private String email_pessoa;

    // Construtor padrao
    public Pessoa() {
    }

    // Construtor completo
    public Pessoa(int id_pessoa, String nome_pessoa, String logradouro_pessoa, String cidade_pessoa, String estado_pessoa, String telefone_pessoa, String email_pessoa) {
        this.id_pessoa = id_pessoa;
        this.nome_pessoa = nome_pessoa;
        this.logradouro_pessoa = logradouro_pessoa;
        this.cidade_pessoa = cidade_pessoa;
        this.estado_pessoa = estado_pessoa;
        this.telefone_pessoa = telefone_pessoa;
        this.email_pessoa = email_pessoa;
    }



    // Metodos getters
    public int getId() {
        return id_pessoa;
    }

    public String getNome() {
        return nome_pessoa;
    }

    public String getLogradouro() {
        return logradouro_pessoa;
    }

    public String getCidade() {
        return cidade_pessoa;
    }

    public String getEstado() {
        return estado_pessoa;
    }

    public String getTelefone() {
        return telefone_pessoa;
    }

    public String getEmail() {
        return email_pessoa;
    }
    
    //Metodo setters
    public void setId(int id) {
    this.id_pessoa = id;
    }

    public void setNome(String nome) {
        this.nome_pessoa = nome;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro_pessoa = logradouro;
    }

    public void setCidade(String cidade) {
        this.cidade_pessoa = cidade;
    }

    public void setEstado(String estado) {
        this.estado_pessoa = estado;
    }

    public void setTelefone(String telefone) {
        this.telefone_pessoa = telefone;
    }

    public void setEmail(String email) {
        this.email_pessoa = email;
    }    

// Retorna uma representação de string formatada do objeto.
    @Override
    public String toString() {
        return String.format("ID: %d%nNome: %s%nLogradouro: %s%nCidade: %s%nEstado: %s%nTelefone: %s%nEmail: %s%n",
                getId(),
                getNome(),
                getLogradouro(),
                getCidade(),
                getEstado(),
                getTelefone(),
                getEmail());
    }
}
