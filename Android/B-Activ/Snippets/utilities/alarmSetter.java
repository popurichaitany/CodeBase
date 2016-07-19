package org.app.utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.app.anand.b_activ.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

import java.util.Calendar;

/**
 * Created by Anand on 2/29/2016.
 */
public class alarmSetter extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient activityClientListener;
    String TAG = "Alarm Setter";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        try {
                MainActivity.alarmPref = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                String loggerAlarm = MainActivity.alarmPref.getString("com.example.b.activ.alarmSetOrNot", "alarmNotSet");
                Log.d(TAG, "Alarm state:" + loggerAlarm);
                long lastReboot = SystemClock.elapsedRealtime();

                if(lastReboot < 300000 && !loggerAlarm.equals("alarmReset")) {
                        Toast.makeText(this, "Attempting to Restart Activity Recognition ..", Toast.LENGTH_LONG).show();
                        buildActivityClient();
                        initActivityCapture();
                        Toast.makeText(this, "Hello....B-Activ!", Toast.LENGTH_LONG).show();
                    MainActivity.alarmPref.edit().putString("com.example.b.activ.alarmSetOrNot", "alarmReset").commit();
                } else {
                    SharedPreferences spMode = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                    String mode = spMode.getString("com.example.b.activ.mode", "Continuous");
                    Log.d(TAG, "Mode:" + mode);
                    buildActivityClient();
                    initActivityCapture();
                    MainActivity.alarmPref.edit().putString("com.example.b.activ.alarmSetOrNot","alarmSet").commit();
                }
        }catch (Exception e){
            try {
                    buildActivityClient();
                    initActivityCapture();
            }catch(Exception ec){}
        }
    }

    public void initActivityCapture(){
        try {
            Calendar calendar = Calendar.getInstance();

            Intent intent1 = new Intent(this, centralActivitySyncCaptureService.class);
            PendingIntent piLogging = PendingIntent.getService(this, 0, intent1, 0);
            AlarmManager alarm_manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm_manager.cancel(piLogging);
            alarm_manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 300000, piLogging);
        }catch(Exception e){}
    }

    private boolean isPlayServiceAvailable() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS;
    }

    private void buildActivityClient(){
        try {
            if (isPlayServiceAvailable()) {
                activityClientListener = new GoogleApiClient.Builder(this)
                        .addApi(ActivityRecognition.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();
                //Connect to Google API
                activityClientListener.connect();

            } else {
                Toast.makeText(this, "Google Play Service not Available", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){}
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
                Intent i = new Intent(this, userActivityRecognitionService.class);
                PendingIntent mActivityRecongPendingIntent = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

                Log.d(TAG, "connected to ActivityRecognition");
                SharedPreferences spMode = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                String mode = spMode.getString("com.example.b.activ.mode", "Continuous");
                Log.d(TAG, "Mode:" + mode);
                if(mode.equals("Continuous"))
                    ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(activityClientListener, 0, mActivityRecongPendingIntent);
                else
                    ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(activityClientListener, mActivityRecongPendingIntent);
        }catch(Exception ex){}
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Suspended to ActivityRecognition");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Not connected to ActivityRecognition");
    }
}