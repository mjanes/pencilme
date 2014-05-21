package com.android.pencilme.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.pencilme.R;
import com.android.pencilme.ui.fragment.NewTaskFragment;

public class NewTaskActivity extends Activity {

    public static final int REQUEST_NEW_TASK = 0;

    protected CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_task);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        mTitle = getString(R.string.title_new_task);

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.layout.fragment_new_task);

        if (fragment == null) {
            fragment = NewTaskFragment.newInstance();
            fm.beginTransaction().
                    add(R.id.fragment_container, fragment).
                    commit();
        }
    }

    @Override
    protected void onResume() {
        this.overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up);
        super.onResume();
    }

    @Override
    protected void onPause() {
        this.overridePendingTransition(R.anim.slide_in_up, R.anim.abc_slide_out_bottom);
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
                cancel();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cancel() {
        finish();
    }
}
