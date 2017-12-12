package pdasolucoes.com.br.consultapreco.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import pdasolucoes.com.br.consultapreco.Model.ProdutoPesquisa;
import pdasolucoes.com.br.consultapreco.R;

/**
 * Created by PDA on 16/11/2017.
 */

public class ListaPrecoVertical extends RecyclerView.Adapter<ListaPrecoVertical.MyViewHolder> {

    private Context context;
    private List<ProdutoPesquisa> lista;
    private LayoutInflater layoutInflater;
    private ItemClick itemClick;
    private ItemPreco itemPreco;


    public interface ItemPreco {
        void onPreco(String preco, int position);
    }

    public void ItemPrecoListener(ItemPreco itemPreco) {
        this.itemPreco = itemPreco;
    }

    public interface ItemClick {
        void onClick(ProdutoPesquisa p);
    }

    public void ItemClickListener(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public ListaPrecoVertical(Context context, List<ProdutoPesquisa> lista) {
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
    public void onBindViewHolder(final ListaPrecoVertical.MyViewHolder holder, final int position) {

        final ProdutoPesquisa p = lista.get(position);

        holder.tvDesc.setText(p.getFamilia());

        holder.tvMarca.setText(p.getMarca());

        if (!p.getPreco().equals("")) {
            if (p.getPrecoDigitado() != null) {
                holder.editPreco.setText(p.getPrecoDigitado().replaceAll("[.]", ","));
                if (p.getPrecoDigitado().equals("0") || p.getPrecoDigitado().equals("")) {
                    holder.editPreco.setText("");
                }
            } else {
                holder.editPreco.setText(p.getPreco().replaceAll("[.]", ","));
                if (p.getPreco().equals("0") || p.getPreco().equals("")) {
                    holder.editPreco.setText("");
                }
            }


        }

        holder.editPreco.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!holder.editPreco.getEditableText().toString().equals("")) {
                    p.setPrecoDigitado(holder.editPreco.getText().toString());
                } else {
                    p.setPrecoDigitado("0");
                }
                itemPreco.onPreco(p.getPrecoDigitado(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public EditText editPreco;
        public TextView tvDesc, tvMarca;
        public LinearLayout llPai;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            tvMarca = (TextView) itemView.findViewById(R.id.tvMarca);
            editPreco = (EditText) itemView.findViewById(R.id.editPreco);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClick.onClick(lista.get(getAdapterPosition()));
        }
    }
}
