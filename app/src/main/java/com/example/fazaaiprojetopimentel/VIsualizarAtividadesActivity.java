package com.example.fazaaiprojetopimentel;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.*;

public class VIsualizarAtividadesActivity extends Activity {

    TextView txTitulo, txDescricao, txPrazo, txFuncionario, txStatus;
    ImageView imgAdicionar, imgSair, imgPrimeiro, imgUltimo, imgProximo, imgAnterior;
    SQLiteDatabase db;
    int indice;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_atividades);

        txTitulo = (TextView) findViewById(R.id.txtitulo);
        txDescricao = (TextView) findViewById(R.id.txdescricao);
        txPrazo = (TextView) findViewById(R.id.txprazo);
        txFuncionario = (TextView) findViewById(R.id.txfuncionario);
        txStatus = (TextView) findViewById(R.id.status);

        txTitulo.setText("");
        txDescricao.setText("");
        txPrazo.setText("");
        txFuncionario.setText("");

        imgPrimeiro = (ImageView) findViewById(R.id.imgprimeiro);
        imgProximo = (ImageView) findViewById(R.id.imgproximo);
        imgAnterior = (ImageView) findViewById(R.id.imganterior);
        imgUltimo = (ImageView) findViewById(R.id.imgultimo);

        imgSair = (ImageView) findViewById(R.id.imgsair);
        imgAdicionar = (ImageView) findViewById(R.id.imgadicionar);

        try {
            db = openOrCreateDatabase("faz_assim", Context.MODE_PRIVATE, null );

            c = db.query("atividades", new String []
                    {"titulo", "descricao", "date", "responsavel"},
                    null, null, null, null, null);

            if (c.getCount() > 0) {
                c.moveToFirst();
                indice = 1;

                txTitulo.setText("0");
                txDescricao.setText("1");
                txPrazo.setText("2");
                txFuncionario.setText("3");

                txStatus.setText(indice + "/" + c.getCount());
            } else {
                txStatus.setText("Nenhum Registro");
            }

            imgPrimeiro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c.getCount() > 0) {
                        c.moveToFirst();
                        indice = 1;

                        txTitulo.setText("0");
                        txDescricao.setText("1");
                        txPrazo.setText("2");
                        txFuncionario.setText("3");

                        txStatus.setText(indice + "/" + c.getCount());
                    } else {
                        txStatus.setText("Nenhum Registro");
                    }
                }
            });

            imgUltimo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c.getCount() > 0) {
                        c.moveToLast();
                        indice = c.getCount();

                        txTitulo.setText("0");
                        txDescricao.setText("1");
                        txPrazo.setText("2");
                        txFuncionario.setText("3");

                        txStatus.setText(indice + "/" + c.getCount());
                    } else {
                        txStatus.setText("Nenhum Registro");
                    }
                }
                });

            imgProximo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (c.getCount() > 0) {
                        if(indice != c.getCount()){

                            indice ++;
                            c.moveToNext();

                        txTitulo.setText("0");
                        txDescricao.setText("1");
                        txPrazo.setText("2");
                        txFuncionario.setText("3");

                        txStatus.setText(indice + "/" + c.getCount());
                    }
                };
            };
            });

            imgAnterior.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(indice > 1){
                        indice --;
                        c.moveToPrevious();

                        txTitulo.setText("0");
                        txDescricao.setText("1");
                        txPrazo.setText("2");
                        txFuncionario.setText("3");

                        txStatus.setText(indice + "/" + c.getCount());
                    }
                }
            });

        }catch(Exception e) {
            MostraMessagem("Erro:" + e.toString());
        }

        imgSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastrarAtividade = new Intent(VIsualizarAtividadesActivity.this,
                        MainActivity.class);
                VIsualizarAtividadesActivity.this.startActivity(cadastrarAtividade);
            }
        });

        imgAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(VIsualizarAtividadesActivity.this,
                        VisualizarTelaAdmActivity.class);
                VIsualizarAtividadesActivity.this.startActivity(logout);
            }
        });

    }
    public void MostraMessagem(String str){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(VIsualizarAtividadesActivity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("Ok", null);
        dialogo.show();
    }
}