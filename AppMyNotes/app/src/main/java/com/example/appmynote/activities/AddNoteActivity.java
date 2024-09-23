package com.example.appmynote.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appmynote.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private RadioGroup radioGroupPriority;
    private TextInputLayout textConteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.editTextTitle);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        Button buttonSave = findViewById(R.id.buttonSave);
        textConteudo = findViewById(R.id.textConteudo);

        buttonSave.setOnClickListener(v -> {
            String titulo = editTextTitle.getText().toString().trim();
            String conteudo = textConteudo.getEditText() != null ? textConteudo.getEditText().getText().toString().trim() : "";

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

            // insere no banco de dados <<<<<<<<<<<
            try {
                SQLiteDatabase bancoDados = openOrCreateDatabase("notasapp", MODE_PRIVATE, null);
                String sql = "INSERT INTO notas (titulo, prioridade, conteudo) VALUES (?, ?, ?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, titulo);
                stmt.bindString(2, prioridade);
                stmt.bindString(3, conteudo);
                stmt.executeInsert();
                stmt.close();
                bancoDados.close();

                Toast.makeText(AddNoteActivity.this, "Nota salva com sucesso!", Toast.LENGTH_SHORT).show();


                Intent returnIntent = new Intent();
                returnIntent.putExtra("titulo", titulo);
                returnIntent.putExtra("prioridade", prioridade);
                returnIntent.putExtra("conteudo", conteudo);
                setResult(RESULT_OK, returnIntent);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(AddNoteActivity.this, "Erro ao salvar a nota", Toast.LENGTH_SHORT).show();
            }


            // retorna para  a mainActivity
            finish();
        });

    }
}
