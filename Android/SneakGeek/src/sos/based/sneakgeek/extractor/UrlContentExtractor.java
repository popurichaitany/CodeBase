package sos.based.sneakgeek.extractor;

public class UrlContentExtractor {

	public String restoreSpecialCharacter(String qbody) {
		// TODO Auto-generated method stub
		//
		if(qbody.contains("&#233"))
		{
			qbody=qbody.replace("&#233", "e");
		}
		if(qbody.contains("&gt;"))
		{
			qbody=qbody.replace("&gt;", ">");
		}
		if(qbody.contains("&lt;"))
		{
			qbody=qbody.replace("&lt;", "<");
		}
		if(qbody.contains("&#39;"))
		{
			qbody=qbody.replace("&#39;", "'");
		}
		if(qbody.contains("&quot;"))
		{
			qbody=qbody.replace("&quot;", "'");
		}
		if(qbody.contains("&#246;"))
		{
			qbody=qbody.replace("&#246;", "o");
		}
		if(qbody.contains("&#227;"))
		{
			qbody=qbody.replace("&#227;", "a");
		}
		if(qbody.contains("&#169;"))
		{
			qbody=qbody.replace("&#169;", "copyright");
		}

		return qbody;
	}


	public String removeTags(String qbody) {
		// TODO Auto-generated method stub
		if(qbody.contains("<p>"))
		{
			qbody=qbody.replace("<p>", "");
		}
		if(qbody.contains("</p>"))
		{
			qbody=qbody.replace("</p>", "");
		}
		if(qbody.contains("<pre>"))
		{
			qbody=qbody.replace("<pre>", "");
		}
		if(qbody.contains("</pre>"))
		{
			qbody=qbody.replace("</pre>", "");
		}
		if(qbody.contains("<code>"))
		{
			qbody=qbody.replace("<code>", "");
		}
		if(qbody.contains("</code>"))
		{
			qbody=qbody.replace("</code>", "");
		}
		if(qbody.contains("<br>"))
		{
			qbody=qbody.replace("<br>", "");
		}
		if(qbody.contains("<strong>"))
		{
			qbody=qbody.replace("<strong>", "");
		}
		if(qbody.contains("<div>"))
		{
			qbody=qbody.replace("<div>", "");
		}
		if(qbody.contains("</strong>"))
		{
			qbody=qbody.replace("</strong>", "");
		}
		if(qbody.contains("<blockquote>"))
		{
			qbody=qbody.replace("<blockquote>", "");
		}
		if(qbody.contains("</a>"))
		{
			qbody=qbody.replace("</a>", "");
		}
		if(qbody.contains("</em>"))
		{
			qbody=qbody.replace("</em>", "");
		}
		if(qbody.contains("<em>"))
		{
			qbody=qbody.replace("<em>", "");
		}
		if(qbody.contains("</blockquote>"))
		{
			qbody=qbody.replace("</blockquote>", "");
		}
		
		return qbody;
	}
	
}
