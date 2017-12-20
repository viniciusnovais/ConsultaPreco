package pdasolucoes.com.br.consultapreco.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.math.BigDecimal;
import java.util.Hashtable;

/**
 * Created by PDA on 06/12/2017.
 */

public class ItemColeta implements KvmSerializable {

    private String ean;
    private int seqFamilia;
    private String familia;
    private int agendaId;
    private BigDecimal preco;
    private String caminhoFoto;
    private byte[] foto;
    private String tipo;
    private int enviado;

    private String precoEnviar;

    public ItemColeta() {
        ean = "";
        seqFamilia = 0;
        familia = "";
        agendaId = 0;
        precoEnviar = "";
        caminhoFoto = "";
        foto = new byte[]{};
        tipo = "";
        enviado = 0;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public int getEnviado() {
        return enviado;
    }

    public void setEnviado(int enviado) {
        this.enviado = enviado;
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

    public String getPrecoEnviar() {
        return precoEnviar;
    }

    public void setPrecoEnviar(String precoEnviar) {
        this.precoEnviar = precoEnviar;
    }

    @Override
    public Object getProperty(int i) {

        switch (i) {
            case 0:
                return seqFamilia;
            case 1:
                return familia;
            case 2:
                return agendaId;
            case 3:
                return precoEnviar;
            case 4:
                return caminhoFoto;
            case 5:
                return tipo;
            case 6:
                return ean;
            case 7:
                return enviado;

        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 8;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0:
                seqFamilia = Integer.parseInt(o.toString());
                break;
            case 1:
                familia = o.toString();
                break;
            case 2:
                agendaId = Integer.parseInt(o.toString());
                break;
            case 3:
                precoEnviar = o.toString();
                break;
            case 4:
                caminhoFoto = o.toString();
                break;
            case 5:
                tipo = o.toString();
                break;
            case 6:
                ean = o.toString();
                break;
            case 7:
                enviado = Integer.parseInt(o.toString());
                break;

        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

        switch (i) {
            case 0:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "seqFamilia";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "familia";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "idAgenda";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "preco";
                break;
            case 4:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "caminhoFoto";
                break;
            case 5:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "tipo";
                break;
            case 6:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "ean";
                break;
            case 7:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "enviado";
                break;
        }
    }
}
