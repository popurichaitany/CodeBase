package sos.based.sneakgeek.extractor;

public class questionExtractor extends UrlContentExtractor{

	public String content;
	public String questionBody;
	public String postTimestamp;
	
	public void extractor(String c) {
		// TODO Auto-generated method stub
		c=this.removeTags(c);
		c=this.restoreSpecialCharacter(c);
		this.setQuestionBody(c);
	}

	public String getQuestionBody() {
		return questionBody;
	}
	public void setQuestionBody(String questionBody) {
		this.questionBody = questionBody;
	}
	public String getPostTimestamp() {
		return postTimestamp;
	}
	public void setPostTimestamp(String postTimestamp) {
		this.postTimestamp = postTimestamp;
	}

}
