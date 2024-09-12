package com.example.appmynote;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmynote.entidades.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private NoteAdapter noteAdapter;
    private ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewNotes);
        notes = new ArrayList<>();

        // Adicionando notas de exemplo
        notes.add(new Note("Pagar conta de luz", "Baixa"));
        notes.add(new Note("Visitar a vó", "Normal"));
        notes.add(new Note("Dar banho no cachorro", "Normal"));
        notes.add(new Note("Comprar cerveja", "Alta"));
        notes.add(new Note("Aniversário da namorada", "Alta"));
        notes.add(new Note("Fazer o projeto de Android", "Baixa"));

        noteAdapter = new NoteAdapter(this, notes);
        listView.setAdapter(noteAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_add_note) {
            // Abrir activity para adicionar nova anotação
            Intent intent = new Intent(this, AddNoteActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_sort_priority) {
            // Ordenar por prioridade
            sortByPriority();
            return true;
        } else if (id == R.id.menu_sort_alpha) {
            // Ordenar por ordem alfabética
            sortByAlphabetical();
            return true;
        } else if (id == R.id.menu_close) {
            // Fechar aplicativo
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void sortByPriority() {
        // Ordenar as notas por prioridade (Alta -> Normal -> Baixa)
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note n1, Note n2) {
                return getPriorityValue(n1.getPriority()) - getPriorityValue(n2.getPriority());
            }
        });

        // Atualizar a lista após a ordenação
        noteAdapter.notifyDataSetChanged();
    }

    private void sortByAlphabetical() {
        // Ordenar as notas por ordem alfabética
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note n1, Note n2) {
                return n1.getTitle().compareToIgnoreCase(n2.getTitle());
            }
        });

        // Atualizar a lista após a ordenação
        noteAdapter.notifyDataSetChanged();
    }

    // Método auxiliar para converter as prioridades em valores numéricos
    private int getPriorityValue(String priority) {
        switch (priority) {
            case "Alta":
                return 1;
            case "Normal":
                return 2;
            case "Baixa":
                return 3;
            default:
                return 99; // valor alto para casos desconhecidos
        }
    }

}
