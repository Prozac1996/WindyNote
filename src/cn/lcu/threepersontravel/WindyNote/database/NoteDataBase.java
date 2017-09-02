package cn.lcu.threepersontravel.WindyNote.database;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2017/6/4.
 */
public class NoteDataBase {

    public static final String TEXT_TYPE = " TEXT";
    public static final String DATE_TYPE = " DATE";
    public static final String INT_TYPE = " INTEGER";
    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NoteLib.TABLE_NAME + " (" +
                    NoteLib._ID + " INTEGER PRIMARY KEY," +
                    NoteLib.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                    NoteLib.COLUMN_CONTENT + TEXT_TYPE + COMMA_SEP +
                    NoteLib.COLIMN_CATEGORY + INT_TYPE + COMMA_SEP +
                    NoteLib.COLUMN_DATE + DATE_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NoteLib.TABLE_NAME;

    public static final String SQL_QUERY_ALL =
            "SELECT * FROM " + NoteLib.TABLE_NAME;

    public static final String SQL_QUERY_SINGLE =
            "SELECT * FROM " + NoteLib.TABLE_NAME + " Where " + NoteLib._ID + " = ? ";

    public static final String SQL_QUERY_BY_CATEGORY =
            "SELECT * FROM " + NoteLib.TABLE_NAME + " Where " + NoteLib.COLIMN_CATEGORY + " = ? ";

    public static final String SQL_CREATE_CATEGORY =
            "CREATE TABLE " + CategoryLib.TABLE_NAME + " (" +
                    CategoryLib._ID + " INTEGER PRIMARY KEY," +
                    CategoryLib.COLUMN_CATEGORY_NAME + " )";

    public static final String SQL_QUERY_CATEGORY =
            "SELECT * FROM " + CategoryLib.TABLE_NAME;

    public static final String SQL_DELETE_CATEGORY =
            "DROP TABLE IF EXISTS " + CategoryLib.TABLE_NAME;

    public NoteDataBase() {
    }

    public static class NoteLib implements BaseColumns {
        public static final String TABLE_NAME = "noteLib";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLIMN_CATEGORY = "category";
    }

    public static class CategoryLib implements BaseColumns {
        public static final String TABLE_NAME = "categoryLib";
        public static final String COLUMN_CATEGORY_NAME = "categoryName";
    }
}
