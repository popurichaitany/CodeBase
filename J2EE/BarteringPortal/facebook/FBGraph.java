package facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class FBGraph {
	private String accessToken;

	public FBGraph(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getFBGraph() {
		System.out.println("In function updated fb graph");
		String graph = null;
		try {

			String g = "https://graph.facebook.com/v2.5/me?access_token=" + accessToken+"&fields=id,name,first_name,last_name,email";
			graph=doConnect(g);
			System.out.println("Response obtained from graph me URL :: "+graph);
            
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in getting FB graph data. " + e);
		}
		return graph;
	}
	
	private String doConnect(String pUrl) throws IOException{
		String lRespMesg = "";
		
		   HttpURLConnection urlconn = null;
		   URL url = new URL(pUrl);	    	   
		
		   urlconn = (HttpURLConnection) url.openConnection();
		   urlconn.setInstanceFollowRedirects(true);
		   urlconn.setRequestMethod("GET");
		   urlconn.setDoOutput(true);
		   urlconn.connect();
		   int respCode = urlconn.getResponseCode();
		   
		   if(respCode!=HttpURLConnection.HTTP_CLIENT_TIMEOUT && respCode==HttpURLConnection.HTTP_OK){
		   
		   String lStream = "";
		   BufferedReader inp = new BufferedReader(new InputStreamReader(
		     urlconn.getInputStream()));
		   while ((lStream = inp.readLine()) != null) {
		    lRespMesg = lRespMesg + lStream;
		   }
		   inp.close();
		   urlconn.disconnect();
		 	{
		 		System.out.println("Response :: "+lRespMesg);
		 	}
		   }else{
			   {
				   System.out.println("Error in connection");
			   }
		   }
		   return lRespMesg;
	}

	public Map<String, String> getGraphData(String fbGraph) {
		System.out.println("Entering method to fetch graph data");
		Map<String, String> fbProfile = new HashMap<String, String>();
		try {
			JSONObject json = new JSONObject(fbGraph);
			Iterator<?> keys = json.keys();

			while( keys.hasNext() ) {
			    String key = (String)keys.next();
			    String value = json.getString(key);
			    fbProfile.put(key, value);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in parsing FB graph data. " + e);
		}
		return fbProfile;
	}
}
