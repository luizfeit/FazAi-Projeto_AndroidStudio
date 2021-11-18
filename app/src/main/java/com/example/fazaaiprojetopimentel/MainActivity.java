package com.example.fazaaiprojetopimentel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    Button btnCadastrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCadastrar = (Button) findViewById(R.id.btncadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastrarDados = new Intent(MainActivity.this, CadastrarAtividadeActivity.class);
                MainActivity.this.startActivity(cadastrarDados);
            }
        });
    }
}