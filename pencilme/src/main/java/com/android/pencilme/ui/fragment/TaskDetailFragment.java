package com.android.pencilme.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.pencilme.R;
import com.android.pencilme.model.Task;
import com.android.pencilme.ui.fragment.abstraction.TaskFragment;

public class TaskDetailFragment extends TaskFragment {

    private static final String ARG_TASK = "task";

    private Task mTask;

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
        if (getArguments() != null) {
            mTask = getArguments().getParcelable(ARG_TASK);
        }
        if(mTask == null) {
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        final EditText title = (EditText) view.findViewById(R.id.title);
        title.setText(mTask.getTitle());


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
        multitaskableCheckbox.setChecked(mTask.isMultitaskable());


        Button save = (Button) view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Actually save
            }
        });

        return view;
    }

}
