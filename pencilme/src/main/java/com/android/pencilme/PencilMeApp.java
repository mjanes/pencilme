package com.android.pencilme;

import android.app.Application;
import android.app.Fragment;
import android.content.Context;

import dagger.ObjectGraph;

/**
 * Created by mjanes on 5/18/2014.
 */
public class PencilMeApp extends Application {

    private ObjectGraph mObjectGraph;

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        getObjectGraph().inject(this);
        sContext = this;
    }

    private synchronized ObjectGraph getObjectGraph() {
        if (mObjectGraph == null) {
            mObjectGraph = ObjectGraph.create(new PencilMeModule(this));
        }
        return mObjectGraph;
    }

    public void injectFragment(Fragment fragment) {
        getObjectGraph().inject(fragment);
    }

    public static Context getContext(){
        return sContext;
    }

    // TODO: Close TaskDao on destroy?

}
