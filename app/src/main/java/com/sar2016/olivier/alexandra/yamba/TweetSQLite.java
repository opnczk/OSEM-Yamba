package com.sar2016.olivier.alexandra.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by alexandra on 16/01/17.
 */

public class TweetSQLite extends SQLiteOpenHelper {
    private static final int VERSION_DB = 27;
    private static final String DB_NAME = "tweets.db";

    private static final String TWEETS_TABLE = "tweets_table";
    private static final String ID = "ID";
    private static final String CREATED_AT = "created_at";
    private static final String TXT = "txt";
    private static final String USER = "user";


    private static final String CREATE_DB = "CREATE TABLE "  + TWEETS_TABLE
            + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                TXT + " TEXT NOT NULL, " +
                USER + " TEXT NOT NULL" +
            ");";

    public TweetSQLite(Context context) {
        super(context, DB_NAME, null, VERSION_DB);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TWEETS_TABLE + ";");
        onCreate(sqLiteDatabase);
    }
}
