package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity
{
    ListView listViewContatos;
    ArrayList<String> contatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listViewContatos = (ListView) findViewById(R.id.ltvContatos);

        // Recebe os contatos passados pela MainActivity
        contatos = getIntent().getStringArrayListExtra("contatos");

        // Configura o ArrayAdapter e exibe os contatos
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contatos);
        listViewContatos.setAdapter(adapter);
    }
}
