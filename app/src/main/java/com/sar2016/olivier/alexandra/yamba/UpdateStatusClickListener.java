package com.sar2016.olivier.alexandra.yamba;

import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.KeyStore;
import java.util.Objects;

import winterwell.jtwitter.Twitter;

/**
 * Created by olivier on 09/01/17.
 */

public class UpdateStatusClickListener implements View.OnClickListener {
    private static final String TAG = "UpdateStatusClickListnr";
    private EditText textArea;
    private String recupText;
    private UpdateStatusFragment mainActivity;
    private Twitter api;

    public UpdateStatusClickListener(EditText textArea,String recupText,UpdateStatusFragment mainActivity){
        this.textArea = textArea;
        this.recupText = recupText;
        this.mainActivity = mainActivity;

    }

    public void onClick(View view) {
        // On est sur que la vue est de type UpdateStatusActivity
        Log.d(TAG, "On a cliqué !");
        this.recupText = textArea.getText().toString();
        Log.d(TAG, "Voici le texte recupéré : " + this.recupText);

        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Object result = null;
                try {
                    result = mainActivity.getApi().setStatus(recupText);
                }
                catch (Exception e) {
                    result = e;
                    //String s = this.getResources().getString(R.string.set_username_password);
                    /*Toast toast = Toast.makeText(mainActivity.getActivity().getBaseContext(),
                            e.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();*/
                }

                Log.d("ASYNC_TASK", result.toString());
                return result;
            }

            @Override
            protected void onPostExecute(Object result){
                //Tester si ca marche ou pas
                Toast toast;
                if (result instanceof Exception) {
                    toast = Toast.makeText(mainActivity.getActivity().getBaseContext(),
                            ((Exception) result).getMessage(), Toast.LENGTH_SHORT);

                    // If we have another exception than Unauthorized exception
                    ((Exception) result).printStackTrace();
                }
                else
                    toast = Toast.makeText(mainActivity.getActivity().getBaseContext(), "Votre nouveau statut est \""+result+"\".", Toast.LENGTH_LONG);

                toast.show();
            }
        };

        Object[] params = {};
        asyncTask.execute(params);
    }
}
