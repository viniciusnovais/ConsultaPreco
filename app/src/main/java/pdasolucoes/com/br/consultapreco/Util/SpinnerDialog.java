package pdasolucoes.com.br.consultapreco.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import pdasolucoes.com.br.consultapreco.Interfaces.OnSpinerItemClick;
import pdasolucoes.com.br.consultapreco.R;


public class SpinnerDialog {
    ArrayList<?> items;
    Activity context;
    String dTitle;
    OnSpinerItemClick onSpinerItemClick;
    AlertDialog alertDialog;
    int pos;
    int style;

    public SpinnerDialog(Activity activity, ArrayList<?> items, String dialogTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
    }

    public SpinnerDialog(Activity activity, ArrayList<?> items, String dialogTitle, int style) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
    }

    public void bindOnSpinerListener(OnSpinerItemClick onSpinerItemClick1) {
        this.onSpinerItemClick = onSpinerItemClick1;
    }

    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this.context);
        View v = this.context.getLayoutInflater().inflate(in.galaxyofandroid.spinerdialog.R.layout.dialog_layout, null);
        TextView rippleViewClose = (TextView) v.findViewById(in.galaxyofandroid.spinerdialog.R.id.close);
        rippleViewClose.setText("Fechar");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rippleViewClose.setTextAppearance(R.style.TextAppearance_Small);
        } else {
            rippleViewClose.setTextAppearance(context, R.style.TextAppearance_Small);
        }
        TextView title = (TextView) v.findViewById(in.galaxyofandroid.spinerdialog.R.id.spinerTitle);
        title.setText(this.dTitle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            title.setTextAppearance(R.style.TextAppearance_Large);
        } else {
            title.setTextAppearance(context, R.style.TextAppearance_Large);
        }

        ListView listView = (ListView) v.findViewById(in.galaxyofandroid.spinerdialog.R.id.list);
        final EditText searchBox = (EditText) v.findViewById(in.galaxyofandroid.spinerdialog.R.id.searchBox);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            searchBox.setTextAppearance(R.style.TextAppearance_Small);
        } else {
            searchBox.setTextAppearance(context, R.style.TextAppearance_Small);
        }


        final ArrayAdapter<Object> adapter = new ArrayAdapter(this.context, R.layout.adapter_item_spinner_dialog, this.items);
        listView.setAdapter(adapter);
        adb.setView(v);
        this.alertDialog = adb.create();
        this.alertDialog.getWindow().getAttributes().windowAnimations = this.style;
        this.alertDialog.setCancelable(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t = (TextView) view.findViewById(R.id.text1);
                Object object = adapterView.getItemAtPosition(i);

                for (int j = 0; j < SpinnerDialog.this.items.size(); ++j) {
                    if (t.getText().toString().equalsIgnoreCase((items.get(j).toString()))) {
                        SpinnerDialog.this.pos = j;
                    }
                }

                onSpinerItemClick.onClick(object);
                SpinnerDialog.this.alertDialog.dismiss();
            }
        });
        searchBox.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(searchBox.getText().toString());
            }
        });
        rippleViewClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        this.alertDialog.show();
    }

}
