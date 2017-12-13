package pdasolucoes.com.br.consultapreco.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import pdasolucoes.com.br.consultapreco.Dao.ItemColetaDao;
import pdasolucoes.com.br.consultapreco.Model.ItemColeta;
import pdasolucoes.com.br.consultapreco.PrecoActivity;
import pdasolucoes.com.br.consultapreco.R;

/**
 * Created by PDA on 06/12/2017.
 */

public class FuncoesUtil {

    static AlertDialog dialogOpcao;
    static AlertDialog dialogImage;

    public static String verificaRadioGroup(RadioGroup group, int idRadio) {

        RadioButton radio = (RadioButton) group.findViewById(idRadio);

        if (radio.getText().toString().equals("Oferta")) {
            return "Oferta";
        } else if (radio.getText().toString().equals("Fifo")) {
            return "Fifo";
        } else if (radio.getText().toString().equals("Ruptura")) {
            return "Ruptura";
        }

        return "";
    }

    //vou precisar passar o objeto produto
    public static void popupEans(Context context) {
        View v = View.inflate(context, R.layout.popup_eans, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(v);

        TextView tvDescricao;
        ListView listViewEans;
        Button btFeito;

        tvDescricao = (TextView) v.findViewById(R.id.tvDescricao);
        listViewEans = (ListView) v.findViewById(R.id.listViewEans);

        btFeito = (Button) v.findViewById(R.id.btOk);

        btFeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOpcao.dismiss();
            }
        });


        dialogOpcao = builder.create();
        dialogOpcao.show();
    }


    public static File AbrirCamera(Context c) {

        File file;
        Activity activity = (Activity) c;
        String nomeImagem = System.currentTimeMillis() + ".jpg";
        file = SDCardUtils.getPrivateFile(activity.getBaseContext(), nomeImagem, Environment.DIRECTORY_PICTURES);
        // Chama a intent informando o arquivo para salvar a foto
        Context context = activity.getBaseContext();
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = FileProvider.getUriForFile(activity.getBaseContext(), context.getApplicationContext().getPackageName() + ".provider", file);

        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(i, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(i, 0);

        return file;
    }

    public static void mostraImagem(Context context, Uri image, final ItemColeta i) {
        View v = View.inflate(context, R.layout.popup_mostra_imagem, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(v);

        final ItemColetaDao itemColetaDao = new ItemColetaDao(context);

        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);

        int w = imageView.getWidth();
        int h = imageView.getHeight();
        Bitmap bitmap = ImageResizeUtils.getResizedImage(image, w, h, false);
        imageView.setImageBitmap(bitmap);

        Button btFeito = (Button) v.findViewById(R.id.btFeito);

        btFeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemColetaDao.existeColeta(i)) {
                    itemColetaDao.alterarCaminhoFoto(i);
                } else {
                    itemColetaDao.incluir(i);
                }
                dialogImage.dismiss();
            }
        });


        dialogImage = builder.create();
        dialogImage.show();
    }

    public static void selecionarRadio(String text, RadioGroup group) {

        if (text.equals("Oferta")) {
            group.check(R.id.oferta);
        } else if (text.equals("Ruptura")) {
            group.check(R.id.ruptura);
        } else if (text.equals("Fifo")) {
            group.check(R.id.fifo);
        }else {
            group.check(R.id.normal);
        }
    }
}
