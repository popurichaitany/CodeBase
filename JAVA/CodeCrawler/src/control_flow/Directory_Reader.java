package control_flow;

import java.io.File;

public class Directory_Reader {

	  String nam;
	  static int lng=0;
	  static int spc_count=-1;
	  static String path[]=new String[1024]; 
	  public static int java_project=0;
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
	    	java_project=1;
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

	  
	  public Directory_Reader(String p1)
	  {
		  File aFile = new File(p1);
		    Process(aFile);
	  }
	  
	  public void DrcReader(int ctx)
	  {
		  
		 if(ctx==1)
		    {
		      Extractor e=new Extractor();
		       for(int i=0;i<lng;i++)
		       {
		    	e.Populate(path[i]); 
		       }
		       
		    }
		    
		 if(ctx==2)
		    {
		    	Invoke_tracer it=new Invoke_tracer();
		    	for(int i=0;i<lng;i++)
			       {
		    		
			    	it.Tracer(path[i]); 
		           }
		    }
	  }
}
