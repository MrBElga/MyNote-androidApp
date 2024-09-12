package com.example.appmynote;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private RadioGroup radioGroupPriority;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.editTextTitle);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obter o título
                String title = editTextTitle.getText().toString().trim();

                // Verifica se o título não está vazio
                if (title.isEmpty()) {
                    Toast.makeText(AddNoteActivity.this, "O título não pode ser vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obter a prioridade selecionada
                int selectedPriorityId = radioGroupPriority.getCheckedRadioButtonId();

                // Verificar se algum RadioButton foi selecionado
                if (selectedPriorityId == -1) {
                    Toast.makeText(AddNoteActivity.this, "Por favor, selecione uma prioridade", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedPriority = findViewById(selectedPriorityId);
                String priority = selectedPriority.getText().toString();

                // Neste ponto, temos o título e a prioridade selecionados
                Toast.makeText(AddNoteActivity.this, "Nota salva com título: " + title + " e prioridade: " + priority, Toast.LENGTH_SHORT).show();

                // Adicione a lógica para salvar a anotação (por exemplo, enviar a anotação de volta para a MainActivity ou salvar no banco de dados)

                // Fecha a AddNoteActivity após salvar a anotação
                finish();
            }
        });
    }
}
