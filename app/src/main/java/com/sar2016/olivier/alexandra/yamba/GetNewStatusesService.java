package com.sar2016.olivier.alexandra.yamba;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import winterwell.jtwitter.Twitter;

/**
 * Created by olivier on 10/01/17.
 */

public class GetNewStatusesService extends Service{
    private static final String TAG = "GetNewStatusesService";
    //private Twitter api;
    private GetNewStatusesServiceThread wrappingThread;
    private SharedPreferences preferences;
    private Twitter api;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        Log.d(TAG, "Created");
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // API
        api = this.getTwitterObject();

        this.wrappingThread = new GetNewStatusesServiceThread(this);
        this.wrappingThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Called");
        return super.onStartCommand(intent, flags, startId);
    }

    public void methodToExec(){

        Log.d(TAG, "Method To Exec");
        List<Twitter.Status> statuses = api.getHomeTimeline();
        for(int i = 0; i < statuses.size(); i++){
            Log.d("SERVICE", statuses.get(i).getText());
        }
    }

    @Override
    public void onDestroy() {
        wrappingThread.stopThread();
        super.onDestroy();
    }
    public SharedPreferences getPreferences() {
        return this.preferences;
    }

    private Twitter getTwitterObject() {
        String username = this.getPreferences().getString(getResources().getString(R.string.key_username), "DEFAULT");
        String password = this.getPreferences().getString(getResources().getString(R.string.key_password), "DEFAULT");
       /* if (username.equals(getResources().getString(R.string.pref_default_username)) ||
                password.equals(getResources().getString(R.string.pref_default_password))) {

        }*/

        String api = this.getPreferences().getString(getResources().getString(R.string.key_api_url), "DEFAULT");
        Twitter twitter = new Twitter(username, password);
        twitter.setAPIRootUrl(api);
        return twitter;
    }
}
