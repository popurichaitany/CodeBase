package sos.based.sneakgeek.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import sos.based.sneakgeek.model.Answerer;


public class RecentTopAnswererJSONParser {

	public static List<Answerer> RecentTopAnswererParser(String content)
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
				 
				//int temp=obj.getInt("post_count");
				//Log.d("pc",""+temp);
				answ.setPostCount(obj.getInt("post_count"));
				
				answ.setScore(obj.getInt("score"));
				//temp=obj.getInt("score");
				//Log.d("s",""+temp);
				
				JSONObject user = (JSONObject) obj.get("user");
				
				if(user.has("accept_rate")) 
				{
					answ.setAcceptRate(user.getInt("accept_rate"));
					//int t=user.getInt("accept_rate");
					//Log.d("ar",""+t);
				}
				
				//String pent=user.getString("display_name");
				//Log.d("dn", pent);
				answ.setName(user.getString("display_name"));
				
				String result=user.getString("profile_image");
				answ.setProfilePic(result);
				//Log.d("pi", result);
				
				answ.setUserType(user.getString("user_type"));  
				//pent=user.getString("user_type");
				//Log.d("ut", pent);
				
				
				//temp=obj.getInt("accept_rate");
				//Log.d("ar",""+temp);
				
				answ.setReputation(user.getInt("reputation"));
				//int tp=user.getInt("reputation");
				//Log.d("r",""+tp);
				
				answererList.add(answ);
			}
		
			return answererList;
		}catch(JSONException je)
		{
			Log.d("jtype", "json"); 
			return null;
		}catch(Exception e)
		{
			return null;
		}
		
	
	}
}
