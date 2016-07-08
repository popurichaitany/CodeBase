package sos.based.sneakgeek.model;

import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;

public class Question {
	Long questionId;
	String title;
	String askedBy;
	URL profilePic;
	String description;
	String postedDate;
	Bitmap profilePicture;
	
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long l) {
		this.questionId = l;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAskedBy() {
		return askedBy;
	}
	public void setAskedBy(String askedBy) {
		this.askedBy = askedBy;
	}
	public URL getProfilePic() {
		return profilePic;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}
	public Bitmap getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(Bitmap profilePicture) {
		this.profilePicture = profilePicture;
	}
	
	
	
}
