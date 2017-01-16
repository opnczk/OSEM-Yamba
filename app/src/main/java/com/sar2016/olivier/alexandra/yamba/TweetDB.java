package com.sar2016.olivier.alexandra.yamba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by alexandra on 16/01/17.
 */

public class TweetDB {

    private static final String TWEETS_TABLE = "tweets_table";

    // COLUMNS
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;

    private static final String COL_CREATED_AT = "created_at";
    private static final int NUM_COL_CREATED_AT = 1;

    private static final String COL_TXT = "txt";
    private static final int NUM_COL_TXT = 2;

    private static final String COL_USER = "user";
    private static final int NUM_COL_USER = 3;

    private SQLiteDatabase db;
    private TweetSQLite myTweetDB;

    public TweetDB(Context context) {
        myTweetDB = new TweetSQLite(context);
    }

    // Open DB writing mode
    public void open() {
        db = myTweetDB.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDB() {
        return db;
    }

    public long insertTweet(Tweet tweet) {
        ContentValues values = new ContentValues();
        values.put(COL_TXT, tweet.getTxt());
        values.put(COL_USER, tweet.getUser());
        return db.insert(TWEETS_TABLE, null, values);
    }

    public int updateTweet(long id, Tweet tweet) {
        ContentValues values = new ContentValues();
        values.put(COL_TXT, tweet.getTxt());
        values.put(COL_USER, tweet.getUser());
        return db.update(TWEETS_TABLE, values, COL_ID + " = " + id, null);
    }

    // get Tweet by ID
    public Tweet getTweetById(int id) {
        Cursor c = db.query(TWEETS_TABLE, new String[] {COL_ID, COL_CREATED_AT, COL_TXT, COL_USER}, COL_ID + " = " + id,
                null, null, null, null);
        return cursorToTweet(c);
    }

    // Remove Tweet by id
    public int removeTweetById(int id) {
        return db.delete(TWEETS_TABLE, COL_ID + " = " + id, null);
    }

    // get all Tweets
    public ArrayList<Tweet> getAll() {
        Cursor c = db.query(TWEETS_TABLE, new String[] {COL_ID, COL_CREATED_AT, COL_TXT, COL_USER},
                null, null, null, null, null);

        ArrayList<Tweet> tweets = new ArrayList<Tweet>();

        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            Tweet tweet = cursorToTweet(c);
            tweets.add(tweet);
        }

        c.close();
        return tweets;
    }

    public void deleteAll() {
        db.delete(TWEETS_TABLE, null, null);
    }

    private Tweet cursorToTweet(Cursor c) {
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();

        // Create tweet object from DB
        Tweet tweet = new Tweet();
        tweet.setId(c.getInt(NUM_COL_ID));
        String s = new String(c.getBlob(NUM_COL_CREATED_AT));
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tweet.setCreatedAt(date);
        tweet.setTxt(c.getString(NUM_COL_TXT));
        tweet.setUser(c.getString(NUM_COL_USER));

        return tweet;
    }
}
