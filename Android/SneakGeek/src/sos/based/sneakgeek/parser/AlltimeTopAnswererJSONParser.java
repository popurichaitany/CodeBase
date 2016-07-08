package sos.based.sneakgeek.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import sos.based.sneakgeek.model.Answerer;

public class AlltimeTopAnswererJSONParser {

	public static List<Answerer> AlltimeTopAnswererParser(String content)
	{
		List<Answerer> answererList=null;
		try 
		{
			JSONObject jo = new JSONObject(content);
			//arr = new JSONArray(content);
			//JSONObject obj = arr.getJSONObject(i);
			answererList = new ArrayList<>();
			JSONArray items= (JSONArray) jo.get("items");
			
			for(int i=0; i<items.length(); i++) 
			{
				
				Answerer answ = new Answerer();
				JSONObject obj = items.getJSONObject(i);
				 
				if(obj.has("post_count"))
					answ.setPostCount(obj.getInt("post_count"));
				if(obj.has("score"))
					answ.setScore(obj.getInt("score"));
							
				JSONObject user = (JSONObject) obj.get("user");
				 
				answ.setName(user.getString("display_name"));
				String result=user.getString("profile_image");
				answ.setProfilePic(result);
				answ.setUserType(user.getString("user_type"));  
				if(user.has("accept_rate")) 
					answ.setAcceptRate(user.getInt("accept_rate")); 
				if(user.has("reputation")) 
					answ.setReputation(user.getInt("reputation")); 
				
				answererList.add(answ);
			}
		
			return answererList;
		}catch(JSONException je)
		{
			Log.d("ajtype", "json"); 
			return null;
		}catch(Exception e)
		{
			return null;
		}
		
	}
}
