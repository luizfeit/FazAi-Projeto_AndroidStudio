package com.example.fazaaiprojetopimentel;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.database. sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.*;
public class MainActivity extends Activity {

    Button btnCadastrar, btnLogin;
    EditText txEmail,txSenha;
    SQLiteDatabase db;

    Cursor c;


    public String validaEmail, validaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCadastrar = (Button) findViewById(R.id.btncadastrar);
        btnLogin = (Button) findViewById(R.id.btnlogin);

        txEmail = (EditText) findViewById(R.id.txemail);
        txSenha = (EditText) findViewById(R.id.txsenha);




        try{
            db = openOrCreateDatabase("faz_assim", Context.MODE_PRIVATE, null);
            db.execSQL("create table if not exists " +
                    "usuarios(id integer primary key " +
                    "autoincrement, nome text not null, email text " +
                    "not null, senha text not null, funcao integer not null)");

            db.execSQL("create table if not exists " +
                    "atividades(id integer primary key " +
                    "autoincrement, titulo text not null, descricao text not null,"+
                    "date text not null, responsavel text)");




        }catch (Exception e){
            MostraMessagem("Erro ao criar o Banco de Dados: " + e.toString());
            e.printStackTrace();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String email = txEmail.getText().toString();
                    String senha = txSenha.getText().toString();

                    c = db.rawQuery("select email, senha from usuarios where email = ? and senha = ?",
                          new String[]{email, senha});

                    System.out.println(c);

                        if(txEmail.getText().length() == 0 || txSenha.getText().length() == 0){
                            MostraMessagem("Preencha todos os campos");

                        }else if(c.getCount() > 0){
                            limparTudo();
                                Intent telaVisualizarAtividade = new Intent(MainActivity.this,
                                        VIsualizarAtividadesActivity.class);
                                MainActivity.this.startActivity(telaVisualizarAtividade);
                        }
                        else{
                            limparLogin();
                            MostraMessagem("E-mail ou senha incorretas");
                        }

                }catch (Exception e){
                    MostraMessagem("Algo deu Errado: " + e.toString());
                    e.printStackTrace();
                }

            }
        });

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

    public void limparLogin(){
        txSenha.setText("");
    }
    public void limparTudo(){
        txEmail.setText("");
        txSenha.setText("");
    }

}