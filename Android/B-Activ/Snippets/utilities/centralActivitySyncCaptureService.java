package org.app.utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import org.app.DbMgmt.DataManip;
import org.app.DbMgmt.cloudDataSync;
import org.app.DbMgmt.dbSyncAndRestoreService;
import org.app.anand.b_activ.Configurations;
import org.app.anand.b_activ.InitActivity;
import org.app.anand.b_activ.MainActivity;
import org.app.model.Notification;
import org.app.model.dailyActivity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class centralActivitySyncCaptureService extends Service {

    public static SharedPreferences act;
    private String startStamp = "", endStamp = "",action="";
    int running = 0, walking = 0, onBicycle = 0, still = 0, tilting = 0, inVehicle = 0, unknown = 0, rowCount = 0,dataPopulated=0,count=0,goAhead = 0,dateChanged = 0;
    long hours=0,quarters=0, units =0,diff =0,installed=0;
    SQLiteDatabase sdb=null;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    public centralActivitySyncCaptureService() {    }

    public static SharedPreferences getAct() {  return act;  }

    private void init(){
        action="";
        running = 0; walking = 0; onBicycle = 0; still = 0; tilting = 0; inVehicle = 0; unknown = 0; rowCount = 0; dataPopulated=0; count=0; goAhead = 0; dateChanged = 0;hours=0;quarters=0;
        units =0;installed=0;diff=0;
    }

    private boolean isOnline() {
        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        } else
            return false;
    }

    private void setStamp(String refStamp, int lapse) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd h:mm:ss a");
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(sdf.parse(refStamp));
                cal.add(Calendar.MINUTE, lapse);
            } catch (ParseException pe) {
            }
            if(lapse > 0) {
                startStamp = refStamp;
                endStamp = sdf.format(cal.getTime());
            } else {
                endStamp = refStamp;
                startStamp = sdf.format(cal.getTime());
            }
        }catch (Exception e){}
    }

    private String compareStamp(String d,String g){
        SimpleDateFormat compSdf = new SimpleDateFormat("yyyy-MM-dd h:mm:ss a");
        try {
            Date d1 = compSdf.parse(d);
            Date d2 = compSdf.parse(g);
            if (d1.compareTo(d2) >= 0)
                return d;
            else
                return g;
        }catch (ParseException pe){

        }
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar Now = Calendar.getInstance();
        int currentHour = Now.get(Calendar.HOUR_OF_DAY);

        if (currentHour < 5)
            return START_STICKY;

            init();
            int currentMinutes = Now.get(Calendar.MINUTE);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String strDate = sdf.format(Now.getTime());
            act = getApplicationContext().getSharedPreferences("com.example.b.activ.logger", Context.MODE_PRIVATE);

            DataManip dm = new DataManip(getApplicationContext());
            SharedPreferences spMain = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
            String lastUpload = spMain.getString("com.example.b.activ.lastUpload", "NotAvailable");

            if (lastUpload.equals("NotAvailable")) {
                dateChanged = 1;
            } else {
                try {
                    Date d1 = sdf.parse(strDate);
                    Date d2 = sdf.parse(lastUpload);
                    if (d1.compareTo(d2) == 0)
                        dateChanged = 0;
                    else
                        dateChanged = 1;
                } catch (ParseException pe) {
                }
            }

            try {
                PackageManager pm = getPackageManager();
                ApplicationInfo appInfo = pm.getApplicationInfo("org.app.anand.b_activ", 0);
                String appFile = appInfo.sourceDir;
                installed = new File(appFile).lastModified();
                long now = Calendar.getInstance().getTimeInMillis();
                diff = now - installed;
                hours = diff / 3600000;
                quarters = diff / 900000;

                if (dateChanged == 1) {
                    if (diff > 120000) {
                        if (isOnline()) {
                            InitActivity.spAuthentication = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                            String username = InitActivity.spAuthentication.getString("com.example.b.activ.authentication.username", "NotAvailable");
                            String password = InitActivity.spAuthentication.getString("com.example.b.activ.authentication.password", "NotAvailable");
                            if (!username.equals("NotAvailable") && !password.equals("NotAvailable")) {
                                Intent cloudGetKey = new Intent(centralActivitySyncCaptureService.this, cloudDataSync.class);
                                cloudGetKey.putExtra("Flow", "getDailyKey");
                                cloudGetKey.putExtra("Username", username);
                                cloudGetKey.putExtra("Password", password);
                                new cloudDataSync(cloudGetKey, this);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            SharedPreferences spMode = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
            String mode = spMode.getString("com.example.b.activ.mode", "Continuous");
            if(mode.equals("Continuous")) {
                getActivityCounts(Now, dm);

                if (dataPopulated == 1) {
                    try {
                        sdb = dm.getWritableDatabase();
                        sdb.execSQL("Delete from currentActivityLog");
                    } catch (SQLException se) {
                    } catch (Exception e) {
                    } finally {
                        try {
                            sdb.close();
                        } catch (Exception x) {
                        }
                    }
                    try {
                            getLastUserActivity();
                            String updatedAct = "Last User Activity: " + action;

                        if (!action.equals("Tilting") && !action.equals("Unknown")) {// && startStamp.length() > 0 && endStamp.length() > 0) {
                            Configurations.userDetails = getApplicationContext().getSharedPreferences("com.example.b.activ.userDetails", Context.MODE_PRIVATE);
                            String insertActivity = "Insert into activityLog(startTime,endTime,lastActivity) values('" + startStamp + "','" + endStamp + "','" + action + "')";
                            dm.insert(insertActivity);
                            Log.d("Logger", "Stamp line captured:" + startStamp + "," + endStamp + "," + action);
                            logWriter l = new logWriter(this.getApplicationContext());
                            l.lastActivityLogger(startStamp + "," + endStamp + "," + action + "\n", l.getExtern(), this.getApplicationContext());

                            Configurations.userDetails = getApplicationContext().getSharedPreferences("com.example.b.activ.userDetails", Context.MODE_PRIVATE);
                            float userBMR = Configurations.userDetails.getFloat("com.example.b.activ.userDetails.BMR", 2000.0f);
                            if (action.equals("Walking") || action.equals("Running") || action.equals("On Bicycle")) {
                                float MET = 0;
                                String currActivity = "";

                                if (action.equals("Walking")) {
                                    currActivity = "Walking";
                                    MET = 2.5f;
                                } else if (action.equals("Running")) {
                                    currActivity = "Running";
                                    MET = 7.5f;
                                } else if (action.equals("On Bicycle")) {
                                    currActivity = "Bicycling";
                                    MET = 3.5f;
                                }
                                updateActivityDetails(currActivity, MET, strDate, userBMR);
                            }
                            pushCurrentActivityLevel(strDate, userBMR);
                        }

                        act.edit().putString("com.example.b.activ.logger.lastAct", updatedAct).commit();



                        Calendar calendar = Calendar.getInstance();
                        Intent intent3 = new Intent(this, userActivityRecognitionService.class);
                        PendingIntent piRecog = PendingIntent.getService(this, 0, intent3, 0);
                        AlarmManager alarm_mngr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarm_mngr.cancel(piRecog);
                        alarm_mngr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 300000, piRecog);

                        if (goAhead == 2)
                            MainActivity.alarmPref.edit().putString("com.example.b.activ.alarmSetOrNot", "alarmSet").commit();
                    } catch (ArrayIndexOutOfBoundsException aib) {
                    } catch (NullPointerException npe) {
                    } catch (Exception e) {
                    }
                } else {
                    try {
                        act.edit().putString("com.example.b.activ.logger.lastAct", "Last User Activity:Unknown").commit();
                    } catch (Exception e) {
                    }
                }
            } else {
                try {
                    sdb = dm.getWritableDatabase();
                    sdb.execSQL("Delete from currentActivityLog");
                } catch (SQLException se) {
                } catch (Exception e) {
                } finally {
                    try {
                        sdb.close();
                    } catch (Exception x) {
                    }
                }
                act.edit().putString("com.example.b.activ.logger.lastAct", "Last User Activity:Unknown").commit();
            }
            try {
                long hoursPassed = act.getLong("com.example.b.activ.logger.hours",0);
                long quartersPassed = act.getLong("com.example.b.activ.logger.quarters",0);
                if(hoursPassed<hours) {
                    Intent dbIntent = new Intent(this, dbSyncAndRestoreService.class);
                    startService(dbIntent);
                    act.edit().putLong("com.example.b.activ.logger.hours",hours).commit();
                }

                if(quartersPassed<quarters){
                    Log.d("Logger", "Stored quarters:" + quartersPassed);
                    act.edit().putLong("com.example.b.activ.logger.quarters", quarters).commit();
                    act.edit().putString("com.example.b.activ.logger.pollutionFetch", "Yes").commit();
                    act.edit().putString("com.example.b.activ.logger.trafficFetch","Yes").commit();
                    act.edit().putString("com.example.b.activ.logger.pollutantFetch","Yes").commit();
                    if(isOnline()) {
                        Log.d("Logger","Attempting to invoke refresh broadcast");
                        InitActivity.spAuthentication = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                        String username = InitActivity.spAuthentication.getString("com.example.b.activ.authentication.username", "NotAvailable");
                        String password = InitActivity.spAuthentication.getString("com.example.b.activ.authentication.password", "NotAvailable");
                        if (!username.equals("NotAvailable") && !password.equals("NotAvailable")) {
                            Intent cloudUpdateUvIndex = new Intent(getApplicationContext(), cloudDataSync.class);
                            cloudUpdateUvIndex.putExtra("Flow", "pushLocalBroadCast");
                            cloudUpdateUvIndex.putExtra("Username", username);
                            cloudUpdateUvIndex.putExtra("Password", password);
                            cloudUpdateUvIndex.putExtra("broadcastMessage", "NotAvailable");
                            cloudUpdateUvIndex.putExtra("type", "Refresh");
                            new cloudDataSync(cloudUpdateUvIndex, this);
                        }
                    }
                } else {
                    act.edit().putString("com.example.b.activ.logger.pollutionFetch", "No").commit();
                    act.edit().putString("com.example.b.activ.logger.trafficFetch","No").commit();
                    act.edit().putString("com.example.b.activ.logger.pollutantFetch","No").commit();
                }
            }catch(Exception e){}
            try {
                Calendar Today = Calendar.getInstance();
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                String today = sdf2.format(Today.getTime());
                Date dateToday = sdf2.parse(today);

                String slot1, slot2, slot3,summary;
                SharedPreferences notifications = getApplicationContext().getSharedPreferences("com.example.b.activ.notifications", Context.MODE_PRIVATE);
                slot1 = notifications.getString("com.example.b.activ.notifications.slot1", "NotAvailable");
                slot2 = notifications.getString("com.example.b.activ.notifications.slot2", "NotAvailable");
                slot3 = notifications.getString("com.example.b.activ.notifications.slot3", "NotAvailable");
                summary = notifications.getString("com.example.b.activ.notifications.dailySummary", "NotAvailable");

                if (slot1.equals("NotAvailable")) {
                    if (currentHour == 5 && currentMinutes >= 30) {
                        checkForExerciseSlot("05:30:00", "06:00:00", "06:00:00-09:00:00");
                        Log.d("Logger", "slot1 status:" + slot1);
                    }
                } else{
                    Date dateSlot1 = sdf2.parse(slot1);
                    if(dateToday.compareTo(dateSlot1) > 0){
                        if (currentHour == 5 && currentMinutes >= 30) {
                            checkForExerciseSlot("05:30:00", "06:00:00", "06:00:00-09:00:00");
                            Log.d("Logger", "slot1 status:" + slot1);
                        }
                    }
                }
                if (slot2.equals("NotAvailable")) {
                    if (currentHour == 14 && currentMinutes >= 30) {
                        checkForExerciseSlot("14:30:00", "15:00:00", "15:00:00-18:00:00");
                        Log.d("Logger", "slot2 status:" + slot2);
                    }
                } else{
                    Date dateSlot2 = sdf2.parse(slot2);
                    if(dateToday.compareTo(dateSlot2) > 0){
                        if (currentHour == 14 && currentMinutes >= 30) {
                            checkForExerciseSlot("14:30:00", "15:00:00", "15:00:00-18:00:00");
                            Log.d("Logger", "slot2 status:" + slot2);
                        }
                    }
                }
                if (slot3.equals("NotAvailable")) {
                    if (currentHour == 17 && currentMinutes >= 30) {
                        checkForExerciseSlot("17:30:00", "18:00:00", "18:00:00-21:00:00");
                        Log.d("Logger", "slot3 status:" + slot3);
                    }
                } else{
                    Date dateSlot3 = sdf2.parse(slot3);
                    if(dateToday.compareTo(dateSlot3) > 0){
                        if (currentHour == 17 && currentMinutes >= 30) {
                            checkForExerciseSlot("17:30:00", "18:00:00", "18:00:00-21:00:00");
                            Log.d("Logger", "slot3 status:" + slot3);
                        }
                    }
                }
                if(currentHour > 22 && summary.equals("NotAvailable")){
                    checkForDailyActivityLevel(today);
                    Log.d("Logger", "summary status:" + summary);
                } else if(!summary.equals("NotAvailable") && currentHour > 22) {
                        Date dateSummary = sdf2.parse(summary);
                        if (dateToday.compareTo(dateSummary) > 0) {
                            checkForDailyActivityLevel(today);
                            Log.d("Logger", "summary status:" + summary);
                        }
                }
            }catch(Exception e){
                Log.d("Logger","Error in pre conditioning of exercise slot");
                e.printStackTrace();
            }

        return START_STICKY;
    }

    private void getActivityCounts(Calendar Now,DataManip dm){
        String endTime="",current ="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd h:mm:ss a");
        current = sdf.format(Now.getTime());
        sdb = dm.getWritableDatabase();
        try{
            Cursor endCursor = sdb.rawQuery("SELECT endTime FROM activityLog;", null);
            count = endCursor.getCount();
            if (count > 0) {
                endCursor.moveToLast();
                endTime = endCursor.getString(0);
                this.setStamp(endTime, 4);
                String result = compareStamp(endStamp, current);
                if (result.equals(current)) {
                    goAhead = 2;
                    this.setStamp(current, -5);
                } else
                    goAhead = 0;
            } else {
                goAhead = 1;
                this.setStamp(current, -5);
            }
            act.edit().putString("com.example.b.activ.logger.lastAct", "Last User Activity: Processing...").commit();
        }catch (CursorIndexOutOfBoundsException ci){Log.d("Logger", "Cursor error");
        }catch(SQLException se){
        }catch (Exception e){ Log.d("Logger","Manipulation block exception:"+e.getMessage()); e.printStackTrace();
        }finally {
            try{
                sdb.close();
            }catch(Exception e){ }
        }
        if(goAhead > 0) {
            try {
                sdb = dm.getReadableDatabase();
                Cursor activityCursor = sdb.rawQuery("SELECT captureTime,activity FROM currentActivityLog;", null);
                int rows = activityCursor.getCount();
                if (rows > 0) {
                    dataPopulated = 1;
                    activityCursor.moveToFirst();
                    do {
                        String act = activityCursor.getString(1);//String capture = activityCursor.getString(0);
                        switch (act) {
                            case "In Vehicle":
                                inVehicle++;
                                break;
                            case "On Bicycle":
                                onBicycle++;
                                break;
                            case "Running":
                                running++;
                                break;
                            case "Walking":
                                walking++;
                                break;
                            case "Tilting":
                                tilting++;
                                break;
                            case "Still":
                                still++;
                                break;
                            case "Unknown":
                                unknown++;
                                break;
                        }
                    } while (activityCursor.moveToNext());
                }
            } catch (CursorIndexOutOfBoundsException ci) {
            } catch (SQLException se) {
            } catch (Exception e) {
            } finally {
                try {
                    sdb.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private void getLastUserActivity(){
        try {
            String possibleActivities[] = {"Walking", "Running", "On Bicycle", "In Vehicle", "Tilting", "Still", "Unknown"};
            int activityCount[] = {walking, running, onBicycle, inVehicle, tilting, still, unknown};
            int observe = -1, index = -1;
            action = "Unknown";
            for (int i = 0; i < activityCount.length; i++) {
                if (activityCount[i] > observe) {
                    observe = activityCount[i];
                    index = i;
                }
            }
            action = possibleActivities[index];
        }catch(Exception ex){}
    }

    private void updateActivityDetails(String activity,float MET,String today,float BMR) {
        String cloudActivity = activity;
        activity = activity.toLowerCase();
        ContentValues cv = new ContentValues();
        DataManip dm = new DataManip(getApplicationContext());
        sdb = dm.getReadableDatabase();
        int mins = 0, calories = 0, updatedCalories = 0;
        String dbKey = "";
        act = getApplicationContext().getSharedPreferences("com.example.b.activ.logger", Context.MODE_PRIVATE);
        String dailyKey = act.getString("com.example.b.activ.logger.dailyPushKey", "NA");
        Log.d("Logger", "Today sp key:"+dailyKey);
            try {
                Log.d("Logger", "Retrieving key for:" + today);
                sdb = dm.getReadableDatabase();
                Cursor c = sdb.rawQuery("Select cloudKey from dailyActivityTracker where activityDate='" + today + "'", null);
                if (c != null) {
                    if(c.getCount() > 0) {
                        c.moveToFirst();
                        dbKey = c.getString(0);
                        Log.d("Logger", "Today db key:" + dbKey);
                    }
                }
                if (!dbKey.equals(dailyKey) && !dailyKey.equals("NA")) {
                    ContentValues cvKey = new ContentValues();
                    cvKey.put("cloudKey", dailyKey);
                    dm.update("dailyActivityTracker", cvKey, "activityDate"+" = '"+today+"'");

                    Cursor cursor = sdb.rawQuery("Select cloudKey from dailyActivityTracker where activityDate='" + today + "'", null);
                    if (cursor != null) {
                        int rows = cursor.getCount();
                        if (rows == 1) {
                            dbKey = c.getString(0);
                            if(dbKey.equals(dailyKey))
                                Log.d("Logger", "update successful db key:" + dbKey);
                        }
                    }
                } else if (!dbKey.equals(dailyKey) && dbKey != null && dailyKey.equals("NA")) {
                    if(!dbKey.equals("")) {
                        act.edit().putString("com.example.b.activ.logger.dailyPushKey", dbKey).commit();
                        dailyKey = dbKey;
                    }
                }
            } catch (Exception selEx) {
            } finally {
                try {
                    sdb.close();
                } catch (Exception ex) {
                }
            }
            if (dailyKey.equals(dbKey)) {
                cv.put("uploaded", 0);
                try {
                    sdb = dm.getReadableDatabase();
                    Cursor c = sdb.rawQuery("Select '" + activity + "' ,CaloriesBurnt from dailyActivityTracker where activityDate='" + today + "'", null);
                    if(c!=null) {
                        c.moveToFirst();
                        if(c.getCount() > 0) {
                            mins = c.getInt(0);
                            mins = mins + 5;
                            calories = c.getInt(1);
                            updatedCalories = (int) ((BMR * MET) / (24 * 12));
                            updatedCalories = calories + updatedCalories;

                            cv.put(activity, mins); //These Fields should be your String values of actual column names
                            cv.put("CaloriesBurnt", updatedCalories);
                        }
                    }
                } catch (Exception exp) {
                } finally {
                    sdb.close();
                }
            } else {
                try {
                    Log.d("Logger", "Daily Key not saved/mismatched reference :/");
                    dailyActivity da = new dailyActivity();
                    switch (activity) {
                        case "walking":
                            mins = da.getWalkingMinutes();
                            break;
                        case "running":
                            mins = da.getRunningMinutes();
                            break;
                        case "bicycling":
                            mins = da.getBicyclingMinutes();
                            break;
                    }

                    mins = mins + 5;
                    updatedCalories = (int) ((BMR * MET * mins) / (24 * 60));
                    cv.put("uploaded", 0);
                    cv.put(activity, mins); //These Fields should be your String values of actual column names

                }catch(Exception e){e.printStackTrace();}
            }
            int r = dm.update("dailyActivityTracker", cv, "activityDate" + " = '" + today + "'");

            if(isOnline()) {
                InitActivity.spAuthentication = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                String username = InitActivity.spAuthentication.getString("com.example.b.activ.authentication.username", "NotAvailable");
                String password = InitActivity.spAuthentication.getString("com.example.b.activ.authentication.password", "NotAvailable");
                if (!username.equals("NotAvailable") && !password.equals("NotAvailable") && !dailyKey.equals("NA")) {
                    Intent cloudUpdateMins = new Intent(centralActivitySyncCaptureService.this, cloudDataSync.class);
                    cloudUpdateMins.putExtra("Flow", "updateMins");
                    cloudUpdateMins.putExtra("Username", username);
                    cloudUpdateMins.putExtra("Password", password);
                    cloudUpdateMins.putExtra("Key", dailyKey);
                    cloudUpdateMins.putExtra("Activity", cloudActivity);
                    cloudUpdateMins.putExtra("Minutes", "" + mins);
                    cloudUpdateMins.putExtra("Calories", "" + updatedCalories);
                    new cloudDataSync(cloudUpdateMins, this);
                } else
                    Log.d("Logger", "User credentials missing :/");
            }
    }

    private synchronized  void checkForExerciseSlot(String preExecuteStamp,String exerciseSlot,String slotWindow){
        try {
            int dailySlot=0;
            String title="B-Activ Session",slotMessage="";
            SharedPreferences notifications = getApplicationContext().getSharedPreferences("com.example.b.activ.notifications", Context.MODE_PRIVATE);
            String slot = "",slotPreference = "";
            Log.d("Logger","Initiating slot check");
            switch(preExecuteStamp){
                case "05:30:00":slotPreference ="com.example.b.activ.notifications.slot1";
                    break;
                case "14:30:00":slotPreference ="com.example.b.activ.notifications.slot2";
                    break;
                case "17:30:00":slotPreference ="com.example.b.activ.notifications.slot3";
                    break;
            }
            Log.d("Logger","current slot:"+slotPreference);
            slot = notifications.getString(slotPreference, "NotAvailable");
            Log.d("Logger","Last time this slot explored:"+slot);
            Calendar Today = Calendar.getInstance();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String strSlotDate = sdf1.format(Today.getTime());
            Log.d("Logger","Today is:"+strSlotDate);
            if(slot.equals("NotAvailable")) {
                dailySlot =1;
            } else{
                Date daily = sdf1.parse(slot);
                Date today = sdf1.parse(strSlotDate);
                if(today.compareTo(daily) > 0) {
                    dailySlot = 1;
                    Log.d("Logger", "Need to re check for current slot");
                }
            }

            if(dailySlot==1){
                Log.d("Logger","Slot need to be explored");
                String compareStamp = strSlotDate +" "+ preExecuteStamp;
                double high,low;
                Calendar Now = Calendar.getInstance();
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strNow = sdf2.format(Now.getTime());

                Date d1 = sdf2.parse(compareStamp);
                Date d2 = sdf2.parse(strNow);
                if (d2.compareTo(d1) > 0) {
                    notifications.edit().putString(slotPreference,strSlotDate).commit();
                    String rtvTodaySlots = "Select condition,high,low from weeklyForecast where forecastDate='" + strSlotDate +"' and slotStart='"+exerciseSlot+"'";
                    DataManip dm = new DataManip(getApplicationContext());
                    sdb = dm.getReadableDatabase();
                    Cursor slotCursor = sdb.rawQuery(rtvTodaySlots, null);
                    if(slotCursor != null){
                        if(slotCursor.getCount() > 0){
                            slotCursor.moveToFirst();
                            String condition = slotCursor.getString(0);
                            high = Double.parseDouble(slotCursor.getString(1));
                            low = Double.parseDouble(slotCursor.getString(2));
                            if(condition.equals("clear sky") || condition.equals("few clouds") || condition.equals("clouds") || condition.equals("overcast clouds") || condition.equals("scattered clouds")){
                                if (high < 27.1 && low > 14.9) {
                                    slotMessage = strSlotDate + " @ " + slotWindow + " is upcoming slot for outdoor exercise such as walking,running or bicycling.B-Activ!";
                                }
                            }
                            Log.d("Logger", "Prompt for current slot:" + slotMessage);
                            if(slotMessage.length()>0) {
                                Log.d("Logger","Attempting to invoke notification");
                                Intent notifyIntent = new Intent(this, activityNotificationManager.class);
                                Notification notification = new Notification();
                                notification.setMessage(slotMessage);
                                notification.setTitle(title);
                                activityNotificationManager.setNotification(notification);
                                startService(notifyIntent);
                            }
                        }
                    } else
                        Log.d("Logger","Slot cursor is null :/");
                }
            }
        }catch(Exception ex){
        }
    }

    private void checkForDailyActivityLevel(String today){
        try {
            Log.d("Logger","Inside summary function");
            int activityScore = 0,walkMins=0,runMins=0,bicyleMins=0,dbRun=0,dbWalk=0,dbBicycle=0;
            dailyActivity da = new dailyActivity();
            if(da != null) {
                walkMins = da.getWalkingMinutes()*5; runMins = da.getRunningMinutes()*5; bicyleMins = da.getBicyclingMinutes()*5;
            }
            if (walkMins == 0 && runMins == 0 && bicyleMins == 0) {
                Intent notifyIntent = new Intent(this, activityNotificationManager.class);
                Notification notification = new Notification();
                notification.setMessage("You have zero activity level for today, please try and indulge in active life(walking/running/bicycling) to avoid obesity and cholesterol related diseases. B-Activ!");
                notification.setTitle("B-Activ : Activity Level Warning!");
                activityNotificationManager.setNotification(notification);
                startService(notifyIntent);
            } else {
                Calendar Now = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String strDate = sdf.format(Now.getTime());
                DataManip dm = new DataManip(getApplicationContext());
                Cursor cursor = dm.select("Select walking,running,bicycling from dailyActivityTracker where activityDate='" + strDate + "'");
                if(cursor != null){
                    cursor.moveToFirst();
                    if(cursor.getCount()>0){
                        dbWalk = cursor.getInt(0);
                        dbRun = cursor.getInt(1);
                        dbBicycle = cursor.getInt(2);
                    }
                }
                if(dbWalk>walkMins)
                    walkMins =dbWalk;
                if(dbRun>runMins)
                    runMins = dbRun;
                if(dbBicycle>bicyleMins)
                    bicyleMins = dbBicycle;
                SharedPreferences configurations = getApplicationContext().getSharedPreferences("com.example.b.activ.userDetails", Context.MODE_PRIVATE);
                String userBMI = configurations.getString("com.example.b.activ.userDetails.BMI", "NotAvailable");
                switch(userBMI) {
                    case "Underweight":
                        if (walkMins > 0)
                            activityScore += walkMins * 10;
                        if (runMins > 0)
                            activityScore += runMins * 25;
                        if (bicyleMins > 0)
                            activityScore += bicyleMins * 20;
                    case "Average":
                        if (walkMins > 0)
                            activityScore += walkMins * 4;
                        if (runMins > 0)
                            activityScore += runMins * 6.66;
                        if (bicyleMins > 0)
                            activityScore += bicyleMins * 5;
                    case "Overweight":
                        if (walkMins > 0)
                            activityScore += walkMins * 2.5;
                        if (runMins > 0)
                            activityScore += runMins * 4;
                        if (bicyleMins > 0)
                            activityScore += bicyleMins * 3.33;
                    case "Obese":
                        if (walkMins > 0)
                            activityScore += walkMins * 1.66;
                        if (runMins > 0)
                            activityScore += runMins * 3.33;
                        if (bicyleMins > 0)
                            activityScore += bicyleMins * 2.23;
                    case "NotAvailable":
                        if (walkMins > 0)
                            activityScore += walkMins * 4;
                        if (runMins > 0)
                            activityScore += runMins * 6.66;
                        if (bicyleMins > 0)
                            activityScore += bicyleMins * 5;

                }


                Log.d("Logger","Activity score:"+activityScore);
                if (activityScore < 100) {
                    Intent notifyIntent = new Intent(this, activityNotificationManager.class);
                    Notification notification = new Notification();
                    notification.setMessage("You have low activity level for today, please try and indulge in active life(walking/running/bicycling)  to avoid obesity and cholesterol related diseases. B-Activ!");
                    notification.setTitle("B-Activ : Activity Level Warning!");
                    activityNotificationManager.setNotification(notification);
                    startService(notifyIntent);
                }
            }
            SharedPreferences notifications = getApplicationContext().getSharedPreferences("com.example.b.activ.notifications", Context.MODE_PRIVATE);
            notifications.edit().putString("com.example.b.activ.notifications.dailySummary",today).commit();
        }catch(Exception e){e.printStackTrace();}
    }

    private void pushCurrentActivityLevel(String today,float BMR){
        DataManip dm = new DataManip(getApplicationContext());
        sdb = dm.getReadableDatabase();
        dailyActivity da = new dailyActivity();
        int Walk,Run,Bicycle,dbWalk=0,dbRun=0,dbBicycle=0,Calories,walkMins = da.getWalkingMinutes(), runMins = da.getRunningMinutes(), bicyleMins = da.getBicyclingMinutes();
        walkMins = walkMins * 5; runMins = runMins * 5; bicyleMins = bicyleMins * 5;
        Calendar Now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = sdf.format(Now.getTime());
        Cursor cursor = dm.select("Select walking,running,bicycling from dailyActivityTracker where activityDate='" + strDate + "'");
        if(cursor != null){
            cursor.moveToFirst();
            if(cursor.getCount()>0){
                dbWalk = cursor.getInt(0);
                dbRun = cursor.getInt(1);
                dbBicycle = cursor.getInt(2);
            }
        }
        if(walkMins<dbWalk)
            walkMins=dbWalk;
        if(runMins < dbRun)
            runMins=dbRun;
        if(bicyleMins < dbBicycle)
            bicyleMins=dbBicycle;
        try{
            Cursor cloudSyncCursor = sdb.rawQuery("SELECT walking,running,bicycling,CaloriesBurnt FROM dailyActivityTracker where activityDate = '"+today+"'", null);
            if (cloudSyncCursor != null) {
                    cloudSyncCursor.moveToFirst();
                    if(cloudSyncCursor.getCount() > 0){
                        Walk = cloudSyncCursor.getInt(0);
                        Run = cloudSyncCursor.getInt(1);
                        Bicycle = cloudSyncCursor.getInt(2);
                        Calories = cloudSyncCursor.getInt(3);

                        if(walkMins!=Walk || runMins!=Run || bicyleMins!=Bicycle){
                            int w,r,b,updatedCalories=0;
                            ContentValues cvUserAct = new ContentValues();
                            if(walkMins>Walk)
                                w = walkMins;
                            else
                                w = Walk;
                            if(runMins>Run)
                                r = runMins;
                            else
                                r = Run;
                            if(bicyleMins>Bicycle)
                                b = bicyleMins;
                            else
                                b = Bicycle;
                            cvUserAct.put("walking",w);
                            cvUserAct.put("running",r);
                            cvUserAct.put("bicycling",b);
                            cvUserAct.put("uploaded",0);
                            if(w>0)
                                updatedCalories = (int) ((BMR * 2.5 * w) / (24 * 60));
                            if(r>0)
                                updatedCalories = updatedCalories + (int) ((BMR * 7.5 * r) / (24 * 60));
                            if(b>0)
                                updatedCalories = updatedCalories + (int) ((BMR * 3.5 * b) / (24 * 60));
                            if(updatedCalories > Calories)
                                cvUserAct.put("CaloriesBurnt",updatedCalories);
                            dm.update("dailyActivityTracker", cvUserAct, "activityDate"+" = '"+today+"'");
                        }
                    }
                }
        }catch(Exception e){
            try{
                sdb.close();
            }catch(Exception ex){}
        }
    }
}
 /*
Now = Calendar.getInstance();
}if(goAhead == 2) {
                            if (rowCount == 0 && count > 0) {
                                startStamp = compareStamp(capture, startStamp);
                                if (startStamp != null)
                                    this.setStamp(startStamp, 5);
                                else
                                    this.setStamp(capture, 5);
                            } else if (rowCount == 0 && count == 0)
                                this.setStamp(capture, 5);
                        }
                        rowCount++;*/
          /*String endTime="",currentEnd ="";
        try {
            Cursor compareCursor = sdb.rawQuery("Select captureTime FROM currentActivityLog;", null);
            count = compareCursor.getCount();
            if(count > 0) {
                boolean moved = compareCursor.moveToLast();
                if (moved) {
                    currentEnd = compareCursor.getString(0);
                    System.out.println("Current End:"+currentEnd);
                }
                count = 0;
                Cursor endCursor = sdb.rawQuery("SELECT endTime FROM activityLog;", null);
                count = endCursor.getCount();

                if (count > 0) {
                    if(count % 12 == 0)
                        dbRestoreFlag = 1;
                    endCursor.moveToLast();
                    endTime = endCursor.getString(0);//startStamp = endCursor.getString(0);
                    System.out.println("Last End Stamp:"+endTime);
                    this.setStamp(endTime, 5);
                    String result = compareStamp(endStamp, currentEnd);
                    if(result.equals(currentEnd)) {
                        goAhead = 1;
                        this.setStamp(currentEnd, -5);
                    } else
                        goAhead = 0;
                } else
                    goAhead = 2;
            } else
                goAhead = 2;
        if(goAhead > 0){*/
//} else
//System.out.println("Stamp line captured is not 5 mins");
/*MainActivity.dm;
        if(dm==null)
            dm
/*public static Thread UiUpdateBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    MainActivity.activityCapture a= (MainActivity.activityCapture) runnable;
                    a.init(uiComponent, value);
                    a.run();
                } catch(Exception e){
                    System.out.println("UI update failed:"+e.getMessage());
                } finally{

                }
            }
        };
        t.start();
        return t;
    }*/
/* boolean live = act.getBoolean("com.example.b.activ.logger.homeFlag",false);
                if(live){
                    System.out.println("Main activity is alive");
                    uiComponent="lastActivity";value=updatedAct;
                    centralActivitySyncCaptureService.UiUpdateBackgroundThread(new MainActivity().getAc());
                }*/
/*uiComponent = "lastActivity";    value = updatedAct;
/*public static Thread UiUpdateBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    MainActivity.activityCapture a= (MainActivity.activityCapture) runnable;
                    a.init(uiComponent, value);
                    a.run();
                } catch(Exception e){
                    System.out.println("UI update failed:"+e.getMessage());
                } finally{

                }
            }
        };
        t.start();
        return t;
    }
/*if (walking > running && walking > onBicycle && walking > inVehicle && walking > still && walking > tilting && walking > unknown) {
        action = "Walking";
        } else if (running > walking && running > onBicycle && running > inVehicle && running > still && running > tilting && running > unknown) {
        action = "Running";
        } else if (onBicycle > walking && onBicycle > running && onBicycle > inVehicle && onBicycle > still && onBicycle > tilting && onBicycle > unknown) {
        action = "On bicycle";
        } else if (inVehicle > walking && inVehicle > running && inVehicle > onBicycle && inVehicle > still && inVehicle > tilting && inVehicle > unknown) {
        action = "In vehicle";
        } else if (tilting > walking && tilting > onBicycle && tilting > inVehicle && tilting > still && tilting > running && tilting > unknown) {
        action = "Tilting";
        } else if (still > walking && still > onBicycle && still > inVehicle && still > unknown && still > running && still > tilting) {
        action = "Still";
        } else if (unknown > walking && unknown > onBicycle && unknown > inVehicle && unknown > still && unknown > running && unknown > tilting)
        action = "Unknown";
        else
        action = "Unknown";
/*
    /**
/*String activityList[] = MainActivity.broadcastText.split("\n");
        MainActivity.broadcastText="";
        uiComponent="textview";value="";

if (MainActivity.broadcastText != null) {
            if (MainActivity.broadcastText.length() > 0) {
                int running = 0, walking = 0, onBicycle = 0, still = 0, tilting = 0, inVehicle = 0, unknown = 0;
                String activityList[] = MainActivity.broadcastText.split("\n");
                MainActivity.activityCapture ac=new MainActivity().getAc();
                MainActivity.broadcastText="";
                uiComponent="textview";value="";
                centralActivitySyncCaptureService.UiUpdateBackgroundThread(ac);
                for (int itr = 0; itr < activityList.length; itr++) {
                    String lineLog = activityList[itr];
                    if (lineLog.contains(";")) {
                        System.out.println("LogLine:" + lineLog);
                        String LogString[] = lineLog.split(";");
                        final String displayAction = LogString[1];
                        switch (displayAction) {
                            case "In-Vehicle":
                                inVehicle++;
                                break;
                            case "On-Bicycle":
                                onBicycle++;
                                break;
                            case "Running":
                                running++;
                                break;
                            case "Walking":
                                walking++;
                                break;
                            case "Tilting":
                                tilting++;
                                break;
                            case "Still":
                                still++;
                                break;
                            case "Unknown":
                                unknown++;
                                break;
                        }
                    }
                }
                if (walking > running && walking > onBicycle && walking > inVehicle && walking > still && walking > tilting && walking > unknown) {
                    action = "Walking";
                } else if (running > walking && running > onBicycle && running > inVehicle && running > still && running > tilting && running > unknown) {
                    action = "Running";
                } else if (onBicycle > walking && onBicycle > running && onBicycle > inVehicle && onBicycle > still && onBicycle > tilting && onBicycle > unknown) {
                    action = "On bicycle";
                } else if (inVehicle > walking && inVehicle > running && inVehicle > onBicycle && inVehicle > still && inVehicle > tilting && inVehicle > unknown) {
                    action = "In vehicle";
                } else if (tilting > walking && tilting > onBicycle && tilting > inVehicle && tilting > still && tilting > running && tilting > unknown) {
                    action = "Tilting";
                } else if (still > walking && still > onBicycle && still > inVehicle && still > unknown && still > running && still > tilting) {
                    action = "Still";
                } else if (unknown > walking && unknown > onBicycle && unknown > inVehicle && unknown > still && unknown > running && unknown > tilting)
                    action = "Unknown";
                else
                    action="Unknown";
                String updatedAct = "Last User Activity:" + action;
                this.setCapturedAction(updatedAct);
                MainActivity.alarmPref.edit().putString("com.example.bactiv2.lastAct",updatedAct).commit();
                uiComponent="lastActivity";value=updatedAct;
                centralActivitySyncCaptureService.UiUpdateBackgroundThread(ac);
                if (startStamp.length() > 0 && endStamp.length() > 0)
                    l.lastActivityLogger("\n" + startStamp + "," + endStamp + "," + action);
                    for (int itr = 0; itr < activityList.length; itr++) {
                String lineLog = activityList[itr];
                if (lineLog.contains(";")) {
                    System.out.println("LogLine:" + lineLog);
                    String LogString[] = lineLog.split(";");
                    final String displayAction = LogString[1];*/
/*int itr=act.getInt("com.example.b.activ.logger.iteration", -1);
                if(itr==-1){
                    act.edit().putInt("com.example.b.activ.logger.iteration", 1).commit();
                } else{
                    itr=itr + 1;
                    act.edit().putInt("com.example.b.activ.logger.iteration", itr).commit();
                    int hour = itr % 12;
                    if(hour==0){
                        act.edit().putInt("com.example.b.activ.logger.iteration", 0).commit();
                        Intent intent2 = new Intent(this, dbSyncAndRestoreService.class);
                        this.startService(intent2);
                    }
                }*/