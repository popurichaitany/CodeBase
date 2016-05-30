package gui;
import java.io.*;
import control_flow.*;
import Home.*; 


public class fileBackup 
{

	/**
	 * @param args
	 */
	public  String s;

	public  String cname[]=new String[1024]; 
	public String[] getCname() {
		return cname;
	}


	public  String ecname[][]=new String[1024][2];
	public  String mname[][]=new String[1024][128];
	public  String icname[][]=new String[1024][128];
	public  String atr[][]=new String[1024][128]; 
	public int mcnt=0,cmcnt=0,mndx=0,andx=0,cFlag=0,rdnFlag=0,lno=0,clno[]={0},attrcnt=0,mthstart=0;
	public int max_mndx=0,max_andx=0;
	public  int inherit_clscnt=0,classcnt=-1;
	public fileBackup() {
		super();
	}


	public  String currFile,fname,dest,xloc;
	public  PrintWriter bw=null,pw=null;

	String path;

	public File f1;


	public static void main(String []args)
	{
		fileBackup fb=new fileBackup();
		fb.Intr_Backup_move("D:\\CodeCrawler_Reports.xml","D:\\Intr.java");
	}


	public fileBackup(File f)
	{
		currFile=f.getPath();
		Populate();
	}

	public static void xyz()
	{

	}
	public void Populate()
	{
		try
		{
			int obrc=0;
			f1=new File(currFile); 
			BufferedReader br=new BufferedReader(new FileReader(f1));

			int explore_limit;
			while((s=br.readLine())!=null)
			{

				int almt=0;
				lno++;
				explore_limit=0;

				if(s.contains("//") && !(s.contains(";")))
				{
					cFlag=3;
					cmcnt++;
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


				//	        		if((s.contains("int ") || s.contains("float ") || s.contains("long ") || s.contains("String ") ||
				//	        			s.contains("double ")) && s.contains(";") && mthstart==0)
				//	        		{
				//	        			
				//	        			atr[classcnt][andx]="";
				//	        			if(s.contains("="))
				//	        			{
				//	        			  almt=s.indexOf("=");
				//	           			}
				//	        			else if(s.contains(";"))  
				//	        			{
				//	        			  almt=s.indexOf(";"); 
				//	        			}
				//	        			for(int q=0;q<almt;q++)
				//	        			{
				//	        				atr[classcnt][andx]+=s.charAt(q);
				//	        			} 
				//	        			 atr[classcnt][andx]+=";";
				//	        			 
				//	        			 andx++;
				//	           		}
				//	        		if((s.contains("int ") || s.contains("float ") || s.contains("long ") || s.contains("String ") || 
				//	        			s.contains("double ")) && s.contains(";") && s.contains(",") && mthstart==0)
				//	        		{
				//	        			
				//	        			
				//	        			if(s.contains("int "))
				//	        			{
				//	        			 String tmpint=s.substring(s.indexOf("int ")+4,s.length()-1);
				//	        			 String sw[]=s.split(","); 
				//	        			 for(int popl=0;popl<sw.length;popl++)
				//	        			 {
				//	        				atr[classcnt][andx]+="int "+sw[popl];
				//	        				andx++; 
				//	        			 }
				//	        			}  
				//	        			if(s.contains("float "))
				//	        			{
				//	        			 String tmpflt=s.substring(s.indexOf("float ")+4,s.length()-1);
				//	        			 String sw[]=s.split(","); 
				//	        			 for(int popl=0;popl<sw.length;popl++)
				//	        			 {
				//	        				atr[classcnt][andx]+="float "+sw[popl];
				//	        				andx++; 
				//	        			 }
				//	        			}
				//	        			if(s.contains("long "))
				//	        			{
				//	        			 String sw[]=s.split(","); 
				//	        			 for(int popl=0;popl<sw.length;popl++)
				//	        			 {
				//	        				atr[classcnt][andx]+="int "+sw[popl];
				//	        				andx++; 
				//	        			 }
				//	        			}
				//	        			if(s.contains("long "))
				//	        			{
				//	        			 String sw[]=s.split(","); 
				//	        			 for(int popl=0;popl<sw.length;popl++)
				//	        			 {
				//	        				atr[classcnt][andx]+="int "+sw[popl];
				//	        				andx++; 
				//	        			 }
				//	        			}
				//	        			if(s.contains("String "))
				//	        			{
				//	        			 String sw[]=s.split(","); 
				//	        			 for(int popl=0;popl<sw.length;popl++)
				//	        			 {
				//	        				atr[classcnt][andx]+="String "+sw[popl];
				//	        				andx++; 
				//	        			 }
				//	        			}
				//	        			if(s.contains("double "))
				//	        			{
				//	        			 String sw[]=s.split(","); 
				//	        			 for(int popl=0;popl<sw.length;popl++)
				//	        			 {
				//	        				atr[classcnt][andx]+="double "+sw[popl];
				//	        				andx++; 
				//	        			 }
				//	        			}
				//	        			
				//	           		}
				

				if(s.contains("{")) 
					obrc++;

				if(s.contains("}"))
					obrc--;
				
if(s.contains("(")) 
{
	
}
else
{
				if(s.contains("class ") && cFlag==0 && cFlag!=3)
				{
					int cndx,endx,indx,impl_clscnt=0;
					String tmp=s.toString();

					if(s.contains("{"))
						obrc=1;
					else
						obrc=0;
					
					classcnt++;
					if(mcnt>max_mndx)
					{
						max_mndx=mcnt;
					}
					if(andx>max_andx)
					{
						max_andx=andx;
					}
					mndx=0;
					andx=0;
					mcnt=0;
					cndx=tmp.indexOf("class "); 

					cname[inherit_clscnt]="";
					
					if(s.contains("{"))
						s=s.replace("{"," ");
					for(int k=cndx+6;s.charAt(k)!=' ';k++)
					{
						cname[inherit_clscnt]=cname[inherit_clscnt]+s.charAt(k);
					}




					if(s.contains("extends"))
					{
						endx=tmp.indexOf("extends ");

						ecname[classcnt][0]="";

						for(int z=endx+8;s.charAt(z)!=' ';z++)
						{
							ecname[classcnt][0]=ecname[classcnt][0]+s.charAt(z);                		        		 
						}

						indx=tmp.indexOf("implements ");
						if(indx>0)
						{
							icname[classcnt][impl_clscnt]="";
							for(int m=indx+11;(s.charAt(m)!=' ');m++)
							{
								if(s.charAt(m)==',')
								{
									impl_clscnt++;

								}
								else
								{
									icname[classcnt][impl_clscnt]=icname[classcnt][impl_clscnt]+s.charAt(m);
								}
							}
							cname[inherit_clscnt]+=" implements ";
							for(int ec=0;ec<=impl_clscnt;ec++)
							{
								cname[inherit_clscnt]=cname[inherit_clscnt]+icname[classcnt][ec];
								if(ec<impl_clscnt-1)
								cname[inherit_clscnt]=cname[inherit_clscnt]+",";
							}
						}
					}
					else
					{
						ecname[classcnt][0]="";
					}
					inherit_clscnt++;
				}

				


				if(obrc==1)
					mthstart=1;
				else
					mthstart=0;    

				if((s.contains("int ") || s.contains("float ") || s.contains("long ") || s.contains("String ") || 
					s.contains("double ")) && s.contains(";") && s.contains(",") && mthstart==1 && cFlag==0 && cFlag!=3) 
				{


					if(s.contains("int "))
					{
						String tmpnt=s.substring(s.indexOf("int ")+4, s.length()-1);
						String sw[]=tmpnt.split(","); 
						for(int popl=0;popl<sw.length;popl++)
						{
							if(sw[popl].contains("="))
        						sw[popl]=sw[popl].substring(0,sw[popl].indexOf("="));
							atr[classcnt][andx]="";
							atr[classcnt][andx]+="int "+sw[popl]+";";
							andx++; 
						}
					}  
					if(s.contains("float "))
					{
						String  tmpst=s.substring(s.indexOf("float ")+6,s.length()-1);
						String sw[]=tmpst.split(","); 
						for(int popl=0;popl<sw.length;popl++)
						{
							if(sw[popl].contains("="))
        						sw[popl]=sw[popl].substring(0,sw[popl].indexOf("="));
							atr[classcnt][andx]="";
							atr[classcnt][andx]+="float "+sw[popl]+";";
							andx++; 
						}
					}
					if(s.contains("long "))
					{
						String  tmplg=s.substring(s.indexOf("long ")+5,s.length()-1);

						String sw[]=tmplg.split(","); 
						for(int popl=0;popl<sw.length;popl++)
						{
							if(sw[popl].contains("="))
        						sw[popl]=sw[popl].substring(0,sw[popl].indexOf("="));
							atr[classcnt][andx]="";
							atr[classcnt][andx]+="long "+sw[popl]+";";
							andx++; 
						}
					}

					if(s.contains("String "))
					{
						String  tmpst=s.substring(s.indexOf("String ")+7,s.length()-1);

						String sw[]=tmpst.split(","); 
						for(int popl=0;popl<sw.length;popl++)
						{
							atr[classcnt][andx]="";
							atr[classcnt][andx]+="String "+sw[popl]+";";
							andx++; 
						}
					}
					if(s.contains("double "))
					{
						
						String  tmpdb=s.substring(s.indexOf("double ")+7,s.length()-1);
						String sw[]=tmpdb.split(","); 
						for(int popl=0;popl<sw.length;popl++)
						{
							if(sw[popl].contains("="))
        						sw[popl]=sw[popl].substring(0,sw[popl].indexOf("="));
							atr[classcnt][andx]="";
							atr[classcnt][andx]+="double "+sw[popl]+";"; 
							andx++; 
						}
					}

				}

				else if((s.contains("int ") || s.contains("float ") || s.contains("long ") ||
						s.contains("double ")) && s.contains(";") && mthstart==1 && cFlag==0 && cFlag!=3)
				{

					atr[classcnt][andx]="";
					if(s.contains("="))
					{
						almt=s.indexOf("=");
					}
					else if(s.contains(";"))  
					{
						almt=s.indexOf(";"); 
					}
					for(int q=0;q<almt;q++)
					{
						atr[classcnt][andx]+=s.charAt(q);
					} 
					atr[classcnt][andx]+=";";

					andx++;
				}
}

				if((s.contains("public ") || s.contains("private ") || s.contains("protected ") || s.contains("void ") || s.contains("int ") || 
						s.contains("float ") || s.contains("long ") || s.contains("String ") || s.contains("double ")) 
					&& !(s.contains("."))	&& s.contains("(") && s.contains(")") && !(s.contains(";")) && cFlag==0 && cFlag!=3) 
				{ 


					String method_sgn="";
					mthstart=1;
					explore_limit=s.indexOf("(");

					String t="";
					t=s.substring(0,explore_limit+1);

					if(t.indexOf("}")>=0) 
					{
						t=t.replaceAll("}","");
					} 
					mname[classcnt][mcnt]="";
					mname[classcnt][mcnt]+=t;
					mname[classcnt][mcnt]+=") {}";

					mcnt++;

				} 
				
				if(s.contains("String ") && s.contains(";") && mthstart==1 && cFlag==0 && cFlag!=3)
		        {

					atr[classcnt][andx]="";
					if(s.contains("="))
					{
						almt=s.indexOf("=");
					}
					else if(s.contains(";"))  
					{
						almt=s.indexOf(";"); 
					}
					for(int q=0;q<almt;q++)
					{
						atr[classcnt][andx]+=s.charAt(q);
					} 
					atr[classcnt][andx]+=";";

					andx++;
		        }

				if(cFlag==3)
					cFlag=0;

				rdnFlag=0;
				f1.delete();
			}
		}catch(Exception e)
		{
			System.out.println(e.getStackTrace());  
		}
	}  

	public void Intr_Backup_move(String p,String jfpath)
	{
		int exChk=0;
		String s="",temp;
		int i=-1,j=0,k=0,nvg,lmt,flag=0,parent=0,mFlag=0; 
		FileWriter fw=null;
		try
		{
			System.out.println("About to write in intr file....");  
			fw=new FileWriter(jfpath+"\\Intr.java");     
		}catch(IOException ioe)
		{
			System.out.println("IOException"); 
			ioe.printStackTrace();
		}
		bw=new PrintWriter(fw);

		try {
			File f5 = new File(p);
			BufferedReader br = new BufferedReader(new FileReader(f5));
			while((s=br.readLine())!=null) 
			{
				if(s.contains("<CLASS>")) 
				{
					i++;

					j=0;
					k=0;
					flag=1;
					mFlag=0;
				}
				if(s.contains("</CLASS>")) 
				{
					flag=0;
				}
				else if(s.contains("<NAME>") && flag==1) 
				{

					nvg=s.indexOf("<NAME>");
					lmt=s.indexOf("</NAME>");
					temp=s.substring(nvg+6,lmt);

					cname[i]=temp;
					flag=0;

				}
				else if(s.contains("<EXTEND>")) 
				{

					nvg=s.indexOf("<EXTEND>");
					lmt=s.indexOf("</EXTEND>");
					temp=s.substring(nvg+8,lmt);

					ecname[i][0]=temp;

				}
				else if(s.contains("<ATTRIBUTE>")) 
				{

					nvg=s.indexOf("<ATTRIBUTE>");
					lmt=s.indexOf("</ATTRIBUTE>");
					temp=s.substring(nvg+11,lmt);

					atr[i][j]=temp;
					j++;

				}
				else if(s.contains("<METHOD>")) 
				{
					mFlag=1;
				}
				else if(s.contains("</METHOD>")) 
				{
					mFlag=0;
				}

				else if(s.contains("<NAME>") && mFlag==1) 
				{

					nvg=s.indexOf("<NAME>");
					lmt=s.indexOf("</NAME>");
					temp=s.substring(nvg+6,lmt);
					mname[i][k]=temp;
					k++;

				}



			}
		}catch(FileNotFoundException fnf) { 
			fnf.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}



		bw.println("/**");
		bw.println(" * @opt attributes");
		bw.println(" * @opt operations");
		bw.println(" */");
		bw.println("class UMLOptions {}"); 
		bw.println("");

		for(int q=0;q<=i;q++)
		{
			for(int ck=0;ck<q;ck++)
			{
				if(!ecname[ck][0].equals(""))  
				{
					if(ecname[ck][0].equals(ecname[q][0])) 
					{ 
						exChk=1;
					}
				} 
			}
			if(exChk==0)
			{
				if(ecname[q][0].length()>0)
				{
					bw.println("class "+ecname[q][0]+" {}");
				}
			}
			exChk=0;
		}
		for(int f=0;f<=i;f++)
		{   
			bw.println("");
			if(!ecname[f][0].equals(""))
			{
				bw.println("/**");
				bw.println(" * @extends "+ecname[f][0]);
				bw.println(" */"); 
			}



			bw.println("class "+cname[f]);
			bw.println("{");  

			for(int l=0;l<=16;l++)
			{
				if(atr[f][l]!=null)
				{ 
					bw.println(atr[f][l]);
				}
				if(mname[f][l]!=null)
				{
					bw.println(mname[f][l]);
				}
			}
			bw.println("}");
		}
		bw.flush();
	}
}
