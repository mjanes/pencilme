package com.android.pencilme.loader;

/**
 * Created by mjanes on 5/20/2014.
 */

import android.content.AsyncTaskLoader;

import com.android.pencilme.PencilMeApp;
import com.android.pencilme.manager.TaskManager;
import com.android.pencilme.model.Task;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

/**
 * TaskLoader
 *
 * http://www.androiddesignpatterns.com/2012/08/implementing-loaders.html
 */
public class UnscheduledTaskLoader extends AsyncTaskLoader<List<Task>> {

    private List<Task> mTasks;

    @Inject
    TaskManager mTaskManager;

    @Inject
    Bus mBus;

    public UnscheduledTaskLoader(PencilMeApp context) {
        super(context);
        context.injectObject(this);
    }

    @Override
    public void deliverResult(List<Task> tasks) {
        if (isReset()) {
            releaseResources();
            return;
        }


        // Hold a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        List<Task> oldTasks = mTasks;
        mTasks = tasks;

        if (isStarted()) {
            // If the Loader is in a started state, deliver the results to the
            // client. The superclass method does this for us.
            super.deliverResult(tasks);
        }

        // Invalidate the old data as we don't need it any more.
        if (oldTasks != null && oldTasks != tasks) {
            releaseResources();
        }
    }


    @Override
    protected void onStartLoading() {
        if (mTasks != null) {
            // Deliver any previously loaded data immediately.
            deliverResult(mTasks);
        }

        mBus.register(this);

        if (takeContentChanged() || mTasks == null) {
            // When the observer detects a change, it should call onContentChanged()
            // on the Loader, which will cause the next call to takeContentChanged()
            // to return true. If this is ever the case (or if the current data is
            // null), we force a new load.
            forceLoad();
        }
    }


    @Override
    public List<Task> loadInBackground() {
        List<Task> tasks = mTaskManager.getAllUnscheduledTasks();
        // TODO: Sort
        return tasks;
    }

    @Override
    protected void onStopLoading() {
        // The Loader is in a stopped state, so we should attempt to cancel the
        // current load (if there is one).
        cancelLoad();

        // Note that we leave the observer as is. Loaders in a stopped state
        // should still monitor the data source for changes so that the Loader
        // will know to force a new load if it is ever started again.
    }

    @Override
    protected void onReset() {
        // Ensure the loader has been stopped.
        onStopLoading();

        // At this point we can release the resources associated with 'mData'.
        if (mTasks != null) {
            releaseResources();
            mTasks = null;
        }

        mBus.unregister(this);
    }


    @Override
    public void onCanceled(List<Task> data) {
        // Attempt to cancel the current asynchronous load.
        super.onCanceled(data);

        // The load has been canceled, so we should release the resources
        // associated with 'data'.
        releaseResources();
    }

    public void releaseResources() {
        mTasks = null;
    }

    @Subscribe
    public void newTaskEvent(TaskManager.NewTaskEvent event) {
        mTasks.add(event.getTask());
        onContentChanged();
    }

}
