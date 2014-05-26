package com.android.pencilme.ui.fragment;

/**
 * Created by mjanes on 5/17/2014.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.pencilme.PencilMeApp;
import com.android.pencilme.R;
import com.android.pencilme.manager.TaskManager;
import com.android.pencilme.model.Task;
import com.squareup.otto.Bus;

import javax.inject.Inject;

public class NewTaskFragment extends Fragment {

    @Inject
    Bus mBus;

    public static NewTaskFragment newInstance() {
        NewTaskFragment fragment = new NewTaskFragment();
        return fragment;
    }

    public NewTaskFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PencilMeApp) getActivity().getApplication()).injectObject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_task, container, false);

        final EditText title = (EditText) view.findViewById(R.id.title);

        Button done = (Button) view.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task(title.getText().toString());
                TaskManager.createTask(task);
                mBus.post(new TaskManager.NewTaskEvent(task));
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBus.unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}