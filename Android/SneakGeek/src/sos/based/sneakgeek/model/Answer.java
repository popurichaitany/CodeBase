package sos.based.sneakgeek.model;

import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;

public class Answer {
	Long answerId;
	int upvotes;
	int downvotes;
	String reply;
	String answerTimestamp;
	String user;
	URL profilePic;
	Bitmap profilePicture;
	
	public Long getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}
	public int getUpvotes() {
		return upvotes;
	}
	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}
	public int getDownvotes() {
		return downvotes;
	}
	public void setDownvotes(int downvotes) {
		this.downvotes = downvotes;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getAnswerTimestamp() {
		return answerTimestamp;
	}
	public void setAnswerTimestamp(String answerTimestamp) {
		this.answerTimestamp = answerTimestamp;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public URL getProfilePic() {
		if(profilePic != null)
			return profilePic;
		else
			try {
				return new URL("https://www.gravatar.com/avatar/b3a38c8dbc7bbfb3f1f7b066f8e32a5a?s=128&d=identicon&r=PG&f=1");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
				
			}
	}
	public void setProfilePic(String profilePic) {
		try {
			this.profilePic = new URL(profilePic);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			try {
				this.profilePic=new URL("https://www.gravatar.com/avatar/b3a38c8dbc7bbfb3f1f7b066f8e32a5a?s=128&d=identicon&r=PG&f=1");
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	public Bitmap getProfilePicture() 
	{
		return profilePicture;
	}
	public void setProfilePicture(Bitmap profilePicture) {
		this.profilePicture = profilePicture;
	}
	
	
}
