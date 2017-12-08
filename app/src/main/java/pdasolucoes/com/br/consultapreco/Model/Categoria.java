package pdasolucoes.com.br.consultapreco.Model;

import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by PDA on 08/12/2017.
 */

public class Categoria implements Serializable {


    private int cod1;
    private String nivel1;
    private int cod2;
    private String nivel2;
    private int cod3;
    private String nivel3;
    private int cod4;
    private String nivel4;
    private int cod5;
    private String nivel5;
    private String genericText;

    @Override
    public String toString() {
        return genericText;
    }

    public String getGenericText() {
        return genericText;
    }

    public void setGenericText(String genericText) {
        this.genericText = genericText;
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

    public int getCod5() {
        return cod5;
    }

    public void setCod5(int cod5) {
        this.cod5 = cod5;
    }

    public String getNivel5() {
        return nivel5;
    }

    public void setNivel5(String nivel5) {
        this.nivel5 = nivel5;
    }
}
