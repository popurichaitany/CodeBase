package org.app.ExternalFeed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.app.DbMgmt.cloudDataSync;
import org.app.anand.b_activ.InitActivity;
import org.app.parsers.uvIndexJSONParser;
import org.app.web.HttpManager;
import org.app.web.RequestPackage;

/**
 * Created by Anand on 5/6/2016.
 */
public class uvIndexFeed {

    private String uvIndex="";
    private Context ctx;
    public uvIndexFeed(RequestPackage rp,Context context){
        this.ctx = context;
        new rtvUVIndex().execute(rp);
    }

    public String getUvIndex() {
        return this.uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

    class rtvUVIndex extends AsyncTask<RequestPackage,String,String> {

        @Override
        protected String doInBackground(RequestPackage... params) {
            try {
                String level = HttpManager.getData(params[0]);
                if(level != null) {
                    float currentUVIndex = uvIndexJSONParser.localUVIndex(level);
                    if(currentUVIndex != -1) {
                        String UvIndexValue = String.valueOf(currentUVIndex);
                        Log.d("UV Feed","String value of uv index:"+UvIndexValue);
                        SharedPreferences userDetails = ctx.getSharedPreferences("com.example.b.activ.userDetails", Context.MODE_PRIVATE);
                        String storedUvIndex = userDetails.getString("com.example.b.activ.userDetails.uvIndex", "NotAvailable");
                        Log.d("UV Feed","Stored value of uv index:"+storedUvIndex);
                        if (!storedUvIndex.equals(UvIndexValue)) {
                                InitActivity.spAuthentication = ctx.getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                                String username = InitActivity.spAuthentication.getString("com.example.b.activ.authentication.username", "NotAvailable");
                                String password = InitActivity.spAuthentication.getString("com.example.b.activ.authentication.password", "NotAvailable");
                                if (!username.equals("NotAvailable") && !password.equals("NotAvailable")) {
                                    Intent cloudUpdateUvIndex = new Intent(ctx, cloudDataSync.class);
                                    cloudUpdateUvIndex.putExtra("Flow", "pushLocalBroadCast");
                                    cloudUpdateUvIndex.putExtra("Username", username);
                                    cloudUpdateUvIndex.putExtra("Password", password);
                                    cloudUpdateUvIndex.putExtra("broadcastMessage", UvIndexValue);
                                    cloudUpdateUvIndex.putExtra("type", "UV Index");
                                    Log.d("UV Feed", "Forking out uv index upload with:" + UvIndexValue +", for UV Index");
                                    new cloudDataSync(cloudUpdateUvIndex, ctx);
                                    userDetails.edit().putString("com.example.b.activ.userDetails.uvIndex", UvIndexValue).commit();
                                }
                        }
                    }
                } else
                    Log.d("UV Feed", "Not Available");
            } catch (Exception e) { e.printStackTrace(); Log.d("UV Feed","error in pushing UV index");
            }
            return null;
        }
    }
}
