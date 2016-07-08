package sos.based.sneakgeek;

import java.util.ArrayList;
import java.util.List;

import sos.based.sneakgeek.adaptor.answerAdaptor;
import sos.based.sneakgeek.extractor.answerExtractor;
import sos.based.sneakgeek.extractor.questionExtractor;
import sos.based.sneakgeek.model.Answer;
import sos.based.sneakgeek.model.Question;
import sos.based.sneakgeek.web.*;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends Activity {

	private Question current;
	ImageView profilePic;
	TextView qText;
	TextView qDetails;
	ListView answers;
	List<Answer> al=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);

		this.setTitle("SneakGeek - Search results"); 
		setCurrentQuestion();

		profilePic = (ImageView) findViewById(R.id.userImg);
		profilePic.setImageBitmap(current.getProfilePicture());

		qText = (TextView) findViewById(R.id.title);
		qText.setText(current.getTitle());

		qDetails = (TextView) findViewById(R.id.qDetails);
		qDetails.setMovementMethod(new ScrollingMovementMethod());
		queExtractor qE = new queExtractor();
		qE.execute(current.getDescription());

		answers = (ListView) findViewById(R.id.ansList);
		AnsExtractor at = new AnsExtractor();
		at.execute(current.getDescription());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question, menu);
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

	private void setCurrentQuestion()
	{
			current=QuestionListActivity.questionDetail;
	}

	private class queExtractor extends AsyncTask<String,String,String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			String e="question";
			String qLink = params[0];
			String content=HttpManager.getContent(qLink,e);
			questionExtractor qe = new questionExtractor();
			qe.extractor(content);
			String qDescr = qe.getQuestionBody();
			return qDescr;

		}

		@Override
		protected void onPostExecute(String qBody)
		{
			qDetails.setText(qBody);
		}

	}
	private class AnsExtractor extends AsyncTask<String,String,List<Answer>>
	{

		@Override
		protected List<Answer> doInBackground(String... params) {
			// TODO Auto-generated method stub
			String e="answer";
			List<String> ans=new ArrayList<String>();
			al=new ArrayList<>();
			String aLink = params[0];
			String content=HttpManager.getContent(aLink,e);
			answerExtractor ae = new answerExtractor();

			if(content!=null)
			{
				ae.extractor(content);
				String ansDescr = ae.getAnswerBody();
				String[] answers = ansDescr.split(">%");
				for(int j=0;j<answers.length-1;j++)
				{
					Answer a = new Answer();
					String ID,UPS,REPLY,TIME,IMG,BY;
					if(answers[j].contains("id:~") && answers[j].contains("~:di"))
					{
						ID=answers[j].substring(answers[j].indexOf("id:~")+4, answers[j].indexOf("~:di"));
						a.setAnswerId(Long.valueOf(ID));
					}
					if(answers[j].contains("ups:~") && answers[j].contains("~:spu"))
					{
						UPS=answers[j].substring(answers[j].indexOf("ups:~")+5, answers[j].indexOf("~:spu"));
						a.setUpvotes(Integer.valueOf(UPS));  
					}
					if(answers[j].contains("reply:~") && answers[j].contains("~:ylper"))
					{
						REPLY=answers[j].substring(answers[j].indexOf("reply:~")+7, answers[j].indexOf("~:ylper"));
						a.setReply(REPLY); 
					}
					if(answers[j].contains("time:~") && answers[j].contains("~:emit"))
					{
						TIME=answers[j].substring(answers[j].indexOf("time:~")+6, answers[j].indexOf("~:emit"));
						a.setAnswerTimestamp(TIME); 
					}
					if(answers[j].contains("profImg:~") && answers[j].contains("~:gmIforp"))
					{
						IMG=answers[j].substring(answers[j].indexOf("profImg:~")+9, answers[j].indexOf("~:gmIforp"));
						a.setProfilePic(IMG);
					}
					if(answers[j].contains("by:~") && answers[j].contains("~:yb"))
					{
						BY=answers[j].substring(answers[j].indexOf("by:~")+4, answers[j].indexOf("~:yb"));
						a.setUser(BY); 
					}
					ans.add(answers[j]);
					al.add(a);
				}
			}
			else
			{
				Log.d("Unfort","Unable read webpage");
			}

			return al;
		}

		@Override
		protected void onPostExecute(List<Answer> result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			if(result!=null)
			{
				answerAdaptor a=new answerAdaptor(QuestionActivity.this, R.layout.activity_question, result);
				answers.setAdapter(a);
			}
			else
			{
				Toast.makeText(QuestionActivity.this, "Failed to extract answer", Toast.LENGTH_LONG).show();
			}
		}

	}
}
