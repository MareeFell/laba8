package com.pr.lab8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {

    private static DB Instance;

    public static DB getInstance(Context context) {
        if (Instance == null) {
            Instance = new DB(context, null);
        }
        return Instance;
    }

    public DB(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, "notes.db", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE notes (id INT, text TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table notes");
        Log.println(Log.ERROR, "asd", "asd");
        onCreate(db);
    }


    public List<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery("select id, text from notes", null);

        while (cursor.moveToNext()) {
            notes.add(new Note(cursor.getInt(0), cursor.getString(1)));
        }

        cursor.close();

        return notes;
    }

    public void createNode(Note note) {
        ContentValues values = new ContentValues();
        values.put("id", note.getId());
        values.put("text", note.getText());
        getWritableDatabase().insert("notes", null, values);
    }

    public void update(Note note) {
        ContentValues values = new ContentValues();
        values.put("text", note.getText());
        getWritableDatabase().update("notes", values, "id = ?", new String[]{String.valueOf(note.getId())});
    }

    public void delete(int id) {
        getWritableDatabase().delete("notes", "id = ?", new String[]{String.valueOf(id)});
    }

    public Note getNote(int id) {
        Cursor cursor = getReadableDatabase().rawQuery("select id, text from notes where id = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            cursor.close();
            return new Note(cursor.getInt(0), cursor.getString(1));
        }

        cursor.close();

        return null;
    }

    public int maxNoteId() {
        Cursor cursor = getReadableDatabase().rawQuery("select max(id) from notes", null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        cursor.close();
        return id;
    }
}
