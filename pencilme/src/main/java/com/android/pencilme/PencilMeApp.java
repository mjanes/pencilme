package com.android.pencilme;

import android.app.Application;
import android.content.Context;

import com.android.pencilme.database.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import dagger.ObjectGraph;

/**
 * Created by mjanes on 5/18/2014.
 */
public class PencilMeApp extends Application {

    private ObjectGraph mObjectGraph;

    private static Context sContext;

    private static DatabaseHelper sDatabaseHelper;


    @Override
    public void onCreate() {
        super.onCreate();
        ObjectGraph objectGraph = getObjectGraph();
        objectGraph.inject(this);
        objectGraph.injectStatics();
        sContext = this;
    }

    private synchronized ObjectGraph getObjectGraph() {
        if (mObjectGraph == null) {
            mObjectGraph = ObjectGraph.create(new PencilMeModule(this));
        }
        return mObjectGraph;
    }

    public void injectObject(Object object) {
        getObjectGraph().inject(object);
    }

    public static Context getContext(){
        return sContext;
    }

    public static DatabaseHelper getDatabaseHelper() {
        if (sDatabaseHelper == null) {
            sDatabaseHelper = OpenHelperManager.getHelper(sContext, DatabaseHelper.class);
        }
        return sDatabaseHelper;
        // TODO: Release database helper
    }

}
