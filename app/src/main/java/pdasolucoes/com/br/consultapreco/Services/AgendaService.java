package pdasolucoes.com.br.consultapreco.Services;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pdasolucoes.com.br.consultapreco.Model.Agenda;

/**
 * Created by PDA on 27/11/2017.
 */

public class AgendaService {

    private static final String METHOD_NAME = "GetAgenda";
    private static final String METHOD_NAME_ABRIR_AGENDA = "AbrirPesquisa";
    private static final String METHOD_NAME_FECHAR_AGENDA = "FecharPesquisa";
    private static final String ASMX = "wspesquisa.asmx";


    public static List<Agenda> GetListaAgenda() {
        List<Agenda> lista = new ArrayList<>();
        SoapObject response;

        try {

            SoapObject request = new SoapObject(ConfigService.NAMESPACE, METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transportSE = new HttpTransportSE(ConfigService.URL + ASMX);
            transportSE.call(ConfigService.NAMESPACE + METHOD_NAME, envelope);

            response = (SoapObject) envelope.getResponse();

            if (response != null) {
                for (int i = 0; i < response.getPropertyCount(); i++) {
                    SoapObject item = (SoapObject) response.getProperty(i);

                    Agenda a = new Agenda();
                    a.setId(Integer.parseInt(item.getPropertyAsString("id")));
                    a.setIdConcorrente(Integer.parseInt(item.getPropertyAsString("idConcorrente")));
                    a.setNomeConcorrente(item.getPropertyAsString("nomeConcorrente"));
                    a.setData(item.getPropertyAsString("data"));
                    a.setIdLoja(Integer.parseInt(item.getPropertyAsString("idLoja")));
                    a.setNomeLoja(item.getPropertyAsString("nomeLoja"));
                    a.setLista(Integer.parseInt(item.getPropertyAsString("lista")));
                    a.setStatus(Integer.parseInt(item.getPropertyAsString("status")));
                    a.setIdUsuario(Integer.parseInt(item.getPropertyAsString("idUsuario")));

                    lista.add(a);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return lista;
    }

    public static String AbrirPesquisa(int idAgenda, int idUsuario) {
        String resposta = "";

        try {
            SoapObject request = new SoapObject(ConfigService.NAMESPACE, METHOD_NAME_ABRIR_AGENDA);

            PropertyInfo infoAgenda = new PropertyInfo();
            infoAgenda.setName("idAgenda");
            infoAgenda.setType(PropertyInfo.INTEGER_CLASS);
            infoAgenda.setValue(idAgenda);

            request.addProperty(infoAgenda);


            PropertyInfo infoUsuario= new PropertyInfo();
            infoUsuario.setName("idUsuario");
            infoUsuario.setType(PropertyInfo.INTEGER_CLASS);
            infoUsuario.setValue(idUsuario);

            request.addProperty(infoUsuario);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransportSE = new HttpTransportSE(ConfigService.URL + ASMX);
            httpTransportSE.call(ConfigService.NAMESPACE + METHOD_NAME_ABRIR_AGENDA, envelope);

            resposta = envelope.getResponse().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resposta;

    }

    public static String FecharPesquisa(int idAgenda) {
        String resposta = "";

        try {
            SoapObject request = new SoapObject(ConfigService.NAMESPACE, METHOD_NAME_FECHAR_AGENDA);

            PropertyInfo infoAgenda = new PropertyInfo();
            infoAgenda.setName("idAgenda");
            infoAgenda.setType(PropertyInfo.INTEGER_CLASS);
            infoAgenda.setValue(idAgenda);

            request.addProperty(infoAgenda);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransportSE = new HttpTransportSE(ConfigService.URL + ASMX);
            httpTransportSE.call(ConfigService.NAMESPACE + METHOD_NAME_FECHAR_AGENDA, envelope);

            resposta = envelope.getResponse().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resposta;

    }
}
