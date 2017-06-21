package com.com.ulike_app.onebuttonclick;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.com.ulike_app.onebuttonclick.actions.ActionFactory;
import com.com.ulike_app.onebuttonclick.entity.ActionEntity;
import com.com.ulike_app.onebuttonclick.model.HttpProvider;
import com.com.ulike_app.onebuttonclick.model.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int NOTIFY_ID = 101;

    private Button btnAction;
    private ArrayList<ActionEntity> configs;
    private Context context;
    private int index;
    private Intent intentPhone;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        intentPhone = new Intent(Intent.ACTION_DIAL);
        mediaPlayer = MediaPlayer.create(this, R.raw.error_sound);

        btnAction = (Button) findViewById(R.id.btn_action);
        btnAction.setOnClickListener(this);

        initConfigs();
    }

    /**
     * Init configs, checking JSON from SharedPreferences.
     */
    private void initConfigs() {
        configs = new ArrayList<>();
        String jsonConfigs = Utils.getPref(context);

        if (jsonConfigs.length() == 0) {
            new SettingAsyncTask().execute();
        } else {
            configs = Utils.jsonToList(jsonConfigs);
            Collections.sort(configs);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_action) {
            onClickActions();
        }
    }

    private void onClickActions() {
        if (index >= configs.size())
            index = 0;
        if (isActionRunnable()) {
            configs.get(index).setStartTime(Calendar.getInstance().getTimeInMillis());
            String type = configs.get(index++).getType();
            new ActionFactory().getAction(type).actionRun(this);
        } else {
            index++;
            Toast.makeText(this, "No active Actions", Toast.LENGTH_LONG).show();
            mediaPlayer.start();
        }

    }

    public boolean isActionRunnable() {
        ActionEntity tmpAction = configs.get(index);
        if (!tmpAction.isEnabled()) {
            Log.d("isEnabled", "isActionRunnable: false " + tmpAction);
            return false;
        }
        if (!tmpAction.isValidDay()) {
            Log.d("isValidDay", "isActionRunnable: false" + tmpAction);
            return false;
        }
        if (tmpAction.isCoolDown()) {
            Log.d("isCoolDown", "isActionRunnable: false" + tmpAction);
            return false;
        }
        return true;
    }

    public void runCall() {
        startActivity(intentPhone);
    }

    public void runNotification() {
        NotificationCompat.Builder builderNotification =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setContentTitle(getResources().getText(R.string.notif_title))
                        .setContentText(getResources().getText(R.string.notif_text));

        PendingIntent pendingIntentPhone =
                PendingIntent.getActivity(this, 0, intentPhone, PendingIntent.FLAG_UPDATE_CURRENT);

        builderNotification.setContentIntent(pendingIntentPhone);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, builderNotification.build());
    }


    /**
     * Getting JSON config from server.
     */
    private class SettingAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnAction.setEnabled(false);
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "Empty JSON";
            try {
                String jsonResponse = HttpProvider.getInstance().getJsonConfig();
                Utils.saveToPref(jsonResponse, context);
                result = jsonResponse;
            } catch (IOException e) {
                e.printStackTrace();
                result = "Connection ERROR!";
            } catch (Exception e) {
                e.printStackTrace();
                result = e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            initConfigs();
            btnAction.setEnabled(true);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }
    }
}
