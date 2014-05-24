package com.android.pencilme.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.pencilme.model.Task;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by mjanes on 5/20/2014.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static String TAG = "com.android.pencilme.database.DatabaseHelper";

    private Context mContext;

    private static final String DATABASE_NAME = "pencilme.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Task, Long> mTaskDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Task.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int previousVersion, int currenttVversion) {

    }


    public Dao<Task, Long> getTaskDao() throws SQLException {
        if (mTaskDao == null) {
            mTaskDao = DaoManager.createDao(getConnectionSource(), Task.class);
        }
        return mTaskDao;
    }

    @Override
    public void close() {
        super.close();

        if (mTaskDao != null) {
            mTaskDao.clearObjectCache();
            mTaskDao = null;
        }
    }
}
