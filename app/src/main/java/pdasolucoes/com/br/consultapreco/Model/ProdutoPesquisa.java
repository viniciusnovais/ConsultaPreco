package pdasolucoes.com.br.consultapreco.Model;

import java.math.BigDecimal;

/**
 * Created by PDA on 11/12/2017.
 */

public class ProdutoPesquisa {

    private int seqFamilia;
    private String familia;
    private int seqMarca;
    private String marca;
    private String codAcesso;
    private int seqLista;
    private int cod1;
    private String nivel1;
    private int cod2;
    private String nivel2;
    private int cod3;
    private String nivel3;
    private int cod4;
    private String nivel4;

    private String preco;
    private String precoDigitado;

    public String getPrecoDigitado() {
        return precoDigitado;
    }

    public void setPrecoDigitado(String precoDigitado) {
        this.precoDigitado = precoDigitado;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
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

    public int getSeqMarca() {
        return seqMarca;
    }

    public void setSeqMarca(int seqMarca) {
        this.seqMarca = seqMarca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCodAcesso() {
        return codAcesso;
    }

    public void setCodAcesso(String codAcesso) {
        this.codAcesso = codAcesso;
    }

    public int getSeqLista() {
        return seqLista;
    }

    public void setSeqLista(int seqLista) {
        this.seqLista = seqLista;
    }

    public int getCod1() {
        return cod1;
    }

    public void setCod1(int cod1) {
        this.cod1 = cod1;
    }

    public String getNivel1() {
        return nivel1;
    }

    public void setNivel1(String nivel1) {
        this.nivel1 = nivel1;
    }

    public int getCod2() {
        return cod2;
    }

    public void setCod2(int cod2) {
        this.cod2 = cod2;
    }

    public String getNivel2() {
        return nivel2;
    }

    public void setNivel2(String nivel2) {
        this.nivel2 = nivel2;
    }

    public int getCod3() {
        return cod3;
    }

    public void setCod3(int cod3) {
        this.cod3 = cod3;
    }

    public String getNivel3() {
        return nivel3;
    }

    public void setNivel3(String nivel3) {
        this.nivel3 = nivel3;
    }

    public int getCod4() {
        return cod4;
    }

    public void setCod4(int cod4) {
        this.cod4 = cod4;
    }

    public String getNivel4() {
        return nivel4;
    }

    public void setNivel4(String nivel4) {
        this.nivel4 = nivel4;
    }
}
