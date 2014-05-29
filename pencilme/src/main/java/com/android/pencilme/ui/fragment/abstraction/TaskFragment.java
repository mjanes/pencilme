package com.android.pencilme.ui.fragment.abstraction;

import android.app.Activity;
import android.app.Fragment;

public abstract class TaskFragment extends Fragment {

    protected OnSetDurationClickedListener mListener;

    public TaskFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSetDurationClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnSetDurationClickedListener");
        }
    }

    public interface OnSetDurationClickedListener {
        public void onSetDurationClicked();
    }
}
