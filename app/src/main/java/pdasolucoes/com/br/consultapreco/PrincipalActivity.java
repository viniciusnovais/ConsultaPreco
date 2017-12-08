package pdasolucoes.com.br.consultapreco;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;

import java.util.ArrayList;

import pdasolucoes.com.br.consultapreco.Interfaces.OnSpinerItemClick;
import pdasolucoes.com.br.consultapreco.Util.SpinnerDialog;

/**
 * Created by PDA on 16/11/2017.
 */

public class PrincipalActivity extends AbsRuntimePermission {

    private ImageView imageAgenda;
    private static final int REQUEST_PERMISSION = 10;
    private SpinnerDialog spinnerDialogPraca, spinnerEndereco, spinnerDialogNome;
    private CounterFab fab, fab1, fab2;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private Boolean isFabOpen = false;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        preferences = getSharedPreferences(LoginActivity.PREFERENCES, MODE_PRIVATE);

        imageAgenda = (ImageView) findViewById(R.id.imageAgenda);

        //Floating Action Buttons
        fab = (CounterFab) findViewById(R.id.fab);
        fab1 = (CounterFab) findViewById(R.id.fab_1);
        fab2 = (CounterFab) findViewById(R.id.fab_2);

        imageAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                popupFiltroPraca();

                Intent i = new Intent(PrincipalActivity.this, AgendaActivity.class);
                startActivity(i);

            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = View.inflate(PrincipalActivity.this, R.layout.popup_msg, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this);
                final AlertDialog dialog;
                Button btNo = (Button) view.findViewById(R.id.btCancel);
                Button btYes = (Button) view.findViewById(R.id.btDone);
                TextView tvConteudo = (TextView) view.findViewById(R.id.conteudo);
                TextView tvTitle = (TextView) view.findViewById(R.id.title);

                tvTitle.setText(getString(R.string.sair));
                tvConteudo.setText(getString(R.string.deseja_sair));

                builder.setView(view);
                dialog = builder.create();
                dialog.show();

                btYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear().commit();
                        Intent i = new Intent(PrincipalActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                });

                btNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });

        //Animations
        fab_open = AnimationUtils.loadAnimation(

                getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(

                getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(

                getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(

                getApplicationContext(), R.anim.rotate_backward);

        fab.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });

        requestAppPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                R.string.msg, REQUEST_PERMISSION);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    private void popupFiltroPraca() {
        View v = View.inflate(this, R.layout.popup_filtro_concorrente, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final TextView tvPraca;
        //tvNome, tvEndereco;
        final ArrayList<String> arrayNome = new ArrayList<>(),
                arrayPraca = new ArrayList<>(),
                arrayEndereco = new ArrayList<>();

        tvPraca = (TextView) v.findViewById(R.id.tvPraca);
//        tvEndereco = (TextView) v.findViewById(R.id.tvEndereco);
//        tvNome = (TextView) v.findViewById(R.id.tvNome);

        Button btCancelar = (Button) v.findViewById(R.id.btCancelar);
        Button btFeito = (Button) v.findViewById(R.id.btFeito);

        final AlertDialog dialog;
        builder.setView(v);
        dialog = builder.create();
        dialog.show();

        //aqui virão todas as praças cadastradas, mas como ainda não tenho acesso a tabela, estou passando essa de teste

        arrayPraca.add("TESTE");
        arrayPraca.add("outra");

        //praca
        tvPraca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialogPraca.showSpinerDialog();
            }
        });
//        spinnerDialogPraca = new SpinnerDialog(PrincipalActivity.this, arrayPraca, getString(R.string.selecione_praca));
//        spinnerDialogPraca.bindOnSpinerListener(new OnSpinerItemClick() {
//            @Override
//            public void onClick(String s, int i) {
//                tvPraca.setText(s);
//            }
//        });

        //nome
//        tvNome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                spinnerDialogNome.showSpinerDialog();
//            }
//        });
//
//        spinnerDialogNome = new SpinnerDialog(PrincipalActivity.this, arrayNome, getString(R.string.selecione_praca));
//        spinnerDialogNome.bindOnSpinerListener(new OnSpinerItemClick() {
//            @Override
//            public void onClick(String s, int i) {
//                Toast.makeText(getApplicationContext(), "select: " + s, Toast.LENGTH_SHORT).show();
//                tvNome.setText(s);
//            }
//        });


        //endereco
//        tvEndereco.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                spinnerDialogEndereco.showSpinerDialog();
//            }
//        });
//
//        spinnerDialogEndereco = new SpinnerDialog(PrincipalActivity.this, arrayEndereco, getString(R.string.selecione_praca));
//        spinnerDialogEndereco.bindOnSpinerListener(new OnSpinerItemClick() {
//            @Override
//            public void onClick(String s, int i) {
//                Toast.makeText(getApplicationContext(), "select: " + s, Toast.LENGTH_SHORT).show();
//                tvEndereco.setText(s);
//            }
//        });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btFeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrincipalActivity.this, AgendaActivity.class);
                i.putExtra("praca", tvPraca.getText().toString());
                if (!tvPraca.getText().toString().equals("")) {
                    startActivity(i);
                } else {
                    Toast.makeText(PrincipalActivity.this, "Selecione uma praça", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);

            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;

        } else {


            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;

        }
    }
}
