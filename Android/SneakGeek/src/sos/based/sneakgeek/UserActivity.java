package sos.based.sneakgeek;

import java.util.List;

import sos.based.sneakgeek.model.Question;
import sos.based.sneakgeek.model.User;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class UserActivity extends Activity {
	TextView response;
	ListView users;
	Button searchButton;
	EditText srchName;

	String userSearch="http://api.stackexchange.com/2.2/users";
	String faqPost="";
	List<User> userList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

		response=(TextView) findViewById(R.id.responseTxt);
		users = (ListView) findViewById(R.id.uList);
		searchButton = (Button) findViewById(R.id.sarchButton);
		srchName = (EditText) findViewById(R.id.sarchTxt);

		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Do something in response to button click
				hideKeyboard(UserActivity.this);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
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
	
	public static void hideKeyboard(Activity activity)
	{
		InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
}
