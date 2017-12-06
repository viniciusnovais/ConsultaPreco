package pdasolucoes.com.br.consultapreco.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caueg on 22/11/2017.
 */


public class DbHelper extends SQLiteOpenHelper {
    private static final String BANCO_DADOS = "ConsultaPreco";
    private static final int VERSAO = 1;


    public DbHelper(Context context) {

        super(context, BANCO_DADOS, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS agenda(id INTEGER, idConcorrente INTEGER, nomeConcorrente TEXT,idLoja INTEGER,nomeLoja TEXT," +
                " data TEXT, praca TEXT, status INTEGER, idUsuario INTEGER)");

        db.execSQL("CREATE TABLE IF NOT EXISTS concorrente(id INTEGER, nome TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS produto(codProduto INTEGER PRIMARY KEY, ean TEXT, sku TEXT, nome TEXT, marca TEXT, precoConcorrente REAL, ruptura TEXT, oferta TEXT, unidMedida TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            deleteDataBases(db);
            onCreate(db);
        }
    }

    private void deleteDataBases(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        List<String> tables = new ArrayList<>();

        while (cursor.moveToNext()) {
            if (!cursor.getString(0).equals("sqlite_sequence")) {
                tables.add(cursor.getString(0));
            }
        }

        for (String table : tables) {
            String dropQuery = "DROP TABLE IF EXISTS " + table;
            db.execSQL(dropQuery);
        }
    }
}


