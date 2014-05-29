package com.android.pencilme.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.pencilme.manager.TaskManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjanes on 5/18/2014.
 */
@DatabaseTable(tableName=Task.TABLE_NAME)
public class Task implements Parcelable {

    public static final String TABLE_NAME = "task";
    public static final String TASK_EXTRA = "extra.com.android.pencilme.model.task";

    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String EXPECTED_DURATION = "expectedDuration";
    public static final String ELAPSED_DURATION = "elapsedDuration";
    public static final String DUE_DATE = "dueDate";
    public static final String STATUS = "status";
    public static final String MULTITASKABLE = "multitaskable";
    public static final String SCHEDULED_TIME = "scheduledTime";

    @DatabaseField(generatedId=true, columnName=ID)
    private long mId;

    @DatabaseField(canBeNull = false, columnName = TITLE)
    private String mTitle;

    @DatabaseField(columnName = EXPECTED_DURATION)
    private int mExpectedDuration;

    @DatabaseField(columnName = ELAPSED_DURATION)
    private int mElapsedDuration;

    @DatabaseField(columnName = DUE_DATE)
    private Date mDueDate;

    @DatabaseField(columnName = STATUS)
    private Status mStatus;

    @DatabaseField(columnName = MULTITASKABLE)
    private boolean mMultitaskable;

    @DatabaseField(columnName = SCHEDULED_TIME, foreign = true)
    private Event mScheduledTime;

    public enum Status {
        UNSTARTED(0),
        STARTED(1),
        FINISHED(2);

        private final int mInt;

        private Status(int integer) {
            mInt = integer;
        }

        public int toInt() {
            return mInt;
        }

        public static Status fromInt(int integer) {
            return m.get(integer);
        }

        private static final Map<Integer, Status> m = new HashMap<>();

        static {
            for (Status s : Status.values()) {
                m.put(s.toInt(), s);
            }
        }
    }

    /** Constructors and Builder */

    public static class Builder {
        // Required parameters
        private String mTitle;

        // Optional parameters initialized to default values
        private int mExpectedDuration = 0;
        private int mElapsedDuration = 0;
        private Date mDueDate = null;
        private Status mStatus = Status.UNSTARTED;
        private boolean mMultitaskable = false;
        private Event mScheduledTime;

        public Builder(String title) {
            mTitle = title;
        }

        public Builder expectedDuration(int expectedDuration) { mExpectedDuration = expectedDuration; return this; }
        public Builder elapsedDuration(int elapsedDuration) { mElapsedDuration = elapsedDuration; return this; }
        public Builder dueDate(Date dueDate) { mDueDate = dueDate; return this; }
        public Builder status(Status status) { mStatus = status; return this; }
        public Builder multitaskable(boolean multitaskable) { mMultitaskable = multitaskable; return this; }
        public Builder scheduledTime(Event event) { mScheduledTime = event; return this; }

        public Task build() {
            Task task = new Task(this);
            TaskManager.createTask(task);
            return task;
        }

    }

    // Required for ORMLite
    public Task() {}

    public Task(Builder builder) {
        mTitle = builder.mTitle;
        mExpectedDuration = builder.mExpectedDuration;
        mElapsedDuration = builder.mElapsedDuration;
        mDueDate = builder.mDueDate;
        mStatus = builder.mStatus;
        mMultitaskable = builder.mMultitaskable;
        mScheduledTime = builder.mScheduledTime;
    }

    /** Getters and setters */

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getExpectedDuration() {
        return mExpectedDuration;
    }

    public void setExpectedDuration(int expectedDuration) {
        mExpectedDuration = expectedDuration;
    }

    public int getElapsedDuration() {
        return mElapsedDuration;
    }

    public void setElapsedDuration(int elapsedDuration) {
        mElapsedDuration = elapsedDuration;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public void setDueDate(Date dueDate) {
        mDueDate = dueDate;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public boolean isMultitaskable() {
        return mMultitaskable;
    }

    public void setMultitaskable(boolean isMultitaskable) {
        this.mMultitaskable = isMultitaskable;
    }


    /** Utility methods */

    public String getExpectedDurationAsString() {
        return "";
    }

    /** Inheriting from object methods */

    @Override public String toString() {
        return mTitle;
    }


    /** Parceling */

    public Task(Parcel in) {
        mTitle = in.readString();
        mExpectedDuration = in.readInt();
        mElapsedDuration = in.readInt();
        long date = in.readLong();
        mDueDate = date == 0 ? null : new Date(date);
        mStatus = Status.fromInt(in.readInt());
        mMultitaskable = in.readInt() == 1 ? true : false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeInt(mExpectedDuration);
        dest.writeInt(mElapsedDuration);
        dest.writeLong(mDueDate == null ? 0 : mDueDate.getTime());
        dest.writeInt(mStatus.toInt());
        dest.writeInt(mMultitaskable ? 1 : 0);
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {

        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }

    };

}
