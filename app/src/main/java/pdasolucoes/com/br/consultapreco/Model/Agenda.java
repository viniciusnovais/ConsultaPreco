package pdasolucoes.com.br.consultapreco.Model;

/**
 * Created by PDA on 20/11/2017.
 */

public class Agenda {

    private int id;
    private int idConcorrente;
    private String nomeConcorrente;
    private String data;
    private int idLoja;
    private String nomeLoja;
    private String praca;
    private int status;
    private int idUsuario;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdConcorrente() {
        return idConcorrente;
    }

    public void setIdConcorrente(int idConcorrente) {
        this.idConcorrente = idConcorrente;
    }

    public int getIdLoja() {
        return idLoja;
    }

    public void setIdLoja(int idLoja) {
        this.idLoja = idLoja;
    }

    public String getNomeLoja() {
        return nomeLoja;
    }

    public void setNomeLoja(String nomeLoja) {
        this.nomeLoja = nomeLoja;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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



    public String getPraca() {
        return praca;
    }

    public void setPraca(String praca) {
        this.praca = praca;
    }
}
