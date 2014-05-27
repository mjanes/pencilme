package com.android.pencilme.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.pencilme.R;
import com.android.pencilme.model.Task;

public class TaskDetailFragment extends Fragment {

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

        Button save = (Button) view.findViewById(R.id.save);
        // TODO: Save

        return view;
    }

}
