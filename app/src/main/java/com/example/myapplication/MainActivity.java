package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    EditText txtNome, txtPhone, txtEmail;
    ListView listViewContatos;
    Button btnInserir, btnAlterar, btnDelete, btnLista;
    ArrayList<String> listViewContacts = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    SQLiteDatabase dbShow;
    int selectedContactIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNome = findViewById(R.id.txtNome);
        txtPhone = findViewById(R.id.txtTelefone);
        txtEmail = findViewById(R.id.txtEmail);
        listViewContatos = findViewById(R.id.ltvContatos);
        btnInserir = findViewById(R.id.btnAdicionar);
        btnAlterar = findViewById(R.id.btnAlt);
        btnDelete = findViewById(R.id.btnDel);
        btnLista = findViewById(R.id.bntLista);



        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                                                    listViewContacts);
        listViewContatos.setAdapter(arrayAdapter);

        CriarAbrirDB();

        CarregarContatos();

        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gravar();
            }
        });
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Atualizar();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deletar();
            }
        });
        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putStringArrayListExtra("contatos", listViewContacts);
                startActivity(intent);
            }
        });

        listViewContatos.setOnItemClickListener((adapterView, view, position, id) -> {
            String selectedContact = listViewContacts.get(position);
            String[] contactDetails = selectedContact.split(" - ");

            txtNome.setText(contactDetails[0]);
            txtPhone.setText(contactDetails[1]);
            txtEmail.setText(contactDetails[2]);

            selectedContactIndex = position;
        });
    }
    public void Atualizar()
    {
        if (selectedContactIndex != -1) {
            try {
                String nome = txtNome.getText().toString();
                String phone = txtPhone.getText().toString();
                String email = txtEmail.getText().toString();

                String sql = "UPDATE Contatos SET Nome = '" + nome + "', " +
                        "Phone = '" + phone + "', Email = '" + email + "' " +
                        "WHERE Nome = '" + listViewContacts.get(selectedContactIndex).split(" - ")[0] + "'";
                dbShow.execSQL(sql);

                Toast.makeText(MainActivity.this, "Contato atualizado com sucesso!", Toast.LENGTH_LONG).show();
                CarregarContatos();
                txtNome.setText("");
                txtPhone.setText("");
                txtEmail.setText("");
                selectedContactIndex = -1;
            } catch (Exception erro) {
                Toast.makeText(MainActivity.this, "Erro ao atualizar contato: " + erro.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Selecione um contato para editar", Toast.LENGTH_SHORT).show();
        }
    }

    public void CriarAbrirDB()
    {
        String sql;
        try
        {
            sql = "CREATE TABLE IF NOT EXISTS Contatos(" +
                    "Nome TEXT," +
                    "Phone CHAR(11)," +
                    "Email VARCHAR(120))";

            dbShow = openOrCreateDatabase("CONTATO", MODE_PRIVATE, null);
            dbShow.execSQL(sql);

            Toast.makeText(MainActivity.this,
                    "Sistema Carregado com Sucesso!",
                    Toast.LENGTH_LONG).show();
        }
        catch(Exception erro) {
            Toast.makeText(MainActivity.this,
                    "Erro ao Carregar o sistema: " + erro.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void Gravar()
    {
        String sql;
        try
        {
            sql = "INSERT INTO Contatos(Nome,Phone,Email) VALUES('" +
                    txtNome.getText().toString() + "','" +
                    txtPhone.getText().toString() + "','" +
                    txtEmail.getText().toString() + "')";

            dbShow.execSQL(sql);

            Toast.makeText(MainActivity.this,
                    "Contato inserido com sucesso!",
                    Toast.LENGTH_LONG).show();

            txtNome.setText("");
            txtPhone.setText("");
            txtEmail.setText("");

            CarregarContatos();

        }
        catch(Exception erro)
        {
            Toast.makeText(MainActivity.this,
                    "Erro ao inserir Contato: " + erro.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    public void CarregarContatos()
    {
        listViewContacts.clear();

        Cursor cursor = dbShow.rawQuery("SELECT Nome, Phone, Email FROM Contatos", null);

        if (cursor.moveToFirst()) {
            do {
                String nome = cursor.getString(0);
                String phone = cursor.getString(1);
                String email = cursor.getString(2);
                listViewContacts.add(nome + " - " + phone + " - " + email);
            } while (cursor.moveToNext());
        }
        cursor.close();

        arrayAdapter.notifyDataSetChanged();
    }
    public void Deletar(){
        if (selectedContactIndex != -1) {
            try {
                String nome = listViewContacts.get(selectedContactIndex).split(" - ")[0];
                String sql = "DELETE FROM Contatos WHERE Nome = '" + nome + "'";
                dbShow.execSQL(sql);

                Toast.makeText(MainActivity.this, "Contato excluído com sucesso!", Toast.LENGTH_LONG).show();
                CarregarContatos();
                txtNome.setText("");
                txtPhone.setText("");
                txtEmail.setText("");
                selectedContactIndex = -1;
            } catch (Exception erro) {
                Toast.makeText(MainActivity.this, "Erro ao excluir contato: " + erro.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Selecione um contato para excluir", Toast.LENGTH_SHORT).show();
        }
    }
}
