package pdasolucoes.com.br.consultapreco.Model;

import java.math.BigDecimal;

/**
 * Created by PDA on 06/12/2017.
 */

public class ItemColeta {

    private String ean;
    private int seqFamilia;
    private String familia;
    private int agendaId;
    private BigDecimal preco;
    private String caminhoFoto;
    private byte[] foto;
    private String tipo;


    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getSeqFamilia() {
        return seqFamilia;
    }

    public void setSeqFamilia(int seqFamilia) {
        this.seqFamilia = seqFamilia;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public int getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(int agendaId) {
        this.agendaId = agendaId;
    }
}
