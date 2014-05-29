package com.android.pencilme.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.pencilme.R;

public class TimeLengthPicker extends Fragment {

    public static final String ARG_TIME = "com.android.pencilme.ui.fragment.TimeLengthPicker";

    private int mTime;

    private OnTimeLengthPickerListener mListener;

    public static TimeLengthPicker newInstance(int length) {
        TimeLengthPicker fragment = new TimeLengthPicker();
        Bundle args = new Bundle();
        args.putInt(ARG_TIME, length);
        fragment.setArguments(args);
        return fragment;
    }
    public TimeLengthPicker() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTime = getArguments().getInt(ARG_TIME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_length_picker, container, false);
    }

    public void onSavePressed() {
        if (mListener != null) {
            mListener.onTimeLengthSet(getTimeLength());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTimeLengthPickerListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnTimeLengthPickerListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private int getTimeLength() {
        return 0;
    }

    public interface OnTimeLengthPickerListener {
        public void onTimeLengthSet(int time);
    }

}
