package pdasolucoes.com.br.consultapreco.Model;

/**
 * Created by PDA on 16/11/2017.
 */

public class Concorrente {

    private int id;
    private String nome;
    private String endereco;
    private String praca;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getPraca() {
        return praca;
    }

    public void setPraca(String praca) {
        this.praca = praca;
    }
}
