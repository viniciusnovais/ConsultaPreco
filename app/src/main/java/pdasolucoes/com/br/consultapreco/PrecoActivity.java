package pdasolucoes.com.br.consultapreco;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import pdasolucoes.com.br.consultapreco.Adapter.ListaPrecoHorizontal;
import pdasolucoes.com.br.consultapreco.Adapter.ListaPrecoVertical;
import pdasolucoes.com.br.consultapreco.Model.ItemColeta;
import pdasolucoes.com.br.consultapreco.Util.CustomLinearLayoutManager;
import pdasolucoes.com.br.consultapreco.Util.CustomRecyclerView;
import pdasolucoes.com.br.consultapreco.Util.FuncoesUtil;
import pdasolucoes.com.br.consultapreco.Util.ImageResizeUtils;

/**
 * Created by PDA on 16/11/2017.
 */

public class PrecoActivity extends AppCompatActivity {

    private CustomRecyclerView customRecyclerView;
    private List<Integer> lista = new ArrayList<>();
    private AlertDialog dialogOpcao;
    private ListaPrecoHorizontal adapterHorizontal;
    private ListaPrecoVertical adapterVertical;
    private LinearLayout linearLayoutHorizontal, linearLayoutVertical, linearLayoutBusca, linearLayoutDetalhe;
    private RecyclerView recyclerViewVertical;
    private ImageView arrowLeft, arrowRight;
    private RadioGroup groupOpcoesB;
    private Button btFeito;
    private ImageView btListaHorizontal, btListaVertical, btBeep;
    private int position = 0;
    private File file;
    private ItemColeta itemColeta = new ItemColeta();
    private List<ItemColeta> listaColeta = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preco);

        arrowRight = (ImageView) findViewById(R.id.arrowRight);
        arrowLeft = (ImageView) findViewById(R.id.arrowLeft);
        customRecyclerView = (CustomRecyclerView) findViewById(R.id.customRecyclerView);
        linearLayoutHorizontal = (LinearLayout) findViewById(R.id.linearLayoutHorizontal);
        linearLayoutVertical = (LinearLayout) findViewById(R.id.linearLayoutVertical);
        recyclerViewVertical = (RecyclerView) findViewById(R.id.recyclerViewVertical);
        linearLayoutBusca = (LinearLayout) findViewById(R.id.linearLayoutBusca);
        groupOpcoesB = (RadioGroup) findViewById(R.id.groupOpcoesB);
        btFeito = (Button) findViewById(R.id.btFeito);

        btListaHorizontal = (ImageView) findViewById(R.id.listaHorizontal);
        btListaVertical = (ImageView) findViewById(R.id.listaVertical);
        btBeep = (ImageView) findViewById(R.id.beep);

        for (int i = 0; i < 20; i++) {
            lista.add(i);
        }

        btFeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui irei validar se pode enviar parcial e se ja respondeu tudo
            }
        });

        btListaHorizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayoutHorizontal.setVisibility(View.VISIBLE);
                linearLayoutVertical.setVisibility(View.GONE);
                linearLayoutBusca.setVisibility(View.GONE);

                CustomLinearLayoutManager llm = new CustomLinearLayoutManager(PrecoActivity.this, LinearLayoutManager.HORIZONTAL, false);
                customRecyclerView.setLayoutManager(llm);

                adapterHorizontal = new ListaPrecoHorizontal(PrecoActivity.this, lista);
                customRecyclerView.setAdapter(adapterHorizontal);

                adapterHorizontal.ItemPrecoListener(new ListaPrecoHorizontal.ItemPreco() {
                    @Override
                    public void onPreco(String preco) {
                        itemColeta.setPreco(new BigDecimal(preco));
                    }
                });

                adapterHorizontal.OpcaoClickListener(new ListaPrecoHorizontal.OpcaoClick() {
                    @Override
                    public void onClick(String opcao) {
                        acaoAposSelecao(opcao);
                    }
                });

                arrowRight.setVisibility(View.VISIBLE);
                arrowRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                arrowLeft.setVisibility(View.VISIBLE);
                                position++;
                                customRecyclerView.scrollToPosition(position);


                                if (position == customRecyclerView.getAdapter().getItemCount() - 1) {
                                    arrowRight.setVisibility(View.INVISIBLE);
                                }
                            }
                        }, 100);
                    }
                });


                arrowLeft.setVisibility(View.INVISIBLE);
                arrowLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                arrowRight.setVisibility(View.VISIBLE);

                                position--;
                                customRecyclerView.scrollToPosition(position);

                                if (position <= 0)
                                    arrowLeft.setVisibility(View.INVISIBLE);
                            }
                        }, 100);
                    }
                });
            }
        });


        btListaVertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] precoColetado = new String[1];

                linearLayoutHorizontal.setVisibility(View.GONE);
                linearLayoutVertical.setVisibility(View.VISIBLE);
                linearLayoutBusca.setVisibility(View.GONE);

                arrowRight.setVisibility(View.INVISIBLE);
                arrowLeft.setVisibility(View.INVISIBLE);

                CustomLinearLayoutManager llm = new CustomLinearLayoutManager(PrecoActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerViewVertical.setLayoutManager(llm);

                adapterVertical = new ListaPrecoVertical(PrecoActivity.this, lista);
                recyclerViewVertical.setAdapter(adapterVertical);

                adapterVertical.ItemClickListener(new ListaPrecoVertical.ItemClick() {
                    @Override
                    public void onClick(int position) {

                        popupOpcoes(precoColetado[0]);
                    }
                });

                adapterVertical.ItemPrecoListener(new ListaPrecoVertical.ItemPreco() {
                    @Override
                    public void onPreco(String preco) {
                        precoColetado[0] = preco;
                        itemColeta.setPreco(new BigDecimal(preco));
                    }
                });
            }
        });

        btBeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutHorizontal.setVisibility(View.GONE);
                linearLayoutVertical.setVisibility(View.GONE);
                linearLayoutBusca.setVisibility(View.VISIBLE);
                linearLayoutDetalhe = (LinearLayout) findViewById(R.id.linearLayoutDetalhe);

                linearLayoutDetalhe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FuncoesUtil.popupEans(PrecoActivity.this);
                    }
                });

                final EditText editCodigo = (EditText) findViewById(R.id.editCodigo);
                final EditText editPreco = (EditText) findViewById(R.id.editPrecob);
                final LinearLayout linearLayoutRespostaBusca = (LinearLayout) findViewById(R.id.linearLayoutRespostaBusca);

                groupOpcoesB.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                        acaoAposSelecao(FuncoesUtil.verificaRadioGroup(group, checkedId));
                    }
                });

                editCodigo.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if ((event.getRawX() <= editCodigo.getCompoundDrawables()[0].getBounds().width())) {
                                if (!editCodigo.getText().toString().equals("")) {
                                    editCodigo.setText("");
                                    linearLayoutRespostaBusca.setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(PrecoActivity.this, getString(R.string.campos_obrigatorio), Toast.LENGTH_SHORT).show();
                                }

                                editPreco.requestFocus();
                                return true;
                            }
                        }
                        return false;
                    }
                });

                editCodigo.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                if (!editCodigo.getText().toString().equals("")) {
                                    //passar o texto o código para a busca no banco
                                    linearLayoutRespostaBusca.setVisibility(View.VISIBLE);

                                } else {
                                    Toast.makeText(PrecoActivity.this, getString(R.string.campos_obrigatorio), Toast.LENGTH_SHORT).show();
                                }

                                editPreco.requestFocus();
                            }
                        }

                        return false;
                    }
                });

                btFeito.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editPreco.getText().toString().equals("")) {
                            //pega o preço e salva
                            editPreco.setText("");
                            editCodigo.setText("");
                        } else {
                            Toast.makeText(PrecoActivity.this, getString(R.string.campos_obrigatorio), Toast.LENGTH_SHORT).show();
                        }

                        editCodigo.requestFocus();
                    }
                });

            }
        });
    }

    //aqui irei passar o produto selecionado como parametro
    private void popupOpcoes(final String preco) {
        View v = View.inflate(PrecoActivity.this, R.layout.popup_opcoes, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(PrecoActivity.this);
        builder.setView(v);

        TextView tvDescricao;
        ListView listViewEans;
        RadioGroup group;
        Button btFeito, btCancelar;

        tvDescricao = (TextView) v.findViewById(R.id.tvDescricao);
        listViewEans = (ListView) v.findViewById(R.id.listViewEans);
        group = (RadioGroup) v.findViewById(R.id.groupOpcoes);
        btFeito = (Button) v.findViewById(R.id.btFeito);
        btCancelar = (Button) v.findViewById(R.id.btCancelar);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                acaoAposSelecao(FuncoesUtil.verificaRadioGroup(group, checkedId));

            }
        });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOpcao.dismiss();
            }
        });

        btFeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        dialogOpcao = builder.create();
        dialogOpcao.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {

                if (file != null && file.exists()) {

                    //mostrandao imagem ao clicar em oferta
                    FuncoesUtil.mostraImagem(PrecoActivity.this, Uri.fromFile(file));
                }
            }
        }
    }


    private void acaoAposSelecao(String opcao) {

        if (opcao.equals("Oferta")) {
            //foto e preco
            file = FuncoesUtil.AbrirCamera(PrecoActivity.this);
            itemColeta.setCaminhoFoto(file.getAbsolutePath());
        } else if (opcao.equals("Ruptura")) {
            //não pega preco, só insere flag
            itemColeta.setPreco(new BigDecimal(0));
        }
        itemColeta.setTipo(opcao);
    }
}
