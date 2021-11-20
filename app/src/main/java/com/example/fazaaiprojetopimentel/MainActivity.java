package com.example.fazaaiprojetopimentel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.database. sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.*;
public class MainActivity extends Activity {

    Button btnCadastrar;

    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCadastrar = (Button) findViewById(R.id.btncadastrar);


        try{
            db= openOrCreateDatabase("faz_assim", Context.MODE_PRIVATE, null);
            db.execSQL("create table if not exists " +
                    "usuarios(id integer primary key " +
                    "autoincrement, nome text not null, email text " +
                    "not null, senha text not null, funcao integer not null)");

        }catch (Exception e){
            MostraMessagem("Erro ao criar o Banco de Dados: " + e.toString());
        }

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastrarDados = new Intent(MainActivity.this, TelaDeCadastroActivity.class);
                MainActivity.this.startActivity(cadastrarDados);
            }
        });
    }
    public void MostraMessagem(String str){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("Ok", null);
        dialogo.show();
    }
}