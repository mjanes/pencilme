package com.android.pencilme.ui.fragment;

/**
 * Created by mjanes on 5/17/2014.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.pencilme.R;
import com.android.pencilme.ui.activity.MainActivity;
import com.android.pencilme.ui.activity.NewTaskActivity;

public class TasksFragment extends Fragment {

    private ViewPager mViewPager;
    private TasksTabPagerAdapter mViewPagerAdapter;

    public static TasksFragment newInstance() {
        TasksFragment fragment = new TasksFragment();
        return fragment;
    }

    public TasksFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mViewPagerAdapter = new TasksTabPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getString(R.string.title_tasks));
    }

    private static class TasksTabPagerAdapter extends FragmentPagerAdapter {

        public TasksTabPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0) {
                return TBDFragment.newInstance();
            } else {
                return ScheduledFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return TBDFragment.getTitle();
            } else if (position == 1) {
                return ScheduledFragment.getTitle();
            }
            return null;
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tasks, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.add_item) {
            addNewTask();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void addNewTask() {
        Intent intent = new Intent(getActivity(), NewTaskActivity.class);
        startActivity(intent);
    }

}