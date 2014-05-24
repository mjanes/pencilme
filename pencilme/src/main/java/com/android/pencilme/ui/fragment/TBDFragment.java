package com.android.pencilme.ui.fragment;

/**
 * Created by mjanes on 5/17/2014.
 */

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.pencilme.PencilMeApp;
import com.android.pencilme.R;
import com.android.pencilme.manager.TaskManager;
import com.android.pencilme.model.Task;
import com.android.pencilme.ui.adapter.TBDTasksAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

public class TBDFragment extends ListFragment {

    @Inject
    Bus mBus;

    @Inject
    TaskManager mTaskManager;

    private TBDTasksAdapter mAdapter;

    private TBDTaskLoader mTBDTaskLoader;

    public static TBDFragment newInstance() {
        TBDFragment fragment = new TBDFragment();
        return fragment;
    }

    public TBDFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PencilMeApp) getActivity().getApplication()).injectFragment(this);

        mAdapter = new TBDTasksAdapter(getActivity());
        setListAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //View view = inflater.inflate(R.layout.fragment_tbd, container, false);
        View view = inflater.inflate(R.layout.fragment_tbd, null);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mBus.register(this);

        // TODO: This is imperfect, because it always reloading from the db, without checking if things
        // have changed. May use loader manager instead? But that seems overkill, and redundant with Otto.
        mTBDTaskLoader = (TBDTaskLoader) new TBDTaskLoader(this, mTaskManager).execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBus.unregister(this);
        mTBDTaskLoader.cancel(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static CharSequence getTitle() {
        return PencilMeApp.getContext().getString(R.string.title_tbd);
    }

    @Subscribe
    public void newTaskEvent(Task.NewTaskEvent event) {
        mAdapter.addTask(event.getTask());
    }


    public void onTasksLoaded(List<Task> tasks) {
        mAdapter.setTasks(tasks);
    }

    private static class TBDTaskLoader extends AsyncTask<Void, Void, List<com.android.pencilme.model.Task>> {

        WeakReference<TBDFragment> mCallback;
        TaskManager mTaskManager;

        public TBDTaskLoader(TBDFragment callback, TaskManager taskManager) {
            mCallback = new WeakReference<>(callback);
            mTaskManager = taskManager;
        }

        @Override
        protected List<Task> doInBackground(Void... params) {
            return mTaskManager.getAllTasks();
        }

        @Override
        protected void onPostExecute(List<Task> tasks) {
            TBDFragment fragment = mCallback.get();
            if (fragment != null) {
                fragment.onTasksLoaded(tasks);
            }
        }
    }

}