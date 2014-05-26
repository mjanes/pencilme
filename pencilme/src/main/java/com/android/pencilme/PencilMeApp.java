package com.android.pencilme;

import android.app.Application;
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

}
