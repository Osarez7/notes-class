package co.edu.intecap.notes.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.intecap.notes.R;
import co.edu.intecap.notes.api.Note;
import co.edu.intecap.notes.listeners.NoteEventListener;


class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    List<Note> noteEntityList = new ArrayList();

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private NoteEventListener listener;

    public NoteAdapter(List<Note> noteEntityList, @NonNull NoteEventListener listener) {
        this.noteEntityList = noteEntityList;
        this.listener = listener;
    }


    public void setNoteEntityList(List<Note> noteEntityList) {
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
        Note note = noteEntityList.get(position);
        holder.noteId = note.getId();
        holder.txtName.setText(note.getName());
        holder.txtContent.setText(note.getContent());
        if (note.getCreatedDate() != null) {
            holder.txtDate.setText(simpleDateFormat.format(note.getCreatedDate()));
        }
        if (note.getImageUrl() != null && !note.getImageUrl().isEmpty()) {
            Glide.with(holder.ivContent)
                    .load(note.getImageUrl())
                    .centerCrop()
                    .placeholder(R.color.image_holder_color)
                    .into(holder.ivContent);
            holder.ivContent.setVisibility(View.VISIBLE);
        } else {
            holder.ivContent.setImageBitmap(null);
            holder.ivContent.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return noteEntityList.size();
    }
}
