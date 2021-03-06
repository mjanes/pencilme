package com.android.pencilme.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.pencilme.R;
import com.android.pencilme.ui.activity.MainActivity;

/**
 * Created by mjanes on 5/17/2014.
 */
public class ReportsFragment extends Fragment {

    public static ReportsFragment newInstance() {
        return new ReportsFragment();
    }

    public ReportsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reports, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getString(R.string.title_reports));
    }
}
