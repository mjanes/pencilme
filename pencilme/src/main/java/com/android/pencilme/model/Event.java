package com.android.pencilme.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by mjanes on 5/26/2014.
 */
@DatabaseTable(tableName=Task.TABLE_NAME)
public class Event {

    public static final String TABLE_NAME = "event";

    public static final String ID = "_id";
    public static final String CREATED = "created";
    public static final String DATE = "date";

    @DatabaseField(generatedId = true, columnName = ID)
    private long mId;

    @DatabaseField(columnName = CREATED)
    private long mCreated;

    @DatabaseField(columnName = DATE)
    private Date mDate;

}
