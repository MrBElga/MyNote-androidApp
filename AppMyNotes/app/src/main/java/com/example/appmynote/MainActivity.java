package com.example.appmynote;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    private ActivityResultLauncher<Intent> addNoteLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewNotes);
        notes = new ArrayList<>();

        // Adicionando notas de exemplo
        notes.add(new Note("Pagar conta de luz", "Baixa", "pagar a conta em tal dia"));
        notes.add(new Note("Visitar a vó", "Normal", "tenho que ir lá..."));
        notes.add(new Note("Dar banho no cachorro", "Normal", "dar banho no cachorro tal dia"));
        notes.add(new Note("Comprar cerveja", "Alta", "não esquecer a cerveja"));
        notes.add(new Note("Aniversário da namorada", "Alta", "se eu esquecer, vou me ferrar"));
        notes.add(new Note("Fazer o projeto de Android", "Baixa", "kkkkkkkkkkkk"));

        noteAdapter = new NoteAdapter(this, notes);
        listView.setAdapter(noteAdapter);

        // Configuração do ActivityResultLauncher
        addNoteLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Intent data = result.getData();
                String titulo = data.getStringExtra("titulo");
                String prioridade = data.getStringExtra("prioridade");
                String conteudo = data.getStringExtra("conteudo");
                notes.add(new Note(titulo, prioridade, conteudo));
                noteAdapter.notifyDataSetChanged();
            }
        });
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
            Intent intent = new Intent(this, AddNoteActivity.class);
            addNoteLauncher.launch(intent);
            return true;
        } else if (id == R.id.menu_sort_priority) {
            sortByPriority();
            return true;
        } else if (id == R.id.menu_sort_alpha) {
            sortByAlphabetical();
            return true;
        } else if (id == R.id.menu_close) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void sortByPriority() {
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note n1, Note n2) {
                return getPriorityValue(n1.getPrioridade()) - getPriorityValue(n2.getPrioridade());
            }
        });
        noteAdapter.notifyDataSetChanged();
    }

    private void sortByAlphabetical() {
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note n1, Note n2) {
                return n1.getTitulo().compareToIgnoreCase(n2.getTitulo());
            }
        });
        noteAdapter.notifyDataSetChanged();
    }

    private int getPriorityValue(String priority) {
        switch (priority) {
            case "Alta":
                return 1;
            case "Normal":
                return 2;
            case "Baixa":
                return 3;
            default:
                return 99;
        }
    }
}
