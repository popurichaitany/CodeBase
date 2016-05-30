package Home;

import gui.ImageZoomerFrame;
import gui.fileBackup;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;



import converte.DotToImageConverter;
import converte.JavaToDotConverter;


public class ControlFlowReport extends JFrame implements ActionListener{

	JTree tree=null;
	String h1;
	 String content[][]=new String[40][4];
		String columns[]={"Source class","Source method","Target class","Target method"};
		JTable table1;
		 int flow_tracker=0;
	
	 public static void main(String[] args)
     {
		 ControlFlowReport obj=new ControlFlowReport(args[0]);
		 obj.setExtendedState(obj.getExtendedState() | JFrame.MAXIMIZED_BOTH);; 
        obj.setSize(350,500);
        obj.setVisible(true);
        

     }
	public ControlFlowReport(String xmlPath)
	{
		try
		 {
		        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		 }catch(Exception exception)
         {
             exception.printStackTrace();
         }
		         
		 
		 
		 int f=xml_Extractor(xmlPath);
		 DefaultMutableTreeNode repository[]=new DefaultMutableTreeNode[1024];
		

		 System.out.println(Node.cnt); 
		 
		 
		 for(int y1=0;y1<Node.cnt;y1++)
	     {
			 if(y1==0)
			 {
				 String asgn=Node.h[y1].Name;
				 for(int t=0;t<189-asgn.length();t++)
					 asgn+=" ";
			 repository[y1]=new DefaultMutableTreeNode(asgn); 
			 
			 }
			 else
			 {
				 repository[y1]=new DefaultMutableTreeNode(Node.h[y1].Name);
			 }
	     }
		
	     for(int y=0;y<Node.cnt;y++)
	     {
	    	 String id=Node.h[y].ID;
	    	
	    	for(int y2=y+1;y2<Node.cnt;y2++)
	    	{
	    	   	String cmp_pid=Node.h[y2].Parent;
	    	   	if(id.equals(cmp_pid))
	    	   	{
	    	   		repository[y].add(repository[y2]); 
	    	   	}
	    	}
	     }
		 
		 
		 
		 this.setLayout(new BorderLayout());
		
			
		 
			JPanel panel1 = new JPanel();
			
			
			this.add(panel1,BorderLayout.NORTH);
			
			
			
			JButton report2Btn=new JButton("File Property");
			report2Btn.setBounds(290,0,170,20);
			report2Btn.addActionListener(this);
			panel1.add(report2Btn);
			
			JButton report3Btn=new JButton("Class Hierarchy");
			report3Btn.setBounds(350,0,170,20);
			report3Btn.addActionListener(this);
			panel1.add(report3Btn);
				
			JButton RtvBtn=new JButton("Retrieve");
			RtvBtn.setBounds(2290,0,170,20);
			RtvBtn.addActionListener(this);
			panel1.add(RtvBtn);
			
			JButton saveBtn=new JButton("Save");
			saveBtn.setBounds(2350,0,170,20);
			saveBtn.addActionListener(this);
			panel1.add(saveBtn);
		 
			JPanel panel =new JPanel();
			JPanel panel2=new JPanel();
	        
	        
			this.add(panel,BorderLayout.WEST);
			this.add(panel2,BorderLayout.EAST);
			
			
			
		                                                                                                                                                                                   
	        
			table1 = new JTable(content,columns);
			
			
               tree=new JTree(repository[0],true);
	        // tree.setToolTipText("[1] and [1]");
	         
	         
	         panel.add(tree);
			
	         
	         

	         
	         tree.addTreeSelectionListener(new TreeSelectionListener() {
	        	    public void valueChanged(TreeSelectionEvent e) {
	        	        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
	        	                           tree.getLastSelectedPathComponent();

	        	    /* if nothing is selected */ 
	        	        if (node == null) return;

	        	    /* retrieve the node that was selected */ 
	        	        Object nodeInfo = node.getUserObject();
	        	       h1=nodeInfo.toString();
	        	       flow_tracker=2;
	        	       System.out.println(h1); 
	        	       
	        	    
	        	       
	        	       
	        	    
	        	        
	        	    }
	        	});
           
	         panel2.add(table1);
	    
	         JScrollPane scrollPane=null;
	         scrollPane= new   JScrollPane
	         		(
	             	table1,  scrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	             	scrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
	             );
	             add(scrollPane, BorderLayout.CENTER);
		         
		         
		         
		         
		         
                 
		             

		     }
		
	public int  xml_Extractor(String s)
	{
		File f=new File(s);
		String s2="",temp="",p="",type="";
		int i=0,j=0,k=0,nvg,lmt,flag=0,mFlag=0,nodes=0;
		BufferedReader br=null;
		Node n=new Node();
		Node.cnt=0;
		try {
			br=new BufferedReader(new FileReader(f));

			while((s2=br.readLine())!=null)
			{
				if(s2.contains("<Entity>"))
				{
				 n=new Node();
					i=-1;
				 nodes++;
				}
				if(s2.contains("<FType>")) 
				{
					nvg=s2.indexOf("<FType>");
					lmt=s2.indexOf("</FType>");
					type=s2.substring(nvg+7,lmt);
				}
				
				if(s2.contains("<Name>")) 
				{
					nvg=s2.indexOf("<Name>");
					lmt=s2.indexOf("</Name>");
					temp=s2.substring(nvg+6,lmt);
					n.setName(temp);
				}
				if(s2.contains("<Parent>")) 
				{
					nvg=s2.indexOf("<Parent>");
					lmt=s2.indexOf("</Parent>");
					temp=s2.substring(nvg+8,lmt);
					n.setParent(temp);
				}
				if(s2.contains("<ID>")) 
				{
					nvg=s2.indexOf("<ID>");
					lmt=s2.indexOf("</ID>");
					temp=s2.substring(nvg+4,lmt);
					n.setID(temp);
				}
				if(s2.contains("<Path>")) 
				{
					nvg=s2.indexOf("<Path>");
					lmt=s2.indexOf("</Path>");
					p=s2.substring(nvg+6,lmt);
					p=p+"\\"+n.getName();
					n.setPath(p);
				}
				
				if(type.equals("File"))
				{
					
					if(s2.contains("<CLASS>")) 
					{
					    
						j=0;
						k=0;
						flag=1;
						mFlag=0;
					}
					if(s2.contains("</CLASS>")) 
					{
						flag=0;
					}
					else if(s2.contains("<NAME>") && flag==1) 
					{

						nvg=s2.indexOf("<NAME>");
						lmt=s2.indexOf("</NAME>");
						temp=s2.substring(nvg+6,lmt);
						i++;
						n.CLass[i]=temp;
						
				
						flag=0;
					}

					else if(s2.contains("<METHOD>")) 
					{
						mFlag=1;
					}
					else if(s2.contains("</METHOD>")) 
					{
						j++;
						mFlag=0;
					}

					else if(s2.contains("<NAME>") && mFlag==1) 
					{

						nvg=s2.indexOf("<NAME>");
						lmt=s2.indexOf("</NAME>");
						temp=s2.substring(nvg+6,lmt);
						
						n.Method[i][j]=temp;
						
						
						

					}
					else if(s2.contains("<Calls>")) 
					{
						while(s2.contains("Calls>"))
						{
						nvg=s2.indexOf("<Calls>");
						lmt=s2.indexOf("</Calls>");
						temp=s2.substring(nvg+7,lmt);
//                        System.out.println(n.Name+" "+n.CLass[i]+" "+j+"."+n.Method[i][j]+" "+k+"."+temp); 
						n.Target[j][k]=temp;
						k++;
						s2=br.readLine();
						}
						
					}
				}
				
				 if(s2.contains("</Entity>"))
				{
					int pmt=Node.cnt++;
					Node.h[pmt]=n;
					
					type="";
				}
			}
		} catch (FileNotFoundException fnf) {
			// TODO Auto-generated catch block
			fnf.printStackTrace();
		} catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		return nodes;
	}
	
	HSSFWorkbook wb=null;
	
	public void generateFlow(String sc[],String sm[],String inv[],int row_count)
	{
		 try {
	          // Creating a Workbook
	        	
			   wb= new HSSFWorkbook();
	           HSSFFont font =wb.createFont();
	           HSSFCellStyle cellStyle=wb.createCellStyle();
	           font.setFontName(HSSFFont.FONT_ARIAL);         
	                                  font.setFontHeightInPoints((short) 12);         
	                                  font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         
	                                  font.setColor(HSSFColor.BLACK.index); 
	      
	                                  cellStyle.setFont(font); 
	                                  cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
	           cellStyle.setFillPattern(HSSFCellStyle.SPARSE_DOTS);
	           HSSFSheet spreadSheet = wb.createSheet("Control Flow");

	           

	           // Parsing XML Document
	           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	           DocumentBuilder builder = factory.newDocumentBuilder();
	           
	           
	           cellStyle = wb.createCellStyle();
	              cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
	              cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
	              cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
	              cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
	              
	              HSSFRow row1=null;
	              HSSFCell cell=null;
	                  
	              HSSFRow row = spreadSheet.createRow(0);

	              cell = row.createCell((short) 0);
	              cell.setCellStyle(cellStyle);
	              cell.setCellValue("Source class");
	              cell.setCellStyle(cellStyle);
	              cell = row.createCell((short) 1);
	              cell.setCellValue("Source method");
	              cell.setCellStyle(cellStyle);
	              cell = row.createCell((short) 2);
	              cell.setCellValue("Target class");
	              cell.setCellStyle(cellStyle);
	              cell = row.createCell((short) 3);
	              cell.setCellValue("Target method");
	              cell.setCellStyle(cellStyle);
	              
	              if(row_count>0)
	              {
	              
	            	  for(int y=0;y<row_count;y++)
	            	  {
	            		  row1 = spreadSheet.createRow(y+1);

	            		  cell = row1.createCell((short) 0);
	            		  cell.setCellValue(sc[y]);
	            		  content[y][0]=sc[y];
	            		  System.out.println("Source class:"+sc[y]);

	            		  cell = row1.createCell((short) 1);
	            		  sm[y]=sm[y].substring(0,sm[y].lastIndexOf(")")+1);
                          sm[y]=sm[y].trim();
	            		  cell.setCellValue(sm[y]);
	            		  content[y][1]=sm[y];
	            		  System.out.println("Source method:"+sm[y]);

	            		  cell = row1.createCell((short) 2);
	            		  cell.setCellValue(inv[y].substring(0,inv[y].indexOf(".")));  
	            		  System.out.println("Target class:"+inv[y].substring(0,inv[y].indexOf(".")));
	            		  content[y][2]=inv[y].substring(0,inv[y].indexOf("."));

	            		 // content[y][3]=content[y][3].substring(0,content[y][3].lastIndexOf(")"));
	            		 	            		  
	            		  cell = row1.createCell((short) 3);
	            		  inv[y]=inv[y].substring(0,inv[y].lastIndexOf(")")+1);
	            		  inv[y]=inv[y].trim();
	            		  cell.setCellValue(inv[y].substring(inv[y].indexOf(".")+1,inv[y].length()));
	            		  System.out.println("Target method:"+inv[y].substring(inv[y].indexOf(".")+1,inv[y].length()));  
	            		  content[y][3]=inv[y].substring(inv[y].indexOf(".")+1,inv[y].length());
	            	  }
	              }
	              else
	              {
	              	  for(int y=0;y<40;y++)
	            	  {
	            	  row1 = spreadSheet.createRow(y);
            		  cell = row1.createCell((short) 0);
            		  if(y==0)
            		  {
            			  cell.setCellValue("No invocation");
                		  content[0][0]="No invocation";
            		  }
            		  else
            		  {
            		   cell.setCellValue("");
            		   content[y][0]="";
            		  }            		  

            		  cell = row1.createCell((short) 1);
            		  cell.setCellValue("");
            		  content[y][1]="";
            		 
            		  cell = row1.createCell((short) 2);
            		  cell.setCellValue("");  
               		  content[y][2]="";

            		  cell = row1.createCell((short) 3);
            		  cell.setCellValue("");
               		  content[y][3]="";
	            	  }
	              }
	              table1.updateUI();
	              
		 } 
	        catch (Exception e) 
	        {
	           System.out.println(e);
	        } 
	        
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String src=e.getActionCommand();
	   
        
        System.out.println("inside action performd");
        if(src.equals("Retrieve"))
        {
        	flow_tracker=1;
        	System.out.println("f tracker:"+flow_tracker); 
        	String t[]=new String[16];
        	String cname[]=new String[32];
        	String mname[]=new String[16];
        	
        	for(int i=0;i<32;i++)
        	{
        		if(i<16)
        		{
        		 t[i]="";
        		 mname[i]="";
        		}
        		cname[i]=""; 
        	}
 	       int invoke_cntr=0;
 	       
 	       for(int u=0;u<Node.cnt;u++)
 	       {
 	    	   if(Node.h[u].Name.equals(h1))
 	    	   {
 	    		  for(int c=0;c<32;c++)
 	    		   {
   	    			  for(int m=0;m<16;m++)
 	    			  {
 	    				  for(int r=0;r<16;r++)
 	    				  {
 	    	    			if(!(Node.h[u].Target[m][r].equals(""))) 
 	    					  {
 	    						  if(!(Node.h[u].CLass[c].equals("")))
 	    						  {
 	    	    					  if(!(Node.h[u].CLass[c].equals("")))
 	    							  {
 	    		   						  cname[invoke_cntr]=Node.h[u].CLass[c];
 	 	    							  //System.out.println("Source class:"+cname[invoke_cntr]);
 	    								  mname[invoke_cntr]=Node.h[u].Method[c][m];
 	    								  //System.out.println("Source method:"+mname[invoke_cntr]); 
 	    								  //System.out.println("Invocation:"+Node.h[u].Target[m][r]); 
 	    								  t[invoke_cntr]=Node.h[u].Target[m][r];
 	    								  invoke_cntr++;
 	    							  } 
 	    					      }
 	    					  }
 	    				  }

 	    			  }
 	    		  }
 	    	   }
 	       }
 	        
 	       generateFlow(cname,mname,t,invoke_cntr);
        }
        
        if(src.equals("Class Hierarchy"))
        {
        
        try {
 		   
    		String dr="";
    		//frame1.setVisible(false);
			//frame = new Report2("Class Hierarchy Report");
    		//Report2.main(path);
    		fileBackup f=new fileBackup();
    		File ijf=new File(Report1.intr_bk_path); 
    		System.out.println("Parameter got:"+Report1.intr_bk_path); 
    		if(ijf.isFile())
    		{
    			this.setVisible(false);
    			ImageZoomerFrame obji;
    			dr=ijf.getParent();
    			System.out.println("Intermediate java file at:"+dr); 
    			f.Intr_Backup_move(ijf.getPath(),dr);
    			JavaToDotConverter obj = new JavaToDotConverter();
                System.out.println("java to dot");
				obj.setSourcePath(dr);
				obj.DotCreator();
				
				String str = new File("currentdirectory").getAbsolutePath();
				System.out.println(str); 
				Integer index = str.indexOf("currentdirectory");
				String CurrentDirectory = str.substring(0, index);

				dr=dr.concat("\\Intr.java");
				System.out.println("Passing intr java file as:"+dr); 
				Integer index1 = dr.lastIndexOf("\\");
				index1 = index1 + 1;

				Integer index2 = dr.lastIndexOf(".");
				String dotpath = dr.substring(index1, index2);

				
				DotToImageConverter obj1 = new DotToImageConverter();
				System.out.println("dot to image");  
				obj1.setSourcePath(dotpath); 
				obj1.ImageCreator();

				System.out.println("CurrentDirectory:"+CurrentDirectory); 
				
				Image img = Toolkit.getDefaultToolkit().getImage(
						CurrentDirectory + obj1.outputFile);
				 obji = new ImageZoomerFrame(img, 10, "Class Hierarchy",Report1.frame1,obj1); 
				 
    		}
    		
    		
    		
    		
    		
    		
		} 
    	   catch (Exception e1) 
    	   {
			e1.printStackTrace();
    	   }
        }
        if(src.equals("File Property"))
        {
        	this.setVisible(false);
        	Report1.frame1.setVisible(true);
        }
        if(src.equals("Save"))
        {
        	
        	System.out.println("f tracker:"+flow_tracker); 
        	if(flow_tracker==1)
        	{
        		/*try
        		{
        			flow_report_path=new File(Report1.intr_bk_path).getParent();
        			FileOutputStream fos=new FileOutputStream(flow_report_path+"\\"+h1.substring(0,h1.indexOf("."))+".xls");
        			wb.write(fos);
        			fos.flush();
        			fos.close();
        		}catch(FileNotFoundException fnf)
        		{

        		}catch(IOException ioe)
        		{

        		}
        		JOptionPane.showMessageDialog(this,"Flow report saved as"+flow_report_path+"\\"+h1.substring(0,h1.indexOf("."))+".xls");*/
        		JFileChooser chooser = new JFileChooser();
    			chooser.setMultiSelectionEnabled(false);
    			chooser.setFileFilter(new TextFileFilter2());
    			chooser.setAcceptAllFileFilterUsed(false);
    			int retVal = chooser.showSaveDialog(this);
    			File fileToSave = chooser.getSelectedFile();
    			FileOutputStream output=null;
               	
    			try {
    				if (retVal == JFileChooser.APPROVE_OPTION) {

    					String p=fileToSave.getAbsolutePath();


    					System.out.println("f tracker:"+flow_tracker); 

    					if(p.endsWith(".xls")){
    						System.out.println("1"+p);

    					}
    					else{
    						p = p+".xls";
    						System.out.println("2"+p);
    					}

    					//fileToSave.getPath();
    					System.out.println("3"+p);
    					output = new FileOutputStream(p);

    					wb.write(output);
    					output.flush();
    					output.close();
    				}
    			}catch (FileNotFoundException e1) {
    				// TODO Auto-generated catch block
    			    e1.printStackTrace();
    			} catch (IOException e1) {
    			   // TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
        	}
        	else if(flow_tracker==2)
        	{
        		JOptionPane.showMessageDialog(this,"First you should retrieve data to save flow report");
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(this,"First you should choose java file to save flow report");
        	}
        	flow_tracker=0;
        }
        
	}
		 
	

}

class TextFileFilter2 extends javax.swing.filechooser.FileFilter {
	public boolean accept(File f) {

		if (f.isDirectory()) {
			return true;
		} else {
			String fileExtn = null;
			String fname = f.getName();
			int i = fname.lastIndexOf('.');
			if (i > 0) {
				fileExtn = fname.substring(i + 1).toLowerCase();
				if (fileExtn.equals("jpg") || fileExtn.equals("jpeg")
						|| fileExtn.equals("bmp") || fileExtn.equals("png")
						|| fileExtn.equals("gif") || fileExtn.equals("tif")
						|| fileExtn.equals("tiff")) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}


}
