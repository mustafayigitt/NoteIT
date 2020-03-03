package com.mustafayigit.noteit.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mustafayigit.noteit.db.entity.Note;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert()
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("select * from note where noteStatus = :status order by notePriority desc")
    LiveData<List<Note>> getAllNotes(Boolean status);

    @Query("update Note set noteStatus = :status")
    void deleteAllNotes(boolean status);

    @Query("delete from Note")
    void deleteAllNotesDefinitely();


}
