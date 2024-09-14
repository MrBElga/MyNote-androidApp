package com.example.appmynote;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NoteDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewPriority = findViewById(R.id.textViewPriority);
        TextView textViewContent = findViewById(R.id.textViewContent);

        // Receber dados da Intent
        String titulo = getIntent().getStringExtra("titulo");
        String prioridade = getIntent().getStringExtra("prioridade");
        String conteudo = getIntent().getStringExtra("conteudo");

        // Definir o texto nas TextViews
        textViewTitle.setText(titulo);
        textViewPriority.setText(prioridade);
        textViewContent.setText(conteudo);
    }
}
