package com.example.fazaaiprojetopimentel;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.database. sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.*;


public class VisualizarTelaAdmActivity extends Activity {

    EditText txTitulo, txDescricao, txPrazo, txFuncionario;
    TextView txStatus;
    ImageView imgAdicionar, imgSair, imgPrimeiro, imgUltimo, imgProximo, imgAnterior;
    Button btnExcluir, btnAlterar;
    DialogInterface.OnClickListener diExcluiRegistro, diAlteraRegistro;
    SQLiteDatabase db;
    int indice, id;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_tela_adm);

        txTitulo = (EditText) findViewById(R.id.txtitulo);
        txDescricao = (EditText) findViewById(R.id.txdescricao);
        txPrazo = (EditText) findViewById(R.id.txprazo);
        txFuncionario = (EditText) findViewById(R.id.txfuncionario);
        txStatus = (TextView) findViewById(R.id.status);

        btnExcluir = (Button) findViewById(R.id.btnexcluir);
        btnAlterar = (Button) findViewById(R.id.btnalterar);

        imgPrimeiro = (ImageView) findViewById(R.id.imgprimeiro);
        imgProximo = (ImageView) findViewById(R.id.imgproximo);
        imgAnterior = (ImageView) findViewById(R.id.imganterior);
        imgUltimo = (ImageView) findViewById(R.id.imgultimo);

        imgSair = (ImageView) findViewById(R.id.imgsair);
        imgAdicionar = (ImageView) findViewById(R.id.imgadicionar);

        try {
            db = openOrCreateDatabase("faz_assim", Context.MODE_PRIVATE, null );

            CarregarDados();

            imgPrimeiro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c.getCount() > 0) {
                        c.moveToFirst();
                        indice = 1;



                        id = c.getInt(0);
                        txTitulo.setText(c.getString(1));
                        txDescricao.setText(c.getString(2));
                        txPrazo.setText(c.getString(3));
                        txFuncionario.setText(c.getString(4));

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



                        id = c.getInt(0);
                        txTitulo.setText(c.getString(1));
                        txDescricao.setText(c.getString(2));
                        txPrazo.setText(c.getString(3));
                        txFuncionario.setText(c.getString(4));

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



                            id = c.getInt(0);
                            txTitulo.setText(c.getString(1));
                            txDescricao.setText(c.getString(2));
                            txPrazo.setText(c.getString(3));
                            txFuncionario.setText(c.getString(4));

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



                        id = c.getInt(0);
                        txTitulo.setText(c.getString(1));
                        txDescricao.setText(c.getString(2));
                        txPrazo.setText(c.getString(3));
                        txFuncionario.setText(c.getString(4));

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
                Intent cadastrarAtividade = new Intent(VisualizarTelaAdmActivity.this,
                        MainActivity.class);
                VisualizarTelaAdmActivity.this.startActivity(cadastrarAtividade);
            }
        });

        imgAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(VisualizarTelaAdmActivity.this,
                        CadastrarAtividadeActivity.class);
                VisualizarTelaAdmActivity.this.startActivity(logout);
            }
        });

        diExcluiRegistro = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                db.execSQL("delete from atividades where id = " + id);
                CarregarDados();
                MostraMessagem("Atividade excluida com Sucesso!");
            }
        };

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(c.getCount() > 0) {

                    AlertDialog.Builder dialogo = new
                            AlertDialog.Builder(VisualizarTelaAdmActivity.this);
                    dialogo.setTitle("Confirma");
                    dialogo.setMessage("Deseja excluir essa atividade?");
                    dialogo.setNegativeButton("Não", null);
                    dialogo.setPositiveButton("Sim", diExcluiRegistro);
                    dialogo.show();

                }else{
                    MostraMessagem("Não es=xistem registros para excluir!");
                }
            }
        });

        diAlteraRegistro = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                String titulo = txTitulo.getText().toString();
                String descricao = txDescricao.getText().toString();
                String prazo = txPrazo.getText().toString();
                String funcionario = txFuncionario.getText().toString();

                try{
                    db.execSQL("update atividades set titulo = '" + titulo + "'," +
                            "descricao ='" + descricao + "', date = '" + prazo + "',"  +
                            "responsavel = '" + funcionario + "' where id = " + id);

                    MostraMessagem("Dados Alterados com Sucesso!");
                    CarregarDados();
                }catch (Exception e){
                    MostraMessagem("Erro:" + e.toString());
                }
            }
        };

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(c.getCount() > 0) {

                    AlertDialog.Builder dialogo = new
                    AlertDialog.Builder(VisualizarTelaAdmActivity.this);
                    dialogo.setTitle("Confirma");
                    dialogo.setMessage("Deseja alterar essa atividade?");
                    dialogo.setNegativeButton("Não", null);
                    dialogo.setPositiveButton("Sim", diAlteraRegistro);
                    dialogo.show();

                }else{
                    MostraMessagem("Não es=xistem atividades para alterar!");
                }
            }
        });




    }
    public void MostraMessagem(String str){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(VisualizarTelaAdmActivity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("Ok", null);
        dialogo.show();
    }
    public void CarregarDados(){
        c = db.query("atividades", new String []
                        {"id", "titulo", "descricao", "date", "responsavel"},
                null, null, null, null, null);

        txTitulo.setText("");
        txDescricao.setText("");
        txPrazo.setText("");
        txFuncionario.setText("");

        if (c.getCount() > 0) {
            c.moveToFirst();
            indice = 1;

            id = c.getInt(0);
            txTitulo.setText(c.getString(1));
            txDescricao.setText(c.getString(2));
            txPrazo.setText(c.getString(3));
            txFuncionario.setText(c.getString(4));

            txStatus.setText(indice + "/" + c.getCount());
        } else {
            txStatus.setText("Nenhum Registro");
        }
    }
}