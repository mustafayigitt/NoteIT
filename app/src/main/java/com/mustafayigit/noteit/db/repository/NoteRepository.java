package com.mustafayigit.noteit.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mustafayigit.noteit.db.NoteDatabase;
import com.mustafayigit.noteit.db.dao.NoteDAO;
import com.mustafayigit.noteit.db.entity.Note;

import java.util.List;

public class NoteRepository {

    private NoteDAO noteDAO;
    private NoteDatabase noteDatabase;

    public NoteRepository(Application application){
        noteDatabase = NoteDatabase.getInstance(application);
        noteDAO = noteDatabase.noteDAO();
    }

    public void insert(Note note){
        new InsertNoteAsyncTask(noteDAO).execute(note);
    }

    public void update(Note note){
        new UpdateNoteAsyncTask(noteDAO).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDAO).execute(note);
    }

    public void deleteAllNotes(Boolean status){
        new DeleteAllNotesAsyncTask(noteDAO).execute(status);
    }

    public LiveData<List<Note>> getAllNotes(Boolean status){
        return noteDAO.getAllNotes(status);
    }

    public void deleteAllNotesDefinitely(){
        new DeleteAllNotesDefinitelyAsyncTask(noteDAO).execute();
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void> {

        private NoteDAO noteDAO;

        private InsertNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void> {

        private NoteDAO noteDAO;

        private UpdateNoteAsyncTask(NoteDAO noteEntityDAO){
            this.noteDAO = noteEntityDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void> {

        private NoteDAO noteDAO;

        private DeleteNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Boolean,Void,Void> {

        private NoteDAO noteDAO;

        private DeleteAllNotesAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Boolean... status) {
            noteDAO.deleteAllNotes(status[0]);
            return null;
        }
    }

    private static class DeleteAllNotesDefinitelyAsyncTask extends AsyncTask<Void,Void,Void> {

        private NoteDAO noteDAO;

        private DeleteAllNotesDefinitelyAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.deleteAllNotesDefinitely();
            return null;
        }
    }

}
