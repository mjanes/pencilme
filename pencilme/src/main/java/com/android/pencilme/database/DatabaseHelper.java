package com.android.pencilme.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by mjanes on 5/20/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;

    private static final String DATABASE_NAME = "pencilme.db";
    private static final int DATABASE_VERSION = 1;

    // Task table
    public static final String TABLE_TASK = "task";
    public static final String TASK_COLUMN_ID = "_id";
    public static final String TASK_COLUMN_TITLE = "title";
    public static final String TASK_COLUMN_EXPECTED_DURACTION = "expectedDuration";
    public static final String TASK_COLUMN_ELAPSED_DURAION = "elapsedDuration";
    public static final String TASK_COLUMN_DUE_DATE = "dueDate";
    public static final String TASK_COLUMN_STATUS = "status";
    public static final String TASK_COLUMN_MULTITASKABLE = "multitaskable";

    private TaskDao mTaskDao;

    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_TASK + "("
            + TASK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK_COLUMN_TITLE + " text not null,"
            + TASK_COLUMN_EXPECTED_DURACTION + " integer,"
            + TASK_COLUMN_ELAPSED_DURAION + " integer,"
            + TASK_COLUMN_DUE_DATE + " integer, "
            + TASK_COLUMN_STATUS + " integer,"
            + TASK_COLUMN_MULTITASKABLE + " integer"
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }


    public TaskDao getTaskDao() throws SQLException {
        if (mTaskDao == null) {
            mTaskDao = new TaskDao(mContext);
        }
        return mTaskDao;
    }
}
