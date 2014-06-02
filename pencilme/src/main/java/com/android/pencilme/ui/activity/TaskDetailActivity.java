package com.android.pencilme.ui.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.pencilme.R;
import com.android.pencilme.model.Task;
import com.android.pencilme.ui.fragment.TaskDetailFragment;

public class TaskDetailActivity extends Activity {

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
        TaskDetailFragment fragment = (TaskDetailFragment) fm.findFragmentById(R.layout.fragment_task_detail);

        if (fragment == null) {
            fragment = TaskDetailFragment.newInstance(task);
            fm.beginTransaction().
                    add(R.id.fragment_container, fragment).
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

}
