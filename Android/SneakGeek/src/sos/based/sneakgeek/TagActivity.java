package sos.based.sneakgeek;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sos.based.sneakgeek.web.*;
import sos.based.sneakgeek.adaptor.ExpandableListAdapter;
import sos.based.sneakgeek.model.Answerer;
import sos.based.sneakgeek.parser.AlltimeTopAnswererJSONParser;
import sos.based.sneakgeek.parser.RecentTopAnswererJSONParser;
import sos.based.sneakgeek.parser.TagWikisJSONParser;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TagActivity extends Activity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	Spinner tagQuery;
	Button go;
	TextView tagInfo;
	List<String> listDataHeader;
	List<Answerer> allTime;
	List<Answerer> recent;
	String tag;
	String base="http://api.stackexchange.com/2.2/tags/";
	String wikis="/wikis";
	String all_Time="/top-answerers/all_time";
	String month="/top-answerers/month";
	String query,query2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);
		this.setTitle("SneakGeek - Search Tag");
		
		listDataHeader = new ArrayList<String>();
		listDataHeader.add("Last Month Top Answerer");
		listDataHeader.add("All Time Top Answerer");

		tagQuery= (Spinner) findViewById(R.id.tags);
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		go=(Button) findViewById(R.id.srchButton);
		tagInfo=(TextView) findViewById(R.id.tagDescr);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tags_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tagQuery.setAdapter(adapter);

		go.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(isOnline())
				{
					tag=(String) tagQuery.getSelectedItem();
					
					if(tag.contains("#"))
						tag=tag.replace("#","%23");
					
					query=base+tag+wikis;
					requestTagData(query);
					
					query=base+tag+month;
					query2=base+tag+all_Time;
					requestData(query,query2);

					//
					//requestData(query); 
				}
				else
				{
					Toast.makeText(TagActivity.this,"Network isn't avaialble", Toast.LENGTH_LONG).show();
				}

			}
		});

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tag, menu);
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
			case R.id.home:
				Intent info = new Intent(this, MainActivity.class);
				startActivity(info);
				break;
			}

		//}
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

	private void requestTagData(String uri) {
		
			RequestPackage td = new RequestPackage();
			td.setMethod("GET");
			td.setUri(uri);

			td.setParam("site","stackoverflow");

			tagInfoExtractor tie =new tagInfoExtractor();
			tie.execute(td);
		
	}

	private void requestData(String uri,String uri2) {
		
			RequestPackage p = new RequestPackage();
			p.setMethod("GET");
			p.setUri(uri);
			p.setParam("site","stackoverflow");
			
			RequestPackage p2 = new RequestPackage();
			p2.setMethod("GET");
			p2.setUri(uri2);
			p2.setParam("site","stackoverflow");

			tagExtractor te=new tagExtractor();
			te.execute(p,p2);
		
	}

	private class tagInfoExtractor extends AsyncTask<RequestPackage,String,String>
	{

		@Override
		protected String doInBackground(
				RequestPackage... params) {
			// TODO Auto-generated method stub
			String info="";
			String content = HttpManager.getData(params[0]);
			info=TagWikisJSONParser.TagWikisParser(content);

			return info;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			if(result!=null)
			{
				if(tagInfo.length()>12)
				{
					tagInfo.setText("");
					String info="Description:" + result;
					tagInfo.setText(info);
				}
				else
					tagInfo.append(result);
			}
			else
				tagInfo.append("No description found"); 
		}

	}
	private class tagExtractor extends AsyncTask<RequestPackage,String,HashMap<String, List<Answerer>>>
	{

		@Override
		protected HashMap<String, List<Answerer>> doInBackground(
				RequestPackage... params) {
			// TODO Auto-generated method stub
			HashMap<String, List<Answerer>> answererList=new HashMap<String, List<Answerer>>();
			
			String content = HttpManager.getData(params[0]);
			List<Answerer> recent=RecentTopAnswererJSONParser.RecentTopAnswererParser(content);
			answererList.put(listDataHeader.get(0), recent);
			
			String content2 = HttpManager.getData(params[1]);
			List<Answerer> all=AlltimeTopAnswererJSONParser.AlltimeTopAnswererParser(content2);
			answererList.put(listDataHeader.get(1), all);
			
			return answererList;
		}

		@Override
		protected void onPostExecute(HashMap<String, List<Answerer>> result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			listAdapter = new ExpandableListAdapter(TagActivity.this, listDataHeader, result);
			expListView.setAdapter(listAdapter);
		}

	}
	
}



