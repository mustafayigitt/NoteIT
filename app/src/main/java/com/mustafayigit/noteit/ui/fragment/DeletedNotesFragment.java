package com.mustafayigit.noteit.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mustafayigit.noteit.R;
import com.mustafayigit.noteit.db.entity.Note;
import com.mustafayigit.noteit.ui.adapter.NoteAdapter;
import com.mustafayigit.noteit.util.SwipeToDeleteCallback;
import com.mustafayigit.noteit.viewmodel.NoteViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeletedNotesFragment extends Fragment {

    private RecyclerView recyclerViewDeletedNotes;
    private NoteViewModel noteViewModel;

    public DeletedNotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root =  inflater.inflate(R.layout.fragment_deleted_notes, container, false);

        recyclerViewDeletedNotes = root.findViewById(R.id.recyclerViewDeletedNotes);
        recyclerViewDeletedNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewDeletedNotes.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter(getActivity());
        recyclerViewDeletedNotes.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes(false).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(getContext(),adapter,noteViewModel,1));
        itemTouchHelper.attachToRecyclerView(recyclerViewDeletedNotes);


        return root;
    }
}
