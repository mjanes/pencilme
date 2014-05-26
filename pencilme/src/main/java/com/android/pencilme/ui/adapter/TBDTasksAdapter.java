package com.android.pencilme.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.pencilme.PencilMeApp;
import com.android.pencilme.R;
import com.android.pencilme.model.Task;

import java.util.List;

/**
 * Created by mjanes on 5/20/2014.
 */
public class TBDTasksAdapter extends ArrayAdapter<Task> {

    private LayoutInflater mLayoutInflater;

    public TBDTasksAdapter(Context context) {
        super(context, R.layout.item_task);
        mLayoutInflater = (LayoutInflater) PencilMeApp.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private static class ViewHolder {
        TextView mTaskTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_task, null);

            holder = new ViewHolder();
            holder.mTaskTitle = (TextView) convertView.findViewById(R.id.task_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Task task = getItem(position);
        holder.mTaskTitle.setText(task.getTitle());

        return convertView;
    }

    public void setTasks(List<Task> tasks) {
        clear();
        if (tasks == null) return;
        for (Task task : tasks) {
            add(task);
        }
    }

}
