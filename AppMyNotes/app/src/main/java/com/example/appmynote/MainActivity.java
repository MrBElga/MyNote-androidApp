package com.example.appmynote;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    private static final int REQUEST_CODE_ADD_NOTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewNotes);
        notes = new ArrayList<>();

        // Adicionando notas de exemplo
        notes.add(new Note("Pagar conta de luz", "Baixa", "pagar a conta em tal dia"));
        notes.add(new Note("Visitar a vó", "Normal", "tenho q ir la..."));
        notes.add(new Note("Dar banho no cachorro", "Normal", "dar banho no dogão tal dia"));
        notes.add(new Note("Comprar cerveja", "Alta", "a breja n esquecer"));
        notes.add(new Note("Aniversário da namorada", "Alta", "se eu esquecer me fodo"));
        notes.add(new Note("Fazer o projeto de Android", "Baixa", "kkkkkkkkkkkkk"));

        noteAdapter = new NoteAdapter(this, notes);
        listView.setAdapter(noteAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toque curto - abrir a nota detalhada
                Note selectedNote = notes.get(position);
                Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
                intent.putExtra("titulo", selectedNote.getTitulo());
                intent.putExtra("prioridade", selectedNote.getPrioridade());
                intent.putExtra("conteudo", selectedNote.getConteudo());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Toque longo - remover a nota
                notes.remove(position);
                noteAdapter.notifyDataSetChanged();
                return true;
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
            startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK && data != null) {
            String titulo = data.getStringExtra("titulo");
            String prioridade = data.getStringExtra("prioridade");
            String conteudo = data.getStringExtra("conteudo");
            notes.add(new Note(titulo, prioridade, conteudo));
            noteAdapter.notifyDataSetChanged();
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

