package org.app.DbMgmt;

/**
 * Created by Anand on 2/5/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import org.app.anand.b_activ.MainActivity;

public class DataManip extends SQLiteOpenHelper implements BaseColumns{

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME ="bactive57";
    static DataManip dm;
    public static final String SQL_CREATE_LOGGER ="CREATE TABLE activityLog(id integer primary key autoincrement, startTime text, endTime text, lastActivity text)";
    public static final String SQL_CREATE_FORECAST="CREATE TABLE weeklyForecast(id integer primary key autoincrement,forecastDate text,condition text,high text,low text,slotStart text,slotEnd text,city text,state text,country text)";
    public static final String SQL_CREATE_CURRENT_LOGGER ="CREATE TABLE currentActivityLog(id integer primary key autoincrement, captureTime text, activity text)";
    public static final String SQL_CREATE_DAILY_TRACKER = "CREATE TABLE dailyActivityTracker(id integer primary key autoincrement,walking int,running int,bicycling int,CaloriesBurnt int,bmi text,activityDate text,cloudKey text,uploaded int)";

    public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS activityLog";

    public static DataManip getDatabaseHandler(){
        if(dm==null)
            dm=new DataManip(MainActivity.getActivityContext());
        return dm;
    }

    public DataManip(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(SQL_CREATE_LOGGER);
        db.execSQL(SQL_CREATE_FORECAST);
        db.execSQL(SQL_CREATE_CURRENT_LOGGER);
        db.execSQL(SQL_CREATE_DAILY_TRACKER);
    }
    public synchronized int insert(String insSqlStmt){
        SQLiteDatabase db=null;
        try {
            db=this.getWritableDatabase();
            db.execSQL(insSqlStmt);
            return 1;
        }catch (SQLException se){
            Log.d("DataManip", "SQL Insert Error");
            se.printStackTrace();
            return -1;
        } catch (Exception e){
            Log.d("DataManip", "General Insert Error");
            e.printStackTrace();
            return -1;
        }finally {
            db.close();
        }
    }

    public synchronized int update(String table,ContentValues cv,String where){
        SQLiteDatabase db=null;
        try {
            db=this.getWritableDatabase();
            int rows = db.update(table, cv, where, null);
            return rows;
        }catch (SQLException se){ se.printStackTrace();
            se.printStackTrace();
            return -1;
        } catch (Exception e){ e.printStackTrace();
            e.printStackTrace();
            return -1;
        }finally {
            db.close();
        }
    }

    public synchronized int update(String table,ContentValues cv,String where,String arg[]){
        SQLiteDatabase db=null;
        try {
            db=this.getWritableDatabase();
            int rows = db.update(table, cv, where, arg);
            return rows;
        }catch (SQLException se){ se.printStackTrace();
            se.printStackTrace();
            return -1;
        } catch (Exception e){ e.printStackTrace();
            e.printStackTrace();
            return -1;
        }finally {
            db.close();
        }
    }
    public synchronized Cursor select(String selSqlStmt){
        SQLiteDatabase db=null;
        try {
            db = this.getReadableDatabase();
            Cursor result = db.rawQuery(selSqlStmt, null);
            return result;
        }catch (SQLException se){
            se.printStackTrace();
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void close(){
        SQLiteDatabase dbReadable = this.getReadableDatabase();
        try {
            if (dbReadable.isOpen())
                dbReadable.close();
        }catch(Exception e){e.printStackTrace();}
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}
