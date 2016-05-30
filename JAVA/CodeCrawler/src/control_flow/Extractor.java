package control_flow;

import java.io.BufferedReader;
import java.io.*;


public class Extractor {
	static String s;
	static String atr[][][]=new String[1024][64][2]; 
    public static String mname[][][]=new String[1024][32][2];
    public static String cname[]=new String[1024];
    static String ecname[]=new String[1024];
    static int mcnt=0,cmcnt=0,mndx=0,andx=0,cFlag=0,rdnFlag=0,mthstart=0;
	static int inherit_clscnt=0,classcnt=-1;
    static String currFile,fname,dest,xloc;
	static File f1;
	
	
	public Extractor()
	{
		
	}
	
	public Extractor(String s)
	{
		for(int j=0;j<1024;j++)
		{
			cname[j]="";
			ecname[j]="";
		}

		for(int i=0;i<1024;i++)
		{
			for(int j=0;j<64;j++)
			{
				for(int k=0;k<2;k++)
				{
					atr[i][j][k]="";
					if(j<32)
 					mname[i][j][k]="";
				}
			}
		}
	}
	
	public static void main(String []args)
	{
		Extractor e=new Extractor();
		e.Control_Flow_Extractor("D:\\ccm_proj\\src\\com\\ccmpack\\CallReceiver.java","D:\\CodeCrawler_Reports.xml");
	}
	
	
	public void Populate(String c)
	{
		try
		{
			int obrc=0;
			cFlag=0;
			rdnFlag=0;
			mthstart=0;
			f1=new File(c);
			
			BufferedReader br=new BufferedReader(new FileReader(f1));
	        
			int explore_limit=0,explore_limit1=0;
	        	while((s=br.readLine())!=null)
	            {
	        		int almt=0;
	        		explore_limit=0;
	        		
	        		
	        		
	        		if(s.contains("//") && !(s.contains(";")))
	                {
	                	cmcnt++;
	                	cFlag=3;
	                }
	        		
	                if(s.contains("/*"))
	                {
	                	cFlag=1;
	                	cmcnt++;
	                	if(rdnFlag==0)
	                	{
	                	 rdnFlag=1;
	                	}
	                }
	                if((s.contains("* @") || s.contains("*")) && rdnFlag==0)
	                {
	                	if(cFlag==1)
	                    {
	                    	cmcnt++;
	                    }
	                	if(rdnFlag==0)
	                	{
	                	 rdnFlag=1;
	                	}
	                }
	                if(s.contains("*/"))
	                {
	                	if(cFlag==1)
	                    {
	                		if(rdnFlag==0)
	                    	{
	                    	 rdnFlag=1;
	                    	 cmcnt++;
	                    	}
	                    	cFlag=0; 
	                    }
	                }
	        		
	            	
	                if(s.contains("class ") && cFlag==0 && !(s.contains(".")) && !(s.contains("(")) && !(s.contains(")")) && cFlag!=3)
	                {
	                	 int cndx,endx;
                         mthstart=0;
	                	 classcnt++;
	                	 andx=0;
	                	 mcnt=0;
	                	 cndx=s.indexOf("class ");
	                	 for(int k=cndx+6;s.charAt(k)!=' ';k++)
	                	 {
	                		 cname[classcnt]=cname[classcnt]+s.charAt(k);
	                	 }
	                	 
	                	
                                      	    
	                 if(s.contains("extends"))
	                 {
	                	 endx=s.indexOf("extends ");
  	                 	
	                	 ecname[classcnt]="";
                         
                        	 for(int z=endx+8;s.charAt(z)!=' ';z++)
                        	 {
                        		 ecname[classcnt]=ecname[classcnt]+s.charAt(z);                		        		 
                        	 }
	                 }
	                 else
	                 {
	                	 ecname[classcnt]="";
	                 }
	                 
	                }
	                
	                               
	                if(s.contains("{")) 
	        			obrc++;
	        		
	        		if(s.contains("}"))
	            		obrc--;
	            		
	        		
	        		
	        		if(obrc==1)
	        		    mthstart=1;
            		else
               			mthstart=0;
	                
	        	      
	        		if((s.contains("int ") || s.contains("float ") || s.contains("long ") || s.contains("String ") || 
                        s.contains("double ")) && s.contains(";") && s.contains(",") && mthstart==1 && cFlag==0
                        && cFlag!=3) 
            {
                            
                          
	        			if(s.contains("int "))
	        			{
	        				String tmpnt=s.substring(s.indexOf("int ")+4,s.indexOf(";"));
	        				String sw[]=tmpnt.split(","); 
	        				for(int popl=0;popl<sw.length;popl++)
	        				{
	        					if(sw[popl].contains("="))
	        						sw[popl]=sw[popl].substring(0,sw[popl].indexOf("=")); 
	        					atr[classcnt][andx][0]+="int";
	        					atr[classcnt][andx][1]+=sw[popl];
	        				     
	        					andx++; 
	        				}
	        			}  
	        			if(s.contains("float "))
	        			{
	        				String  tmpst=s.substring(s.indexOf("float ")+6,s.indexOf(";"));
	        				String sw[]=tmpst.split(","); 
	        				for(int popl=0;popl<sw.length;popl++)
	        				{
	        					if(sw[popl].contains("="))
	        						sw[popl]=sw[popl].substring(0,sw[popl].indexOf("="));
	        					atr[classcnt][andx][0]+="float";
	        					atr[classcnt][andx][1]+=sw[popl];
	        			
	        					andx++; 
	        				}
	        			}
	        			if(s.contains("long "))
	        			{
	        				String  tmplg=s.substring(s.indexOf("long ")+5,s.indexOf(";"));

	        				String sw[]=tmplg.split(","); 
	        				for(int popl=0;popl<sw.length;popl++)
	        				{
	        					if(sw[popl].contains("="))
	        						sw[popl]=sw[popl].substring(0,sw[popl].indexOf("="));
	        					atr[classcnt][andx][0]+="long";
	        					atr[classcnt][andx][1]+=sw[popl];
	        			
	        					andx++; 
	        				}
	        			}

	        			if(s.contains("String "))
	        			{
	        				String  tmpst=s.substring(s.indexOf("String ")+7,s.indexOf(";"));

	        				String sw[]=tmpst.split(","); 
	        				for(int popl=0;popl<sw.length;popl++)
	        				{
	        					if(sw[popl].contains("="))
	        						sw[popl]=sw[popl].substring(0,sw[popl].indexOf("="));
	        					atr[classcnt][andx][0]+="String";
	        					atr[classcnt][andx][1]+=sw[popl];
	        			
	        					andx++; 
	        				}
	        			}
	        			if(s.contains("double "))
	        			{
	        				String  tmpdb=s.substring(s.indexOf("double ")+7,s.indexOf(";"));
	        				String sw[]=tmpdb.split(","); 
	        				for(int popl=0;popl<sw.length;popl++)
	        				{
	        					if(sw[popl].contains("="))
	        						sw[popl]=sw[popl].substring(0,sw[popl].indexOf("="));
	        					atr[classcnt][andx][0]+="double";
	        					atr[classcnt][andx][1]+=sw[popl]; 
	        			
	        					andx++; 
	        				}
	        			}
                            
                            
            }
            
            else if((s.contains("int ") || s.contains("float ") || s.contains("long ") || s.contains("String ") ||
                     s.contains("double ")) && s.contains(";") && mthstart==1 && cFlag==0 && cFlag!=3)
                            {
                                            
            	                    String tmp;
                                            if(s.contains("="))
                                            {
                                              almt=s.indexOf("=");
                                              if(s.contains("int "))
                                              {
                                                   tmp=s.substring(s.indexOf("int ")+4,almt);
                                              
                                                atr[classcnt][andx][0]+="int";   
                                                atr[classcnt][andx][1]+=tmp; 
                                
                                              }
                                              if(s.contains("float "))
                                              {
                                                  tmp=s.substring(s.indexOf("float ")+6,almt);
                                              
                                                atr[classcnt][andx][0]+="float";   
                                                atr[classcnt][andx][1]+=tmp; 
                                           
                                              } 
                                              if(s.contains("long "))
                                              {
                                                   tmp=s.substring(s.indexOf("long ")+5,almt);
                                              
                                                atr[classcnt][andx][0]+="long";   
                                                atr[classcnt][andx][1]+=tmp; 
                              
                                              }
                                              if(s.contains("double "))
                                              {
                                                   tmp=s.substring(s.indexOf("double ")+7,almt);
                                              
                                                atr[classcnt][andx][0]+="double";   
                                                atr[classcnt][andx][1]+=tmp; 
                               
                                              }
                                              if(s.contains("String "))
                                              {
                                                   tmp=s.substring(s.indexOf("String ")+7,almt);
                                              
                                                atr[classcnt][andx][0]+="int";   
                                                atr[classcnt][andx][1]+=tmp; 
                                
                                              }
                                            }
                                            else if(s.contains(";"))  
                                            {
                                            	 if(s.contains("int "))
                                                 {
                                                      tmp=s.substring(s.indexOf("int ")+4,s.indexOf(";"));
                                                 
                                                   atr[classcnt][andx][0]+="int";   
                                                   atr[classcnt][andx][1]+=tmp; 
                               
                                                 }
                                                 if(s.contains("float "))
                                                 {
                                                      tmp=s.substring(s.indexOf("float ")+6,s.indexOf(";"));
                                                 
                                                   atr[classcnt][andx][0]+="float";   
                                                   atr[classcnt][andx][1]+=tmp; 
                               
                                                 } 
                                                 if(s.contains("long "))
                                                 {
                                                      tmp=s.substring(s.indexOf("long ")+5,s.indexOf(";"));
                                                 
                                                   atr[classcnt][andx][0]+="long";   
                                                   atr[classcnt][andx][1]+=tmp; 
                               
                                                 }
                                                 if(s.contains("double "))
                                                 {
                                                      tmp=s.substring(s.indexOf("double ")+7,s.indexOf(";"));
                                                 
                                                   atr[classcnt][andx][0]+="double";   
                                                   atr[classcnt][andx][1]+=tmp; 
                               
                                                 }
                                                 if(s.contains("String "))
                                                 {
                                                      tmp=s.substring(s.indexOf("String ")+7,s.indexOf(";"));
                                                 
                                                   atr[classcnt][andx][0]+="String";   
                                                   atr[classcnt][andx][1]+=tmp; 
                               
                                                 }
                                            }
                                           
                                            
                                            
                                             andx++;
                            }
	        		
	        		
	        		
	        		if((s.contains(" public ") || s.contains(" private ") || s.contains(" protected ") || s.contains(" void ") || 
		            	s.contains(" int ") || s.contains(" float ") || s.contains(" long ") || s.contains(" String ") || 
		            	s.contains(" double ")) && s.contains("(") && s.contains(")") && !(s.contains(";")) && !(s.contains("."))
		            	&& cFlag!=3)   
		            	{ 
		            		
			                explore_limit=s.indexOf("(");
		            		explore_limit1=s.indexOf(")");
		            		String t="",t2="";
		               		t=s.substring(0,explore_limit+1);
		               	
		               		if(explore_limit1!=explore_limit+1)
		               		{
		               			t2=s.substring(explore_limit+1, explore_limit1-1);
		               			
		               			String prm[]=t2.split(","); 
		               			mname[classcnt][mcnt][0]="";
		               			mname[classcnt][mcnt][0]+=t;

		               			for(int p=0;p<prm.length;p++)
		               			{
		               				if(prm[p].startsWith(" "))
		               				{
		               					prm[p]=prm[p].substring(1,prm[p].length()-1);
		               				}
		               				if(prm[p].endsWith(" "))
		               				{
		               					prm[p]=prm[p].substring(0,prm[p].length()-2);
		               				}
		               				prm[p]=prm[p].substring(0,prm[p].indexOf(" "));
		               				mname[classcnt][mcnt][0]+=prm[p];
		               				if(p<prm.length-1)
		               					mname[classcnt][mcnt][0]+=",";
		               			}
		               		}
		               		else 
		               		{
		               			mname[classcnt][mcnt][0]="";
			               		mname[classcnt][mcnt][0]+=t;
		               		}
		               		mname[classcnt][mcnt][0]+=")";
		               		mname[classcnt][mcnt][1]=s.substring(0,explore_limit1+1);
		               		 
		               		            		
		               		
		               	    mcnt++; 	            		
		             	   } 
	        		
	                if(cFlag==3)
	                	cFlag=0;
	            }
		}catch(ArrayIndexOutOfBoundsException ai)
		{
			System.out.println(ai.getStackTrace()); 
		}
		catch(Exception e)
	     {
	       e.getMessage();  
	     }

	}
	
	public void Control_Flow_Extractor(String path,String xmlPath)
	{
	  File r=new File(path);
	  String cmp_fname=r.getName();
	  //String fc,fm,fs;
	  int i=-1,j=-1,cf=0,k=-1;
	  String fcname[]=new String[16];
	  String fmname[][]=new String[16][32];
	  String fsname[][][]=new String[32][32][32];
	  if(r.isFile())
	  {
		  File xml=new File(xmlPath);
		   try
		   {
     	    BufferedReader br=new BufferedReader(new FileReader(xml));
     	    String s;
     	    while((s=br.readLine())!=null)
     	    {
     	    	if(s.contains("<Entity>"))
     	    	{
                     String e="";
     	    		
     	    	       s=br.readLine();
     	    	       if(s.substring(s.indexOf("<Name>")+6,s.indexOf("</Name>")).equals(cmp_fname))
     	    	       {
     	    	    	  while(!s.contains("</Entity>"))
     	     	    		{
     	    	    		  s=br.readLine();
     	    	    		  if(s.contains("<CLASS>"))
     	    	    		  {
     	    	    			  i++;
     	    	    			  j=-1;
     	    	    			  cf=1;
     	    	    		  }
     	    	    		  if(s.contains("<METHOD>"))
     	    	    		  {
     	    	    			  cf=0;  
     	    	    			  k=-1;
     	    	    		  }
     	    	    		  if(s.contains("<NAME>") && cf==1)
     	    	    		  {
     	    	    			  e=s.substring(s.indexOf("<NAME>")+6,s.indexOf("</NAME>"));
     	    	    			  System.out.println("class name:"+e); 
     	    	    			  fcname[i]=e;
     	    	    		  }
     	    	    		  if(s.contains("<NAME>") && cf==0)
     	    	    		  {
     	    	    			  e=s.substring(s.indexOf("<NAME>")+6,s.indexOf("</NAME>"));
     	    	    			  System.out.println("Method name:"+e); 
     	    	    			  j++;
     	    	    			  fmname[i][j]=e;
     	    	    		  }
     	    	    		 if(s.contains("<Calls>"))
    	    	    		  {
    	    	    			  e=s.substring(s.indexOf("<Calls>")+7,s.indexOf("</Calls>"));
    	    	    			  System.out.println("Called method:"+e); 
    	    	    			  k++;
    	    	    			  fsname[i][j][k]=e;
    	    	    		  }
     	     	    		}
     	    	       }
     	      		}
     	    	}
     	    
     	    
     	    
		   }catch(FileNotFoundException fnf)
		   {
			   
		   }catch(IOException ioe)
		   {
			   
		   }
	  }
	  else if(r.isDirectory())
	  {
		System.out.println("Please select File only");  
	  }	  
	}
	

}



