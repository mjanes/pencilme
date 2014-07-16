package com.android.pencilme.ui.fragment;

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

/**
 * TODO: Consider having this and NewTaskFragment inherent from the same parent, or implement the
 * same interface.
 */
public class TaskDetailFragment extends Fragment {

    private static final String ARG_TASK = "task";
    private static final String DIALOG_TAG = "tag.com.android.pencilme.ui.fragment.taskdetailfragment.dialog";

    @Inject
    Bus mBus;

    private Task mTask;

    private TextView mDurationValueTextView;

    /**
     * @param task Task
     * @return A new instance of fragment TaskDetailFragment.
     */
    public static TaskDetailFragment newInstance(Task task) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    public TaskDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PencilMeApp) getActivity().getApplication()).injectObject(this);

        if (getArguments() != null) {
            mTask = getArguments().getParcelable(ARG_TASK);
        }
        if (mTask == null) { // Abort! should not happen
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        final EditText title = (EditText) view.findViewById(R.id.title);
        title.setText(mTask.getTitle());


        final LinearLayout durationContainer = (LinearLayout) view.findViewById(R.id.duration_container);

        mDurationValueTextView = (TextView) durationContainer.findViewById(R.id.duration_value);
        mDurationValueTextView.setText(Task.getDurationAsString(mTask.getExpectedDuration()));

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
        multitaskableCheckbox.setChecked(mTask.isMultitaskable());


        Button save = (Button) view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Check if there were any changes before resaving to db and notifying bus
                mTask.setTitle(title.getText().toString()); // TODO: Have on text changed listener
                mTask.save();
                mBus.post(new TaskManager.EditTaskEvent(mTask));
                getActivity().finish();
            }
        });

        return view;
    }

    public void setTaskDuration(int seconds) {
        mTask.setExpectedDuration(seconds);
        mDurationValueTextView.setText(Task.getDurationAsString(mTask.getExpectedDuration()));
    }
}
