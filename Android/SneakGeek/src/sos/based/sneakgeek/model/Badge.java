package sos.based.sneakgeek.model;

import java.net.URL;

public class Badge {

	String badgeType,rank,link;
	int awardCount;
	
	public String getBadgeType() {
		return badgeType;
	}

	public void setBadgeType(String badgeType) {
		this.badgeType = badgeType;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public int getAwardCount() {
		return awardCount;
	}

	public void setAwardCount(int award_count) {
		this.awardCount = award_count;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
}
