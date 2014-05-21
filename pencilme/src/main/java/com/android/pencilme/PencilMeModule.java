package com.android.pencilme;

import android.content.Context;

import com.android.pencilme.database.TaskDao;
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
        PencilMeApp.class,
        TasksFragment.class,
        NewTaskFragment.class,
        TBDFragment.class
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
    TaskDao provideTaskDao(Context context) {
        return new TaskDao(context);
    }
}
