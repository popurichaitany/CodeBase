package sos.based.sneakgeek.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TagWikisJSONParser {

	public static String TagWikisParser(String content)
	{
		String tagInformation="";
		try 
		{
			JSONObject jo = new JSONObject(content);
			//arr = new JSONArray(content);
			//JSONObject obj = arr.getJSONObject(i);
			
			JSONArray items= (JSONArray) jo.get("items");
			
			for(int i=0; i<items.length(); i++) 
			{
				JSONObject obj = items.getJSONObject(i);
				tagInformation=obj.getString("excerpt");
			}
			
			return tagInformation;
		}catch(JSONException je)
		{
			return null;
		}catch(Exception e)
		{
			return null;
		}
	}
				
}
