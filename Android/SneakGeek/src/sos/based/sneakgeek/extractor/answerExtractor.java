package sos.based.sneakgeek.extractor;

public class answerExtractor extends UrlContentExtractor {

	public String content;
	public String AnswerBody;
	public String AnswerTimestamp;
	
	public void extractor(String c) {
		// TODO Auto-generated method stub
		c=this.removeTags(c);
		c=this.restoreSpecialCharacter(c);
		this.setQuestionBody(c);
	}

	public String getAnswerBody() {
		return AnswerBody;
	}
	public void setQuestionBody(String questionBody) {
		this.AnswerBody = questionBody;
	}
	public String getPostTimestamp() {
		return AnswerTimestamp;
	}
	public void setPostTimestamp(String postTimestamp) {
		this.AnswerTimestamp = postTimestamp;
	}
}
