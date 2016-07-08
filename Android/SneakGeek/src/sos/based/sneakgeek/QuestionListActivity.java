package sos.based.sneakgeek;

import java.util.List;

import sos.based.sneakgeek.adaptor.questionAdaptor;
import sos.based.sneakgeek.model.Question;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


public class QuestionListActivity extends Activity {
	TextView resultText;
	ListView questionList;
	public static List<Question> question;
	public static Question questionDetail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_list);

		resultText = (TextView) findViewById(R.id.fetchCount);
		questionList = (ListView) findViewById(R.id.qnList);

		if(MainActivity.source==2)
		{
			if(MainActivity.q!=null)
			{
				resultText.setText(MainActivity.listLength + " result(s) found");
				question=MainActivity.q;
				questionAdaptor qa=new questionAdaptor(QuestionListActivity.this, R.layout.activity_question_list, question);
				questionList.setAdapter(qa);
			}
		}

		else if(MainActivity.source==1)
		{
			if(MainActivity.nascent!=null)
			{
				resultText.setText(MainActivity.nascent.size() + "new queries(s)");
				question=MainActivity.nascent;
				questionAdaptor qa=new questionAdaptor(QuestionListActivity.this, R.layout.activity_question_list, question);
				questionList.setAdapter(qa);
			}
		}

		questionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				QuestionListActivity.questionDetail = question.get(position);
				Intent myIntent = new Intent(getApplicationContext(), QuestionActivity.class);
				startActivity(myIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question_list, menu);
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
