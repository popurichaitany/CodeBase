package sos.based.sneakgeek.parser;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sos.based.sneakgeek.model.Answerer;
import sos.based.sneakgeek.model.Badge;
import android.util.Log;

public class BadgeJSONParser {

	public static List<Answerer> BadgeRecepientParser(String content)
	{
		List<Answerer> recepientList=null;
		try 
		{
			JSONObject jo = new JSONObject(content);
			//arr = new JSONArray(content);
			//JSONObject obj = arr.getJSONObject(i);
			recepientList = new ArrayList<>();
			JSONArray items= (JSONArray) jo.get("items");

			for(int i=0; i<items.length(); i++) 
			{

				Answerer recp = new Answerer();
				JSONObject obj = items.getJSONObject(i);
				
				JSONObject user = (JSONObject) obj.get("user");

				if(user.has("accept_rate")) 
				{
					recp.setAcceptRate(user.getInt("accept_rate"));
				}
				recp.setName(user.getString("display_name"));
				String result=user.getString("profile_image");
				recp.setProfilePic(result);
				recp.setUserType(user.getString("user_type"));
				recp.setReputation(user.getInt("reputation"));

				recepientList.add(recp);
			}

			return recepientList;
		}catch(JSONException jse)
		{
			return null;
		}catch(Exception e)
		{
			return null;
		}
	}

	public static Badge BadgeInfoParser(String content,String value)
	{
		try
		{
			JSONObject jo = new JSONObject(content);
			//arr = new JSONArray(content);
			//JSONObject obj = arr.getJSONObject(i);
			JSONArray items= (JSONArray) jo.get("items");

			for(int i=0; i<items.length(); i++) 
			{
				JSONObject obj = items.getJSONObject(i);
				String comp=obj.getString("name");
				if(value.toLowerCase().equals(comp.toLowerCase()))
				{
					Badge r=new Badge();
					r.setBadgeType(obj.getString("badge_type"));
					r.setRank(obj.getString("rank"));
					r.setAwardCount(obj.getInt("award_count")); 
					r.setLink(obj.getString("link")); 
					return r;
				}
			}
				
			return null;
			
			

		}catch(JSONException jse)
		{
			return null;
		}catch(Exception e)
		{
			return null;
		}
	}
}