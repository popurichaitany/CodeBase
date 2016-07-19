package org.app.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Anand on 3/2/2016.
 */
public class autoBactiveStart extends BroadcastReceiver
    {
        public void onReceive(Context arg0, Intent arg1)
        {
            try {
                    Intent intent = new Intent(arg0, alarmSetter.class);
                    arg0.startService(intent);
                    Log.i("autoBactiveStart", "started successfully");
            }catch (Exception e){            }
        }
    }
