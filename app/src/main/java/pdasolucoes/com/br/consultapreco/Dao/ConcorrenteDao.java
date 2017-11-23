package pdasolucoes.com.br.consultapreco.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import pdasolucoes.com.br.consultapreco.Model.Concorrente;

/**
 * Created by caueg on 22/11/2017.
 */

public class ConcorrenteDao {

    public SQLiteDatabase database;
    private  DbHelper helper;


    public ConcorrenteDao(Context context){
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

    public long incluir(Concorrente concorrente) {

        ContentValues values = new ContentValues();

        values.put("id", concorrente.getId() );
        values.put("nome", concorrente.getNome());
        values.put("endereco", concorrente.getEndereco());
        values.put("praca", concorrente.getPraca());

        return getDatabase().insert("concorrente", null, values);
    }
}
