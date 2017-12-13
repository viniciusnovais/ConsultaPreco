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
import android.text.Editable;
import android.text.TextWatcher;
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
import pdasolucoes.com.br.consultapreco.Dao.CategoriaDao;
import pdasolucoes.com.br.consultapreco.Dao.ItemColetaDao;
import pdasolucoes.com.br.consultapreco.Dao.PesquisaProdutoDao;
import pdasolucoes.com.br.consultapreco.Interfaces.OnSpinerItemClick;
import pdasolucoes.com.br.consultapreco.Model.Categoria;
import pdasolucoes.com.br.consultapreco.Model.ItemColeta;
import pdasolucoes.com.br.consultapreco.Model.ProdutoPesquisa;
import pdasolucoes.com.br.consultapreco.Util.CustomLinearLayoutManager;
import pdasolucoes.com.br.consultapreco.Util.CustomRecyclerView;
import pdasolucoes.com.br.consultapreco.Util.FuncoesUtil;
import pdasolucoes.com.br.consultapreco.Util.ImageResizeUtils;
import pdasolucoes.com.br.consultapreco.Util.SpinnerDialog;

/**
 * Created by PDA on 16/11/2017.
 */

public class PrecoActivity extends AppCompatActivity {

    private CustomRecyclerView customRecyclerView;
    private AlertDialog dialogOpcao;
    private List<ProdutoPesquisa> lista;
    private ListaPrecoHorizontal adapterHorizontal;
    private ListaPrecoVertical adapterVertical;
    private LinearLayout linearLayoutHorizontal, linearLayoutVertical, linearLayoutBusca, linearLayoutDetalhe;
    private RecyclerView recyclerViewVertical;
    private ImageView arrowLeft, arrowRight;
    private RadioGroup groupOpcoesB;
    private SpinnerDialog spinnerSecao, spinnerSubSecao, spinnerTipo, spinnerSubTipo, spinnerProduto;
    private Button btFeito;
    private ImageView imageFilter;
    private int position = 0, cod1 = -1, cod2 = -1, cod3 = -1, cod4 = -1;
    private File file;
    private CategoriaDao categoriaDao;
    private PesquisaProdutoDao pesquisaProdutoDao;
    private LinearLayout llFiltro, linearLayoutRespostaBusca;
    private TextView tvSecao, tvSubSecao, tvTipo, tvSubTipo;
    private EditText editCodigo;
    private ItemColeta itemColeta;
    private ItemColetaDao itemColetaDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preco);

        categoriaDao = new CategoriaDao(this);
        pesquisaProdutoDao = new PesquisaProdutoDao(this);
        itemColetaDao = new ItemColetaDao(this);
        itemColeta = new ItemColeta();
        //pegando o id da agenda
        itemColeta.setAgendaId(getIntent().getIntExtra("agendaId", 0));

        arrowRight = (ImageView) findViewById(R.id.arrowRight);
        arrowLeft = (ImageView) findViewById(R.id.arrowLeft);
        customRecyclerView = (CustomRecyclerView) findViewById(R.id.customRecyclerView);
        linearLayoutHorizontal = (LinearLayout) findViewById(R.id.linearLayoutHorizontal);
        linearLayoutVertical = (LinearLayout) findViewById(R.id.linearLayoutVertical);
        recyclerViewVertical = (RecyclerView) findViewById(R.id.recyclerViewVertical);
        linearLayoutBusca = (LinearLayout) findViewById(R.id.linearLayoutBusca);
        groupOpcoesB = (RadioGroup) findViewById(R.id.groupOpcoesB);
        imageFilter = (ImageView) findViewById(R.id.imageFilter);
        btFeito = (Button) findViewById(R.id.btFeito);
        llFiltro = (LinearLayout) findViewById(R.id.llFiltros);
        tvSecao = (TextView) findViewById(R.id.tvSecao);
        tvSubSecao = (TextView) findViewById(R.id.tvSubSecao);
        tvTipo = (TextView) findViewById(R.id.tvTipo);
        tvSubTipo = (TextView) findViewById(R.id.tvSubTipo);


        //Busca por Produto
        linearLayoutRespostaBusca = (LinearLayout) findViewById(R.id.linearLayoutRespostaBusca);
        editCodigo = (EditText) findViewById(R.id.editCodigo);


        atualizaListas(pesquisaProdutoDao.filtroPesquisa(cod1, cod2, cod3, cod4));

        imageFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (llFiltro.getVisibility() != View.VISIBLE) {
                    llFiltro.setVisibility(View.VISIBLE);
                } else {
                    llFiltro.setVisibility(View.GONE);
                }

            }
        });

        btFeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui irei validar se pode enviar parcial e se ja respondeu tudo
            }
        });

        tvSecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                secaoFiltro(categoriaDao.listarSecao());
            }
        });
    }

    //aqui irei passar o produto selecionado como parametro
    private void popupOpcoes(final ProdutoPesquisa p) {
        View v = View.inflate(PrecoActivity.this, R.layout.popup_opcoes, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(PrecoActivity.this);
        builder.setView(v);
//
//        TextView tvDescricao;
//        ListView listViewEans;
        RadioGroup group;
        Button btFeito, btCancelar;

//        tvDescricao = (TextView) v.findViewById(R.id.tvDescricao);
//        listViewEans = (ListView) v.findViewById(R.id.listViewEans);
        group = (RadioGroup) v.findViewById(R.id.groupOpcoes);
        btFeito = (Button) v.findViewById(R.id.btFeito);
        btCancelar = (Button) v.findViewById(R.id.btCancelar);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                acaoAposSelecao(FuncoesUtil.verificaRadioGroup(group, checkedId), p);

            }
        });

        FuncoesUtil.selecionarRadio(p.getTipo(), group);

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOpcao.dismiss();
            }
        });

        btFeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incluirAlteraTipo(itemColeta, p);
                dialogOpcao.dismiss();
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
                    itemColeta.setCaminhoFoto(file.getAbsolutePath());
                    FuncoesUtil.mostraImagem(PrecoActivity.this, Uri.fromFile(file), itemColeta);

                }
            }
        }
    }


    private void acaoAposSelecao(String opcao, ProdutoPesquisa p) {

        if (opcao.equals("Oferta")) {
            //foto e preco
            file = FuncoesUtil.AbrirCamera(PrecoActivity.this);
        } else if (opcao.equals("Ruptura")) {
            //não pega preco, só insere flag
            itemColeta.setPreco(new BigDecimal("0"));
            itemColeta.setCaminhoFoto("");
        } else if (opcao.equals("Fifo")) {
            itemColeta.setCaminhoFoto("");
        }
        itemColeta.setTipo(opcao);
        incluirAlteraTipo(itemColeta, p);
    }

    private void secaoFiltro(ArrayList<Categoria> lista) {

        spinnerSecao = new SpinnerDialog(PrecoActivity.this, lista, "Selecione a Seção");
        spinnerSecao.showSpinerDialog();
        spinnerSecao.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(Object o) {
                final Categoria c = (Categoria) o;
                tvSecao.setText(c.getGenericText());
                defultCampos(1);
                cod1 = c.getCod1();
                atualizaListas(pesquisaProdutoDao.filtroPesquisa(cod1, cod2, cod3, cod4));
                tvSubSecao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subSecaoFiltro(categoriaDao.listarSubSecao(c.getCod1()));

                    }
                });

            }
        });
    }

    private void subSecaoFiltro(ArrayList<Categoria> lista) {

        spinnerSubSecao = new SpinnerDialog(PrecoActivity.this, lista, "Selecione a SubSeção");
        spinnerSubSecao.showSpinerDialog();
        spinnerSubSecao.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(Object o) {
                final Categoria c = (Categoria) o;

                tvSubSecao.setText(c.getGenericText());
                defultCampos(2);
                cod2 = c.getCod2();
                atualizaListas(pesquisaProdutoDao.filtroPesquisa(cod1, cod2, cod3, cod4));
                tvTipo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cod2 = c.getCod2();
                        tipoFiltro(categoriaDao.listarTipo(c.getCod2()));
                        atualizaListas(pesquisaProdutoDao.filtroPesquisa(cod1, cod2, cod3, cod4));
                    }
                });
            }
        });
    }

    private void tipoFiltro(ArrayList<Categoria> lista) {
        spinnerTipo = new SpinnerDialog(PrecoActivity.this, lista, "Selecione o Tipo");
        spinnerTipo.showSpinerDialog();
        spinnerTipo.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(Object o) {
                final Categoria c = (Categoria) o;
                tvTipo.setText(c.getGenericText());
                defultCampos(3);
                cod3 = c.getCod3();
                atualizaListas(pesquisaProdutoDao.filtroPesquisa(cod1, cod2, cod3, cod4));
                tvSubTipo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subTipoFiltro(categoriaDao.listarSubTipo(c.getCod3()));
                    }
                });

            }
        });
    }


    private void subTipoFiltro(ArrayList<Categoria> lista) {

        spinnerSubTipo = new SpinnerDialog(PrecoActivity.this, lista, "Selecione o SubTipo");
        spinnerSubTipo.showSpinerDialog();
        spinnerSubTipo.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(Object o) {
                final Categoria c = (Categoria) o;
                tvSubTipo.setText(c.getGenericText());
                cod4 = c.getCod4();
                atualizaListas(pesquisaProdutoDao.filtroPesquisa(cod1, cod2, cod3, cod4));
            }
        });

    }


    private void defultCampos(int tipo) {

        if (tipo == 1) {
            tvSubSecao.setText("SubSeção");
            cod2 = -1;
            tvTipo.setText("Tipo");
            cod3 = -1;
            tvSubTipo.setText("SubTipo");
            cod4 = -1;
        } else if (tipo == 2) {
            tvTipo.setText("Tipo");
            cod3 = -1;
            tvSubTipo.setText("SubTipo");
            cod4 = -1;
        } else if (tipo == 3) {
            tvSubTipo.setText("SubTipo");
            cod4 = -1;
        }
    }


    private void atualizaListas(List<ProdutoPesquisa> lista) {

        if (getIntent().hasExtra("tipo_pesquisa")) {
            if (getIntent().getStringExtra("tipo_pesquisa").equals("H")) {

                linearLayoutHorizontal.setVisibility(View.VISIBLE);
                linearLayoutVertical.setVisibility(View.GONE);
                linearLayoutBusca.setVisibility(View.GONE);

                CustomLinearLayoutManager llm = new CustomLinearLayoutManager(PrecoActivity.this, LinearLayoutManager.HORIZONTAL, false);
                customRecyclerView.setLayoutManager(llm);

                adapterHorizontal = new ListaPrecoHorizontal(PrecoActivity.this, lista);
                customRecyclerView.setAdapter(adapterHorizontal);

                adapterHorizontal.ItemPrecoListener(new ListaPrecoHorizontal.ItemPreco() {
                    @Override
                    public void onPreco(String preco, int position) {
                        incluiOuAlteraPreco(preco, pesquisaProdutoDao.filtroPesquisa(cod1, cod2, cod3, cod4).get(position));
                    }
                });

                adapterHorizontal.OpcaoClickListener(new ListaPrecoHorizontal.OpcaoClick() {
                    @Override
                    public void onClick(String opcao, ProdutoPesquisa p) {
                        acaoAposSelecao(opcao, p);
                    }
                });


                arrowRight.setVisibility(View.VISIBLE);
                if (position == customRecyclerView.getAdapter().getItemCount() - 1) {
                    arrowRight.setVisibility(View.INVISIBLE);
                }
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


                if (position <= 0) {
                    arrowLeft.setVisibility(View.INVISIBLE);
                }
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

            } else if (getIntent().getStringExtra("tipo_pesquisa").equals("V")) {

                linearLayoutHorizontal.setVisibility(View.GONE);
                linearLayoutVertical.setVisibility(View.VISIBLE);
                linearLayoutBusca.setVisibility(View.GONE);

                editCodigo.setText("");
                linearLayoutBusca.setVisibility(View.INVISIBLE);

                arrowRight.setVisibility(View.INVISIBLE);
                arrowLeft.setVisibility(View.INVISIBLE);

                CustomLinearLayoutManager llm = new CustomLinearLayoutManager(PrecoActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerViewVertical.setLayoutManager(llm);

                adapterVertical = new ListaPrecoVertical(PrecoActivity.this, lista);
                recyclerViewVertical.setAdapter(adapterVertical);

                adapterVertical.ItemPrecoListener(new ListaPrecoVertical.ItemPreco() {
                    @Override
                    public void onPreco(String preco, int position) {
                        incluiOuAlteraPreco(preco, pesquisaProdutoDao.filtroPesquisa(cod1, cod2, cod3, cod4).get(position));
                    }
                });

                adapterVertical.ItemClickListener(new ListaPrecoVertical.ItemClick() {
                    @Override
                    public void onClick(ProdutoPesquisa p) {

                        popupOpcoes(p);
                    }
                });
            } else if (getIntent().getStringExtra("tipo_pesquisa").equals("B")) {
                linearLayoutHorizontal.setVisibility(View.GONE);
                linearLayoutVertical.setVisibility(View.GONE);
                linearLayoutBusca.setVisibility(View.VISIBLE);
                linearLayoutDetalhe = (LinearLayout) findViewById(R.id.linearLayoutDetalhe);

                final ProdutoPesquisa[] p = new ProdutoPesquisa[1];
                final EditText editPreco = (EditText) findViewById(R.id.editPrecob);
                final TextView tvDescricao = (TextView) findViewById(R.id.tvDescb);
                final TextView tvMarca = (TextView) findViewById(R.id.tvMarcab);

                editPreco.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!s.toString().equals("")) {
                            incluiOuAlteraPreco(s.toString(), p[0]);
                        }
                    }
                });

                editCodigo.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if ((event.getRawX() <= editCodigo.getCompoundDrawables()[0].getBounds().width())) {
                                if (!editCodigo.getText().toString().equals("")) {
                                    p[0] = pesquisaProdutoDao.buscaProdEan(editCodigo.getText().toString());
                                    if (p[0] != null) {
                                        editCodigo.setText("");
                                        FuncoesUtil.selecionarRadio(p[0].getTipo(), groupOpcoesB);
                                        tvMarca.setText(p[0].getMarca());
                                        tvDescricao.setText(p[0].getFamilia());
                                        if (p[0].getPreco() != null) {
                                            editPreco.setText(p[0].getPreco().replaceAll("[.]", ","));
                                        }
                                        linearLayoutRespostaBusca.setVisibility(View.VISIBLE);
                                    } else {
                                        Toast.makeText(PrecoActivity.this, getString(R.string.produto_n_encontrado), Toast.LENGTH_SHORT).show();
                                    }
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
                                    p[0] = pesquisaProdutoDao.buscaProdEan(editCodigo.getText().toString());
                                    if (p[0] != null) {
                                        editCodigo.setText("");
                                        FuncoesUtil.selecionarRadio(p[0].getTipo(), groupOpcoesB);
                                        tvMarca.setText(p[0].getMarca());
                                        tvDescricao.setText(p[0].getFamilia());
                                        if (p[0].getPreco() != null) {
                                            editPreco.setText(p[0].getPreco().replaceAll("[.]", ","));
                                        }
                                        linearLayoutRespostaBusca.setVisibility(View.VISIBLE);
                                    } else {
                                        Toast.makeText(PrecoActivity.this, getString(R.string.produto_n_encontrado), Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(PrecoActivity.this, getString(R.string.campos_obrigatorio), Toast.LENGTH_SHORT).show();
                                }

                                editPreco.requestFocus();
                            }
                        }

                        return false;
                    }
                });

                groupOpcoesB.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                        acaoAposSelecao(FuncoesUtil.verificaRadioGroup(group, checkedId), p[0]);
                    }
                });
            }
        }

    }

    private void incluiOuAlteraPreco(String preco, ProdutoPesquisa p) {
        preco = preco.replaceAll("[,]", ".");
        itemColeta.setSeqFamilia(p.getSeqFamilia());
        itemColeta.setFamilia(p.getFamilia());
        itemColeta.setTipo("Normal");
        try {
            itemColeta.setPreco(new BigDecimal(preco.replaceAll("[,]", ".")));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (itemColetaDao.existeColeta(itemColeta)) {
            itemColetaDao.alterarPreco(itemColeta);
        } else {
            itemColetaDao.incluir(itemColeta);
        }
    }

    private void incluirAlteraTipo(ItemColeta itemColeta, ProdutoPesquisa p) {
        itemColeta.setSeqFamilia(p.getSeqFamilia());
        itemColeta.setFamilia(p.getFamilia());

        if (itemColetaDao.existeColeta(itemColeta)) {
            itemColetaDao.alterarTipo(itemColeta);
        } else {
            itemColetaDao.incluir(itemColeta);
        }
    }
}
