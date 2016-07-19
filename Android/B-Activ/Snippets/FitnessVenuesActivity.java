package org.app.anand.b_activ;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.app.Adaptor.currentForecastListAdaptor;
import org.app.Adaptor.hourlyForecastListAdaptor;
import org.app.Adaptor.venueExpandListAdaptor;
import org.app.DbMgmt.DataManip;
import org.app.DbMgmt.cloudDataSync;
import org.app.ExternalFeed.userAddress;
import org.app.model.Notification;
import org.app.model.fitnessCentre;
import org.app.model.forecastSlot;
import org.app.parsers.addressJSONParser;
import org.app.parsers.fitnessVenuesJSONParser;
import org.app.parsers.hourlyForecastJSONParser;
import org.app.utility.activityNotificationManager;
import org.app.web.HttpManager;
import org.app.web.LocaleDetails;
import org.app.web.RequestPackage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class FitnessVenuesActivity extends AppCompatActivity implements  GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    TextView textResponse,scheduleResponse;
    ProgressDialog dialog;
    ExpandableListView expListViewVenues,expListViewCurrentWeek;
    ArrayList<String> venueListDataHeader,weatherForecastDataHeader;
    venueExpandListAdaptor listAdaptor;
    currentForecastListAdaptor forecastListAdaptor;
    ListView currentForecast;
    BigDecimal Latitude,Longitude;
    String city="",state="",country="";
    int expand1 = 0,expand2 = 0,forecastFetch =0,venueFetch=0;
    double latitude,longitude;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.venueToolbar);
        setSupportActionBar(toolbar);

        venueListDataHeader = new ArrayList<>();
        venueListDataHeader.add("List of nearby Park");
        venueListDataHeader.add("List of nearby GYM");
        weatherForecastDataHeader = new ArrayList<>();
        weatherForecastDataHeader.add("Current Weather Forecast");

        textResponse = (TextView) findViewById(R.id.text_xml);
        scheduleResponse = (TextView) findViewById(R.id.text_schedule);
        expListViewVenues = (ExpandableListView) findViewById(R.id.venueList);
        expListViewCurrentWeek = (ExpandableListView) findViewById(R.id.weeklyForecastList);
        currentForecast = (ListView) findViewById(R.id.scheduleList);
        currentForecast.setScrollContainer(true);

        expListViewVenues.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                expand1 = 1;
                scheduleResponse.setVisibility(View.GONE);
                expListViewCurrentWeek.setVisibility(View.GONE);
                currentForecast.setVisibility(View.GONE);
            }
        });

        expListViewVenues.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                expand1 = 0;
                if(expand2==0)
                    scheduleResponse.setVisibility(View.VISIBLE);
                expListViewCurrentWeek.setVisibility(View.VISIBLE);
                currentForecast.setVisibility(View.VISIBLE);
            }
        });

        expListViewCurrentWeek.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                expand2 = 1;
                scheduleResponse.setVisibility(View.GONE);
                currentForecast.setVisibility(View.GONE);
            }
        });

        expListViewCurrentWeek.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener(){
            @Override
            public void onGroupCollapse(int groupPosition) {
                expand2 = 0;
                if(expand1==0)
                    scheduleResponse.setVisibility(View.VISIBLE);
                currentForecast.setVisibility(View.VISIBLE);
            }
        });

        this.setTitle("Fitness Venues & Exercise Slots");

        try {
            if(!isOnline())
                Toast.makeText(this, "Offline state!Unable to fetch nearby fitness venues.", Toast.LENGTH_LONG).show();
                getLocation();
        }catch(Exception e){}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_venues, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goto_home:
                Intent home = new Intent(this, MainActivity.class);
                startActivity(home);
                this.finish();
                break;
            case R.id.goto_info:
                Intent info = new Intent(this, AboutBactivActivity.class);
                startActivity(info);
                break;
            case R.id.action_settings:
                Intent settings = new Intent(this, Configurations.class);
                startActivity(settings);
                break;
            case R.id.goto_broadcast:
                Intent broadcast = new Intent(this, LocalBroadcast.class);
                startActivity(broadcast);
                break;
            case R.id.tutorialList:
                Intent tutorial = new Intent(this, TuotorialListActivity.class);
                startActivity(tutorial);
                break;
        }
        return true;
    }

    protected boolean isOnline() {
        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        } else
            return false;
    }

    private void getLocation(){
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                if (isPlayServiceAvailable()) {
                    googleApiClient = new GoogleApiClient.Builder(this)
                            .addApi(LocationServices.API)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .build();
                    //Connect to Google API
                    googleApiClient.connect();
                }
            } catch (Exception e) {

            }
        } else {
            if (locationAvailable()) {
                LocaleDetails ld = new LocaleDetails(this);
                latitude = ld.getLatitude();
                longitude = ld.getLongitude();
            } else {
                Toast.makeText(FitnessVenuesActivity.this, "Note : GPS is off, location details needed for accurate fitness venues data", Toast.LENGTH_LONG).show();
                InitActivity.spAuthentication = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                String permission = InitActivity.spAuthentication.getString("com.example.b.activ.permission", "No");
                if (permission.equals("Yes")) {
                    InitActivity.spAuthentication = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                    String ltd = InitActivity.spAuthentication.getString("com.example.b.activ.latitude", "NotAvailable");
                    String lng = InitActivity.spAuthentication.getString("com.example.b.activ.longitude", "NotAvailable");
                    if (!ltd.equals("NotAvailable") && !lng.equals("NotAvailable")) {
                        latitude = Double.parseDouble(ltd);
                        longitude = Double.parseDouble(lng);
                    } else {
                        Toast.makeText(FitnessVenuesActivity.this, "Error : GPS is off, location details needed for fitness venues data", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
            if(isOnline())
                rtvVenues();
            else
                rtvOfflineForecast();
        }
    }
    private void rtvVenues() {
        String venue="Gym",venue2="park",clientID=getResources().getString(R.string.fourSApiCID),clientSecret=getResources().getString(R.string.fourSApiCS);
        String URI=getResources().getString(R.string.fourSApiLink),intent="browse",radius="2500",ll="";

        try {
                venueFetch++;

                if (!Double.isNaN(latitude) && !Double.isNaN(longitude) && latitude != longitude) {
                    ll = "" + latitude + "," + longitude + "";
                    Log.d("Fitness", "Location:" + ll);
                    Calendar Now = Calendar.getInstance();
                    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd");
                    String strDate = sdfNow.format(Now.getTime());

                    RequestPackage rpVenue = new RequestPackage();
                    rpVenue.setMethod("GET");
                    rpVenue.setUri(URI);
                    rpVenue.setParam("client_id", clientID);
                    rpVenue.setParam("client_secret", clientSecret);
                    rpVenue.setParam("v", strDate);
                    rpVenue.setParam("ll", ll);
                    rpVenue.setParam("query", venue2);
                    rpVenue.setParam("intent", intent);
                    rpVenue.setParam("radius", radius);

                    RequestPackage rpVenue2 = new RequestPackage();
                    rpVenue2.setMethod("GET");
                    rpVenue2.setUri(URI);
                    rpVenue2.setParam("client_id", clientID);
                    rpVenue2.setParam("client_secret", clientSecret);
                    rpVenue2.setParam("v", strDate);
                    rpVenue2.setParam("near", ll);
                    rpVenue2.setParam("query", venue);
                    rpVenue2.setParam("intent", intent);
                    rpVenue2.setParam("radius", radius);

                    venueSuggestions vs = new venueSuggestions();
                    vs.execute(rpVenue, rpVenue2);
                } else {
                    if (venueFetch < 3) {
                        getLocation();
                    }else
                        Toast.makeText(FitnessVenuesActivity.this, "Error : GPS is off, location details needed for fitness venues data", Toast.LENGTH_LONG).show();
                }
        }catch(Exception e){}
    }

    private void rtvHourlyForecast() {
        try{
            String baseUrl = getResources().getString(R.string.openWApiLink);
            String openWeatherApiKey = getResources().getString(R.string.openWApiKey);

            RequestPackage rp = new RequestPackage();
            rp.setMethod("GET");
            rp.setUri(baseUrl);
        if(String.valueOf(latitude).length() > 5) {
            BigDecimal locLat = new BigDecimal(latitude);
            locLat = locLat.setScale(2, RoundingMode.HALF_UP);
            Latitude = locLat.setScale(1, RoundingMode.HALF_UP);
            rp.setParam("lat", "" + locLat);
        } else {
            rp.setParam("lat", "" + latitude);
        }
        if(String.valueOf(longitude).length() > 5) {
            BigDecimal locLon = new BigDecimal(longitude);
            locLon = locLon.setScale(2, RoundingMode.HALF_UP);
            Longitude = locLon.setScale(1, RoundingMode.HALF_UP);
            rp.setParam("lon", "" + locLon);
        } else {
            rp.setParam("lon", "" + longitude);
        }
//http://api.owm.io/air/1.0/uvi/current?lat=33.8&lon=-118.1&appid=5c118208ee2624dac6933f56d77dcd40
//http://api.openweathermap.org/pollution/v1/co/28,77/current.json?appid=5c118208ee2624dac6933f56d77dcd40

        rp.setParam("appid", openWeatherApiKey);

        forecastPopulator fp = new forecastPopulator();
        fp.execute(rp);
    }catch(Exception e){e.printStackTrace();}
    }

    private void rtvUserAddress() {
        try {
            if (!Double.isNaN(latitude) && !Double.isNaN(longitude)) {
                RequestPackage rpLoc = new RequestPackage();
                rpLoc.setMethod("GET");
                rpLoc.setUri("http://maps.googleapis.com/maps/api/geocode/json");
                rpLoc.setParam("latlng", latitude + "," + longitude);
                rtvAddress ra = new rtvAddress();
                ra.execute(rpLoc);
            }
        }catch(Exception e){}
    }

    private void rtvOfflineForecast() {
        SQLiteDatabase sdb = null;
        try {
            DataManip dm = MainActivity.dm;
            if (dm == null)
                dm = new DataManip(getApplicationContext());
            sdb = dm.getReadableDatabase();//String ltd = "" + Latitude;  String lng = "" + Longitude;
            HashMap<String, List<forecastSlot>> currentSlotForecasts = new HashMap<>();
            List<forecastSlot> fSlot = new ArrayList<>();
            List<forecastSlot> suitableSlot = new ArrayList<>();
            forecastSlot tempFs;
            if(!isOnline()) {
                SharedPreferences userDetails = getApplicationContext().getSharedPreferences("com.example.b.activ.userDetails", Context.MODE_PRIVATE);
                city = userDetails.getString("com.example.b.activ.userDetails.city", "NotAvailable");
                state = userDetails.getString("com.example.b.activ.userDetails.state", "NotAvailable");
                country = userDetails.getString("com.example.b.activ.userDetails.country", "NotAvailable");
            }
            if(!city.equals("NotAvailable") && !state.equals("NotAvailable") && !country.equals("NotAvailable")) {
                Cursor countCursor = sdb.rawQuery("SELECT COUNT(*) FROM weeklyForecast where city='" + city + "' and state='" + state + "' and country='" + country + "'", null);
                countCursor.moveToFirst();
                int cnt = countCursor.getInt(0);
                if (cnt > 0) {
                    Cursor currentForecastCursor = sdb.rawQuery("SELECT * FROM weeklyForecast where city='" + city + "' and state='" + state + "' and country='" + country + "'", null);
                    currentForecastCursor.moveToLast();
                    String lastStamp = currentForecastCursor.getString(1);
                    lastStamp += " " + currentForecastCursor.getString(6);
                    Calendar Now = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strDate = sdf.format(Now.getTime());
                    Date d1 = sdf.parse(strDate);
                    Date d2 = sdf.parse(lastStamp);
                    if (d1.compareTo(d2) > 0) {
                        if (isOnline()) {
                            sdb.execSQL("Delete from weeklyForecast where where city='" + city + "' and state='" + state + "' and country='" + country + "'");
                            rtvHourlyForecast();
                        } else
                            Toast.makeText(this, "Offline state!Unable to fetch latest weather forecast", Toast.LENGTH_LONG).show();
                    } else {
                        int err = 0, suited = 0;
                        try {
                            currentForecastCursor = sdb.rawQuery("SELECT * FROM weeklyForecast where city='" + city + "' and state='" + state + "' and country='" + country + "'", null);
                            currentForecastCursor.moveToFirst();
                            do {
                                tempFs = new forecastSlot();//date,condition,high,low,slotStart,slotEnd,latitude,longitude
                                suited = 0;
                                String forecastDate = currentForecastCursor.getString(1);
                                tempFs.setForecastDate(forecastDate);
                                String rtvCondition = currentForecastCursor.getString(2);
                                tempFs.setCondition(rtvCondition);
                                String slotStart = currentForecastCursor.getString(5);
                                tempFs.setSlotStart(slotStart);
                                if ((rtvCondition.equals("clear sky") || rtvCondition.equals("few clouds") || rtvCondition.equals("clouds") || rtvCondition.equals("overcast clouds") || rtvCondition.equals("scattered clouds")) &&
                                        (slotStart.equals("06:00:00") || slotStart.equals("15:00:00") || slotStart.equals("18:00:00")))
                                    suited = 1;
                                tempFs.setMaxTemp(new BigDecimal(Double.parseDouble(currentForecastCursor.getString(3))).setScale(2, RoundingMode.HALF_UP));
                                tempFs.setMinTemp(new BigDecimal(Double.parseDouble(currentForecastCursor.getString(4))).setScale(2, RoundingMode.HALF_UP));
                                tempFs.setSlotEnd(currentForecastCursor.getString(6));
                                fSlot.add(tempFs);
                                if (suited == 1)
                                    suitableSlot.add(tempFs);
                            } while (currentForecastCursor.moveToNext());
                        } catch (Exception e) {
                            err = 1;
                        }
                        if (err == 0) {
                            textResponse.append("Venues nearby you for fitness and recreation:");

                            currentSlotForecasts.put(weatherForecastDataHeader.get(0), fSlot);
                            forecastListAdaptor = new currentForecastListAdaptor(FitnessVenuesActivity.this, weatherForecastDataHeader, currentSlotForecasts);
                            expListViewCurrentWeek.setAdapter(forecastListAdaptor);

                            scheduleResponse.setText("Current schedule slots:");

                            if (suitableSlot.size() > 0) {
                                hourlyForecastListAdaptor hfla = new hourlyForecastListAdaptor(FitnessVenuesActivity.this, R.layout.list_item, suitableSlot);
                                currentForecast.setAdapter(hfla);
                            }
                        }
                    }
                } else {
                    if (isOnline())
                        rtvHourlyForecast();
                    else
                        Toast.makeText(this, "Offline state!Unable to fetch latest weather forecast.", Toast.LENGTH_LONG).show();
                }
            } else {
                if(forecastFetch < 3) {
                    if (isOnline())
                        rtvUserAddress();
                    else {
                        forecastFetch = 4;
                        Toast.makeText(this, "Current location to weather forecast association not possible, please try later.", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(this, "Current location to weather forecast association not possible, please try later.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
        } finally {
            try {
                sdb.close();
            } catch (Exception ie) {
            }
        }
    }

    private class venueSuggestions extends AsyncTask<RequestPackage,String,HashMap<String,List<fitnessCentre>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(FitnessVenuesActivity.this);
            dialog.setMessage("Loading Venues…Please wait");
            dialog.setCancelable(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.show();
        }

        @Override
        protected HashMap<String, List<fitnessCentre>> doInBackground(RequestPackage... params) {
            try {
                HashMap<String, List<fitnessCentre>> hfc = new HashMap<>();
                List<fitnessCentre> fcGym, fcPark;
                fitnessCentre fcEmpty;

                String parkList = HttpManager.getData(params[0]);
                if (parkList != null) {
                    fcPark = fitnessVenuesJSONParser.localFitnessSuggestor(parkList, "park");
                } else {
                    fcPark = new ArrayList<fitnessCentre>();
                    fcEmpty = new fitnessCentre();
                    fcEmpty.setName("No Park nearby your vicinity");
                    fcEmpty.setAddress("NA");
                    fcPark.add(fcEmpty);
                }

                String gymList = HttpManager.getData(params[1]);
                if (gymList != null) {
                    fcGym = fitnessVenuesJSONParser.localFitnessSuggestor(gymList, "gym");
                } else {
                    fcGym = new ArrayList<fitnessCentre>();
                    fcEmpty = new fitnessCentre();
                    fcEmpty.setName("No GYM nearby your vicinity");
                    fcEmpty.setAddress("NA");
                    fcGym.add(fcEmpty);
                }
                hfc.put(venueListDataHeader.get(0), fcPark);
                hfc.put(venueListDataHeader.get(1), fcGym);
                dialog.dismiss();
                return hfc;
            }catch(Exception e){}
            return null;
        }

        @Override
        protected void onPostExecute(HashMap<String, List<fitnessCentre>> venueResult) {
            // TODO Auto-generated method stub
            try {
                super.onPostExecute(venueResult);
                if (venueResult != null) {
                    if (!venueResult.isEmpty()) {
                        try {
                            listAdaptor = new venueExpandListAdaptor(FitnessVenuesActivity.this, venueListDataHeader, venueResult);
                            expListViewVenues.setAdapter(listAdaptor);
                        } catch (Exception e) {
                        }
                        rtvUserAddress();
                    }
                }
            }catch(Exception e){}
        }
    }

    private class forecastPopulator extends AsyncTask<RequestPackage,String,HashMap<String,List<forecastSlot>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(FitnessVenuesActivity.this);
            dialog.setMessage("Loading Forecast…Please wait");
            dialog.setCancelable(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.show();
        }

        @Override
        protected HashMap<String,List<forecastSlot>> doInBackground(RequestPackage... params) {
            try {
                HashMap<String, List<forecastSlot>> currentSlotForecasts = new HashMap<>();
                List<forecastSlot> fSlot;

                String forecastList = HttpManager.getData(params[0]);
                if (forecastList != null) {
                    try {
                        fSlot = hourlyForecastJSONParser.forecastPopulator(forecastList);
                        currentSlotForecasts.put(weatherForecastDataHeader.get(0), fSlot);
                        dialog.dismiss();
                        return currentSlotForecasts;
                    } catch (Exception e) {
                    }
                } else {
                    fSlot = new ArrayList<forecastSlot>();
                    forecastSlot fsEmpty = new forecastSlot();
                    fsEmpty.setForecastDate("Sorry! Not available");
                    fSlot.add(fsEmpty);
                    currentSlotForecasts.put(weatherForecastDataHeader.get(0), fSlot);
                    dialog.dismiss();
                    return currentSlotForecasts;
                }
                dialog.dismiss();
            }catch(Exception e){}
            return null;
        }

        @Override
        protected void onPostExecute(HashMap<String,List<forecastSlot>> forecastSlots) {
            try {
                super.onPostExecute(forecastSlots);
                if (forecastSlots != null) {
                    textResponse.append("Venues nearby you for fitness and recreation:");
                    forecastListAdaptor = new currentForecastListAdaptor(FitnessVenuesActivity.this, weatherForecastDataHeader, forecastSlots);
                    expListViewCurrentWeek.setAdapter(forecastListAdaptor);
                    List<forecastSlot> suitableSlots = new ArrayList<>();
                    List<String> slotDates = new ArrayList<>();
                    int suitable = 0;
                    try {
                        List<forecastSlot> forecastResult = forecastSlots.get(weatherForecastDataHeader.get(0));
                        if (forecastResult.size() > 1) {
                            Iterator<forecastSlot> fsItr = forecastResult.iterator();
                            while (fsItr.hasNext()) {
                                suitable = 0;
                                forecastSlot tempFs = fsItr.next();
                                String dte = tempFs.getForecastDate();
                                String condition = tempFs.getCondition();
                                String start = tempFs.getSlotStart();
                                if ((condition.equals("clear sky") || condition.equals("few clouds") || condition.equals("broken clouds") || condition.equals("clouds") || condition.equals("overcast clouds") || condition.equals("scattered clouds")) &&
                                        (start.equals("06:00:00") || start.equals("15:00:00") || start.equals("18:00:00")))
                                    suitable = 1;
                                BigDecimal max = tempFs.getMaxTemp();
                                BigDecimal min = tempFs.getMinTemp();
                                String high = "" + max.toString().substring(0, 5);
                                String low = "" + min.toString().substring(0, 5);
                                String end = tempFs.getSlotEnd();//String ln=""+Longitude; String lt=""+Latitude;
                                String insertForecast = "Insert into weeklyForecast(forecastDate,condition,high,low,slotStart,slotEnd,city,state,country) values('" + dte + "','" + condition + "','" + high + "','" + low + "','" + start + "','" + end + "','" + city + "','" + state + "','" + country + "')";
                                DataManip dm = MainActivity.dm;
                                if (dm == null)
                                    dm = new DataManip(FitnessVenuesActivity.this);
                                dm.insert(insertForecast);
                                if (suitable == 1) {
                                    if (!slotDates.contains(dte))
                                        slotDates.add(dte);
                                    suitableSlots.add(tempFs);
                                }
                            }
                            if (slotDates.size() < 3) {
                                Intent notifyIntent = new Intent(FitnessVenuesActivity.this, activityNotificationManager.class);
                                Notification notification = new Notification();
                                notification.setMessage("For current schedule there are fewer slots for outdoor exercising. Please try to engage in calisthenics or stretching at home.B-Activ!");
                                notification.setTitle("B-Activ : Indoor Exercising");
                                activityNotificationManager.setNotification(notification);
                                startService(notifyIntent);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Insertion error:" + e.getMessage());
                    }
                    try {
                        scheduleResponse.setText("Current schedule slots:");
                        if (suitableSlots.size() > 0) {
                            //forecastSlots.put(weatherForecastDataHeader.get(1),suitableSlots);
                            hourlyForecastListAdaptor hfla = new hourlyForecastListAdaptor(FitnessVenuesActivity.this, R.layout.list_item, suitableSlots);
                            currentForecast.setAdapter(hfla);
                        } else
                            Log.d("FitnessExercise", "Error:Suitability populating failed");

                    } catch (Exception e) {
                    }
                }
            }catch(Exception e){}
        }
    }

    private class rtvAddress extends AsyncTask<RequestPackage,String,String>{

        @Override
        protected String doInBackground(RequestPackage... params) {
            try {
                forecastFetch++;
                String response = HttpManager.getData(params[0]);
                if (response != null)
                    return response;
                else
                    return null;
            }catch(Exception e){}
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                super.onPostExecute(s);
                if (s != null) {
                    String currAddress = addressJSONParser.getAddress(s);
                    SharedPreferences userDetails = getApplicationContext().getSharedPreferences("com.example.b.activ.userDetails", Context.MODE_PRIVATE);
                    int getLocation = 0;
                    if (currAddress != null) {
                        String storedCity = userDetails.getString("com.example.b.activ.userDetails.city", "NotAvailable");
                        String storedState = userDetails.getString("com.example.b.activ.userDetails.state", "NotAvailable");
                        String storedCountry = userDetails.getString("com.example.b.activ.userDetails.country", "NotAvailable");
                        Log.d("Venues", "Async Address:" + currAddress);
                        String address = currAddress;
                        String addressComponents[] = address.split(",");
                        if (addressComponents.length >= 3) {
                            state = addressComponents[addressComponents.length - 2];
                            String temp[] = state.split(" ");
                            state = temp[1];
                            Log.d("Venues", "state:" + state);
                            city = addressComponents[addressComponents.length - 3];
                            Log.d("Venues", "City:" + city);
                            country = addressComponents[addressComponents.length - 1];
                            Log.d("Venues", "Country:" + country);
                        }

                        if (city != null && state != null && country != null) {
                            if (city.length() > 0 && state.length() > 0 && country.length() > 0) {
                                if (!city.equals("NotAvailable") && !state.equals("NotAvailable") && !country.equals("NotAvailable")) {
                                    if (!storedCity.equals("NotAvailable") && !storedState.equals("NotAvailable") && !storedCountry.equals("NotAvailable")) {
                                        if (!storedCity.equals(city) || !storedState.equals(state) || !storedCountry.equals(country))
                                            getLocation = 1;
                                        else
                                            rtvOfflineForecast();
                                    } else
                                        getLocation = 1;
                                    if (getLocation == 1) {
                                        userDetails.edit().putString("com.example.b.activ.userDetails.city", city).commit();
                                        userDetails.edit().putString("com.example.b.activ.userDetails.state", state).commit();
                                        userDetails.edit().putString("com.example.b.activ.userDetails.country", country).commit();
                                        rtvOfflineForecast();

                                        InitActivity.spAuthentication = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                                        String username = InitActivity.spAuthentication.getString("com.example.b.activ.authentication.username", "NotAvailable");
                                        String password = InitActivity.spAuthentication.getString("com.example.b.activ.authentication.password", "NotAvailable");

                                        if (!username.equals("NotAvailable") && !password.equals("NotAvailable")) {
                                            Intent cloudUpdateUser = new Intent(FitnessVenuesActivity.this, cloudDataSync.class);
                                            cloudUpdateUser.putExtra("Flow", "updateLocation");
                                            cloudUpdateUser.putExtra("Username", username);
                                            cloudUpdateUser.putExtra("Password", password);
                                            cloudUpdateUser.putExtra("City", "" + city);
                                            cloudUpdateUser.putExtra("State", "" + state);
                                            cloudUpdateUser.putExtra("Country", "" + country);
                                            new cloudDataSync(cloudUpdateUser, FitnessVenuesActivity.this);
                                        }
                                    }
                                } else {
                                    if (!storedCity.equals("NotAvailable") && !storedState.equals("NotAvailable") && !storedCountry.equals("NotAvailable")) {
                                        city = storedCity;
                                        state = storedState;
                                        country = storedCountry;
                                        rtvOfflineForecast();
                                    } else if (forecastFetch < 3)
                                        rtvUserAddress();
                                }
                            } else {
                                if (forecastFetch < 3)
                                    rtvUserAddress();
                            }
                        } else {
                            if (forecastFetch < 3)
                                rtvUserAddress();
                        }
                    }
                }
            }catch(Exception ex){}
        }
    }

    private boolean locationAvailable(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {
            boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (gpsIsEnabled)
                return true;
            else {
                return false;
            }
        } else {
            return false;
        }

    }

    private boolean isPlayServiceAvailable() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS;
    }

    @Override
    @TargetApi(23)
    public void onConnected(Bundle bundle) {
        try {
            Log.d("Login", "connected to ActivityRecognition");

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                int stored = 0;
                SharedPreferences spAuthentication = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                LocaleDetails ld = new LocaleDetails(this);
                latitude = ld.getLatitude();
                longitude = ld.getLongitude();

                if (!Double.isNaN(latitude) && !Double.isNaN(longitude) && latitude != longitude) {
                    new userAddress(getApplicationContext(), latitude, longitude);
                    BigDecimal locLat = new BigDecimal(latitude);
                    locLat = locLat.setScale(2, RoundingMode.HALF_UP);
                    BigDecimal Latitude = locLat.setScale(1, RoundingMode.HALF_UP);

                    BigDecimal locLon = new BigDecimal(longitude);
                    locLon = locLon.setScale(2, RoundingMode.HALF_UP);
                    BigDecimal Longitude = locLon.setScale(1, RoundingMode.HALF_UP);
                    spAuthentication.edit().putString("com.example.b.activ.latitude", String.valueOf(Latitude)).commit();
                    spAuthentication.edit().putString("com.example.b.activ.longitude", String.valueOf(Longitude)).commit();
                    if(isOnline())
                        rtvVenues();
                    else
                        rtvOfflineForecast();
                } else {
                    stored = 1;
                    Toast.makeText(FitnessVenuesActivity.this, "Note : GPS is off, location details needed for accurate fitness venues data", Toast.LENGTH_LONG).show();
                }

                if(stored == 1){
                    spAuthentication = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                    String permission = spAuthentication.getString("com.example.b.activ.permission", "No");
                    if (permission.equals("Yes")) {
                        spAuthentication = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                        String ltd = spAuthentication.getString("com.example.b.activ.latitude", "NotAvailable");
                        String lng = spAuthentication.getString("com.example.b.activ.longitude", "NotAvailable");
                        if (!ltd.equals("NotAvailable") && !lng.equals("NotAvailable")) {
                            latitude = Double.parseDouble(ltd);
                            longitude = Double.parseDouble(lng);
                            if(isOnline())
                                rtvVenues();
                            else
                                rtvOfflineForecast();
                        } else {
                            return;
                        }
                    }
                }
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            }

        }catch(Exception connEx){Toast.makeText(this,"Exception:"+connEx.getMessage(),Toast.LENGTH_LONG).show();}
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Login", "Suspended to ActivityRecognition");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Login", "Not connected to ActivityRecognition");
    }

    @Override
    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            switch (requestCode) {
                case REQUEST_PERMISSION_LOCATION:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission granted
                        int stored = 0;
                        SharedPreferences spAuthentication = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                        LocaleDetails ld = new LocaleDetails(this);
                        latitude = ld.getLatitude();
                        longitude = ld.getLongitude();

                        if (!Double.isNaN(latitude) && !Double.isNaN(longitude) && latitude != longitude) {
                            new userAddress(getApplicationContext(), latitude, longitude);
                            BigDecimal locLat = new BigDecimal(latitude);
                            locLat = locLat.setScale(2, RoundingMode.HALF_UP);
                            BigDecimal Latitude = locLat.setScale(1, RoundingMode.HALF_UP);

                            BigDecimal locLon = new BigDecimal(longitude);
                            locLon = locLon.setScale(2, RoundingMode.HALF_UP);
                            BigDecimal Longitude = locLon.setScale(1, RoundingMode.HALF_UP);
                            spAuthentication.edit().putString("com.example.b.activ.latitude", String.valueOf(Latitude)).commit();
                            spAuthentication.edit().putString("com.example.b.activ.longitude", String.valueOf(Longitude)).commit();
                            if(isOnline())
                                rtvVenues();
                            else
                                rtvOfflineForecast();
                        } else {
                            stored = 1;
                            Toast.makeText(FitnessVenuesActivity.this, "Note : GPS is off, location details needed for accurate fitness venues data", Toast.LENGTH_LONG).show();
                        }

                        if(stored == 1){
                            spAuthentication = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                            String permission = spAuthentication.getString("com.example.b.activ.permission", "No");
                            if (permission.equals("Yes")) {
                                spAuthentication = getApplicationContext().getSharedPreferences("com.example.b.activ", Context.MODE_PRIVATE);
                                String ltd = spAuthentication.getString("com.example.b.activ.latitude", "NotAvailable");
                                String lng = spAuthentication.getString("com.example.b.activ.longitude", "NotAvailable");
                                if (!ltd.equals("NotAvailable") && !lng.equals("NotAvailable")) {
                                    latitude = Double.parseDouble(ltd);
                                    longitude = Double.parseDouble(lng);
                                    if(isOnline())
                                        rtvVenues();
                                    else
                                        rtvOfflineForecast();
                                } else {
                                    return;
                                }
                            }
                        }
                    }
                    break;
            }
        }catch(Exception e){Toast.makeText(this,"Exception:"+e.getMessage(),Toast.LENGTH_LONG).show();}
    }
}


