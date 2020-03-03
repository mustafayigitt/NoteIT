package com.mustafayigit.noteit.util;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mustafayigit.noteit.R;
import com.mustafayigit.noteit.db.entity.Note;
import com.mustafayigit.noteit.ui.adapter.NoteAdapter;
import com.mustafayigit.noteit.viewmodel.NoteViewModel;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private NoteViewModel noteViewModel;
    private NoteAdapter mAdapter;
    private Drawable icon;
    private final ColorDrawable background;
    private int removeOrDelete;

    public SwipeToDeleteCallback(Context context, NoteAdapter adapter, NoteViewModel noteViewModel,int removeOrDelete) {
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        this.noteViewModel = noteViewModel;
        this.removeOrDelete = removeOrDelete;
        icon = ContextCompat.getDrawable(context,
                R.drawable.ic_deleted_notes);
        background = new ColorDrawable(Color.WHITE);

    }
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {

        if (removeOrDelete == 0){
            //Delete
            final int positionForDelete = viewHolder.getAdapterPosition();
            final Note deletedNote = mAdapter.getNoteAt(positionForDelete);
            deletedNote.setNoteStatus(false);
            noteViewModel.update(deletedNote);
            mAdapter.notifyItemChanged(positionForDelete);

            Snackbar snkDelete = Snackbar.make(viewHolder.itemView,R.string.toast_message_delete_question,Snackbar.LENGTH_SHORT);
            snkDelete.setAction(R.string.toast_message_undo_question, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletedNote.setNoteStatus(true);
                    noteViewModel.update(deletedNote);
                    Toast.makeText(v.getContext(), R.string.toast_message_cancelled, Toast.LENGTH_SHORT).show();
                    mAdapter.notifyItemChanged(positionForDelete);
                }
            });
            snkDelete.show();
        } else {
            if (direction == ItemTouchHelper.RIGHT){
                final int positionForRecover = viewHolder.getAdapterPosition();
                final Note recoveredNote = mAdapter.getNoteAt(positionForRecover);
                recoveredNote.setNoteStatus(true);
                noteViewModel.update(recoveredNote);
                mAdapter.notifyItemChanged(positionForRecover);

                Snackbar snackbar = Snackbar.make(viewHolder.itemView,R.string.toast_message_recover_question,Snackbar.LENGTH_SHORT);
                snackbar.setAction(R.string.toast_message_undo_question, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recoveredNote.setNoteStatus(false);
                        noteViewModel.update(recoveredNote);
                        Toast.makeText(v.getContext(), R.string.toast_message_cancelled, Toast.LENGTH_SHORT).show();
                        mAdapter.notifyItemChanged(positionForRecover);
                    }
                });
                snackbar.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.itemView.getContext());
                builder.setCancelable(false);
                builder.setTitle(R.string.toast_message_recover_note_delete_definitely_question);
                builder.setMessage(R.string.toast_message_continue_question);
                builder.setNegativeButton(R.string.toast_message_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mAdapter.notifyDataSetChanged();
                    }
                });
                builder.setPositiveButton(R.string.toast_message_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final int positionForDeleteDefinitely = viewHolder.getAdapterPosition();
                        final Note deletedNote = mAdapter.getNoteAt(positionForDeleteDefinitely);
                        noteViewModel.delete(deletedNote);
                        mAdapter.notifyItemChanged(positionForDeleteDefinitely);
                    }
                });
                builder.show();
            }
            //Recover

        }


        /*
        if (removeOrDelete == 1){
            final int position = viewHolder.getAdapterPosition();
            final Note deletedNote = mAdapter.getNoteAt(position);
            noteViewModel.delete(deletedNote);

            //mAdapter.notifyItemRemoved(position);
            Snackbar snackbar = Snackbar.make(viewHolder.itemView,R.string.toast_message_deleted,Snackbar.LENGTH_SHORT);
            snackbar.setAction(R.string.toast_message_undo_question, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note undoNote = new Note(deletedNote.getNoteHeader(),deletedNote.getNoteContent(),deletedNote.getNotePriority(),deletedNote.getNoteCreateTime());
                    noteViewModel.deleteDefinitely(deletedNote);
                    noteViewModel.insert(undoNote);
                }
            });
            snackbar.show();
        } else {
            final int position = viewHolder.getAdapterPosition();
            final Note deletedNote = mAdapter.getNoteAt(position);
            noteViewModel.insert(new Note(deletedNote.getNoteHeader(),deletedNote.getNoteContent(),deletedNote.getNotePriority(),deletedNote.getNoteCreateTime()));
            mAdapter.notifyItemRemoved(position);
            final Snackbar snackbar = Snackbar.make(viewHolder.itemView,R.string.toast_message_recover_note,Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.toast_message_recover_note_delete_definitely, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar snackbar = Snackbar.make(viewHolder.itemView,R.string.toast_message_deleted,Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            });
            snackbar.show();
        }
*/

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) { // Swiping to the right
            int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
            int iconRight = itemView.getLeft() + iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                    itemView.getBottom());
        } else if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        icon.draw(c);


    }
}
