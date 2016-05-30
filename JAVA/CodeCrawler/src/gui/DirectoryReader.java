package gui;
import java.io.File;

public class DirectoryReader {

  String nam;
  static int lng=0;
  static int spc_count=-1;
  static String path[]=new String[100]; 
  public static fileBackup fB;
  static void Process(File aFile) {
    spc_count++;
    String spcs = "";
    for (int i = 0; i < spc_count; i++)
      spcs += " ";
    if(aFile.isFile())
      //System.out.println(spcs + "[FILE] " + aFile.getName());
    {
      if(aFile.getName().endsWith(".java"))
      {
       path[lng]=aFile.getAbsolutePath();
       lng++;
      }
    }
    else if (aFile.isDirectory()) {
      File[] listOfFiles = aFile.listFiles();
      if(listOfFiles!=null) {
        for (int i = 0; i < listOfFiles.length; i++)
          Process(listOfFiles[i]);
      } 
    }
    spc_count--;
   }

  public DirectoryReader(String p1)
  {
	  nam=p1;
	 //nam = "C:\\Documents and Settings\\kt00109189\\Desktop\\ccm_proj";
	  File aFile = new File("D:\\ccm_proj");
	    Process(aFile);
	   fB=new fileBackup();
//	    for(int i=0;i<lng;i++)
//	    {
//	    	
//	    	if(i<=lng-2)
//	    	{
//	    		System.out.println("Next file in process");  
//	    	 new fileBackup(path[i],0);
//	    	}
//	    	else
//	    	{
//	    		  
//	    		int tp=fB.f1.length;
//	    		System.out.println("Last file no."+tp+"processed"); 
//	    		for(int u=0;u<tp;u++)
//	    		{
//	    			System.out.println(fB.f1[u]);  
//	    		}
//	 	    	 new fileBackup(path[i],1); 
//	    	}
//	    }
  }
}
