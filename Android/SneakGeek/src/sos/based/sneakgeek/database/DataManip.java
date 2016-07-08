package sos.based.sneakgeek.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class DataManip extends SQLiteOpenHelper implements BaseColumns{

	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME ="sneakgeek";
	static DataManip dm;
	public static final String COLUMN_LAST_READ="LastRead";
	public static final String COLUMN_TECHNOLOGY="Technology";
	public static final String SQL_CREATE_LOGOUT ="CREATE TABLE logoutrack (" +
			DataManip._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			DataManip.COLUMN_LAST_READ + " INTEGER"+
			");";
	public static final String SQL_CREATE_SUBSCRIBE="CREATE TABLE subscribe (" +
			DataManip._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			DataManip.COLUMN_TECHNOLOGY + " TEXT" +
			");";
	public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS logoutrack";
	public static final String SQL_DELETE_TABLE2 = "DROP TABLE IF EXISTS subscribe";

	public static  DataManip getDatabaseHandler(Context context) {
		if(dm==null)
			dm=new DataManip(context);	
		return dm;
	}

	private DataManip(Context context) 
	{
		super(context, DATABASE_NAME, null,DATABASE_VERSION);
		getWritableDatabase();
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_CREATE_LOGOUT);
		db.execSQL(SQL_CREATE_SUBSCRIBE);
		ContentValues values=new ContentValues();
		values.put(DataManip.COLUMN_LAST_READ, 1427760000);
		db.insert("logoutracker", null, values);
		ContentValues values2=new ContentValues();
		values2.put(DataManip.COLUMN_TECHNOLOGY,"Java");
		db.insert("subscribe", null, values2);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
