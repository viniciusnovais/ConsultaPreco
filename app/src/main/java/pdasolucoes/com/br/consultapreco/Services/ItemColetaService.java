package pdasolucoes.com.br.consultapreco.Services;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

import pdasolucoes.com.br.consultapreco.Model.ItemColeta;

/**
 * Created by PDA on 14/12/2017.
 */

public class ItemColetaService {

    public static final String ASMX = "wspesquisa.asmx";
    public static final String METHOD_NAME = "setItemPesquisa";

    public static String setItemColeta(List<ItemColeta> lista) {
        SoapObject response;
        String result = "";
        try {

            SoapObject request = new SoapObject(ConfigService.NAMESPACE, METHOD_NAME);
            SoapObject soapObject = new SoapObject(ConfigService.NAMESPACE, "lista");

            for (ItemColeta i : lista) {
                soapObject.addProperty("ItemPesquisaEO", i);
            }

            request.addSoapObject(soapObject);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.implicitTypes = true;
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            MarshalFloat md = new MarshalFloat();
            md.register(envelope);

            envelope.addMapping(ConfigService.NAMESPACE, "ItemPesquisaEO", new ItemColeta().getClass());

            HttpTransportSE transportSE = new HttpTransportSE(ConfigService.URL + ASMX);
            transportSE.call(ConfigService.NAMESPACE + METHOD_NAME, envelope);

            response = (SoapObject) envelope.bodyIn;
            result = response.getPropertyAsString("setItemPesquisaResult");


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }
}
