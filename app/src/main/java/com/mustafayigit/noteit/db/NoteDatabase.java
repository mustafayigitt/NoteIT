package com.mustafayigit.noteit.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mustafayigit.noteit.db.dao.NoteDAO;
import com.mustafayigit.noteit.db.entity.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;
    public abstract NoteDAO noteDAO();

    public static synchronized NoteDatabase getInstance(Context context){
        if (instance == null){
            instance =  Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,"NoteDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(instance).execute();
        }
    };

    private static class PopulateAsyncTask extends AsyncTask<Void,Void,Void> {

        private NoteDAO noteDAO;

        private PopulateAsyncTask(NoteDatabase database){
            noteDAO = database.noteDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.insert(new Note("Bilgilendirme Notu 1","Notlarınızı sağa ve sola kaydırarak silebilirsiniz.",2, new SimpleDateFormat("dd/MM/yy").format(Calendar.getInstance().getTime()),true));
            noteDAO.insert(new Note("Bilgilendirme Notu 2","Notlarını üzerine tıklayarak düzenleyebilirsiniz.",5,new SimpleDateFormat("dd/MM/yy").format(Calendar.getInstance().getTime()),true));
            noteDAO.insert(new Note("Bilgilendirme Notu 3","Notlarınızı geri getirmek için sağa; tamamen silmek için sola kaydırın.",8,new SimpleDateFormat("dd/MM/yy").format(Calendar.getInstance().getTime()),false));

            return null;
        }
    }

}
