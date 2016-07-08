package sos.based.sneakgeek.model;

import java.util.List;

public class Notification {
	
	List<Question> ql=null;
	List<Tutorial> tl=null;
	String label;
	String type;
	
	public List<Question> getQl() {
		return ql;
	}
	public void setQl(List<Question> ql) {
		this.ql = ql;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Tutorial> getTl() {
		return tl;
	}
	public void setTl(List<Tutorial> tl) {
		this.tl = tl;
	}
}
