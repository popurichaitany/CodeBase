package sos.based.sneakgeek;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import android.os.Bundle;
import android.widget.Toast;

public class TutorialActivity extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener {

	private String YoutubeAPIKey="AIzaSyDx-MDXhjIe015fy0sECEksbz0DtHoXIAU";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial);

		YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

		youTubeView.initialize(YoutubeAPIKey, this);


		//GET https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=50&publishedAfter=
		//2015-05-01T00%3A00%3A00Z&publishedBefore=2015-05-05T00%3A00%3A00Z&q=java&relevanceLanguage=en&
		//type=video&key={YOUR_API_KEY}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tutorial, menu);
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

	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult error) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Oh no! "+error.toString(),Toast.LENGTH_LONG).show();

	}

	@Override
	public void onInitializationSuccess(Provider arg0, YouTubePlayer player,
			boolean arg2) {
		// TODO Auto-generated method stub
		player.loadVideo(TuotorialListActivity.VIDEO);
	}
}

/*{
   "kind": "youtube#searchResult",
   "etag": "\"krboRo_tpL036O3XTWYvMqtyDRY/ioJ07AsfGreAkgNW6-IzFAZ0LZU\"",
   "id": {
    "kind": "youtube#video",
    "videoId": "g-DwL9cfAJY"
   },
   "snippet": {
    "publishedAt": "2015-05-05T09:22:53.000Z",
    "channelId": "UCGlbQErbNGG3a5Ia5MW61Jw",
    "title": "Java Programming Tutorial - 26 - Random Number Generator",
    "description": "http://tinyurl.com/73ia8oc24lo Visit my website at / for all of my videos and tutorials! Have questions or looking for source code? Check out the forum at / My ...",
    "thumbnails": {
     "default": {
      "url": "https://i.ytimg.com/vi/g-DwL9cfAJY/default.jpg"
     },
     "medium": {
      "url": "https://i.ytimg.com/vi/g-DwL9cfAJY/mqdefault.jpg"
     },
     "high": {
      "url": "https://i.ytimg.com/vi/g-DwL9cfAJY/hqdefault.jpg"
     }
    },
    "channelTitle": "",
    "liveBroadcastContent": "none"
   }
  },
  {
   "kind": "youtube#searchResult",
   "etag": "\"krboRo_tpL036O3XTWYvMqtyDRY/ql1kVoBJ9Io_LzRArZAhKivVakM\"",
   "id": {
    "kind": "youtube#video",
    "videoId": "sYk9p5nEzPA"
   },
   "snippet": {
    "publishedAt": "2015-05-05T09:17:48.000Z",
    "channelId": "UCGlbQErbNGG3a5Ia5MW61Jw",
    "title": "jPET: an Automatic Test-Case Generator for Java",
    "description": "http://tinyurl.com/t6pe7o6m3pu jPET video demostration.",
    "thumbnails": {
     "default": {
      "url": "https://i.ytimg.com/vi/sYk9p5nEzPA/default.jpg"
     },
     "medium": {
      "url": "https://i.ytimg.com/vi/sYk9p5nEzPA/mqdefault.jpg"
     },
     "high": {
      "url": "https://i.ytimg.com/vi/sYk9p5nEzPA/hqdefault.jpg"
     }
    },
    "channelTitle": "",
    "liveBroadcastContent": "none"
   }
  }*/
