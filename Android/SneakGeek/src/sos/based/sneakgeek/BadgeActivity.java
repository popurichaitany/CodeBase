package sos.based.sneakgeek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sos.based.sneakgeek.adaptor.BadgeReciepientAdapter;
import sos.based.sneakgeek.model.Answerer;
import sos.based.sneakgeek.model.Badge;
import sos.based.sneakgeek.parser.BadgeJSONParser;
import sos.based.sneakgeek.web.*;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BadgeActivity extends Activity {

	BadgeReciepientAdapter listAdapter;
	ExpandableListView badgeListView;
	Spinner badgeQuery;
	Button find;
	TextView badgeInfo;
	List<String> listDataHeader;
	List<Answerer> allTime;
	List<Answerer> recent;
	String badge;
	Badge r;
	String base="http://api.stackexchange.com/2.2/badges/";
	String query,query2;
	int badgeID[]={222,1306,260,9,221,1973,8,4,31,7,4127,2278,37,3,1287,4368,2600,219,144,23,20,5,38,26,892,220,1276,900,837,10,14,2,804,6,1224,254,884,1,63,1108,600,283,535,292,635,2549,2425,1753,1891,1242,1872,1738,851,3497,2006,645,2453,898,4536,3934,2359,4425,2409,1985,2092,2579,1875,724,289,4026,643,2428,1775,742,2422,806,273,286,276,270,295,356,296,785,333,1020,298,307,280,268,314,315,678,865,287,291,352,410,3469,640,418,766};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_badge);
		this.setTitle("SneakGeek - Search Badge");

		listDataHeader = new ArrayList<String>();
		listDataHeader.add("Last Month recepients");
		listDataHeader.add("All Time recepients");

		badgeQuery= (Spinner) findViewById(R.id.badges);
		badgeListView = (ExpandableListView) findViewById(R.id.badgeList);
		find=(Button) findViewById(R.id.searchButton);
		badgeInfo=(TextView) findViewById(R.id.badgeDescr);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.badges_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		badgeQuery.setAdapter(adapter);

		find.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(isOnline())
				{
					int pos=badgeQuery.getSelectedItemPosition();
					switch(pos)
					{
					case 6:badge="citizen-patrol";
					break;
					case 19:badge="nice-answer";
					break;
					case 20:badge="nice-question";
					break;
					case 22:badge="peer-pressure";
					break;
					case 23:badge="popular-question";
					break;
					case 35:badge="tag-editor";
					break;
					case 39:badge="vox-populi";
					break;
					case 98:badge="ado-net";
					break;
					default:badge=badgeQuery.getSelectedItem().toString().toLowerCase();
					}
					String description="http://stackoverflow.com/badges/"+badgeID[pos]+"/"+badge;
					query2=base.substring(0,base.length()-1);
					requestBadgeData(description,query2); 

					query=base+"/"+badgeID[pos]+"/recipients";
					requestData(query);				
				}
				else
				{
					Toast.makeText(BadgeActivity.this,"Network isn't avaialble", Toast.LENGTH_LONG).show();
				}
			}
		});
		badgeInfo.setMovementMethod(new ScrollingMovementMethod());
		//badge-description
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.badge, menu);
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

	private void requestBadgeData(String uri,String uri2) {
		badgeInfoExtractor bie =new badgeInfoExtractor();

		RequestPackage bd = new RequestPackage();
		bd.setMethod("GET");
		bd.setUri(uri);
		
		RequestPackage bd2 = new RequestPackage();
		bd2.setMethod("GET");
		bd2.setUri(uri2);

		bd2.setParam("order", "desc");
		bd2.setParam("sort", "rank");
		bd2.setParam("inname", badge);
		bd2.setParam("site","stackoverflow");

		bie.execute(bd,bd2);
	}

	private void requestData(String uri) {
		TimeTracker tt=new TimeTracker();
		long current=tt.getCurrent();
		long month=tt.getMonthAgo(current);

		RequestPackage p = new RequestPackage();
		p.setMethod("GET");
		p.setUri(uri);

		p.setParam("page", "1");
		p.setParam("pagesize", "100");
		p.setParam("fromdate", String.valueOf(month));
		p.setParam("todate", String.valueOf(current));
		p.setParam("site","stackoverflow");

		RequestPackage p2 = new RequestPackage();
		p2.setMethod("GET");
		p2.setUri(uri);

		p2.setParam("page", "1");
		p2.setParam("pagesize", "100");
		p2.setParam("site","stackoverflow");

		badgeExtractor be=new badgeExtractor();
		be.execute(p,p2);
	}

	private class badgeInfoExtractor extends AsyncTask<RequestPackage,String,String>
	{
		@Override
		protected String doInBackground(
				RequestPackage... params) {
			// TODO Auto-generated method stub
			String info="";
			String content = HttpManager.getData(params[0]);
			//info=TagWikisJSONParser.TagWikisParser(content);

			String content2= HttpManager.getData(params[1]);
			 r=BadgeJSONParser.BadgeInfoParser(content2, badge);
			 
			//String description=HttpManager.getContent(r.getLink(),"badge");
			//"Description:" + description + "\n
			String result="Type :"+r.getBadgeType() + "\nRank :" +r.getRank() + "\nCount:"+r.getAwardCount();
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			
			if(result!=null)
			{
				if(badgeInfo.length()>12)
				{
					badgeInfo.setText("");
					String info=result;
					badgeInfo.setText(info);
				}
				else
					badgeInfo.append("\n"+result);
			}
			else
				badgeInfo.append("No description found"); 
		}
	}

	private class badgeExtractor extends AsyncTask<RequestPackage,String,HashMap<String, List<Answerer>>>
	{
		@Override
		protected HashMap<String, List<Answerer>> doInBackground(
				RequestPackage... params) {
			// TODO Auto-generated method stub
			HashMap<String, List<Answerer>> recepientList=new HashMap<String, List<Answerer>>();

			String content = HttpManager.getData(params[0]);
			List<Answerer> recent=BadgeJSONParser.BadgeRecepientParser(content);
			recepientList.put(listDataHeader.get(0), recent);

			String content2 = HttpManager.getData(params[1]);
			List<Answerer> all=BadgeJSONParser.BadgeRecepientParser(content2);
			recepientList.put(listDataHeader.get(1), all);

			return recepientList;
		}

		@Override
		protected void onPostExecute(HashMap<String, List<Answerer>> result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			listAdapter = new BadgeReciepientAdapter(BadgeActivity.this, listDataHeader, result);
			badgeListView.setAdapter(listAdapter);
		}
	}
}
