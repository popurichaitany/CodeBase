package control_flow;
import java.io.*;
public class Invoke_tracer extends Extractor
{
	String stk[][][]=new String[64][64][2];
	public static String callee[][][]=new String[1024][32][16];
	String curr_cname="";
	String pmi[]=new String[64]; 
	int pmi_tr=0;
	int a;
	public Invoke_tracer()
	{
	}
	public Invoke_tracer(String s)
	{
		for(int i=0;i<1024;i++)
		{
			for(int j=0;j<64;j++)
			{
				for(int k=0;k<16;k++)
				{
					if(i<64 && j<64 && k<2)
						stk[i][j][k]="";
					if(j<32 && k<16)
						callee[i][j][k]="";
				}
			}
		}
	}
	public void Tracer(String pt)
	{
		for(int u=0;u<64;u++)
		{
			for(int r=0;r<64;r++)
			{
				for(int q=0;q<2;q++)
				{
					stk[u][r][q]=""; 
				}
			}
		}
		try
		{
			int mobrc=0,mcbrc=0;
			cFlag=0;
			rdnFlag=0;
			mthstart=0;
			f1=new File(pt);
			BufferedReader br=new BufferedReader(new FileReader(f1));
			int mth=0,cl=0,cld_mth=0; 
			while((s=br.readLine())!=null)
			{
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
				
				if(s.contains("class ") && cFlag==0 && !(s.contains(".")) && !(s.contains("(")) && !(s.contains(")")))
				{
					int cndx;
					cl=0;
					mth=0;
					cld_mth=0;
					curr_cname="";
					andx=0;
					mcnt=0;
					pmi_tr=0;
					a=0;
					cndx=s.indexOf("class ");
					for(int r=0;r<64;r++)
					{
						pmi[r]="";
					}
					for(int k=cndx+6;s.charAt(k)!=' ';k++)
					{
						curr_cname=curr_cname+s.charAt(k); 
					}
					for(int v=0;v<=Extractor.classcnt;v++)
					{
						if(Extractor.cname[v].equals(curr_cname)) 
						{
							cl=v;
							for(int u=0;u<32;u++)
							{
								if(mname[v][u][1]!="")
								{
									pmi[pmi_tr]=mname[v][u][1];
									pmi_tr++;
								}
							}
							for(int l=0;l<=Extractor.classcnt;l++)
							{
								if(Extractor.ecname[v].equals(Extractor.cname[l]))
								{
									for(int em=0;em<32;em++)
									{
										if(mname[v][em][1]!="")
										{
											pmi[pmi_tr]=mname[v][em][1];
											pmi_tr++;
										}
									} 
								}
							}
						}
					}
				}
				if((s.contains("public ") || s.contains("private ") || s.contains("protected ") || s.contains("void ") || 
						s.contains("int ") || s.contains("float ") || s.contains("long ") || s.contains("String ") || 
						s.contains("double ")) && s.contains("(") && s.contains(")") && s.indexOf(";")<s.lastIndexOf(")") && !(s.contains("."))
						&& cFlag!=3) 
				{
					int z=0;
					mobrc=0;
					mcbrc=0;
					a=0;
					for(int i=0;i<32;i++)
					{
						if(Extractor.mname[cl][i][1].contains(s)) 
						{
							mth=i;
						}
					}
					if(s.contains("{")) 
						mobrc++;
					else
					{
						while(!(s.contains("{")))
						{
							s=br.readLine();
						}
						mobrc++;
					}
					while(mobrc!=mcbrc)
					{
						s=br.readLine();
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
						
						
						if(s.contains("{")) 
						{
							mobrc++;
							z++;
							a=0;
						}
						if(s.contains("}"))
						{
							mcbrc++;
							z--;
						}
						if(s.contains("new "))
						{
							stk[z][a][0]="";
							stk[z][a][1]="";
							stk[z][a][0]=s.substring(s.indexOf("new ")+4,s.lastIndexOf("("));
							stk[z][a][1]=s.substring(s.indexOf(" ")+1,s.indexOf("="));
							String ecn;
							String apnd;
							int ecndx;
							for(int v=0;v<=Extractor.classcnt;v++)
							{
								if(Extractor.cname[v].equals(stk[z][a][0])) 
								{
									ecn=Extractor.cname[v];
									ecndx=v;
									for(int u=0;u<32;u++)
									{
										if(mname[v][u][1]!="")
										{
											apnd=mname[v][u][0].substring(mname[v][u][0].lastIndexOf(" ")+1,mname[v][u][0].length()); 
											pmi[pmi_tr]=cname[v]+"."+apnd;
											pmi_tr++;
										}
									}
									if(ecname[ecndx]!="") 
									{
										for(int l=0;l<=Extractor.classcnt;l++)
										{
											if(ecn.equals(Extractor.cname[l]))
											{
												for(int em=0;em<32;em++)
												{
													if(mname[v][em][1]!="")
													{
														apnd=mname[v][em][0].substring(mname[v][em][0].lastIndexOf(" ")+1,mname[v][em][0].length());
														pmi[pmi_tr]=ecn+"."+apnd;
														pmi_tr++;
													}
												} 
											}
										}
									}
								}
							}
							a++;
						}
						if((s.contains("int ") || s.contains("float ") || s.contains("long ") || s.contains("String ") || 
								s.contains("double ")) && s.contains(";") && s.contains(",") && cFlag==0 && 
								cFlag!=3) 
						{
							if(s.contains("int "))
							{
								String tmpnt=s.substring(s.indexOf("int ")+4,s.indexOf(";"));
								String sw[]=tmpnt.split(","); 
								for(int popl=0;popl<sw.length;popl++)
								{
									if(sw[popl].contains("="))
										sw[popl]=sw[popl].substring(0,sw[popl].indexOf("=")); 
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="int";
									stk[z][a][1]+=sw[popl];
									a++; 
								}
							} 
							if(s.contains("float "))
							{
								String tmpst=s.substring(s.indexOf("float ")+6,s.indexOf(";"));
								String sw[]=tmpst.split(","); 
								for(int popl=0;popl<sw.length;popl++)
								{
									if(sw[popl].contains("="))
										sw[popl]=sw[popl].substring(0,sw[popl].indexOf("="));
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="float";
									stk[z][a][1]+=sw[popl];
									a++; 
								}
							}
							if(s.contains("long "))
							{
								String tmplg=s.substring(s.indexOf("long ")+5,s.indexOf(";"));
								String sw[]=tmplg.split(","); 
								for(int popl=0;popl<sw.length;popl++)
								{
									if(sw[popl].contains("="))
										sw[popl]=sw[popl].substring(0,sw[popl].indexOf("="));
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="long";
									stk[z][a][1]+=sw[popl];
									a++; 
								}
							}
							if(s.contains("String "))
							{
								String tmpst=s.substring(s.indexOf("String ")+7,s.indexOf(";"));
								String sw[]=tmpst.split(","); 
								for(int popl=0;popl<sw.length;popl++)
								{
									if(sw[popl].contains("="))
										sw[popl]=sw[popl].substring(0,sw[popl].indexOf("="));
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="String";
									stk[z][a][1]+=sw[popl];
									a++; 
								}
							}
							if(s.contains("double "))
							{
								String tmpdb=s.substring(s.indexOf("double ")+7,s.indexOf(";"));
								String sw[]=tmpdb.split(","); 
								for(int popl=0;popl<sw.length;popl++)
								{
									if(sw[popl].contains("="))
										sw[popl]=sw[popl].substring(0,sw[popl].indexOf("="));
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="double";
									stk[z][a][1]+=sw[popl]; 
									a++; 
								}
							}
						}
						else if((s.contains("int ") || s.contains("float ") || s.contains("long ") || s.contains("String ") ||
								s.contains("double ")) && s.contains(";") && cFlag==0 && cFlag!=3)
						{
							String tmp;
							if(s.contains("="))
							{
								if(s.contains("int "))
								{
									tmp=s.substring(s.indexOf("int ")+4,s.indexOf("="));
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="int"; 
									stk[z][a][1]+=tmp; 
									a++;
								}
								if(s.contains("float "))
								{
									tmp=s.substring(s.indexOf("float ")+6,s.indexOf("="));
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="float"; 
									stk[z][a][1]+=tmp; 
									a++;
								} 
								if(s.contains("long "))
								{
									tmp=s.substring(s.indexOf("long ")+5,s.indexOf("="));
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="long"; 
									stk[z][a][1]+=tmp; 
									a++;
								}
								if(s.contains("double "))
								{
									tmp=s.substring(s.indexOf("double ")+7,s.indexOf("="));
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="double"; 
									stk[z][a][1]+=tmp; 
									a++;
								}
								if(s.contains("String "))
								{
									tmp=s.substring(s.indexOf("String ")+7,s.indexOf("="));
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="String"; 
									stk[z][a][1]+=tmp; 
									a++;
								}
							}
							else if(s.contains(";")) 
							{
								if(s.contains("int "))
								{
									tmp=s.substring(s.indexOf("int ")+4,s.indexOf(";"));
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="int"; 
									stk[z][a][1]+=tmp; 
									a++;
								}
								if(s.contains("float "))
								{
									tmp=s.substring(s.indexOf("float ")+6,s.indexOf(";"));
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="float"; 
									stk[z][a][1]+=tmp; 
									a++; 
								} 
								if(s.contains("long "))
								{
									tmp=s.substring(s.indexOf("long ")+5,s.indexOf(";"));
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="long"; 
									stk[z][a][1]+=tmp; 
									a++;
								}
								if(s.contains("double "))
								{
									tmp=s.substring(s.indexOf("double ")+7,s.indexOf(";"));
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="double"; 
									stk[z][a][1]+=tmp; 
									a++;
								}
								if(s.contains("String "))
								{
									tmp=s.substring(s.indexOf("String ")+7,s.indexOf(";"));
									stk[z][a][0]="";
									stk[z][a][1]="";
									stk[z][a][0]+="String"; 
									stk[z][a][1]+=tmp; 
									a++;
								}
							}
						}
						
						if(s.contains("new ") && s.contains(").") && s.contains(";") && cFlag==0 && cFlag!=3)
						{
							//new Collection_Formation().process();
							String result="";
							String j[]=s.split(";");
							for(int y=0;y<j.length;y++)
							{
								if(j[y].contains("new ") && j[y].contains(").")) 
								{
								  int start=j[y].indexOf("new "); 	
								  int open=j[y].indexOf("(");
								  int close=j[y].indexOf("."); 
								  String r=j[y].substring(start+4,open);
								  String r1=j[y].substring(close+1,j[y].lastIndexOf(")"));
								  if(r1.indexOf("(")+1==r1.lastIndexOf(")"))
								  {
									  r=r+"."+r1; 
								  }
								  else if(r1.contains(","))
								  {
									  String prm[]=r1.split(",");
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
											if(prm[p].contains("String.valueOf"))
												result+="String";
											else if(prm[p].contains("Integer.valueOf"))
												result+="int";
											else if(prm[p].contains("Float.valueOf"))
												result+="float";
											else if(prm[p].contains("Double.valueOf"))
												result+="double";
											else if(prm[p].contains("Long.valueOf"))
												result+="long";
											else if(prm[p].contains("new String("))
												result+="String";
											else if(prm[p].contains("\""))
												result+="String";
												
											else
											{
												for(int xi=0;xi<64;xi++)
												{
													for(int xii=0;xii<64;xii++)
													{
														if(prm[p].equals(stk[xi][xii][1]))
														{
															result+=stk[xi][xii][0];
														}
													}
												}
											}
											if(p<prm.length-1)
												result+=",";
											else
												result+=")"; 
										}
								  }
								  else
								  {
									  if(r1.startsWith(" "))
										{
											r1=r1.substring(1,r1.length()-1);
										}
										if(r1.endsWith(" "))
										{
											r1=r1.substring(0,r1.length()-2);
										}
										if(r1.contains("String.valueOf"))
											result+="String";
										else if(r1.contains("Integer.valueOf"))
											result+="int";
										else if(r1.contains("Float.valueOf"))
											result+="float";
										else if(r1.contains("Double.valueOf"))
											result+="double";
										else if(r1.contains("Long.valueOf"))
											result+="long";
										else if(r1.contains("new String("))
											result+="String";
										else if(r1.contains("\""))
											result+="String";
										else if(r1.contains("0") || r1.contains("1") || r1.contains("2") ||
												r1.contains("3") || r1.contains("4") || r1.contains("5") ||
												r1.contains("6") || r1.contains("7") || r1.contains("8") ||
												r1.contains("9") && r1.contains(".") && r1.contains("f"))
										{
											result+="float";
										}
										else if(r1.contains("0") || r1.contains("1") || r1.contains("2") ||
												r1.contains("3") || r1.contains("4") || r1.contains("5") ||
												r1.contains("6") || r1.contains("7") || r1.contains("8") ||
												r1.contains("9") && r1.contains(".") && !(r1.contains("f")))
										{
											result+="double";
										}
										else if(r1.contains("0") || r1.contains("1") || r1.contains("2") ||
												r1.contains("3") || r1.contains("4") || r1.contains("5") ||
												r1.contains("6") || r1.contains("7") || r1.contains("8") ||
												r1.contains("9") && !(r1.contains(".")))
										{
											result+="int"; 
										}
										else
										{
											for(int xi=0;xi<=z;xi++)
											{
												for(int xii=0;xii<64;xii++)
												{
													if(r1.equals(stk[xi][xii][1]))
													{
														result+=stk[xi][xii][0];
													}
												}
											}
										}
								  }
								}
							}
							 
							  
							 
						}
						
						if(s.contains(".") && s.contains("(") && s.contains(")") && cFlag==0 && cFlag!=3)
						{
							
							
							String c="",cmpr="",cmpr1="",expr1="",result;
							int pivot=0,tr_flag=0;
							for(int itr=0;itr<s.length();itr++)
							{
								c+=s.charAt(itr);
								if(c.contains("("))
								{
									cmpr=c;
									c="";
									cmpr1=cmpr;
									pivot=cmpr.indexOf("(");
									cmpr=cmpr.substring(cmpr.indexOf(".")+1,pivot);
									for(int ptr=0;ptr<pmi_tr;ptr++)
									{
										if(pmi[ptr].contains(cmpr))
										{
											for(int i=0;i<=z;i++)
											{
												for(int o=0;o<64;o++)
												{
													if(stk[i][o][1]!="")
													{
														expr1=stk[i][o][1]+".";
														if(cmpr1.contains(expr1) && tr_flag==0) 
														{
															System.out.println(s); 
															result=stk[i][o][0]+"."+cmpr+"("; 
															String trc_prm=""; 
															trc_prm=s.substring(itr+1);
															trc_prm=trc_prm.substring(0,trc_prm.indexOf(")"));
															if(trc_prm.equals(""))
															{
																tr_flag=1;
																result+=")";
															}
															else if(trc_prm.contains(","))
															{
																tr_flag=1;
																String prm[]=trc_prm.split(",");
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
																	if(prm[p].contains("String.valueOf"))
																		result+="String";
																	else if(prm[p].contains("Integer.valueOf"))
																		result+="int";
																	else if(prm[p].contains("Float.valueOf"))
																		result+="float";
																	else if(prm[p].contains("Double.valueOf"))
																		result+="double";
																	else if(prm[p].contains("Long.valueOf"))
																		result+="long";
																	else if(prm[p].contains("new String("))
																		result+="String";
																	else if(prm[p].contains("\""))
																		result+="String";
																		
																	else
																	{
																		for(int xi=0;xi<64;xi++)
																		{
																			for(int xii=0;xii<64;xii++)
																			{
																				if(prm[p].equals(stk[xi][xii][1]))
																				{
																					result+=stk[xi][xii][0];
																				}
																			}
																		}
																	}
																	if(p<prm.length-1)
																		result+=",";
																	else
																		result+=")"; 
																}
															}
															else
															{
																tr_flag=1;
																if(trc_prm.startsWith(" "))
																{
																	trc_prm=trc_prm.substring(1,trc_prm.length()-1);
																}
																if(trc_prm.endsWith(" "))
																{
																	trc_prm=trc_prm.substring(0,trc_prm.length()-2); 
																}
																if(trc_prm.contains("String.valueOf"))
																	result+="String";
																else if(trc_prm.contains("Integer.valueOf"))
																	result+="int";
																else if(trc_prm.contains("Float.valueOf"))
																	result+="float";
																else if(trc_prm.contains("Double.valueOf"))
																	result+="double";
																else if(trc_prm.contains("Long.valueOf"))
																	result+="long";
																else if(trc_prm.contains("new String("))
																	result+="String";
																else if(trc_prm.contains("\""))
																	result+="String";
																else
																{
																	for(int xi=0;xi<=z;xi++)
																	{
																		for(int xii=0;xii<64;xii++)
																		{
																			if(trc_prm.equals(stk[xi][xii][1]))
																			{
																				result+=stk[xi][xii][0];
																			}
																		}
																	}
																}
																result+=")";
															}
															
															callee[cl][mth][cld_mth]=result;
                                   							cld_mth++;
														}
													}
												}
											}
										}
									} 
								}
							}
						}
						if(cFlag==3)
							cFlag=0;
					}
				}
				if(cFlag==3)
					cFlag=0;
			} 
		}catch(Exception e)
		{
		}
	}
}

