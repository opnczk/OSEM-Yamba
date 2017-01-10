package com.sar2016.olivier.alexandra.yamba;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import winterwell.jtwitter.Twitter;

/**
 * Created by olivier on 10/01/17.
 */

public class GetNewStatusesService extends Service{
    private static final String TAG = "GetNewStatusesService";
    private Twitter api;
    private GetNewStatusesServiceThread wrappingThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        Log.d(TAG, "Created");
        this.api = new Twitter("student", "password");
        this.api.setAPIRootUrl("http://yamba.newcircle.com/api");
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
    }

    @Override
    public void onDestroy() {
        wrappingThread.stopThread();
        super.onDestroy();
    }
}
