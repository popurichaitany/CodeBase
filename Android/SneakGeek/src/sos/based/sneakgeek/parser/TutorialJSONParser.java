package sos.based.sneakgeek.parser;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import sos.based.sneakgeek.model.Tutorial;

public class TutorialJSONParser {

	public static List<Tutorial> TutorialParser(String content)
	{
		try 
		{
			JSONObject jo = new JSONObject(content);
			//arr = new JSONArray(content);
			//JSONObject obj = arr.getJSONObject(i);
			
			List<Tutorial> ltt = new ArrayList<>();
			JSONArray items= (JSONArray) jo.get("items");
			
			for(int i=0; i<items.length(); i++) 
			{
				Tutorial tt = new Tutorial();
				
				JSONObject obj = items.getJSONObject(i);
				JSONObject id = (JSONObject) obj.get("id");
				JSONObject snippet = (JSONObject) obj.get("snippet");
				JSONObject thumbnails = (JSONObject) snippet.get("thumbnails");
				JSONObject medium = (JSONObject) thumbnails.get("medium");
				
				tt.setPublishedAt(snippet.getString("publishedAt"));
				tt.setTitle(snippet.getString("title"));
				tt.setUrl(new URL(medium.getString("url")));
				tt.setVideoId(id.getString("videoId"));
				
				ltt.add(tt);
			}
			
			return ltt;
		}catch(Exception e)
		{
			return null;
		}
	}
}
