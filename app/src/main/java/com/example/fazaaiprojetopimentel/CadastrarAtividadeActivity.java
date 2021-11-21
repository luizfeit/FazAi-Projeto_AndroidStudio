package com.example.fazaaiprojetopimentel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.database. sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.*;

import java.util.Calendar;
import java.util.Date;


public class CadastrarAtividadeActivity extends Activity {

    DatePickerDialog datePickerDialog;
    Button  btCadastrar, btCancelar, dateButton;
    EditText txTitulo, txDescricao;
    Spinner spinnerResp;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_atividade);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        btCadastrar = (Button) findViewById(R.id.btncadastrar);
        btCancelar = (Button) findViewById(R.id.btncancelar);

        txTitulo = (EditText) findViewById(R.id.txtitulo);
        txDescricao = (EditText) findViewById(R.id.txdescricao);
        spinnerResp = (Spinner) findViewById(R.id.spinner2);

        String[] lsresp = getResources().getStringArray(R.array.resp);
        spinnerResp.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, lsresp));

        try{
            db = openOrCreateDatabase("faz_assim", Context.MODE_PRIVATE, null);
        }catch (Exception e){
            MostraMensagem ("Erro: " + e.toString());
        }

        btCadastrar.setOnClickListener(v -> {
            String titulo = txTitulo.getText().toString();
            String descricao = txDescricao.getText().toString();
            String date = dateButton.getText().toString();
            String responsavel = lsresp.toString();

            ContentValues valor = new ContentValues();

            valor.put("titulo", titulo);
            valor.put("descricao", descricao);
            valor.put("date", (String) date);
            valor.put("responsavel", responsavel);

            System.out.println(titulo + " " + descricao + " " + date + "" + responsavel);

            try{
                db.insert("atividade", null, valor);
                MostraMensagem("Registrado com Sucesso");
            }catch (Exception e){
                //MostraMensagem("Algo deu errado ao Cadastrar: " + e.printStackTrace());
                e.printStackTrace();
            }

        });

        btCancelar.setOnClickListener(view -> {
            Intent cancelar = new Intent(CadastrarAtividadeActivity.this, MainActivity.class);
            CadastrarAtividadeActivity.this.startActivity(cancelar);
        });

    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public void MostraMensagem(String str){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(CadastrarAtividadeActivity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("Ok", null);
        dialogo.show();
    }

    //Override
    //public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    //};


   // public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
     //   String text = parent.getItemAtPosition(position).toString();
    //};

    
   //public void onNothingSelected(AdapterView<?> parent) {

    //};
}