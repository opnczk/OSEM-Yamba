package com.sar2016.olivier.alexandra.yamba;

import android.os.StrictMode;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class UpdateStatusActivity extends AppCompatActivity {
    private Button button;
    private EditText textArea;
    private String recupText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_status);
        button = (Button)findViewById(R.id.update_status_button);
        textArea = (EditText)findViewById(R.id.update_status_editText);
        button.setOnClickListener(new UpdateStatusClickListener(textArea, recupText, this));
        int green = ResourcesCompat.getColor(getResources(), R.color.colorGreen, null);
        int orange = ResourcesCompat.getColor(getResources(), R.color.colorOrange, null);
        int red = ResourcesCompat.getColor(getResources(), R.color.colorRed, null);
        textArea.addTextChangedListener(new UpdateStatusTextWatcher(this.textArea, green, orange, red));
    }

}
