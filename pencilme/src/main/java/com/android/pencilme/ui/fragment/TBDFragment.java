package com.android.pencilme.ui.fragment;

/**
 * Created by mjanes on 5/17/2014.
 */

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.pencilme.PencilMeApp;
import com.android.pencilme.R;
import com.android.pencilme.loader.UnscheduledTaskLoader;
import com.android.pencilme.model.Task;
import com.android.pencilme.ui.activity.TaskDetailActivity;
import com.android.pencilme.ui.adapter.TBDTasksAdapter;

import java.util.ArrayList;
import java.util.List;

public class TBDFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Task>> {

    private TBDTasksAdapter mAdapter;

    public static TBDFragment newInstance() {
        TBDFragment fragment = new TBDFragment();
        return fragment;
    }

    public TBDFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new TBDTasksAdapter(getActivity());
        setListAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tbd, null);
        return view;
    }

    public static CharSequence getTitle() {
        return PencilMeApp.getContext().getString(R.string.title_tbd);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Task selectedTask = (Task) getListAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
        intent.putExtra(Task.TASK_EXTRA, selectedTask);
        startActivity(intent);
    }


    /** Loader Manager Callbacks */

    @Override
    public Loader<List<Task>> onCreateLoader(int id, Bundle args) {
        return new UnscheduledTaskLoader((PencilMeApp) getActivity().getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Task>> loader, List<Task> data) {
        mAdapter.setTasks(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Task>> loader) {
        mAdapter.setTasks(new ArrayList<Task>());
    }

}