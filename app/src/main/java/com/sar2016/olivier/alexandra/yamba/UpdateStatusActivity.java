package com.sar2016.olivier.alexandra.yamba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class UpdateStatusActivity extends AppCompatActivity {
    private static final String TAG = "UpdateStatusActivity";
    private Button button;
    private EditText textArea;
    private String recupText;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "created");
        setContentView(R.layout.activity_update_status);
        button = (Button) findViewById(R.id.update_status_button);
        textArea = (EditText) findViewById(R.id.update_status_editText);
        button.setOnClickListener(new UpdateStatusClickListener(textArea, recupText, this));
        int green = ResourcesCompat.getColor(getResources(), R.color.colorGreen, null);
        int orange = ResourcesCompat.getColor(getResources(), R.color.colorOrange, null);
        int red = ResourcesCompat.getColor(getResources(), R.color.colorRed, null);
        textArea.addTextChangedListener(new UpdateStatusTextWatcher(this.textArea, green, orange, red));

       // preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());



        startService(new Intent(getBaseContext(), GetNewStatusesService.class));
    }

    public SharedPreferences getPreferences() {
        return this.preferences;
    }

}
