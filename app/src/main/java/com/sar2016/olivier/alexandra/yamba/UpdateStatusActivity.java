package com.sar2016.olivier.alexandra.yamba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import winterwell.jtwitter.Twitter;

public class UpdateStatusActivity extends AppCompatActivity {
    private static final String TAG = "UpdateStatusActivity";
    private Button button;
    private EditText textArea;
    private String recupText;
    private SharedPreferences preferences;
    private Twitter api;

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
        preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        // API
        api = this.getTwitterObject();
        
        // Listener on preferences changings
        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
                SharedPreferences.Editor editor = preferences.edit();
                Toast toast = Toast.makeText(getApplicationContext(), key + " in settings have changed !", Toast.LENGTH_SHORT);
                toast.show();
            }
        };
        preferences.registerOnSharedPreferenceChangeListener(listener);

        startService(new Intent(getBaseContext(), GetNewStatusesService.class));
    }

    public SharedPreferences getPreferences() {
        return this.preferences;
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private Twitter getTwitterObject() {
        String username = this.getPreferences().getString(getResources().getString(R.string.key_username), "DEFAULT");
        String password = this.getPreferences().getString(getResources().getString(R.string.key_password), "DEFAULT");
        String api = this.getPreferences().getString(getResources().getString(R.string.key_api_url), "DEFAULT");
        Twitter twitter = new Twitter(username, password);
        twitter.setAPIRootUrl(api);
        return twitter;
    }

    public Twitter getApi() {
        return this.api;
    }

}
