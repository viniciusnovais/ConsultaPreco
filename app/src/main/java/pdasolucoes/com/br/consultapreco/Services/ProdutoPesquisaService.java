package pdasolucoes.com.br.consultapreco.Services;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import pdasolucoes.com.br.consultapreco.Model.ProdutoPesquisa;

/**
 * Created by PDA on 11/12/2017.
 */

public class ProdutoPesquisaService {

    public static final String ASMX = "wspesquisa.asmx";
    public static final String METHOD_NAME = "GetListaPesquisa";

    public static List<ProdutoPesquisa> listarProdutosPesquisa(int idAgenda) {
        List<ProdutoPesquisa> lista = new ArrayList<>();
        SoapObject response;
        try {

            SoapObject request = new SoapObject(ConfigService.NAMESPACE, METHOD_NAME);

            PropertyInfo propertyAgenda = new PropertyInfo();
            propertyAgenda.setName("idAgenda");
            propertyAgenda.setType(PropertyInfo.INTEGER_CLASS);
            propertyAgenda.setValue(idAgenda);

            request.addProperty(propertyAgenda);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.implicitTypes = true;
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transportSE = new HttpTransportSE(ConfigService.URL + ASMX);
            transportSE.call(ConfigService.NAMESPACE + METHOD_NAME, envelope);

            response = (SoapObject) envelope.getResponse();
            for (int i = 0; i < response.getPropertyCount(); i++) {
                SoapObject item = (SoapObject) response.getProperty(i);

                ProdutoPesquisa p = new ProdutoPesquisa();
                p.setSeqFamilia(Integer.parseInt(item.getPropertyAsString("seqFamilia")));
                p.setFamilia(item.getPropertyAsString("familia"));
                p.setSeqMarca(Integer.parseInt(item.getPropertyAsString("seqMarca")));
                p.setMarca(item.getPropertyAsString("marca"));
                p.setCodAcesso(item.getPropertyAsString("codAcesso"));
                p.setCod1(Integer.parseInt(item.getPropertyAsString("cod1")));
                p.setNivel1(item.getPropertyAsString("nivel1"));
                p.setCod2(Integer.parseInt(item.getPropertyAsString("cod2")));
                p.setNivel2(item.getPropertyAsString("nivel2"));
                p.setCod3(Integer.parseInt(item.getPropertyAsString("cod3")));
                p.setNivel3(item.getPropertyAsString("nivel3"));
                p.setCod4(Integer.parseInt(item.getPropertyAsString("cod4")));
                p.setNivel4(item.getPropertyAsString("nivel4"));

                lista.add(p);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
