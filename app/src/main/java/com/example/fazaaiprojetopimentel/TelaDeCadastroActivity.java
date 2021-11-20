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

public class TelaDeCadastroActivity extends Activity {

    Button btCadastrar, btCancelar;
    EditText txNome, txEmail,txSenha;
    RadioButton rdFuncionario, rdAdministrador ;
    SQLiteDatabase db;

    Integer funcao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_cadastro);

        btCadastrar = (Button) findViewById(R.id.btncadastrar);
        btCancelar = (Button) findViewById(R.id.btncancelar);

        txNome = (EditText) findViewById(R.id.txnome);
        txEmail = (EditText) findViewById(R.id.txemail);
        txSenha = (EditText) findViewById(R.id.txsenha);

        rdFuncionario = (RadioButton) findViewById(R.id.rdfuncionario);
        rdAdministrador = (RadioButton) findViewById(R.id.rdadministrador);

        try{
            db = openOrCreateDatabase("faz_assim", Context.MODE_PRIVATE, null);
        }catch (Exception e){
            MostraMessagem ("Erro: " + e.toString());
        }

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = txNome.getText().toString();
                String email = txEmail.getText().toString();
                String senha = txSenha.getText().toString();


                if(rdFuncionario.isChecked()){
                     funcao = 1;
                }else{
                     funcao = 2;
                }

                ContentValues valor = new ContentValues();

                valor.put("nome", nome);
                valor.put("email", email);
                valor.put("senha", senha);
                valor.put("funcao", funcao);

                System.out.println(nome + " " + email + " " + senha+ " " + funcao );



                try{
                    db.insert("usuarios", null, valor);
                    MostraMessagem("Registrado com Sucesso");
                }catch (Exception e){
                    //MostraMessagem("Algo deu errado ao Cadastrar: " + e.printStackTrace());
                    e.printStackTrace();
                }

            }
        });

    }
    public void MostraMessagem(String str){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(TelaDeCadastroActivity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("Ok", null);
        dialogo.show();
    }

}