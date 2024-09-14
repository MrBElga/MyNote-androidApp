package com.example.appmynote;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appmynote.entidades.Note;

import java.util.ArrayList;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Note> notes;

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
        }

        Note currentNote = (Note) getItem(position);

        TextView title = convertView.findViewById(R.id.textViewTitle);
        LinearLayout noteLayout = convertView.findViewById(R.id.noteLayout);

        title.setText(currentNote.getTitulo());

        // Ajustando a cor de fundo de acordo com a prioridade
        switch (currentNote.getPrioridade()) {
            case "Baixa":
                noteLayout.setBackgroundResource(android.R.color.holo_orange_light);
                break;
            case "Normal":
                noteLayout.setBackgroundResource(android.R.color.holo_blue_light);
                break;
            case "Alta":
                noteLayout.setBackgroundResource(android.R.color.holo_red_light);
                break;
        }

        noteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = notes.get(position);
                Intent intent = new Intent(context, NoteDetailActivity.class);
                intent.putExtra("titulo", note.getTitulo());
                intent.putExtra("prioridade", note.getPrioridade());
                intent.putExtra("conteudo", note.getConteudo());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
