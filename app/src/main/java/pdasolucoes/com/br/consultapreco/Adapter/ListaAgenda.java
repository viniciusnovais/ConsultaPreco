package pdasolucoes.com.br.consultapreco.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pdasolucoes.com.br.consultapreco.Model.Agenda;
import pdasolucoes.com.br.consultapreco.R;

/**
 * Created by PDA on 20/11/2017.
 */

public class ListaAgenda extends RecyclerView.Adapter<ListaAgenda.MyViewHolder> {

    private List<Agenda> lista;
    private Context context;
    private LayoutInflater layoutInflater;
    private ItemClick itemClick;

    public interface ItemClick {
        void onClick(int position);
    }

    public void ItemClickListener(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public ListaAgenda(List<Agenda> lista, Context context) {
        this.lista = lista;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.adapter_list_agenda, parent, false);

        MyViewHolder mv = new MyViewHolder(v);

        return mv;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvNome, tvPraca,tvEndereco,tvData;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvNome = (TextView) itemView.findViewById(R.id.nomeConcorrente);
            tvPraca = (TextView) itemView.findViewById(R.id.praca);
            tvEndereco = (TextView) itemView.findViewById(R.id.endereco);
            tvData = (TextView) itemView.findViewById(R.id.data);
        }

        @Override
        public void onClick(View v) {
            itemClick.onClick(getAdapterPosition());
        }
    }
}
