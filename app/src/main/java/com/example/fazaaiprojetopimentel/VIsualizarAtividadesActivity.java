package com.example.fazaaiprojetopimentel;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.database. sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.*;

public class VIsualizarAtividadesActivity extends Activity {

    ImageView imgAdicionar,imgSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_atividades);

        imgSair = (ImageView) findViewById(R.id.imgsair);
        imgAdicionar = (ImageView) findViewById(R.id.imgadicionar);

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
}