package pdasolucoes.com.br.consultapreco.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import pdasolucoes.com.br.consultapreco.R;

/**
 * Created by PDA on 16/11/2017.
 */

public class ListaPrecoVertical extends RecyclerView.Adapter<ListaPrecoVertical.MyViewHolder> {

    private Context context;
    private List<Integer> lista;
    private LayoutInflater layoutInflater;
    private ItemClick itemClick;
    private ItemPreco itemPreco;


    public interface ItemPreco {
        void onPreco(String preco);
    }

    public void ItemPrecoListener(ItemPreco itemPreco) {
        this.itemPreco = itemPreco;
    }

    public interface ItemClick {
        void onClick(int position);
    }

    public void ItemClickListener(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public ListaPrecoVertical(Context context, List<Integer> lista) {
        this.context = context;
        this.lista = lista;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ListaPrecoVertical.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = layoutInflater.inflate(R.layout.adapter_list_preco_vertical, parent, false);
        MyViewHolder mv = new MyViewHolder(v);

        return mv;
    }

    @Override
    public void onBindViewHolder(final ListaPrecoVertical.MyViewHolder holder, int position) {

        holder.editPreco.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    itemPreco.onPreco(holder.editPreco.getText().toString());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public EditText editPreco;

        public MyViewHolder(View itemView) {
            super(itemView);

            editPreco = (EditText) itemView.findViewById(R.id.editPreco);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClick.onClick(getAdapterPosition());
        }
    }
}
