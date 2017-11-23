package pdasolucoes.com.br.consultapreco;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import pdasolucoes.com.br.consultapreco.Model.Usuario;
import pdasolucoes.com.br.consultapreco.Services.AutenticacaoService;

public class LoginActivity extends AppCompatActivity {

    private Button btEntrar;
    public static final String PREFERENCES = "Login";
    private TextInputEditText editLogin, editSenha;
    private String login, senha;
    private Usuario usuario = new Usuario();
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);

        editLogin = (TextInputEditText) findViewById(R.id.login);
        editSenha = (TextInputEditText) findViewById(R.id.senha);


        if (!(preferences.getString("login", "").equals("") || preferences.getString("senha", "").equals(""))) {
            login = preferences.getString("login", "");
            senha = preferences.getString("senha", "");
            AsyncLogin task = new AsyncLogin();
            task.execute(login, senha);
        }

        btEntrar = (Button) findViewById(R.id.btEntrar);

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(editLogin.getText().toString().equals("") || editSenha.getText().toString().equals(""))) {
                    login = editLogin.getText().toString();
                    senha = editSenha.getText().toString();
                    AsyncLogin task = new AsyncLogin();
                    task.execute(login, senha);
                }

            }
        });
    }


    private class AsyncLogin extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getString(R.string.carregando));
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            usuario = AutenticacaoService.GetAutenticacao(params[0].toString(), params[1].toString());

            return usuario;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                if (usuario.getMsgErro().equals("")) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("idUsuario", usuario.getId());
                    editor.putString("login", login);
                    editor.putString("senha", senha);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, usuario.getMsgErro(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
