package com.example.appmynote;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NoteDetailActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewPriority;
    private TextView textViewContent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        textViewTitle = findViewById(R.id.textViewTitleDetail);
        textViewPriority = findViewById(R.id.textViewPriorityDetail);
        textViewContent = findViewById(R.id.textViewContentDetail);

        // Receber os dados da Intent
        Intent intent = getIntent();
        String titulo = intent.getStringExtra("titulo");
        String prioridade = intent.getStringExtra("prioridade");
        String conteudo = intent.getStringExtra("conteudo");

        // Exibir os dados
        textViewTitle.setText(titulo);
        textViewPriority.setText(prioridade);
        textViewContent.setText(conteudo);
    }
}
