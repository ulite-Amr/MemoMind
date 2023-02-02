package com.uliteteam.notes.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.AbsSavedState;
import android.widget.Toast;
import com.uliteteam.notes.Note;
import com.uliteteam.notes.util.NoteDataBase;
import java.util.ArrayList;
import java.util.List;
import com.itsaky.androidide.logsender.LogSender;

public class NoteDataBase extends SQLiteOpenHelper {
    
    

  private static final int DATABASE_VERSION = 2;
  private static final String DATABASE_NAME = "notedbs";
  private static final String DATABASE_TABLE = "notestables";

  // all keys here
  private static final String KEY_ID = "id";
  private static final String KEY_TITLE = "title";
  private static final String KEY_CONTENT = "content";
  private static final String KEY_DATE = "date";
  private static final String KEY_COLOR = "color";

public  NoteDataBase(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String  query = "CREATE TABLE "+ DATABASE_TABLE+
    
   "("  +KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
         KEY_TITLE+" TEXT,"+
         KEY_CONTENT+" TEXT,"+
         KEY_DATE+" TEXT,"+
         KEY_COLOR+" TEXT"+  ")";

    db.execSQL(query);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion >= newVersion) return;
    db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
    Log.d("Done create Data base:>","Done");
    
    onCreate(db);
  }

  public long addNote(Note note) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues c = new ContentValues();
    c.put(KEY_TITLE, note.getTitle());
    c.put(KEY_CONTENT, note.getContent());
    c.put(KEY_DATE, note.getDate());
    c.put(KEY_COLOR, note.getColor());

    long ID = db.insert(DATABASE_TABLE, null, c);
    
    Log.d("The Id Is ==================================ID IS=======================================",":"+ID);
    return ID;
    
    
  }
  
  public Note getNote(long ID){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursur = db.query(DATABASE_TABLE,new String[] {KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_DATE,KEY_COLOR},KEY_ID+"=?",new String [] {String.valueOf(ID)},null,null,null);
      
      if(cursur != null)
      
      cursur.moveToFirst();
      
       
      return new Note(cursur.getLong(0),cursur.getString(1),cursur.getString(2),
      cursur.getString(3),cursur.getString(4));
  }
  
  public List<Note> getNote(){
      SQLiteDatabase db = this.getReadableDatabase();
      List<Note> allNotes = new ArrayList<>();
      
      
      String query = "SELECT * FROM "+DATABASE_TABLE;
      Cursor curser = db.rawQuery(query,null);


    if (curser.moveToFirst()) {

      do {
        Note note = new Note();  
        note.setID(curser.getLong(0));
        note.setTitle(curser.getString(1));
        note.setContent(curser.getString(2));
        note.setDate(curser.getString(3));
        note.setColor(curser.getString(4));

        allNotes.add(note);
      } while (curser.moveToNext());
    }
    return allNotes;
  }
    
    
    public int editNote(Note note){
        
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_TITLE, note.getTitle());
        c.put(KEY_CONTENT, note.getContent());
        c.put(KEY_DATE, note.getDate());
        c.put(KEY_COLOR, note.getColor());
        return db.update(DATABASE_TABLE,c,KEY_ID+"=?",new String[] {String.valueOf(note.getID())});
        
    }
  
  
 public void deleteNote(long id){
      SQLiteDatabase db = this.getReadableDatabase();
          db.delete(DATABASE_TABLE,KEY_ID+"=?",new String[] {String.valueOf(id)});
              db.close();
      
  }
  
  
  
  
  
  
  
  
  
}