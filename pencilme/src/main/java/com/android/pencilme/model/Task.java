package com.android.pencilme.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjanes on 5/18/2014.
 */
public class Task implements Parcelable {

    public static final String TASK_EXTRA = "extra.com.android.pencilme.model.task";

    private long mId;
    private String mTitle;
    private long mExpectedDuration;
    private long mElapsedDuration;
    private long mDueDate;
    private Status mStatus;
    private boolean mIsMultitaskable;

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

    /** Constructors */

    public Task() {}

    public Task(String title) {
        mTitle = title;
        mStatus = Status.UNSTARTED;
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

    public long getExpectedDuration() {
        return mExpectedDuration;
    }

    public void setExpectedDuration(long expectedDuration) {
        mExpectedDuration = expectedDuration;
    }

    public long getElapsedDuration() {
        return mElapsedDuration;
    }

    public void setElapsedDuration(long elapsedDuration) {
        mElapsedDuration = elapsedDuration;
    }

    public long getDueDate() {
        return mDueDate;
    }

    public void setDueDate(long dueDate) {
        mDueDate = dueDate;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public boolean isMultitaskable() {
        return mIsMultitaskable;
    }

    public void setMultitaskable(boolean isMultitaskable) {
        this.mIsMultitaskable = isMultitaskable;
    }


    /** Object methods */

    @Override public String toString() {
        return mTitle;
    }


    /** Parceling */

    public Task(Parcel in) {
        mTitle = in.readString();
        mExpectedDuration = in.readLong();
        mElapsedDuration = in.readLong();
        mDueDate = in.readLong();
        mStatus = Status.fromInt(in.readInt());
        // TODO: mIsMultitaskable
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeLong(mExpectedDuration);
        dest.writeLong(mElapsedDuration);
        dest.writeLong(mDueDate);
        dest.writeInt(mStatus.toInt());
        // TODO: mIsMultitaskable
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {

        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }

    };



    /** EventBus */

    public static class NewTaskEvent {
        private final Task mTask;

        public NewTaskEvent(Task task) {
            mTask = task;
        }

        public Task getTask() {
            return mTask;
        }
    }


}
