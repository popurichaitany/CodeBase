package com.example.accelorometer;


import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

	private static final String TAG = "com.example.accelerometer.MainActivity";
	private WakeLock mWakeLock;
	SimulationView mSimulationView;
	
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        
        mSimulationView= new SimulationView(this);
        setContentView(mSimulationView);
        
        PowerManager mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock=mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,TAG);
        
       
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    @Override
    protected void onResume() {
    	super.onResume();
    	mWakeLock.acquire();
    	mSimulationView.startSimulation();
    	
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	mSimulationView.stopSimulation();
    	mWakeLock.release();
    	
    }


	
}
