package com.mustafayigit.noteit.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mustafayigit.noteit.R;
import com.mustafayigit.noteit.db.entity.Note;


public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    Activity activity;
    public NoteAdapter(Activity activity) {
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getNoteID() == newItem.getNoteID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return
                    oldItem.getNoteHeader().equals(newItem.getNoteHeader()) &&
                            oldItem.getNoteContent().equals(newItem.getNoteContent()) &&
                            oldItem.getNotePriority() == newItem.getNotePriority() &&
                            oldItem.getNoteRememberDate().equals(newItem.getNoteRememberDate());
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_note_item,parent,false);
        return new NoteHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);

        holder.txtNoteHeader.setText(currentNote.getNoteHeader());
        holder.txtNoteContent.setText(currentNote.getNoteContent());
        holder.txtNoteDate.setText(currentNote.getNoteRememberDate());
        holder.imgNotePriority.setBackgroundResource(getDrawableByPriority(currentNote.getNotePriority(), holder.mCardView));

    }

    private int getDrawableByPriority(int priority,CardView cardView){
        if (priority > 6){
            cardView.setCardBackgroundColor(ContextCompat.getColor(activity,R.color.colorHighPriority));
            return R.drawable.ic_high_priority;
        } else if (priority > 3){
            cardView.setCardBackgroundColor(ContextCompat.getColor(activity,R.color.colorMedPriority));
            return R.drawable.ic_med_priority;
        } else{
            cardView.setCardBackgroundColor(ContextCompat.getColor(activity,R.color.colorLowPriority));
            return R.drawable.ic_low_priority;
        }
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        private TextView txtNoteHeader;
        private TextView txtNoteContent;
        private TextView txtNoteDate;
        private ImageView imgNotePriority;

        private CardView mCardView;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            txtNoteHeader = itemView.findViewById(R.id.txtNoteHeader);
            txtNoteContent = itemView.findViewById(R.id.txtNoteContent);
            txtNoteDate = itemView.findViewById(R.id.txtNoteDate);
            imgNotePriority = itemView.findViewById(R.id.imgNotePriority);

            mCardView = (CardView) itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener{
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
