package pdasolucoes.com.br.consultapreco.Services;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

import pdasolucoes.com.br.consultapreco.Model.Categoria;

/**
 * Created by PDA on 08/12/2017.
 */

public class CategoriaService {


    public static final String ASMX = "wspesquisa.asmx";
    public static final String METHOD_NAME = "GetCategoria";

    public static List<Categoria> listarCategoria() {
        List<Categoria> lista = new ArrayList<>();
        SoapObject response;
        try {

            SoapObject request = new SoapObject(ConfigService.URL, METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.implicitTypes = true;
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transportSE = new HttpTransportSE(ConfigService.URL + ASMX);
            transportSE.call(ConfigService.NAMESPACE + METHOD_NAME, envelope);

            response = (SoapObject) envelope.getResponse();
            for (int i = 0; i < response.getPropertyCount(); i++) {
                SoapObject item = (SoapObject) response.getProperty(i);

                Categoria c = new Categoria();

                c.setCod1(Integer.parseInt(item.getPropertyAsString("cod1")));
                c.setNivel1(item.getPropertyAsString("nivel1"));
                c.setCod2(Integer.parseInt(item.getPropertyAsString("cod2")));
                c.setNivel2(item.getPropertyAsString("nivel2"));
                c.setCod3(Integer.parseInt(item.getPropertyAsString("cod3")));
                c.setNivel3(item.getPropertyAsString("nivel3"));
                c.setCod4(Integer.parseInt(item.getPropertyAsString("cod4")));
                c.setNivel4(item.getPropertyAsString("nivel4"));
                c.setCod5(Integer.parseInt(item.getPropertyAsString("cod5")));
                c.setNivel5(item.getPropertyAsString("nivel5"));

                lista.add(c);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
