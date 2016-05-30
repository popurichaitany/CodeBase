package Home;



public class Node
{
	String Name,ID,Parent,type,path;
	


	String CLass[]=new String[32];
	String Method[][]=new String[32][16];
	String Target[][]=new String[16][16];
	int c=0,m=0,t=0;
	
	
	static int cnt=0;
	


	public static Node h[]=new Node[1024];
	
	public Node()
	{
		
		for(int i=0;i<32;i++)
		{
			CLass[i]="";
			for(int j=0;j<16;j++)
			{
				Method[i][j]="";
				if(i<16)
				Target[i][j]="";
			}
		}
	
	}
	
	public static int getCnt() {
		return cnt;
	}

	public static void setCnt(int cnt) {
		Node.cnt = cnt;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
		
	}


	public String getID() {
		return ID;
	}


	public void setID(String id) {
		ID = id;
	}


	public String getParent() {
		return Parent;
	}


	public void setParent(String parent) {
		Parent = parent;
	}


	


	public String[][] getMethod() {
		return Method;
	}


	public void setMethod(String[][] method) {
		Method = method;
	}


	public String[][] getTarget() {
		return Target;
	}


	public void setTarget(String[][] target) {
		Target = target;
	}


	


	
	
}

