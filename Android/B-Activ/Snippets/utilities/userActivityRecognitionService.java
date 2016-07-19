package org.app.utility;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.app.DbMgmt.DataManip;
import org.app.anand.b_activ.MainActivity;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class userActivityRecognitionService extends IntentService {
    //LogCat
    private static final String TAG = userActivityRecognitionService.class.getSimpleName();
    public userActivityRecognitionService() {
        super("userActivityRecognitionIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
            if(ActivityRecognitionResult.hasResult(intent)) {

            String activity="";
            try {
                Calendar Now = Calendar.getInstance();
                int currentHour = Now.get(Calendar.HOUR_OF_DAY);
                if(currentHour > 4) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd h:mm:ss a");
                    String strDate = sdf.format(Now.getTime());
                    //Extract the result from the Response
                    ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
                    DetectedActivity detectedActivity = result.getMostProbableActivity();

                    //Get the Confidence and Name of Activity
                    int confidence = detectedActivity.getConfidence();
                    String mostProbableName = getActivityName(detectedActivity.getType());

                    if ((mostProbableName.equals("Tilting") && confidence > 50) || (mostProbableName.equals("Unknown") && confidence > 50)
                            || (!mostProbableName.equals("Tilting") && !mostProbableName.equals("Unknown"))) {
                        if (mostProbableName.equals("On Foot")) {
                            activity = exploreOnFoot(result);
                        } else
                            activity = mostProbableName;
                        DataManip dm = MainActivity.dm;
                        if (dm == null)
                            dm = new DataManip(this);

                        String insertActivity = "Insert into currentActivityLog(captureTime,activity) values('" + strDate + "','" + activity + "')";
                        dm.insert(insertActivity);
                    }
                }
            }catch (Exception e){
                Log.d(TAG,"Error in current activity insertion:"+e.getMessage());
            }
        }
    }

    private String exploreOnFoot(ActivityRecognitionResult r){
        int cntW=0,cntR=0;
        try {
            List<DetectedActivity> lDA = r.getProbableActivities();
            Iterator<DetectedActivity> itrDA = lDA.iterator();
            while (itrDA.hasNext()) {
                DetectedActivity da = itrDA.next();
                int actType = da.getType();
                switch (actType) {
                    case DetectedActivity.WALKING:
                        cntW++;
                        break;
                    case DetectedActivity.RUNNING:
                        cntR++;
                        break;
                }
            }
        }catch(Exception e){
            System.out.println("Decision error");
        }
        if(cntR>cntW)
            return "Running";
        else if(cntW>cntR)
            return "Walking";
        else
            return "On Foot";
    }
    //Get the activity name
    private String getActivityName(int type) {
        switch (type)
        {
            case DetectedActivity.IN_VEHICLE:
                return "In Vehicle";
            case DetectedActivity.ON_BICYCLE:
                return "On Bicycle";
            case DetectedActivity.ON_FOOT:
                return "On Foot";
            case DetectedActivity.WALKING:
                return "Walking";
            case DetectedActivity.STILL:
                return "Still";
            case DetectedActivity.TILTING:
                return "Tilting";
            case DetectedActivity.RUNNING:
                return "Running";
            case DetectedActivity.UNKNOWN:
                return "Unknown";
        }
        return "N/A";
    }
}

//Fire the intent with activity name & confidence
                /* Intent i = new Intent("ImActive");
                i.putExtra("activity", activity);

                //Send Broadcast to be listen in MainActivity
                this.sendBroadcast(i);*/
 /*if (mostProbableName.equals("On Foot") && confidence == 100)
                    activity = "Running";
                else if (mostProbableName.equals("On Foot") && confidence < 99)
                    activity = "Walking";*/
/*System.out.println("Size:"+lDA.size());
            System.out.println("\nactType:"+actType+"Confidence:"+da.getConfidence());
            System.out.println("System says walking");
                        if (da.getConfidence() > w)
                            w = da.getConfidence();
                        break;
                        System.out.println("System says running");
                        if (da.getConfidence() > n)
                            n = da.getConfidence();
                            i.putExtra("confidence", confidence);*/