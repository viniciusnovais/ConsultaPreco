package pdasolucoes.com.br.consultapreco.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.ViewAnimationUtils;

import java.util.ArrayList;
import java.util.List;

import pdasolucoes.com.br.consultapreco.Model.Agenda;

/**
 * Created by caueg on 22/11/2017.
 */

public class AgendaDao {

    public SQLiteDatabase database;
    private DbHelper helper;


    public AgendaDao(Context context) {
        helper = new DbHelper(context);
    }

    public SQLiteDatabase getDatabase() {

        if (database == null)
            database = helper.getWritableDatabase();

        return database;
    }

    public void close() {
        helper.close();
        if (database != null && database.isOpen())
            database.close();
    }

    public void incluir(List<Agenda> lista) {

        try {

            deletar();

            for (Agenda agenda : lista) {

                ContentValues values = new ContentValues();

                values.put("id", agenda.getId());
                values.put("idConcorrente", agenda.getIdConcorrente());
                values.put("nomeConcorrente", agenda.getNomeConcorrente());
                values.put("idLoja", agenda.getIdLoja());
                values.put("nomeLoja", agenda.getNomeLoja());
                values.put("data", agenda.getData());
                values.put("lista", agenda.getLista());
                values.put("status", agenda.getStatus());
                values.put("idUsuario", agenda.getIdUsuario());
                values.put("dataHoraInicio", agenda.getDataHoraInicio());

                getDatabase().insert("agenda", null, values);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void deletar() {
        getDatabase().delete("agenda", null, null);
    }

    public List<Agenda> listar() {
        List<Agenda> lista = new ArrayList<>();
        Cursor cursor = getDatabase().rawQuery("SELECT * FROM agenda", null);

        try {
            while (cursor.moveToNext()) {
                Agenda a = new Agenda();

                a.setId(cursor.getInt(cursor.getColumnIndex("id")));
                a.setIdConcorrente(cursor.getInt(cursor.getColumnIndex("idConcorrente")));
                a.setNomeConcorrente(cursor.getString(cursor.getColumnIndex("nomeConcorrente")));
                a.setData(cursor.getString(cursor.getColumnIndex("data")));
                a.setDataHoraInicio(cursor.getString(cursor.getColumnIndex("dataHoraInicio")));
                a.setIdLoja(cursor.getInt(cursor.getColumnIndex("idLoja")));
                a.setNomeLoja(cursor.getString(cursor.getColumnIndex("nomeLoja")));
                a.setLista(cursor.getInt(cursor.getColumnIndex("lista")));
                a.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                a.setIdUsuario(cursor.getInt(cursor.getColumnIndex("idUsuario")));

                lista.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return lista;
    }

    public void AlterarDataHoraInicio(Agenda agenda) {
        ContentValues values = new ContentValues();

        values.put("dataHoraInicio", agenda.getDataHoraInicio());

        getDatabase().update("agenda", values, "id = " + agenda.getId(), null);
    }


    public int idAgendaAberta(int idUsuario) {

        Cursor cursor = getDatabase().rawQuery("SELECT id FROM agenda WHERE idUsuario = ? and status = 1 ", new String[]{idUsuario + ""});

        try {
            while (cursor.moveToNext()) {
                return cursor.getInt(cursor.getColumnIndex("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return 0;
    }
}
