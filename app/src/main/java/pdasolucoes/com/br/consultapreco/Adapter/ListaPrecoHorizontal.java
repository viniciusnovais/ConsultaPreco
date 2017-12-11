package pdasolucoes.com.br.consultapreco.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.IllegalFormatCodePointException;
import java.util.List;

import pdasolucoes.com.br.consultapreco.Model.ProdutoPesquisa;
import pdasolucoes.com.br.consultapreco.R;
import pdasolucoes.com.br.consultapreco.Util.FuncoesUtil;

/**
 * Created by PDA on 16/11/2017.
 */

public class ListaPrecoHorizontal extends RecyclerView.Adapter<ListaPrecoHorizontal.MyViewHolder> {

    private Context context;
    private List<ProdutoPesquisa> lista;
    private LayoutInflater layoutInflater;
    private OpcaoClick opcaoClick;
    private ItemPreco itemPreco;

    public interface ItemPreco {
        void onPreco(String preco);
    }

    public void ItemPrecoListener(ItemPreco itemPreco) {
        this.itemPreco = itemPreco;
    }

    public interface OpcaoClick {
        void onClick(String opcao);
    }

    public void OpcaoClickListener(OpcaoClick opcaoClick) {
        this.opcaoClick = opcaoClick;
    }

    public ListaPrecoHorizontal(Context context, List<ProdutoPesquisa> lista) {
        this.context = context;
        this.lista = lista;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ListaPrecoHorizontal.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = layoutInflater.inflate(R.layout.adapter_list_preco_horizontal, parent, false);

        MyViewHolder mv = new MyViewHolder(v);

        return mv;
    }

    @Override
    public void onBindViewHolder(final ListaPrecoHorizontal.MyViewHolder holder, int position) {

        ProdutoPesquisa p = lista.get(position);

        holder.tvDesc.setText(p.getFamilia());

        holder.tvMarca.setText(p.getMarca());

        holder.linearLayoutDetalhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FuncoesUtil.popupEans(context);
            }
        });

        holder.editPreco.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().equals("")) {
                    itemPreco.onPreco(s.toString());
                }
            }
        });

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //vou ter q criar uma interface para passar esses dados para a activity
                opcaoClick.onClick(FuncoesUtil.verificaRadioGroup(group, checkedId));
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public RadioGroup radioGroup;
        public LinearLayout linearLayoutDetalhe, llItem;
        public TextView tvDesc, tvMarca;
        public EditText editPreco;

        public MyViewHolder(View itemView) {
            super(itemView);

            radioGroup = (RadioGroup) itemView.findViewById(R.id.groupOpcoesH);
            linearLayoutDetalhe = (LinearLayout) itemView.findViewById(R.id.linearLayoutDetalhe);
            llItem = (LinearLayout) itemView.findViewById(R.id.llItem);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            tvMarca = (TextView) itemView.findViewById(R.id.tvMarca);
            editPreco = (EditText) itemView.findViewById(R.id.editPreco);
        }
    }


}
