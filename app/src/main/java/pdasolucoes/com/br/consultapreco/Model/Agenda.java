package pdasolucoes.com.br.consultapreco.Model;

/**
 * Created by PDA on 20/11/2017.
 */

public class Agenda {

    private String nomeConcorrente;
    private String data;
    private String endereco;
    private String praca;

    public String getNomeConcorrente() {
        return nomeConcorrente;
    }

    public void setNomeConcorrente(String nomeConcorrente) {
        this.nomeConcorrente = nomeConcorrente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
