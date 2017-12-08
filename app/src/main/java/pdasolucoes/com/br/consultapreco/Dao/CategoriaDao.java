package pdasolucoes.com.br.consultapreco.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;

import java.util.ArrayList;
import java.util.List;

import pdasolucoes.com.br.consultapreco.Model.Categoria;

/**
 * Created by PDA on 08/12/2017.
 */

public class CategoriaDao {

    private SQLiteDatabase database;
    private DbHelper helper;

    public CategoriaDao(Context context) {
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


    public void incluir(List<Categoria> lista) {


        try {

            deletar();

            for (Categoria c : lista) {
                ContentValues values = new ContentValues();

                values.put("cod1", c.getCod1());
                values.put("nivel1", c.getNivel1());
                values.put("cod2", c.getCod2());
                values.put("nivel2", c.getNivel2());
                values.put("cod3", c.getCod3());
                values.put("nivel3", c.getNivel3());
                values.put("cod4", c.getCod4());
                values.put("nivel4", c.getNivel4());
                values.put("cod5", c.getCod5());
                values.put("nivel5", c.getNivel5());

                getDatabase().insert("categoria", null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar() {
        getDatabase().delete("categoria", null, null);
    }


    public ArrayList<Categoria> listarSecao() {
        ArrayList<Categoria> lista = new ArrayList<>();
        Cursor cursor = getDatabase().rawQuery("SELECT DISTINCT cod1, nivel1 FROM categoria", null);

        try {

            while (cursor.moveToNext()) {
                Categoria c = new Categoria();

                c.setCod1(cursor.getInt(cursor.getColumnIndex("cod1")));
                c.setGenericText(cursor.getString(cursor.getColumnIndex("nivel1")));
                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return lista;
    }


    public ArrayList<Categoria> listarSubSecao(int cod1) {
        ArrayList<Categoria> lista = new ArrayList<>();
        Cursor cursor = getDatabase().rawQuery("SELECT DISTINCT cod2, nivel2 FROM categoria WHERE cod1 = ?", new String[]{cod1 + ""});

        try {

            while (cursor.moveToNext()) {
                Categoria c = new Categoria();

                c.setCod2(cursor.getInt(cursor.getColumnIndex("cod2")));
                c.setGenericText(cursor.getString(cursor.getColumnIndex("nivel2")));
                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return lista;
    }

    public ArrayList<Categoria> listarTipo(int cod2) {
        ArrayList<Categoria> lista = new ArrayList<>();
        Cursor cursor = getDatabase().rawQuery("SELECT DISTINCT cod3, nivel3 FROM categoria WHERE cod2 = ?", new String[]{cod2 + ""});

        try {

            while (cursor.moveToNext()) {
                Categoria c = new Categoria();

                c.setCod3(cursor.getInt(cursor.getColumnIndex("cod3")));
                c.setGenericText(cursor.getString(cursor.getColumnIndex("nivel3")));
                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return lista;
    }

    public ArrayList<Categoria> listarSubTipo(int cod3) {
        ArrayList<pdasolucoes.com.br.consultapreco.Model.Categoria> lista = new ArrayList<>();
        Cursor cursor = getDatabase().rawQuery("SELECT DISTINCT cod4, nivel4 FROM categoria WHERE cod3 = ?", new String[]{cod3 + ""});

        try {

            while (cursor.moveToNext()) {
                pdasolucoes.com.br.consultapreco.Model.Categoria c = new pdasolucoes.com.br.consultapreco.Model.Categoria();

                c.setCod4(cursor.getInt(cursor.getColumnIndex("cod4")));
                c.setGenericText(cursor.getString(cursor.getColumnIndex("nivel4")));
                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return lista;
    }

    public ArrayList<Categoria> listarProduto(int cod4) {
        ArrayList<pdasolucoes.com.br.consultapreco.Model.Categoria> lista = new ArrayList<>();
        Cursor cursor = getDatabase().rawQuery("SELECT DISTINCT cod5, nivel5 FROM categoria WHERE cod4 = ?", new String[]{cod4 + ""});

        try {

            while (cursor.moveToNext()) {
                pdasolucoes.com.br.consultapreco.Model.Categoria c = new pdasolucoes.com.br.consultapreco.Model.Categoria();

                c.setCod5(cursor.getInt(cursor.getColumnIndex("cod5")));
                c.setGenericText(cursor.getString(cursor.getColumnIndex("nivel5")));
                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return lista;
    }
}
