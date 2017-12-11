package pdasolucoes.com.br.consultapreco.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pdasolucoes.com.br.consultapreco.Model.ProdutoPesquisa;

/**
 * Created by PDA on 11/12/2017.
 */

public class PesquisaProdutoDao {

    private SQLiteDatabase database;
    private DbHelper helper;

    public PesquisaProdutoDao(Context context) {
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


    public void incluir(List<ProdutoPesquisa> lista) {

        try {
            deletar();

            for (ProdutoPesquisa p : lista) {
                ContentValues values = new ContentValues();

                values.put("seqFamilia", p.getSeqFamilia());
                values.put("familia", p.getFamilia());
                values.put("seqMarca", p.getSeqMarca());
                values.put("marca", p.getMarca());
                values.put("codAcesso", p.getCodAcesso());
                values.put("seqLista", p.getSeqLista());
                values.put("cod1", p.getCod1());
                values.put("nivel1", p.getNivel1());
                values.put("cod2", p.getCod2());
                values.put("nivel2", p.getNivel2());
                values.put("cod3", p.getCod3());
                values.put("nivel3", p.getNivel3());
                values.put("cod4", p.getCod4());
                values.put("nivel4", p.getNivel4());

                getDatabase().insert("produtoPesquisa", null, values);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletar() {
        getDatabase().delete("produtoPesquisa", null, null);
    }


    public List<ProdutoPesquisa> listarPesquisa() {
        List<ProdutoPesquisa> lista = new ArrayList<>();
        Cursor cursor = getDatabase().rawQuery("SELECT seqFamilia,familia,seqMarca,marca,seqLista,cod1,nivel1,cod2,nivel2,cod3,nivel3,cod4,nivel4 FROM produtoPesquisa" +
                " GROUP BY seqFamilia,familia,seqMarca,marca,seqLista,cod1,nivel1,cod2,nivel2,nivel3,cod4,nivel4", null);

        try {

            while (cursor.moveToNext()) {
                ProdutoPesquisa p = new ProdutoPesquisa();

                p.setSeqFamilia(cursor.getInt(cursor.getColumnIndex("seqFamilia")));
                p.setFamilia(cursor.getString(cursor.getColumnIndex("familia")));
                p.setSeqMarca(cursor.getInt(cursor.getColumnIndex("seqMarca")));
                p.setMarca(cursor.getString(cursor.getColumnIndex("marca")));
                p.setSeqLista(cursor.getInt(cursor.getColumnIndex("seqLista")));
                p.setCod1(cursor.getInt(cursor.getColumnIndex("cod1")));
                p.setNivel1(cursor.getString(cursor.getColumnIndex("nivel1")));
                p.setCod2(cursor.getInt(cursor.getColumnIndex("cod2")));
                p.setNivel2(cursor.getString(cursor.getColumnIndex("nivel2")));
                p.setCod3(cursor.getInt(cursor.getColumnIndex("cod3")));
                p.setNivel3(cursor.getString(cursor.getColumnIndex("nivel3")));
                p.setCod4(cursor.getInt(cursor.getColumnIndex("cod4")));
                p.setNivel4(cursor.getString(cursor.getColumnIndex("nivel4")));

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return lista;
    }

}
