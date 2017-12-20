package pdasolucoes.com.br.consultapreco.Dao;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        values.put("enviado", 0);
        values.put("ean", i.getEan());

        getDatabase().insert("itemColeta", null, values);
    }

    public void deletar() {
        getDatabase().delete("itemColeta", null, null);
    }

    public void alterarPreco(ItemColeta i) {
        ContentValues values = new ContentValues();

        values.put("preco", String.valueOf(i.getPreco()));

        getDatabase().update("itemColeta", values, "seqFamilia = " + i.getSeqFamilia() + " and agendaId = " + i.getAgendaId(), null);
    }

    public void alterarTipo(ItemColeta i) {
        ContentValues values = new ContentValues();

        values.put("tipo", i.getTipo());
        values.put("caminhoFoto", i.getCaminhoFoto());
        values.put("preco", String.valueOf(i.getPreco()));

        getDatabase().update("itemColeta", values, "seqFamilia = " + i.getSeqFamilia() + " and agendaId = " + i.getAgendaId(), null);
    }

    public void alterarCaminhoFoto(ItemColeta i) {
        ContentValues values = new ContentValues();

        values.put("caminhoFoto", i.getCaminhoFoto());

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

    public int totalPesquisa() {

        Cursor cursor = getDatabase().rawQuery("SELECT COUNT(*) total FROM (SELECT * FROM produtoPesquisa group by seqfamilia)", null);

        try {
            while (cursor.moveToNext()) {
                return cursor.getInt(cursor.getColumnIndex("total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return 0;
    }

    public int pesquisaFeita() {

        Cursor cursor = getDatabase().rawQuery("SELECT COUNT(*) feitos FROM itemColeta WHERE preco <> 0 or tipo = 'Ruptura'",null);

        try {
            while (cursor.moveToNext()) {
                return cursor.getInt(cursor.getColumnIndex("feitos"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return 0;
    }


    public List<ItemColeta> listaColeta() {
        List<ItemColeta> lista = new ArrayList<>();
        Cursor cursor = getDatabase().rawQuery("SELECT seqFamilia, familia, agendaId,preco,caminhoFoto,tipo,ean,enviado FROM itemColeta WHERE ((enviado = 0) OR (enviado is null))" +
                " AND ((preco <> 0) or (tipo = 'Ruptura'))", null);

        try {
            while (cursor.moveToNext()) {
                ItemColeta i = new ItemColeta();

                i.setSeqFamilia(cursor.getInt(cursor.getColumnIndex("seqFamilia")));
                i.setFamilia(cursor.getString(cursor.getColumnIndex("familia")));
                i.setAgendaId(cursor.getInt(cursor.getColumnIndex("agendaId")));
                i.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));
                i.setPrecoEnviar(cursor.getString(cursor.getColumnIndex("preco")));
                i.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
                i.setEan(cursor.getString(cursor.getColumnIndex("ean")));
                i.setEnviado(cursor.getInt(cursor.getColumnIndex("enviado")));

                lista.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return lista;
    }

    public void enviado(ItemColeta i) {

        ContentValues values = new ContentValues();
        values.put("enviado", 1);
        getDatabase().update("itemColeta", values, "seqfamilia = " + i.getSeqFamilia() + " and agendaId = " + i.getAgendaId(), null);
    }

}
