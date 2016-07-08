package sos.based.sneakgeek.parser;


import java.util.List;
import java.util.ArrayList;

import sos.based.sneakgeek.model.Question;

import org.json.*;

import android.util.Log;
public class QuestionJSONParser {

	public static List<Question> questionParser(String content)
	{
		List<Question> questionList,bkList=null;
		try 
		{
			JSONObject jo = new JSONObject(content);
			//arr = new JSONArray(content);
			//JSONObject obj = arr.getJSONObject(i);
			questionList = new ArrayList<>();
			JSONArray items= (JSONArray) jo.get("items");
			String l= ""+ items.length();
			Log.d("Length",l);
			for(int i=0; i<items.length(); i++) 
			{

				Question qstn = new Question();
				JSONObject obj = items.getJSONObject(i);

				qstn.setQuestionId(obj.getLong("question_id"));
				qstn.setTitle(obj.getString("title"));
				qstn.setDescription(obj.getString("link"));

				JSONObject owner = (JSONObject) obj.get("owner");

				qstn.setAskedBy(owner.getString("display_name"));
				if(owner.has("profile_image"))
				{
					String result=owner.getString("profile_image");
					qstn.setProfilePic(result);
				}
				else
				{
					qstn.setProfilePic("https://www.gravatar.com/avatar/aad2be9634781351bd67a1ae941925c5?s=128&d=identicon&r=PG&f=1");
				}
				questionList.add(qstn);
				bkList=questionList;
			}

			return questionList;
		}catch(JSONException je)
		{
			return null;
		}catch(Exception e)
		{
			return bkList;
		}


	}
}
