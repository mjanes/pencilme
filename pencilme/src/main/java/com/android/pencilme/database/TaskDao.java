package com.android.pencilme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.pencilme.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjanes on 5/20/2014.
 */
public class TaskDao {

    private static final String TAG = "com.android.pencilme.database.TaskDao";

    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;

    private static String[] sAllColumns = {
            DatabaseHelper.TASK_COLUMN_ID,
            DatabaseHelper.TASK_COLUMN_TITLE,
            DatabaseHelper.TASK_COLUMN_EXPECTED_DURACTION,
            DatabaseHelper.TASK_COLUMN_ELAPSED_DURAION,
            DatabaseHelper.TASK_COLUMN_DUE_DATE,
            DatabaseHelper.TASK_COLUMN_STATUS,
            DatabaseHelper.TASK_COLUMN_MULTITASKABLE
    };

    public TaskDao(Context context) {
        mDbHelper = new DatabaseHelper(context);
        try {
            open();
        } catch (SQLException exception) {
            Log.e(TAG, exception.getMessage(), exception);
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Task createTask(Task task) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.TASK_COLUMN_TITLE, task.getTitle());
        values.put(DatabaseHelper.TASK_COLUMN_STATUS, task.getStatus().toInt());
        // TODO: Other columns
        long insertId = mDatabase.insert(DatabaseHelper.TABLE_TASK, null, values);
        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_TASK, sAllColumns, DatabaseHelper.TASK_COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Task newTask = cursorToTask(cursor);
        cursor.close();
        return newTask;
    }

    public void deleteTask(Task task) {
        long id = task.getId();
        mDatabase.delete(DatabaseHelper.TABLE_TASK, DatabaseHelper.TASK_COLUMN_ID + " = " + id, null);
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_TASK, sAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return tasks;
    }

    public List<Task> getUnstartedTasks() {
        List<Task> tasks = new ArrayList<>();

        Cursor cursor = mDatabase.query(DatabaseHelper.TABLE_TASK, sAllColumns, "WHERE " + DatabaseHelper.TASK_COLUMN_STATUS + " = ?", new String[]{String.valueOf(Task.Status.UNSTARTED.toInt())}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return tasks;
    }

    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getLong(0));
        task.setTitle(cursor.getString(1));
        return task;
    }
}
