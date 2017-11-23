package pdasolucoes.com.br.consultapreco.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import pdasolucoes.com.br.consultapreco.Model.Produto;

/**
 * Created by caueg on 22/11/2017.
 */

public class ProdutoDao {

    public SQLiteDatabase database;
    private  DbHelper helper;


    public ProdutoDao(Context context){
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

    public long incluir(Produto produto) {

        ContentValues values = new ContentValues();

        values.put("codProduto", produto.getCodProduto());
        values.put("ean", produto.getEan());
        values.put("sku", produto.getSku());
        values.put("nome", produto.getNome());
        values.put("marca", produto.getMarca());
        values.put("precoConcorrente", String.valueOf(produto.getPrecoConcorrente()));
        values.put("ruptura", produto.getRuptura());
        values.put("oferta", produto.getOferta());
        values.put("unidMedida", produto.getUnidMedida());

        return getDatabase().insert("produto", null, values);
    }
}
