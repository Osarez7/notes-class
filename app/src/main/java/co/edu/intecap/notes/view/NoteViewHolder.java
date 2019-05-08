package co.edu.intecap.notes.view;

import android.view.View;
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
    long noteId;

    public NoteViewHolder(@NonNull View itemView,@NonNull final NoteEventListener listener) {
        super(itemView);

        this.listener = listener;
        txtName = itemView.findViewById(R.id.txt_name);
        txtContent = itemView.findViewById(R.id.txt_content);
        txtDate = itemView.findViewById(R.id.txt_date);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNoteSelected(noteId);
            }
        });

    }
}
