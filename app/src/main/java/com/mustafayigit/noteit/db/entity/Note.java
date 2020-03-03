package com.mustafayigit.noteit.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Note")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int noteID;

    private String noteHeader;
    private String noteContent;
    private int notePriority;
    private String noteRememberDate;
    private boolean noteStatus;

    public Note(String noteHeader, String noteContent, int notePriority, String noteRememberDate, boolean noteStatus) {
        this.noteHeader = noteHeader;
        this.noteContent = noteContent;
        this.notePriority = notePriority;
        this.noteRememberDate = noteRememberDate;
        this.noteStatus = noteStatus;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getNoteHeader() {
        return noteHeader;
    }

    public void setNoteHeader(String noteHeader) {
        this.noteHeader = noteHeader;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public int getNotePriority() {
        return notePriority;
    }

    public void setNotePriority(int notePriority) {
        this.notePriority = notePriority;
    }

    public String getNoteRememberDate() {
        return noteRememberDate;
    }

    public void setNoteRememberDate(String noteRememberDate) {
        this.noteRememberDate = noteRememberDate;
    }

    public boolean isNoteStatus() {
        return noteStatus;
    }

    public void setNoteStatus(boolean noteStatus) {
        this.noteStatus = noteStatus;
    }

}
