package com.android.pencilme;

import com.android.pencilme.loader.UnscheduledTaskLoader;
import com.android.pencilme.manager.TaskManager;
import com.android.pencilme.ui.fragment.NewTaskFragment;
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
        NewTaskFragment.class,

        // Loaders
        UnscheduledTaskLoader.class
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
    PencilMeApp provideApplicationContext() {
        return mApplication;
    }

}
