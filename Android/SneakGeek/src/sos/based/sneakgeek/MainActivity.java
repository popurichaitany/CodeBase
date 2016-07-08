package sos.based.sneakgeek;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import sos.based.sneakgeek.adaptor.NotificationListAdaptor;
import sos.based.sneakgeek.database.DataManip;
import sos.based.sneakgeek.model.Notification;
import sos.based.sneakgeek.model.Question;
import sos.based.sneakgeek.model.Tutorial;
import sos.based.sneakgeek.parser.QuestionJSONParser;
import sos.based.sneakgeek.parser.TutorialJSONParser;
import sos.based.sneakgeek.web.*;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
//import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView output;
	ListView questions;
	Button search;
	EditText srchString;
	String tags="",tag="";
	ExpandableListView noteListView,tutoListView;
	NotificationListAdaptor listAdapter;
	List<String> listDataHeader;
	int looped=0;
	static int listLength;
	String postQuery="http://api.stackexchange.com/2.2/search/advanced";
	String notification="http://api.stackexchange.com/2.2/questions/no-answers";
	String YoutubeAPIKey="AIzaSyDx-MDXhjIe015fy0sECEksbz0DtHoXIAU";//AIzaSyADvSwy4Rcs4mAHTBd20Iox-QzzxVjrF2M
	String tutorialBaseLink="https://www.googleapis.com/youtube/v3/search";
	String faqPost="";
	List<Notification> notify;
	public static List<Question> q;
	public static List<Question> nascent;
	public static List<Tutorial> ttl;
	public static SQLiteDatabase db;
	public static int source;
	int lastOne;
	public static String[] subscription=new String[16];
	String tagList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DataManip dm=DataManip.getDatabaseHandler(this);
		db=dm.getWritableDatabase();
			db.execSQL(DataManip.SQL_DELETE_TABLE);
			db.execSQL(DataManip.SQL_DELETE_TABLE2);
			db.execSQL(DataManip.SQL_CREATE_LOGOUT);
			db.execSQL(DataManip.SQL_CREATE_SUBSCRIBE);
			ContentValues values=new ContentValues();
			values.put(DataManip.COLUMN_LAST_READ, 1427760000);
			db.insert("logoutrack", null, values);
			ContentValues values2=new ContentValues();
			values2.put(DataManip.COLUMN_TECHNOLOGY,"java");
			db.insert("subscribe", null, values2);

		Cursor c=db.rawQuery("SELECT * FROM logoutrack", null);
		while(c.moveToNext())
		{
			lastOne=c.getInt(1);
		}
		int i=0;
		Cursor c2=db.rawQuery("SELECT * FROM subscribe", null);
		while(c2.moveToNext())
		{
			subscription[i]=c2.getString(1);
			i++;
		}
		
		for(int k=0;k<subscription.length;k++)
		{
			tagList=subscription[k]+";";
		}
		
		listDataHeader = new ArrayList<String>();
		listDataHeader.add("New notifications");

		notify=new ArrayList<>();
		output=(TextView) findViewById(R.id.rsltTxt);
		questions = (ListView) findViewById(R.id.qList);
		search = (Button) findViewById(R.id.srchButton);
		srchString = (EditText) findViewById(R.id.srchTxt);
		noteListView = (ExpandableListView) findViewById(R.id.ntExp);
		tutoListView = (ExpandableListView) findViewById(R.id.tExp);
		questions.setVisibility(View.GONE); 

		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Do something in response to button click
				hideKeyboard(MainActivity.this);
				if(questions.getChildCount()>0)
					questions.removeAllViewsInLayout();
				try
				{
					if(srchString.length()>0)
					{
						if(isOnline())
						{
							String[] words = srchString.getText().toString().split("\\s+");
							TagExtractor te = new TagExtractor(srchString.getText().toString());
							if(words.length==1 && te.isTag(words[0]))
							{
								tags=words[0];
								tag=words[0];
								faqPost="http://api.stackexchange.com/2.2/tags/"+tag+"/faq";
								requestData(faqPost);
							}
							else
							{
								tags=te.resultanTag(te.getQuestion());
								requestData(postQuery, srchString.getText().toString());
							}
						}
						else
						{
							Toast.makeText(MainActivity.this,"Network isn't avaialble", Toast.LENGTH_LONG).show();
						}
					}
					else
					{
						Toast.makeText(MainActivity.this,"No results, search string empty", Toast.LENGTH_LONG).show();	
					}
				}catch(Exception e)
				{

				}
			}
		});
		requestNotification(notification,tutorialBaseLink); 

		noteListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Notification nf=notify.get(childPosition);
				if(nf.getType().equals("question"))
				{
					source = 1;
					Intent myIntent = new Intent(getApplicationContext(), QuestionListActivity.class);
					startActivity(myIntent);
				}
				if(nf.getType().equals("tutorial"))
				{
					source = 1;
					Intent myIntent = new Intent(getApplicationContext(), TuotorialListActivity.class);
					startActivity(myIntent);
				}
				return true;
			}
		});

		noteListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				if(noteListView.getChildCount()==0)
				{
					Toast.makeText(getApplicationContext(), "No new notification", Toast.LENGTH_LONG).show();
					noteListView.collapseGroup(0);
				}
				return false;
			}
		});
		//output.setMovementMethod(new ScrollingMovementMethod());
	}

	public String paramWebEncoder(long limiter)
	{
		long unixSeconds = limiter;//1372339860;
		Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
		String formattedDate = sdf.format(date);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
		formattedDate = formattedDate.replace(" ", "T");
		String rm=formattedDate.substring(19,formattedDate.length());
		formattedDate = formattedDate.replace(rm, "Z"); 

		return formattedDate;
	}

	public static void hideKeyboard(Activity activity)
	{
		InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
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
		//int id = item.getItemId();
		//if (id == R.id.action_settings) {
		switch (item.getItemId()) {
		case R.id.goto_tag:
			Intent tags = new Intent(this, TagActivity.class);
			startActivity(tags);
			break;
		case R.id.goto_badge:
			Intent badges = new Intent(this, BadgeActivity.class);
			startActivity(badges);
			break;
		case R.id.action_settings:
			Intent setting = new Intent(this, SettingsActivity.class);
			startActivity(setting);
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		int tm=-1;
		String noval="_id != ?";
		String novals[]={"" + tm};

		ContentValues values3=new ContentValues();
		values3.put(DataManip.COLUMN_LAST_READ,new TimeTracker().getCurrent());
		db.update("logoutrack", values3,noval,novals);
	}

	private void requestNotification(String uri,String uri2)
	{
		RequestPackage p = new RequestPackage();
		p.setMethod("GET");
		p.setUri(uri);

		p.setParam("page", "1");
		p.setParam("pagesize", "100");
		p.setParam("fromdate", String.valueOf(lastOne));//1430438400
		p.setParam("todate", String.valueOf(new TimeTracker().getCurrent())); //String.valueOf(new TimeTracker().getCurrent()) 
		p.setParam("order","desc");
		p.setParam("sort","creation");
		p.setParam("tagged","java");
		p.setParam("site","stackoverflow");

		RequestPackage p2 = new RequestPackage(); 
		p2.setMethod("GET");
		p2.setUri(uri2);

		String from=paramWebEncoder(lastOne);
		String to=paramWebEncoder(new TimeTracker().getCurrent());
		
		p2.setParam("part","snippet");
		p2.setParam("maxResults","50");
		p2.setParam("publishedAfter",from);
		p2.setParam("publishedBefore",to);
		p2.setParam("q","java");
		p2.setParam("relevanceLanguage","en");
		p2.setParam("type","video");
		p2.setParam("key",YoutubeAPIKey);

		//part=snippet&maxResults=50&publishedAfter=
		//2015-05-01T00%3A00%3A00Z&publishedBefore=2015-05-05T00%3A00%3A00Z&q=java&relevanceLanguage=en&
		//type=video&key={YOUR_API_KEY}

		NotifyFeed nf = new NotifyFeed();
		nf.execute(p,p2); 
	}


	private void requestData(String uri,String questn) {
		RequestPackage p = new RequestPackage();
		p.setMethod("GET");
		p.setUri(uri);

		p.setParam("page", "1");
		p.setParam("pagesize", "100");
		p.setParam("fromdate", "1104537600");
		p.setParam("todate", "1427760000");
		p.setParam("order","desc");
		p.setParam("sort","relevance");
		p.setParam("q",questn);
		p.setParam("answers","1");
		p.setParam("tagged",tags);
		p.setParam("site","stackoverflow");

		MyTask task=new MyTask();
		task.execute(p);

		if(listLength>0)
		{
			source = 2;
			Intent myIntent = new Intent(getApplicationContext(), QuestionListActivity.class);
			startActivity(myIntent);
		}
		else
		{
			Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
		}
	}

	private void requestData(String uri) {
		RequestPackage p = new RequestPackage();
		p.setMethod("GET");
		p.setUri(uri);

		p.setParam("page", "1");
		p.setParam("pagesize", "100");
		p.setParam("site","stackoverflow");

		MyTask task=new MyTask();
		task.execute(p);
	}


	protected void updateDisplay(String message)
	{
		output.setText(message);
	}
	protected boolean isOnline() {
		ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if(netInfo != null && netInfo.isConnectedOrConnecting()){
			return true;
		}
		else
		{
			return false;
		}
	}

	private class MyTask extends AsyncTask<RequestPackage,String,List<Question>>
	{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			//super.onPreExecute();

			//updateDisplay("Searching...");
		}

		@Override
		protected List<Question> doInBackground(RequestPackage... params) {
			// TODO Auto-generated method stub
			/*runOnUiThread(new Runnable() {

			public void run() {
				Toast.makeText(getApplicationContext(), "content-length:"+content.length(), Toast.LENGTH_LONG).show();
			//Toast.makeText(getApplicationContext(), "Example for Toast", Toast.LENGTH_SHORT).show();

			} });*/
			String content = HttpManager.getData(params[0]);
			q=QuestionJSONParser.questionParser(content);

			return q;
		}

		@Override
		protected void onPostExecute(List<Question> result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			if(result!=null)
			{
				if(result.size()>0)
				{
					//updateDisplay(result.size()+" Result(s).");
					//questionAdaptor qa=new questionAdaptor(MainActivity.this, R.layout.activity_main, q);
					//questions.setAdapter(qa);
					listLength=result.size();
				}
				else
				{
					looped=1;
					faqPost="http://api.stackexchange.com/2.2/tags/"+tags+"/faq";
					requestData(faqPost);
				}
			}
			else if(looped==0)
			{
				looped=1;
				faqPost="http://api.stackexchange.com/2.2/tags/"+tags+"/faq";
				requestData(faqPost);
			}
			else
			{
				updateDisplay("No Result");
				Toast.makeText(getApplicationContext(), "Data not found, SORRY!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class NotifyFeed extends AsyncTask<RequestPackage, String, HashMap<String, List<Notification>>>
	{
		@Override
		protected HashMap<String, List<Notification>> doInBackground(RequestPackage... params) {
			// TODO Auto-generated method stub

			HashMap<String, List<Notification>> nList=new HashMap<String, List<Notification>>();
			String content = HttpManager.getData(params[0]);
			//String content2;
			//final String tmp,tmp2,tmp3;
			//tmp=params[1].getUri();
			//tmp2=params[1].getEncodedParams();
			//tmp3=params[0].getEncodedParams();
			String content2 = HttpManager.getData(params[1]);

			nascent=QuestionJSONParser.questionParser(content);
			Notification note=new Notification();
			note.setQl(nascent); 
			if(nascent!=null)
				note.setLabel("Number of new questions posted in area of interest: "+nascent.size());
			else
				note.setLabel("No new feed for queries");
			note.setType("question"); 
			notify.add(note);

			ttl=TutorialJSONParser.TutorialParser(content2);
			Notification note2 = new Notification();
			note2.setTl(ttl); 
			if(ttl!=null)
				note2.setLabel("Number of new tutorials posted in area of interest: " + ttl.size()); 
			else
				note2.setLabel("No new feed for tutorials");
			note2.setType("tutorial"); 
			notify.add(note2);

			nList.put(listDataHeader.get(0), notify);
			return nList;
		}

		@Override
		protected void onPostExecute(HashMap<String, List<Notification>> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null)
			{
				if(result.size()>0)
				{
					listAdapter = new NotificationListAdaptor(MainActivity.this, listDataHeader, result);
					noteListView.setAdapter(listAdapter);
				}
			}
		}
	}

	
}
