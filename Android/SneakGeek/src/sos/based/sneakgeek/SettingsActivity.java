package sos.based.sneakgeek;

import sos.based.sneakgeek.database.DataManip;
import sos.based.sneakgeek.web.TimeTracker;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;


public class SettingsActivity extends Activity {
	Spinner tagQuery;
	Button go;
	String refer[];
	int flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		refer=MainActivity.subscription;
		flag=0;
		tagQuery= (Spinner) findViewById(R.id.technology);
		go=(Button) findViewById(R.id.subscribe);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.subcribe_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tagQuery.setAdapter(adapter);
		
		go.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String cmp=tagQuery.getSelectedItem().toString().toLowerCase();
				/*for(int j=0;j<refer.length;j++)
				{
					if(cmp.equals(refer[j]))
					{
						flag=1;
					}
				}
				if(flag==0)
				{
					/*ContentValues values2=new ContentValues();
					values2.put(DataManip.COLUMN_TECHNOLOGY,tagQuery.getSelectedItem().toString().toLowerCase());
					MainActivity.db.insert("subscribe", null, values2);*/
					int tm=-1;
					String noval="_id != ?";
					String novals[]={"" + tm};

					ContentValues values3=new ContentValues();
					values3.put(DataManip.COLUMN_TECHNOLOGY ,cmp);
					MainActivity.db.update("subscribe", values3,noval,novals);
				/*}
				else
				{
					Toast.makeText(getApplicationContext(),"Cannot subscribe again, already subscribed to this technology", Toast.LENGTH_LONG).show();
				}*/

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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
