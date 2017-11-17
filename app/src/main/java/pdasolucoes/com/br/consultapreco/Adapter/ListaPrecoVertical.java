package pdasolucoes.com.br.consultapreco.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pdasolucoes.com.br.consultapreco.R;

/**
 * Created by PDA on 16/11/2017.
 */

public class ListaPrecoVertical extends RecyclerView.Adapter<ListaPrecoVertical.MyViewHolder> {

    private Context context;
    private List<Integer> lista;
    private LayoutInflater layoutInflater;

    public ListaPrecoVertical(Context context, List<Integer> lista) {
        this.context = context;
        this.lista = lista;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ListaPrecoVertical.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = layoutInflater.inflate(R.layout.adapter_list_preco_vertical,parent,false);
        MyViewHolder mv = new MyViewHolder(v);

        return mv;
    }

    @Override
    public void onBindViewHolder(ListaPrecoVertical.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
