package com.example.accelorometer;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.BitmapFactory.Options;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class SimulationView extends View implements SensorEventListener{

	private Bitmap mField;
	private Bitmap mBasket;
	private Bitmap mBitmap;
	
	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
	private Display mDisplay;
	
	private static final int BALL_SIZE = 64;
	private static final int BASKET_SIZE = 80;
	
	private float mXOrigin;
	private float mYOrigin;
	private float mHorizontalBound;
	private float mVerticalBound;
	float mSensorX, mSensorY, mSensorZ;
	long mSensorTimeStamp;
	Particle mBall = new Particle();
	
	public SimulationView(Context context) 
	{
		super(context);
		Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.basketball);
		mBitmap =Bitmap.createScaledBitmap(ball, BALL_SIZE, BALL_SIZE,true);
		Bitmap basket=BitmapFactory.decodeResource(getResources(), R.drawable.basket);
		mBasket=Bitmap.createScaledBitmap(basket,BASKET_SIZE,BASKET_SIZE,true);
		Options opts=new Options();
		opts.inDither=true;
		opts.inPreferredConfig=Bitmap.Config.RGB_565;
		mField=BitmapFactory.decodeResource(getResources(),R.drawable.field,opts);
		
		WindowManager mWindowManager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		mDisplay=mWindowManager.getDefaultDisplay();
		
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        mHorizontalBound=457;
        mVerticalBound=300;
	}
	
	/*@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
		mBitmap =Bitmap.createScaledBitmap(ball, BALL_SIZE, BALL_SIZE,true);
		Bitmap basket=BitmapFactory.decodeResource(getResources(), R.drawable.basket);
		mBasket=Bitmap.createScaledBitmap(basket,BASKET_SIZE,BASKET_SIZE,true);
		Options opts=new Options();
		opts.inDither=true;
		opts.inPreferredConfig=Bitmap.Config.RGB_565;
		mField=BitmapFactory.decodeResource(getResources(),R.drawable.field,opts);
	}*/
	public void startSimulation()
    {
    	mSensorManager.registerListener(this, 
    			mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
    			SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    public void stopSimulation()
    {
    	mSensorManager.unregisterListener(this);
    }

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		{
			if (mDisplay.getRotation() == Surface.ROTATION_270)
			{
				mSensorX = event.values[0];
				mSensorY = -event.values[1];
			} else if(mDisplay.getRotation() == Surface.ROTATION_180)
			{
				mSensorX = -event.values[1];
				mSensorY = -event.values[0];
			} else if(mDisplay.getRotation() == Surface.ROTATION_90)
			{
				mSensorX = -event.values[1];
				mSensorY = event.values[0];
			} else if(mDisplay.getRotation() == Surface.ROTATION_0)
			{
				mSensorX = event.values[0];
				mSensorY = event.values[1];
			}
			 
			mSensorZ = event.values[2];
			mSensorTimeStamp=event.timestamp;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
    

	
    @Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawBitmap(mField,  0, 0, null);
		canvas.drawBitmap(mBasket, mXOrigin + BASKET_SIZE/2, mYOrigin + BASKET_SIZE/2, null);
		
		mBall.updatePosition(mSensorX, mSensorY, mSensorZ, mSensorTimeStamp);
		mBall.resolveCollisionWithBounds(mHorizontalBound,mVerticalBound);
		
		canvas.drawBitmap(mBitmap,
							(mXOrigin + 2*BALL_SIZE) + mBall.mPosX,
							(mYOrigin + 2*BALL_SIZE) - mBall.mPosY, null);
	
		invalidate();
	}
}
