package com.android.pencilme.ui.fragment;

/**
 * Created by mjanes on 5/17/2014.
 */

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.pencilme.PencilMeApp;
import com.android.pencilme.R;
import com.android.pencilme.manager.TaskManager;
import com.android.pencilme.model.Task;
import com.android.pencilme.ui.widget.DurationPickerDialogFragment;
import com.squareup.otto.Bus;

import javax.inject.Inject;

public class NewTaskFragment extends Fragment {

    private static final String DIALOG_TAG = "tag.com.android.pencilme.ui.fragment.newtaskfragment.dialog";

    @Inject
    Bus mBus;

    private TextView mDurationValueTextView;
    private int mTaskDuration;


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

        mDurationValueTextView = (TextView) durationContainer.findViewById(R.id.duration_value);

        durationContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag(DIALOG_TAG);

                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                DurationPickerDialogFragment dialogFragment = DurationPickerDialogFragment.newInstance();
                dialogFragment.show(ft, DIALOG_TAG);
            }
        });

        final CheckBox multitaskableCheckbox = (CheckBox) view.findViewById(R.id.multitaskable_checkbox);

        Button done = (Button) view.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task.Builder(title.getText().toString())
                        .multitaskable(multitaskableCheckbox.isChecked())
                        .expectedDuration(mTaskDuration)
                        .build();
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

    public void setTaskDuration(int seconds) {
        mTaskDuration = seconds;
        mDurationValueTextView.setText(Task.getDurationAsString(mTaskDuration));
    }
}