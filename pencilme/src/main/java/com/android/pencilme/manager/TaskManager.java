package com.android.pencilme.manager;

import android.util.Log;

import com.android.pencilme.PencilMeApp;
import com.android.pencilme.model.Task;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by mjanes on 5/21/2014.
 */
public class TaskManager {

    private static final String TAG = "com.android.pencilme.manager.TaskManager";

    @Inject
    static PencilMeApp sPencilMeApp;

    /** Static db interaction */

    public static void createTask(Task task) {
        try {
            sPencilMeApp.getDatabaseHelper().getTaskDao().create(task);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }


    public static List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            tasks = sPencilMeApp.getDatabaseHelper().getTaskDao().queryForAll();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return tasks;
    }

    public static List<Task> getAllUnscheduledTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            Dao<Task, Long> taskDao = sPencilMeApp.getDatabaseHelper().getTaskDao();
            QueryBuilder<Task, Long> queryBuilder = taskDao.queryBuilder();
            PreparedQuery<Task> preparedQuery = queryBuilder.where().isNull(Task.SCHEDULED_DATE).prepare();
            tasks = taskDao.query(preparedQuery);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return tasks;
    }


    /** EventBus */

    public static class NewTaskEvent {
        private final Task mTask;

        public NewTaskEvent(Task task) {
            mTask = task;
        }

        public Task getTask() {
            return mTask;
        }
    }

}
