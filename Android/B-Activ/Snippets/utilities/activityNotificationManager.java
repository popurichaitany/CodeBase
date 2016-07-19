package org.app.utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.app.anand.b_activ.MainActivity;
import org.app.anand.b_activ.R;

import java.util.Calendar;

/**
 * Created by Anand on 5/6/2016.
 */
public class activityNotificationManager extends Service {

    private static String currentTitle, currentMessage;
    private static org.app.model.Notification notification;
    NotificationManager mNotificationManager;

    public static org.app.model.Notification getNotification() {
        return notification;
    }

    public static void setNotification(org.app.model.Notification notification) {
        activityNotificationManager.notification = notification;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {return null;}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            long realStamp = Calendar.getInstance().getTimeInMillis();
            long notificationID = realStamp % 10000;
            currentTitle = notification.getTitle();
            currentMessage = notification.getMessage();

            if(currentTitle != null && currentMessage != null) {
                if(currentTitle.length()>0 && currentMessage.length()>0) {
                    Notification.Builder builder = new Notification.Builder(getApplicationContext());
                    builder.setContentTitle(currentTitle)
                            .setContentText(currentMessage)
                            .setSmallIcon(R.drawable.b_activ)
                            .setAutoCancel(true)
                            .setSound(soundUri);
                    android.app.Notification n = new Notification.BigTextStyle(builder).bigText(currentMessage).build();//= nBuilder.build(); builder.setContentIntent(resultPendingIntent);
                    mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent resultIntent = new Intent(this, MainActivity.class);
                    PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(resultPendingIntent);
                    push(n,(int) notificationID);
                }
            }
        }catch(Exception exp){ Log.d("Notification","Notification Exception");
            exp.printStackTrace();}
        return super.onStartCommand(intent, flags, startId);
    }

    private synchronized void push(Notification n,int ID){
        mNotificationManager.notify(ID, n);
    }
}
