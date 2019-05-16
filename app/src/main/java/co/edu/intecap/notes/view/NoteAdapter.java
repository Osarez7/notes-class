package co.edu.intecap.notes.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.intecap.notes.R;
import co.edu.intecap.notes.network.model.Note;
import co.edu.intecap.notes.view.listeners.NoteEventListener;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private HashMap<String, Note> noteMap;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private NoteEventListener listener;

    public NoteAdapter(@NonNull NoteEventListener listener) {
        this.listener = listener;
    }

    public HashMap<String, Note> getNoteMap() {
        return noteMap;
    }

    public void setNoteMap(HashMap<String, Note> noteMap) {
        this.noteMap = noteMap;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteMap.get(noteMap.keySet().toArray()[position]);
        holder.noteId = noteMap.keySet().toArray()[position].toString();
        holder.txtName.setText(note.getName());
        holder.txtContent.setText(note.getContent());
//        holder.txtDate.setText(simpleDateFormat.format(note.getCreatedDate()));
    }

    @Override
    public int getItemCount() {
        if (noteMap == null) {
            return 0;
        } else {
            return noteMap.keySet().size();
        }
    }
}
