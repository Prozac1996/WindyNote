package cn.lcu.threepersontravel.WindyNote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/6/4.
 */
public class NoteReaderHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "NoteDataBase.db";


    public NoteReaderHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NoteDataBase.SQL_CREATE_ENTRIES);
        db.execSQL(NoteDataBase.SQL_CREATE_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(NoteDataBase.SQL_DELETE_ENTRIES);
        db.execSQL(NoteDataBase.SQL_DELETE_CATEGORY);
        onCreate(db);
    }
}
