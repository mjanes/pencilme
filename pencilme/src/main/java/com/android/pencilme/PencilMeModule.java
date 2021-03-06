package com.android.pencilme;

import com.android.pencilme.loader.UnscheduledTaskLoader;
import com.android.pencilme.ui.fragment.NewTaskFragment;
import com.android.pencilme.ui.fragment.TaskDetailFragment;
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
        TaskDetailFragment.class,

        // Loaders
        UnscheduledTaskLoader.class
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
