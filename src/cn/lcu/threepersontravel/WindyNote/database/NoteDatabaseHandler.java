package cn.lcu.threepersontravel.WindyNote.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.lcu.threepersontravel.WindyNote.Category;
import cn.lcu.threepersontravel.WindyNote.Note;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 * 数据库的控制类
 */
public class NoteDatabaseHandler {

    private static SQLiteDatabase db;

    public NoteDatabaseHandler(Context context) {
        if (db == null)
            db = new NoteReaderHelper(context).getWritableDatabase();
    }

    public List<Note> queryAll(){
        ArrayList resultList = new ArrayList();
        Cursor c = db.rawQuery(NoteDataBase.SQL_QUERY_ALL,null);
        while(c.moveToNext()){
            int _id = c.getInt(c.getColumnIndex(NoteDataBase.NoteLib._ID));
            String title = c.getString(c.getColumnIndex(NoteDataBase.NoteLib.COLUMN_TITLE));
            String date = c.getString(c.getColumnIndex(NoteDataBase.NoteLib.COLUMN_DATE));
            String content = c.getString(c.getColumnIndex(NoteDataBase.NoteLib.COLUMN_CONTENT));
            int category = c.getInt(c.getColumnIndex(NoteDataBase.NoteLib.COLIMN_CATEGORY));
            Note note = new Note(_id,title,date,content,category);
            resultList.add(note);
        }
        return resultList;
    }

    public Note query(long id){
        Cursor c = db.rawQuery(NoteDataBase.SQL_QUERY_SINGLE,new String[]{String.valueOf(id)});
        if(c.moveToNext()){
            int _id = c.getInt(c.getColumnIndex(NoteDataBase.NoteLib._ID));
            String title = c.getString(c.getColumnIndex(NoteDataBase.NoteLib.COLUMN_TITLE));
            String date = c.getString(c.getColumnIndex(NoteDataBase.NoteLib.COLUMN_DATE));
            String content = c.getString(c.getColumnIndex(NoteDataBase.NoteLib.COLUMN_CONTENT));
            int category = c.getInt(c.getColumnIndex(NoteDataBase.NoteLib.COLIMN_CATEGORY));
            Note note = new Note(_id,title,date,content,category);
            return note;
        }
        return null;
    };

    public List<Note> queryByCategory(long catgoryId){
        List list = new ArrayList<>();
        Cursor c = db.rawQuery(NoteDataBase.SQL_QUERY_BY_CATEGORY,new String[]{String.valueOf(catgoryId)});
        while(c.moveToNext()){
            int _id = c.getInt(c.getColumnIndex(NoteDataBase.NoteLib._ID));
            String title = c.getString(c.getColumnIndex(NoteDataBase.NoteLib.COLUMN_TITLE));
            String date = c.getString(c.getColumnIndex(NoteDataBase.NoteLib.COLUMN_DATE));
            String content = c.getString(c.getColumnIndex(NoteDataBase.NoteLib.COLUMN_CONTENT));
            int category = c.getInt(c.getColumnIndex(NoteDataBase.NoteLib.COLIMN_CATEGORY));
            Note note = new Note(_id,title,date,content,category);
            list.add(note);
        }
        return list;
    }

    public void add(String title,String content,long id){
        ContentValues values = new ContentValues();
        values.put(NoteDataBase.NoteLib.COLUMN_TITLE,title);
        values.put(NoteDataBase.NoteLib.COLUMN_DATE, DateFormat.getDateInstance(DateFormat.FULL).format(new Date()));
        values.put(NoteDataBase.NoteLib.COLUMN_CONTENT,content);
        values.put(NoteDataBase.NoteLib.COLIMN_CATEGORY,id);
        db.insert(NoteDataBase.NoteLib.TABLE_NAME,null,values);
    }

    public void delete(long id){
        db.delete(NoteDataBase.NoteLib.TABLE_NAME, NoteDataBase.NoteLib._ID + " = ? ",new String[]{String.valueOf(id)});
    }


    public void modify(long id,String title,String content,long cid){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteDataBase.NoteLib.COLUMN_TITLE,title);
        contentValues.put(NoteDataBase.NoteLib.COLUMN_DATE,DateFormat.getDateInstance(DateFormat.FULL).format(new Date()));
        contentValues.put(NoteDataBase.NoteLib.COLUMN_CONTENT,content);
        contentValues.put(NoteDataBase.NoteLib.COLIMN_CATEGORY,cid);
        db.update(NoteDataBase.NoteLib.TABLE_NAME,contentValues, NoteDataBase.NoteLib._ID + " = ? ",new String[]{String.valueOf(id)});
    }

    public void addCategory(String categoryName){
        ContentValues values = new ContentValues();
        values.put(NoteDataBase.CategoryLib.COLUMN_CATEGORY_NAME,categoryName);
        db.insert(NoteDataBase.CategoryLib.TABLE_NAME,null,values);
    }

    public List<Category> queryAllCategory(){
        ArrayList list = new ArrayList();
        Cursor c = db.rawQuery(NoteDataBase.SQL_QUERY_CATEGORY,null);
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(NoteDataBase.CategoryLib._ID));
            String name = c.getString(c.getColumnIndex(NoteDataBase.CategoryLib.COLUMN_CATEGORY_NAME));
            Category category = new Category(id,name);
            list.add(category);
        }
        return list;
    };

    public void modifyCategory(long id,String name){
        ContentValues cv = new ContentValues();
        cv.put(NoteDataBase.CategoryLib.COLUMN_CATEGORY_NAME,name);
        db.update(NoteDataBase.CategoryLib.TABLE_NAME,cv, NoteDataBase.CategoryLib._ID + " = ?",new String[]{String.valueOf(id)});
    }

    public void deleteCategory(long id){
        db.delete(NoteDataBase.CategoryLib.TABLE_NAME,NoteDataBase.CategoryLib._ID + " = ?",new String[]{String.valueOf(id)});
    }

}
