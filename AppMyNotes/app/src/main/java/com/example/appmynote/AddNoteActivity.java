package com.example.appmynote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private RadioGroup radioGroupPriority;
    private Button buttonSave;
    private TextInputLayout textConteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.editTextTitle);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        buttonSave = findViewById(R.id.buttonSave);
        textConteudo = findViewById(R.id.textConteudo);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = editTextTitle.getText().toString().trim();
                String conteudo = textConteudo.getEditText().getText().toString(); // Corrigido aqui

                if (titulo.isEmpty()) {
                    Toast.makeText(AddNoteActivity.this, "O título não pode ser vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedPriorityId = radioGroupPriority.getCheckedRadioButtonId();
                if (selectedPriorityId == -1) {
                    Toast.makeText(AddNoteActivity.this, "Por favor, selecione uma prioridade", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (conteudo.isEmpty()) {
                    Toast.makeText(AddNoteActivity.this, "O conteúdo não pode ser vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedPriority = findViewById(selectedPriorityId);
                String prioridade = selectedPriority.getText().toString();

                // Criar um Intent para retornar os dados
                Intent resultIntent = new Intent();
                resultIntent.putExtra("titulo", titulo);
                resultIntent.putExtra("prioridade", prioridade);
                resultIntent.putExtra("conteudo", conteudo);
                setResult(RESULT_OK, resultIntent);

                // Finalizar a atividade
                finish();
            }
        });
    }
}

