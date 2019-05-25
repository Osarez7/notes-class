package co.edu.intecap.notes.view;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.intecap.notes.R;
import co.edu.intecap.notes.listeners.NoteEventListener;
import co.edu.intecap.notes.model.NoteEntity;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    List<NoteEntity> noteList = new ArrayList();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private NoteEventListener listener;

    public NoteAdapter(List<NoteEntity> noteList, @NonNull NoteEventListener listener) {
        this.noteList = noteList;
        this.listener = listener;
    }


    public List<NoteEntity> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<NoteEntity> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
           NoteEntity note = noteList.get(position);
           holder.noteId = note.getId();
           holder.txtName.setText(note.getName());
           holder.txtContent.setText(note.getContent());
           holder.txtDate.setText(simpleDateFormat.format(note.getCreatedDate()));
           if(note.getImagePath() != null){
               holder.ivContent.setImageURI(Uri.parse(note.getImagePath()));
           }
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
