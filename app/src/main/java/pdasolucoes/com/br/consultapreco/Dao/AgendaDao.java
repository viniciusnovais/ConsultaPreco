package pdasolucoes.com.br.consultapreco.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import pdasolucoes.com.br.consultapreco.Model.Agenda;

/**
 * Created by caueg on 22/11/2017.
 */

public class AgendaDao {

    public SQLiteDatabase database;
    private  DbHelper helper;


    public AgendaDao(Context context){
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

    public long incluir (Agenda agenda){

        ContentValues values = new ContentValues();

        values.put("nomeConcorrente", agenda.getNomeConcorrente());
        values.put("data", agenda.getData());
        values.put("endereco", agenda.getEndereco());
        values.put("praca", agenda.getPraca());

        return getDatabase().insert("agenda",null,values);
    }
}
