package com.mustafayigit.noteit.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    private NoteViewModel noteViewModel;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root =  inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter noteAdapter = new NoteAdapter(getActivity());
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes(true).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.submitList(notes);
            }
        });

        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                NavController navController = Navigation.findNavController(getView());
                Bundle bundle = new Bundle();
                bundle.putInt("noteID",note.getNoteID());
                bundle.putString("noteHeader",note.getNoteHeader());
                bundle.putString("noteContent",note.getNoteContent());
                bundle.putInt("notePriority",note.getNotePriority());
                bundle.putString("noteRememberDate",note.getNoteRememberDate());
                navController.navigate(R.id.action_navigation_home_to_navigation_update_note,bundle);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(getContext(),noteAdapter,noteViewModel,0));
        itemTouchHelper.attachToRecyclerView(recyclerView);



        return root;
    }


}
