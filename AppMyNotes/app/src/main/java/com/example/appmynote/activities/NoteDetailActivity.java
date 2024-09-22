package com.example.appmynote.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmynote.R;

public class NoteDetailActivity extends AppCompatActivity {

    private Context context;
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

        // recebendo dados da itent
        Intent intent = getIntent();
        String titulo = intent.getStringExtra("titulo");
        String prioridade = intent.getStringExtra("prioridade");
        String conteudo = intent.getStringExtra("conteudo");

        // exibe os dados da nota
        textViewTitle.setText(titulo);
        textViewPriority.setText(prioridade);
        textViewContent.setText(conteudo);
    }



}
