package co.edu.intecap.notes.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.edu.intecap.notes.R;
import co.edu.intecap.notes.listeners.NoteEventListener;

class NoteViewHolder extends RecyclerView.ViewHolder {
    private final NoteEventListener listener;
    TextView txtName;
    TextView txtContent;
    TextView txtDate;
    ImageView ivContent;
    ImageButton ibDeleteNote;
    long noteId;

    public NoteViewHolder(@NonNull View itemView,@NonNull final NoteEventListener listener) {
        super(itemView);

        this.listener = listener;
        txtName = itemView.findViewById(R.id.txt_name);
        txtContent = itemView.findViewById(R.id.txt_content);
        txtDate = itemView.findViewById(R.id.txt_date);
        ivContent = itemView.findViewById(R.id.iv_image_conent);
        ibDeleteNote = itemView.findViewById(R.id.ib_delete_note);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNoteSelected(noteId);
            }
        });
        ibDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteNote(noteId);
            }
        });

    }
}
