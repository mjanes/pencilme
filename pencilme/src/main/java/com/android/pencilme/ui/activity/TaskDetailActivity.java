package com.android.pencilme.ui.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.pencilme.R;
import com.android.pencilme.model.Task;
import com.android.pencilme.ui.fragment.NewTaskFragment;
import com.android.pencilme.ui.fragment.TaskDetailFragment;
import com.android.pencilme.ui.widget.DurationPickerDialogFragment;

public class TaskDetailActivity extends Activity implements DurationPickerDialogFragment.OnDurationSetListener {

    TaskDetailFragment mTaskDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        final Task task = getIntent().getExtras().getParcelable(Task.TASK_EXTRA);
        if (task == null) {
            finish();
            return;
        }

        FragmentManager fm = getFragmentManager();
        mTaskDetailFragment = (TaskDetailFragment) fm.findFragmentById(R.layout.fragment_task_detail);

        if (mTaskDetailFragment == null) {
            mTaskDetailFragment = TaskDetailFragment.newInstance(task);
            fm.beginTransaction().
                    add(R.id.fragment_container, mTaskDetailFragment).
                    commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDurationSet(int seconds) {
        mTaskDetailFragment.setTaskDuration(seconds);
    }
}
