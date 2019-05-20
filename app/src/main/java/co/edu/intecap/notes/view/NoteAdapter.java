package co.edu.intecap.notes.view;

import android.annotation.SuppressLint;
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
import co.edu.intecap.notes.model.entities.NoteEntity;

class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    List<NoteEntity> noteEntityList = new ArrayList();

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private NoteEventListener listener;

    public NoteAdapter(List<NoteEntity> noteEntityList, @NonNull NoteEventListener listener) {
        this.noteEntityList = noteEntityList;
        this.listener = listener;
    }


    public void setNoteEntityList(List<NoteEntity> noteEntityList) {
        this.noteEntityList = noteEntityList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
           NoteEntity noteEntity = noteEntityList.get(position);
           holder.noteId = noteEntity.getId();
           holder.txtName.setText(noteEntity.getName());
           holder.txtContent.setText(noteEntity.getContent());
           holder.txtDate.setText(simpleDateFormat.format(noteEntity.getCreatedDate()));
           if(noteEntity.getImagePath() != null && !noteEntity.getImagePath().isEmpty()){
               holder.ivContent.setImageURI(Uri.parse(noteEntity.getImagePath()));
               holder.ivContent.setVisibility(View.VISIBLE);
           }else{
               holder.ivContent.setImageBitmap(null);
               holder.ivContent.setVisibility(View.GONE);
           }
    }

    @Override
    public int getItemCount() {
        return noteEntityList.size();
    }
}
