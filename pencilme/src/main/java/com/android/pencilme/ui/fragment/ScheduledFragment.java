package com.android.pencilme.ui.fragment;

/**
 * Created by mjanes on 5/17/2014.
 */

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.pencilme.PencilMeApp;
import com.android.pencilme.R;

public class ScheduledFragment extends ListFragment {

    public static ScheduledFragment newInstance() {
        ScheduledFragment fragment = new ScheduledFragment();
        return fragment;
    }

    public ScheduledFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scheduled, container, false);
        return rootView;
    }

    public static CharSequence getTitle() {
        return PencilMeApp.getContext().getString(R.string.title_scheduled);
    }
}