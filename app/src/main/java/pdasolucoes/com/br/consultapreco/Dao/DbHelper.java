package pdasolucoes.com.br.consultapreco.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
            db.execSQL("CREATE TABLE IF NOT EXISTS agenda(nomeConcorrente TEXT, data TEXT, endereco TEXT, praca TEXT )");

            db.execSQL("CREATE TABLE IF NOT EXISTS concorrente(id INTEGER PRIMARY KEY, nome TEXT, endereco TEXT, praca TEXT)");

            db.execSQL("CREATE TABLE IF NOT EXISTS produto(codProduto INTEGER PRIMARY KEY, ean TEXT, sku TEXT, nome TEXT, marca TEXT, precoConcorrente REAL, ruptura TEXT, oferta TEXT, unidMedida TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }


