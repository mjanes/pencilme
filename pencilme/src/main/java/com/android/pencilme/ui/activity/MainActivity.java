package com.android.pencilme.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;

import com.android.pencilme.R;
import com.android.pencilme.ui.fragment.HistoryFragment;
import com.android.pencilme.ui.fragment.NavigationDrawerFragment;
import com.android.pencilme.ui.fragment.ReportsFragment;
import com.android.pencilme.ui.fragment.TasksFragment;


public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.container);

        switch (position) {
            case 0:
                if (!(fragment instanceof TasksFragment)) {
                    fragmentManager.beginTransaction().replace(R.id.container, TasksFragment.newInstance()).commit();
                }
                break;
            case 1:
                if (!(fragment instanceof ReportsFragment)) {
                    fragmentManager.beginTransaction().replace(R.id.container, ReportsFragment.newInstance()).commit();
                }
                break;
            case 2:
                if (!(fragment instanceof HistoryFragment)) {
                    fragmentManager.beginTransaction().replace(R.id.container, HistoryFragment.newInstance()).commit();
                }
                break;
            default:
                fragmentManager.beginTransaction().replace(R.id.container, TasksFragment.newInstance()).commit();
                break;
        }

    }

    public void onSectionAttached(String title) {
        mTitle = title;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.tasks, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

}
