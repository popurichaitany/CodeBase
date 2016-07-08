package sos.based.sneakgeek.web;

public class TagExtractor {

	String []t=new String[5];
	private String question;
	int tagLimit=5;
	String []knownTags={"c","array","arrays","hashmap","recursion","objective-c","c++","java","c#","shell","perl","javascript","jquery","html","css","xml","json","bootstrap","jsp","servlet","j2ee","cookie","sqlite","oracle","sql","plsql","mysql","eclipse","asycntask","hamer","android"};
	public TagExtractor(String q)
	{
		this.question=q;
	}
	public boolean isTag(String t)
	{
		for(int k=0;k<knownTags.length;k++)
		{
			String kt=knownTags[k].toLowerCase();
			if(kt.equals(t.toLowerCase()))
				return true;
		}
		return false;
	}
	public String resultanTag(String que)
	{
		String result="";
		String []units=que.split(" ");
		
		for(int i=0;i<units.length;i++)
		{
			for(int j=0;j<knownTags.length;j++)
			{
				if(result.length()>0)
				{
					int popdTags=result.split(";").length;
					if(popdTags==tagLimit)
						return result;
				}
				if(units[i].equals(knownTags[j]))
				{
					result += units[i];
					result += ";";
				}
			}
		}
		
		return result;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
}
