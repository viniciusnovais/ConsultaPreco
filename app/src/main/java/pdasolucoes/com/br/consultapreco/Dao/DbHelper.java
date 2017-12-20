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
    private static final int VERSAO = 7;


    public DbHelper(Context context) {

        super(context, BANCO_DADOS, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS agenda(id INTEGER, idConcorrente INTEGER, nomeConcorrente TEXT,idLoja INTEGER,nomeLoja TEXT," +
                " data TEXT, lista INTEGER, status INTEGER, idUsuario INTEGER, dataHoraInicio TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS concorrente(id INTEGER, nome TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS categoria(cod1 INTEGER, nivel1 TEXT, cod2 INTEGER, nivel2 TEXT, cod3 INTEGER, nivel3 TEXT, cod4 INTEGER, nivel4 TEXT, cod5 INTEGER, nivel5 TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS produtoPesquisa(seqFamilia INTEGER, familia TEXT, seqMarca INTEGER, marca TEXT, codAcesso TEXT, seqLista INTEGER," +
                " cod1 INTEGER, nivel1 TEXT, cod2 INTEGER, nivel2 TEXT, cod3 INTEGER, nivel3 TEXT, cod4 INTEGER, nivel4 TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS itemColeta(seqFamilia INTEGER,familia TEXT, agendaId INTEGER, preco REAL, caminhoFoto TEXT, tipo TEXT, ean TEXT, enviado INTEGER)");
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


