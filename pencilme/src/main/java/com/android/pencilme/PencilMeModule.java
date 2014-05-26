package com.android.pencilme;

import android.content.Context;

import com.android.pencilme.database.DatabaseHelper;
import com.android.pencilme.loader.TaskLoader;
import com.android.pencilme.manager.TaskManager;
import com.android.pencilme.ui.fragment.NewTaskFragment;
import com.android.pencilme.ui.fragment.TBDFragment;
import com.android.pencilme.ui.fragment.TasksFragment;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mjanes on 5/20/2014.
 */


@Module(
    injects = {

        // App
        PencilMeApp.class,

        // Fragments
        TasksFragment.class,
        NewTaskFragment.class,
        TBDFragment.class,

        // Loaders
        TaskLoader.class
    },
    staticInjections = {

        // Managers
        TaskManager.class
    },
    complete = false
)
public class PencilMeModule {

    private final PencilMeApp mApplication;

    public PencilMeModule(PencilMeApp application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper(Context context) { return new DatabaseHelper(context); }

}
