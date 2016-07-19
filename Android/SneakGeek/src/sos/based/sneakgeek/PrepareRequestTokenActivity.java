package sos.based.sneakgeek;

import sos.based.sneakgeek.web.*;
import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;

public class PrepareRequestTokenActivity extends Activity {
	static int flag;
	public static String accessToken,key="nfOcsY6TE8)Et*dyxUKa8g((";
	TextView token;
	WebView web;
	Button auth;
	SharedPreferences sneakgeekAccessToken;
	String uriPost="http://api.stackexchange.com/2.2/question/add",uri="none";

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prepare_request_token);
		sneakgeekAccessToken = this.getSharedPreferences("sos.based.sneakgeek.token", Context.MODE_PRIVATE); 	
		accessToken=sneakgeekAccessToken.getString("accesstoken","none");
		if(accessToken.equals("none"))	{
			auth = (Button)findViewById(R.id.launch);

			RequestPackage p = new RequestPackage();
			p.setMethod("GET");
			p.setUri("https://stackexchange.com/oauth/dialog");

			p.setParam("client_id", "4529");
			p.setParam("scope","no_expiry,write_access"); 
			p.setParam("redirect_uri","https://stackexchange.com/oauth/login_success");

			uri= p.getUri();
			if(p.getMethod().equals("GET")) {
				uri += "?" + p.getEncodedParams();
			}

			auth.setOnClickListener(new View.OnClickListener() {
				Dialog oauth_dialog;
				public void onClick(View v) {
					oauth_dialog = new Dialog(PrepareRequestTokenActivity.this);
					oauth_dialog.setContentView(R.layout.oauth_ui);
					web = (WebView)oauth_dialog.findViewById(R.id.webv);
					web.getSettings().setJavaScriptEnabled(true);
					web.loadUrl(uri);
					web.setWebViewClient(new WebViewClient(){

						@Override
						public void onPageStarted(WebView view, String url, Bitmap favicon){
							super.onPageStarted(view, url, favicon);
						}

						@Override
						public void onPageFinished(WebView view, String url) {
							super.onPageFinished(view, url);

							if (url.contains("access_token=")) {
								Uri uri = Uri.parse(url);
								if (uri != null) {
									new RetrieveAccessTokenTask().execute(uri);
									
									RequestPackage p2 = new RequestPackage();
									p2.setMethod("GET");
									p2.setUri(uriPost);

									p2.setParam("title",UserActivity.title);
									p2.setParam("body",UserActivity.content);
									p2.setParam("key",key);
									p2.setParam("access_token",accessToken);
									p2.setParam("preview","false");

									AuthorizeTask athTsk =new AuthorizeTask();
									athTsk.execute(p2); 
									
									oauth_dialog.dismiss();
								}
							}
						}
					}); 
					oauth_dialog.show();
					oauth_dialog.setTitle("Authorize User");
					oauth_dialog.setCancelable(true);
				}

			});
		} 
		
		Intent postHome = new Intent(this,UserActivity.class);
		startActivity(postHome);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prepare_request_token, menu);
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

	private class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, String> {

		@Override
		protected String doInBackground(Uri... params) {
			// TODO Auto-generated method stub
			String at2="";
			try
			{
				final Uri uri = params[0];
				final String at = uri.toString();
				int start=at.indexOf("access_token");
				int end=at.length();
				at2=at.substring(start+13,end);

			}catch(Exception e){
				runOnUiThread(new Runnable() {

					public void run() {
						Toast.makeText(getApplicationContext(),"Error : Token retrieval failed ", Toast.LENGTH_LONG).show();
					}
				});
			}
			return at2;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null && !result.equals(""))
			{
				accessToken=result;
				sneakgeekAccessToken.edit().putString("accesstoken", accessToken).apply();
			}
			else
				Toast.makeText(getApplicationContext(),"Error : Token retrieval failed, unable to post your question", Toast.LENGTH_LONG).show();
		}

	}

	private class AuthorizeTask extends AsyncTask<RequestPackage,String,Boolean>
	{

		@Override
		protected Boolean doInBackground(RequestPackage... params) {
			// TODO Auto-generated method stub

			String content = HttpManager.getData(params[0]);
			if(content != null)
				return true;
			else
				return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result)
			{
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(), "Query posted successfully", Toast.LENGTH_SHORT).show();
					}
				});
			}
			else
			{
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(), "Failed to post query, try again with hint provided", Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
	}
}

/*			runOnUiThread(new Runnable() {

					public void run() {
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "Access_token:"+at2, Toast.LENGTH_LONG).show();
					}
				});
 */
