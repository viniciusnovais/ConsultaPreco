package pdasolucoes.com.br.consultapreco;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pdasolucoes.com.br.consultapreco.Adapter.ListaAgenda;
import pdasolucoes.com.br.consultapreco.Dao.AgendaDao;
import pdasolucoes.com.br.consultapreco.Dao.PesquisaProdutoDao;
import pdasolucoes.com.br.consultapreco.Model.Agenda;
import pdasolucoes.com.br.consultapreco.Model.ProdutoPesquisa;
import pdasolucoes.com.br.consultapreco.Services.AgendaService;
import pdasolucoes.com.br.consultapreco.Services.ProdutoPesquisaService;

/**
 * Created by PDA on 20/11/2017.
 */

public class AgendaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListaAgenda adapter;
    private List<Agenda> lista;
    private AgendaDao agendaDao;
    private SharedPreferences preferences;
    private AlertDialog dialog;
    private PesquisaProdutoDao pesquisaProdutoDao;
    private Intent i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        i = new Intent(AgendaActivity.this, PrecoActivity.class);
        preferences = getSharedPreferences(LoginActivity.PREFERENCES, MODE_PRIVATE);
        agendaDao = new AgendaDao(this);
        pesquisaProdutoDao = new PesquisaProdutoDao(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        lista = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);

        AsynGetAgenda task = new AsynGetAgenda();
        task.execute();

    }

    private class AsynGetAgenda extends AsyncTask {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AgendaActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getString(R.string.carregando));
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            lista = AgendaService.GetListaAgenda();

            agendaDao.incluir(lista);

            return agendaDao.listar();
        }

        @Override
        protected void onPostExecute(final Object o) {
            super.onPostExecute(o);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();

                List<Agenda> lista = (List<Agenda>) o;

                if (lista.size() > 0) {

                    adapter = new ListaAgenda(lista, AgendaActivity.this, preferences.getInt("idUsuario", 0));
                    recyclerView.setAdapter(adapter);

                    adapter.ItemClickListener(new ListaAgenda.ItemClick() {
                        @Override
                        public void onClick(int position) {
                            Agenda a = ((List<Agenda>) o).get(position);
                            if (agendaDao.idAgendaAberta(preferences.getInt("idUsuario", 0)) == a.getId()) {

                                popupConfirmaInicio(((List<Agenda>) o).get(position));
                            } else if (agendaDao.idAgendaAberta(preferences.getInt("idUsuario", 0)) == 0) {
                                popupConfirmaInicio(((List<Agenda>) o).get(position));
                            } else {

                                Toast.makeText(AgendaActivity.this, "Finalize o formulário em progresso", Toast.LENGTH_SHORT).show();
                            }

                            new AsyncListaProduto().execute(a.getId());
                        }
                    });
                } else {
                    Toast.makeText(AgendaActivity.this, "Não existe agendamento para essa praça", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private class AsyncListaProduto extends AsyncTask {
        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AgendaActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getString(R.string.carregando));
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            List<ProdutoPesquisa> lista = ProdutoPesquisaService.listarProdutosPesquisa(Integer.parseInt(params[0].toString()));
            return lista;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();

                pesquisaProdutoDao.incluir((List<ProdutoPesquisa>) o);

            }
        }
    }

    private class AsyncAbrirPesquisa extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {

            String resposta = AgendaService.AbrirPesquisa(Integer.parseInt(params[0].toString()), preferences.getInt("idUsuario", 0));

            return resposta;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if (o.toString().equals("OK")) {
                startActivity(i);
                finish();
            }
        }
    }

    private void popupConfirmaInicio(final Agenda a) {
        View v = View.inflate(AgendaActivity.this, R.layout.popup_confirma_inicio, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(AgendaActivity.this);
        builder.setView(v);

        Button btCancelar, btFeito;
        TextView tvPraca, tvConcorrente, tvdata, tvUsuario;
        tvPraca = (TextView) v.findViewById(R.id.tvPraca);
        tvConcorrente = (TextView) v.findViewById(R.id.tvConcorrente);
        tvdata = (TextView) v.findViewById(R.id.data);
        tvUsuario = (TextView) v.findViewById(R.id.tvUsuario);
        btCancelar = (Button) v.findViewById(R.id.btCancel);
        btFeito = (Button) v.findViewById(R.id.btDone);

        tvConcorrente.setText(a.getNomeConcorrente());

        tvdata.setText(a.getData());

        tvUsuario.setText(preferences.getString("nome", ""));

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btFeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                i.putExtra("agendaId", a.getId());
                a.setDataHoraInicio(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
                agendaDao.AlterarDataHoraInicio(a);
                new AsyncAbrirPesquisa().execute(a.getId());

            }
        });


        dialog = builder.create();
        dialog.show();
    }
}
