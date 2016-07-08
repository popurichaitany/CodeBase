package sos.based.sneakgeek;

import java.util.List;

import sos.based.sneakgeek.adaptor.TutorialAdaptor;
import sos.based.sneakgeek.adaptor.questionAdaptor;
import sos.based.sneakgeek.model.Question;
import sos.based.sneakgeek.model.Tutorial;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class TuotorialListActivity extends Activity {

	public static String VIDEO="4SK0cUNMnMM";
	TextView resultTxt;
	ListView tuotoList;
	public static List<Tutorial> ttrls;
	public static Tutorial ttDetail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tuotorial_list);
		
		resultTxt = (TextView) findViewById(R.id.TtlCount);
		tuotoList = (ListView) findViewById(R.id.TtlList);

		if(MainActivity.source==1)
		{
			if(MainActivity.ttl!=null)
			{
				resultTxt.setText(MainActivity.ttl.size()+ " tutorial(s) found");
				ttrls=MainActivity.ttl;
				TutorialAdaptor ta=new TutorialAdaptor(TuotorialListActivity.this, R.layout.activity_tuotorial_list, ttrls);
				tuotoList.setAdapter(ta);
			}
		}
		
		tuotoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ttDetail=ttrls.get(position);
				TuotorialListActivity.VIDEO=ttDetail.getVideoId();
				Intent myIntent = new Intent(getApplicationContext(), TutorialActivity.class);
				startActivity(myIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tuotorial_list, menu);
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
}
