package com.sar2016.olivier.alexandra.yamba;

import android.content.Context;
import android.util.Log;

/**
 * Created by olivier on 10/01/17.
 */

public class GetNewStatusesServiceThread extends Thread {
    private static final String TAG = "wrappingThread";
    private GetNewStatusesService service;
    private boolean running;

    public GetNewStatusesServiceThread(GetNewStatusesService service){
        this.service = service;
        this.running = service.getPreferences().getBoolean("switch_timeline", true);
        Log.d(TAG, "created");
    }

    @Override
    public void run() {
        while(running) {
            try {
                service.methodToExec();
            }catch(Exception e){
                service.connected = false;
                e.printStackTrace();
            }
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread(){
        this.running = false;
    }
}
