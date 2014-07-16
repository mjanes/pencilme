package com.android.pencilme.ui.widget;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.android.pencilme.R;

/**
 * Created by mjanes on 6/17/2014.
 */
public class DurationPickerDialogFragment extends DialogFragment {

    private NumberPicker mHourPicker;
    private NumberPicker mMinutePicker;

    private OnDurationSetListener mOnDurationSetListener;

    private static final String[] HOUR_VALUES = {"8", "7", "6", "5", "4", "3", "2", "1", "0"};
    private static final String[] MINUTE_VALUES = {"45", "30", "15", "0"};

    public static DurationPickerDialogFragment newInstance() {
        DurationPickerDialogFragment fragment = new DurationPickerDialogFragment();
        return fragment;
    }

    public DurationPickerDialogFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_duration_picker, container, false);

        getDialog().setTitle(R.string.duration_dialog_title);

        mHourPicker = (NumberPicker) view.findViewById(R.id.hour_picker);
        mHourPicker.setDisplayedValues(HOUR_VALUES);
        mHourPicker.setMinValue(0);
        mHourPicker.setMaxValue(HOUR_VALUES.length - 1);
        mHourPicker.setWrapSelectorWheel(false);
        mHourPicker.setValue(HOUR_VALUES.length - 1);

        mMinutePicker = (NumberPicker) view.findViewById(R.id.minute_picker);
        mMinutePicker.setDisplayedValues(MINUTE_VALUES);
        mMinutePicker.setMinValue(0);
        mMinutePicker.setMaxValue(MINUTE_VALUES.length - 1);
        mMinutePicker.setWrapSelectorWheel(false);
        mMinutePicker.setValue(MINUTE_VALUES.length - 1);

        Button confirmButton = (Button) view.findViewById(R.id.select_time_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mOnDurationSetListener != null) {
                    int hours = Integer.valueOf(HOUR_VALUES[mHourPicker.getValue()]);
                    int minutes = Integer.valueOf(MINUTE_VALUES[mMinutePicker.getValue()]);
                    int seconds = hours * 60 * 60 + minutes * 60;

                    DurationPickerDialogFragment.this.mOnDurationSetListener.onDurationSet(seconds);
                }
                DurationPickerDialogFragment.this.dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnDurationSetListener = (OnDurationSetListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDurationSetListener");
        }
    }

    public static interface OnDurationSetListener {
        public void onDurationSet(int seconds);
    }
}
