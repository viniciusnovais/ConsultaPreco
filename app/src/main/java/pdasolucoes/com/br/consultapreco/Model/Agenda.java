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
    private int lista;
    private int status;
    private int idUsuario;
    private String dataHoraInicio;

    public String getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(String dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

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

    public int getLista() {
        return lista;
    }

    public void setLista(int lista) {
        this.lista = lista;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
