
public class Contatos {
    //Atributos
    private String nome;
    private Double phone;
    private String email;

    //Método Construtor

    public Contatos(String nome, Double phone, String email) {
        this.nome = nome;
        this.phone = phone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPhone() {
        return phone;
    }

    public void setPhone(Double phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
