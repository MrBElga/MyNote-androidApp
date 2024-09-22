package com.example.appmynote.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appmynote.R;
import com.example.appmynote.adapters.NoteAdapter;
import com.example.appmynote.entidades.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase bancoDados;
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
        //inserirDados();
        // arrumei aq estava duplicado o isnerir do addnoteactivity
        addNoteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String titulo = result.getData().getStringExtra("titulo");
                        String prioridade = result.getData().getStringExtra("prioridade");
                        String conteudo = result.getData().getStringExtra("conteudo");
                        // atualizando a listview por aq
                        listarDados();

                        Toast.makeText(MainActivity.this, "Nota adicionada!", Toast.LENGTH_SHORT).show();
                    }
                }
        );


        criarBancoDados();
        listarDados();
    }

    public void criarBancoDados() {
        try {
            bancoDados = openOrCreateDatabase("notasapp", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS notas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "titulo VARCHAR, " +
                    "prioridade VARCHAR, " +
                    "conteudo VARCHAR)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // função para iniciar o banco
    public void inserirDados() {
        try {


            bancoDados = openOrCreateDatabase("notasapp", MODE_PRIVATE, null);
            String sql = "INSERT INTO notas (titulo, prioridade, conteudo) VALUES (?, ?, ?)";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);

            // Dados a serem inseridos
            String[][] notas = {
                    {"Pagar conta de luz", "Baixa", "pagar a conta em tal dia"},
                    {"Visitar a vó", "Normal", "tenho que ir lá..."},
                    {"Dar banho no cachorro", "Normal", "dar banho no cachorro tal dia"},
                    {"Comprar cerveja", "Alta", "não esquecer a cerveja"},
                    {"Aniversário da namorada", "Alta", "se eu esquecer, vou me ferrar"},
                    {"Fazer o projeto de Android", "Baixa", "kkkkkkkkkkkk"}
            };

            for (String[] nota : notas) {
                stmt.bindString(1, nota[0]); // Título
                stmt.bindString(2, nota[1]); // Prioridade
                stmt.bindString(3, nota[2]); // Conteúdo
                stmt.executeInsert();
                stmt.clearBindings();
            }

            stmt.close();
            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    public void listarDados() {
        notes.clear();  // Limpa a lista antes de adicionar novas notas
        try {
            bancoDados = openOrCreateDatabase("notasapp", MODE_PRIVATE, null);
            Cursor cursor = bancoDados.rawQuery("SELECT id, titulo, prioridade, conteudo FROM notas", null);

            if (cursor.moveToFirst()) {
                do {
                    String titulo = cursor.getString(1);
                    String prioridade = cursor.getString(2);
                    String conteudo = cursor.getString(3);
                    notes.add(new Note(titulo, prioridade, conteudo));
                } while (cursor.moveToNext());
            }
            cursor.close();
            bancoDados.close();

            // Atualize o adapter após adicionar os novos dados
            if (noteAdapter == null) {
                noteAdapter = new NoteAdapter(this, notes);
                listView.setAdapter(noteAdapter);
            } else {
                noteAdapter.notifyDataSetChanged();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            ordenarPorPrioridade();
            return true;
        } else if (id == R.id.menu_sort_alpha) {
            ordenarPorOrdem();
            return true;
        } else if (id == R.id.menu_close) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

private void ordenarPorPrioridade() {
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note n1, Note n2) {
                return getPriorityValue(n1.getPrioridade()) - getPriorityValue(n2.getPrioridade());
            }
        });
        noteAdapter.notifyDataSetChanged();
    }

    private void ordenarPorOrdem() {
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
