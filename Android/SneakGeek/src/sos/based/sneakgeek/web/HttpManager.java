package sos.based.sneakgeek.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {

	public static String getData(RequestPackage p)
	{
		
		
		BufferedReader reader = null;
		String uri = p.getUri();
		if(p.getMethod().equals("GET")) {
			uri += "?" + p.getEncodedParams();
		}

		try
		{
			URL url=new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(p.getMethod());
			//JSONObject json = new JSONObject(p.getParams());
			//String params = "params" + json.toString();

			StringBuilder sb=new StringBuilder();
			reader=new BufferedReader(new InputStreamReader(con.getInputStream()));

			String line;
			while((line= reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}

			return sb.toString();

		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		} finally 
		{
			if(reader != null)
			{
				try
				{
					reader.close();
				} catch (IOException ioe)
				{
					ioe.printStackTrace();
					return null;
				}
			}
		}

	}

	public static String getContent(String uri,String element)
	{
		BufferedReader reader = null;

		try
		{
			//uri=URLEncoder.encode(uri, "UTF-8");
			URL url=new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			//con.setRequestMethod("GET");
			//con.connect();
			reader=new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder sb=new StringBuilder();
			String line;
			int startExtraction=0;
			while((line= reader.readLine()) != null)
			{
				switch(element)
				{
				case "question": if(startExtraction==2)
								 {
										if(line.contains("</div>"))
											startExtraction++;
										else if(line.length()>0)
										{
											sb.append(line + "\n");
											if(line.contains(":\n"));
												sb.append("\n");
										}
								 }
								 if(line.contains("question-title"))
									  startExtraction++;
								 if(line.contains("post-text") && startExtraction==1)
									  startExtraction++;
								  break;
				case "answer" : if(startExtraction==8)
								{
									String b="by:~" +line.substring(line.indexOf(">") + 1,line.indexOf("</a>")) + "~:yb>%\n";
									sb.append(b);
									startExtraction=0;
								}
								else if(startExtraction==7 && line.contains("user-details"))
									startExtraction++;
								else if(startExtraction==6)
								{
									sb.append("profImg:~"+line.substring(line.indexOf("src")+5, line.indexOf("alt") - 2) + "~:gmIforp\n");//[%+
									startExtraction++;
								}
								else if(startExtraction==5 && line.contains("user-gravatar"))
									startExtraction++;
								else if(startExtraction==4)
								{
									sb.append("time:~"+line.substring(line.indexOf(">") + 1, line.indexOf("</span>")) + "~:emit\n");//[%^
									startExtraction++;
								}
								else if(startExtraction==3 && line.contains("user-action-time"))
								{
									startExtraction++;
									sb.append("~:ylper\n");
								}
								if(startExtraction==2)
								{
									if(line.contains("</div>"))
										startExtraction++;
									else if(line.length()>0)
									{
										sb.append(line + "\n");
										if(line.contains(":\n"));
											sb.append("\n");
									}
								}
								else if(startExtraction==1 && line.contains("upvoteCount") && line.contains("mobile-vote-count"))
								{
									sb.append("ups:~"+line.substring(line.indexOf("mobile-vote-count")+19, line.indexOf("</span>")) + "~:spu\n");
									sb.append("reply:~");//|^ 
								}
								else if(line.contains("data-answerid"))
								{
									sb.append("id:~" + line.substring(line.indexOf("data-answerid")+15, line.indexOf(">")-1) + "~:di\n");//+%[ 
									startExtraction++;
								}
								else if(line.contains("post-text") && startExtraction==1)
									startExtraction++;
								break;
				case "badge" : 	if(startExtraction==1)
								{
									sb.append(line.substring(0, 12));//.substring(line.indexOf("badge-description")+21, line.indexOf("</p>")));
									break;
								}
								else if(startExtraction==0 && line.contains("badge-description") && line.contains("class"))
								{
									startExtraction++;
								}
				}
				
			}
			
			return sb.toString();

		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		} finally 
		{
			if(reader != null)
			{
				try
				{
					reader.close();
				} catch (IOException ioe)
				{
					ioe.printStackTrace();
					return null;
				}
			}
		}
	}
}
