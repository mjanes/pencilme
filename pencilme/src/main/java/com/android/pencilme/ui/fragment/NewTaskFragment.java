package com.android.pencilme.ui.fragment;

/**
 * Created by mjanes on 5/17/2014.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.pencilme.PencilMeApp;
import com.android.pencilme.R;
import com.android.pencilme.manager.TaskManager;
import com.android.pencilme.model.Task;
import com.android.pencilme.ui.fragment.abstraction.TaskFragment;
import com.squareup.otto.Bus;

import javax.inject.Inject;

public class NewTaskFragment extends TaskFragment {

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

        final LinearLayout durationContainer = (LinearLayout) view.findViewById(R.id.duration_container);
        durationContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onSetDurationClicked();
                }
            }
        });

        final CheckBox multitaskableCheckbox = (CheckBox) view.findViewById(R.id.multitaskable_checkbox);

        Button done = (Button) view.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task.Builder(title.getText().toString()).multitaskable(multitaskableCheckbox.isChecked()).build();
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

}