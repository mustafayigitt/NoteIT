package com.mustafayigit.noteit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mustafayigit.noteit.db.entity.Note;
import com.mustafayigit.noteit.db.repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;


    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
    }

    public void insert(Note note){ noteRepository.insert(note);}

    public void update(Note note){ noteRepository.update(note);}

    public void delete(Note note){ noteRepository.delete(note);}

    public void deleteAll(Boolean status){noteRepository.deleteAllNotes(status);}

    public void deleteAllDefinitely(){noteRepository.deleteAllNotesDefinitely();}

    public LiveData<List<Note>> getAllNotes(Boolean status) {
        return noteRepository.getAllNotes(status);
    }
}
