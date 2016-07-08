package sos.based.sneakgeek.model;

import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;

public class User {

	String name;
	String userType;
	URL profilePic;
	Bitmap profilePicture;
	int reputation=0;
	int acceptRate=0;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public URL getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String result) {
		try {
			this.profilePic = new URL(result);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Bitmap getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(Bitmap profilePicture) {
		this.profilePicture = profilePicture;
	}
	public long getReputation() {
		return reputation;
	}
	public void setReputation(int l) {
		this.reputation = l;
	}
	public long getAcceptRate() {
		return acceptRate;
	}
	public void setAcceptRate(int l) {
		this.acceptRate = l;
	}
}
