package com.djunicode.queuingapp.data;

import android.provider.BaseColumns;

public class ActiveQueuesContract {

    public static final class ActiveQueuesEntry implements BaseColumns{
        public static final String TABLE_NAME = "activeQueue";
        public static final String COLUMN_QUEUE_ID = "queueId";
        public static final String COLUMN_FROM = "fromTime";
        public static final String COLUMN_TO = "toTime";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_SUBJECT = "subject";
    }
}
