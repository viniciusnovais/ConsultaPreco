package pdasolucoes.com.br.consultapreco;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pdasolucoes.com.br.consultapreco.Adapter.ListaAgenda;
import pdasolucoes.com.br.consultapreco.Model.Agenda;

/**
 * Created by PDA on 20/11/2017.
 */

public class AgendaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListaAgenda adapter;
    private List<Agenda> lista;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        lista = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new ListaAgenda(lista, this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);


        adapter.ItemClickListener(new ListaAgenda.ItemClick() {
            @Override
            public void onClick(int position) {
                Intent i = new Intent(AgendaActivity.this, PrecoActivity.class);
                startActivity(i);
            }
        });


    }
}
