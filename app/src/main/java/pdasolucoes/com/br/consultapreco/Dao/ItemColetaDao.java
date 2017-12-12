package pdasolucoes.com.br.consultapreco.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;

import pdasolucoes.com.br.consultapreco.Model.ItemColeta;

/**
 * Created by PDA on 12/12/2017.
 */

public class ItemColetaDao {

    public SQLiteDatabase database;
    private DbHelper helper;


    public ItemColetaDao(Context context) {
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


    public void incluir(ItemColeta i) {
        ContentValues values = new ContentValues();

        values.put("seqFamilia", i.getSeqFamilia());
        values.put("familia", i.getFamilia());
        values.put("agendaId", i.getAgendaId());
        values.put("preco", String.valueOf(i.getPreco()));
        values.put("caminhoFoto", i.getCaminhoFoto());
        values.put("tipo", i.getTipo());
        values.put("ean", i.getEan());

        getDatabase().insert("itemColeta", null, values);
    }

    public void alterarPreco(ItemColeta i) {
        ContentValues values = new ContentValues();

        values.put("preco", String.valueOf(i.getPreco()));

        getDatabase().update("itemColeta", values, "seqFamilia = " + i.getSeqFamilia() + " and agendaId = " + i.getAgendaId(), null);
    }

    public void alterarTipoCaminhoFoto(ItemColeta i) {
        ContentValues values = new ContentValues();

        values.put("caminhoFoto", i.getCaminhoFoto());
        values.put("tipo", i.getTipo());

        getDatabase().update("itemColeta", values, "seqFamilia = " + i.getSeqFamilia() + " and agendaId = " + i.getAgendaId(), null);
    }


    public boolean existeColeta(ItemColeta i) {

        Cursor cursor = getDatabase().rawQuery("SELECT * FROM itemColeta WHERE agendaId = ? and seqFamilia = ?", new String[]{i.getAgendaId() + "", i.getSeqFamilia() + ""});

        try {
            while (cursor.moveToNext()) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return false;
    }
}
