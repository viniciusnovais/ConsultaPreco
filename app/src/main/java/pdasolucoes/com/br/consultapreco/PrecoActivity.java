package pdasolucoes.com.br.consultapreco;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pdasolucoes.com.br.consultapreco.Adapter.ListaPrecoHorizontal;
import pdasolucoes.com.br.consultapreco.Adapter.ListaPrecoVertical;
import pdasolucoes.com.br.consultapreco.Util.CustomLinearLayoutManager;
import pdasolucoes.com.br.consultapreco.Util.CustomRecyclerView;

/**
 * Created by PDA on 16/11/2017.
 */

public class PrecoActivity extends AppCompatActivity {

    private CustomRecyclerView customRecyclerView;
    private List<Integer> lista = new ArrayList<>();
    private ListaPrecoHorizontal adapterHorizontal;
    private ListaPrecoVertical adapterVertical;
    private LinearLayout linearLayoutHorizontal, linearLayoutVertical, linearLayoutBusca;
    private RecyclerView recyclerViewVertical;
    private ImageView arrowLeft, arrowRight;
    private ImageView btListaHorizontal, btListaVertical, btBeep;
    private int position = 0;

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

        btListaHorizontal = (ImageView) findViewById(R.id.listaHorizontal);
        btListaVertical = (ImageView) findViewById(R.id.listaVertical);
        btBeep = (ImageView) findViewById(R.id.beep);

        for (int i = 0; i < 20; i++) {
            lista.add(i);
        }


        btListaHorizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayoutHorizontal.setVisibility(View.VISIBLE);
                linearLayoutVertical.setVisibility(View.GONE);
                linearLayoutBusca.setVisibility(View.GONE);

                final Button btFeito = (Button) findViewById(R.id.btFeitoH);

                CustomLinearLayoutManager llm = new CustomLinearLayoutManager(PrecoActivity.this, LinearLayoutManager.HORIZONTAL, false);
                customRecyclerView.setLayoutManager(llm);

                adapterHorizontal = new ListaPrecoHorizontal(PrecoActivity.this, lista);
                customRecyclerView.setAdapter(adapterHorizontal);

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
                                    btFeito.setVisibility(View.VISIBLE);
                                }
                            }
                        }, 100);
                    }
                });


                arrowLeft.setVisibility(View.INVISIBLE);
                btFeito.setVisibility(View.GONE);
                arrowLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                arrowRight.setVisibility(View.VISIBLE);
                                btFeito.setVisibility(View.GONE);

                                position--;
                                customRecyclerView.scrollToPosition(position);

                                if (position <= 0)
                                    arrowLeft.setVisibility(View.INVISIBLE);
                            }
                        }, 100);
                    }
                });

                btFeito.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });


        btListaVertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayoutHorizontal.setVisibility(View.GONE);
                linearLayoutVertical.setVisibility(View.VISIBLE);
                linearLayoutBusca.setVisibility(View.GONE);

                Button btFeito = (Button) findViewById(R.id.btFeitoV);

                arrowRight.setVisibility(View.INVISIBLE);
                arrowLeft.setVisibility(View.INVISIBLE);

                CustomLinearLayoutManager llm = new CustomLinearLayoutManager(PrecoActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerViewVertical.setLayoutManager(llm);

                adapterVertical = new ListaPrecoVertical(PrecoActivity.this, lista);
                recyclerViewVertical.setAdapter(adapterVertical);

                btFeito.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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

                final EditText editCodigo = (EditText) findViewById(R.id.editCodigo);
                final EditText editPreco = (EditText) findViewById(R.id.editPrecob);
                final Button btFeito = (Button) findViewById(R.id.btFeitob);
                final LinearLayout linearLayoutRespostaBusca = (LinearLayout) findViewById(R.id.linearLayoutRespostaBusca);

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
}
