package com.sar2016.olivier.alexandra.yamba;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by olivier on 09/01/17.
 */

public class UpdateStatusTextWatcher implements TextWatcher {
    private EditText editText;
    private int currentColor;
    private int green;
    private int orange;
    private int red;

    public UpdateStatusTextWatcher(EditText editText, int green, int orange, int red){
        this.green = green;
        this.orange = orange;
        this.red = red;
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after){}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count){
        /*int green = ResourcesCompat.getColor(Resources.getSystem(), R.color.colorGreen, null);
        int orange = ResourcesCompat.getColor(Resources.getSystem(), R.color.colorOrange, null);
        int red = ResourcesCompat.getColor(Resources.getSystem(), R.color.colorRed, null);*/
        if(s.length() > 140){
            this.editText.setTextColor(Color.RED);
            this.editText.setTextColor(red);
        }else if(s.length() > 100){
            this.editText.setTextColor(Color.YELLOW);
            this.editText.setTextColor(orange);
        }else{
            this.editText.setTextColor(Color.GREEN);
            this.editText.setTextColor(green);
        }
    }

    @Override
    public void afterTextChanged(Editable s){}
}
