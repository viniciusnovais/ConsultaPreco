package pdasolucoes.com.br.consultapreco.Model;

import java.math.BigDecimal;

/**
 * Created by PDA on 20/11/2017.
 */

public class Produto {

    private int codProduto;
    private String ean;
    private String sku;
    private String nome;
    private String marca;
    private BigDecimal precoConcorrente;
    private String ruptura;
    private String oferta;
    private String unidMedida;

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public BigDecimal getPrecoConcorrente() {
        return precoConcorrente;
    }

    public void setPrecoConcorrente(BigDecimal precoConcorrente) {
        this.precoConcorrente = precoConcorrente;
    }

    public String getRuptura() {
        return ruptura;
    }

    public void setRuptura(String ruptura) {
        this.ruptura = ruptura;
    }

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    public String getUnidMedida() {
        return unidMedida;
    }

    public void setUnidMedida(String unidMedida) {
        this.unidMedida = unidMedida;
    }
}
