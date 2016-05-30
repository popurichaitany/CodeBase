package Home;



import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import gui.*;
import gui.ImageZoomerFrame.ImagePanel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.tools.JavaFileObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import converte.*;

public class Report1 extends JFrame implements ActionListener
{
	 static String[][] table=new String[1024][10];
     static String columns[] = {"Dir/File name","Type of File","Path","No of classes","LOC","Comments","No of Methods","Date of Creation(mm/dd/yyyy)","Last Date of Modification(mm/dd/yyyy)","Ageing"};
     static JTable table1;
     JButton cancelBtn,saveBtn,report2Btn,report3Btn;
    JPanel panel1,panel2;
    JPanel topPanel;
    static JFrame frame1;
    static String outpath,out,input,className;
    static String path[]=new String[2];
    static int r;
    private ImagePanel m_imagePanel;
	private JScrollPane m_srollPane;
	private JPanel m_imageContainer;
	private JLabel m_zoomedInfo;
	private JButton m_zoomInButton;
	private JButton m_zoomOutButton;
	private JButton m_originalButton;
	private Cursor m_zoomCursor;
	public static String intr_bk_path;
    public static File xlf;
    public static HSSFWorkbook wb;
	public Report1(String s)
	{
		javax.swing.JScrollPane scrollPane = null;
		this.setLayout(new BorderLayout());
		this.setTitle(s);
		
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		
		this.add(panel1,BorderLayout.NORTH);
		this.add(panel2,BorderLayout.SOUTH);
		
		cancelBtn=new JButton("Cancel");
		cancelBtn.setBounds(0,0,100,20);
		cancelBtn.addActionListener(this);
		panel1.add(cancelBtn);
		
		saveBtn=new JButton("Save Report");
		saveBtn.setBounds(120,0,150,20);
		saveBtn.addActionListener(this);
		panel1.add(saveBtn);
		
		report2Btn=new JButton("Class Hierarchy Report");
		report2Btn.setBounds(290,0,170,20);
		report2Btn.addActionListener(this);
		panel1.add(report2Btn);
		
		report3Btn=new JButton("Control Flow Report");
		report3Btn.setBounds(480,0,170,20);
		report3Btn.addActionListener(this);
		panel1.add(report3Btn);
		
		
		
		
		
//		
//		if(className.equals("BrowsePath"))// 
//		{
//			System.out.println(outpath);
//			generateExcel(new File(outpath));
//		}
//		else //GUI1
//		{
			System.out.println(out);
			generateExcel(new File(out));
		//}
        table1 = new JTable(table,columns);
   
        panel2.add(table1);
        scrollPane = new   JScrollPane
        		(
            	table1,  scrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            	scrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
            );
            add(scrollPane, BorderLayout.CENTER);
	}
     
     
     public static void main(String[] args)
	{
    	 out=args[0];//xml path
    	 
    	 intr_bk_path=args[0];
    	 className=args[2]; // which class is calling this main 
    	 r=out.charAt(out.length()-1);
    	 System.out.println("class"+className);
    	 
    	 
//    	 if(className.equals("BrowsePath"))
//    	 {
//	    	 if((r>=65 && r<=90)||(r>=97&&r<122))
//	    	 {
//	    		 outpath=args[0]+"\\CodeCrawler_Reports.xml";
//	    		 System.out.println("outpath if is "+outpath);
//	    	 }
//	    	 else
//	    	 {
//	    		 outpath=args[0]+"CodeCrawler_Reports.xml";
//	    		 System.out.println("outpath else is "+outpath);
//	    	 }
//    	 }
    	
	    	 //input=args[1];
	      frame1=new Report1("File Properties Report");
       
        
        frame1.pack(); 
      // frame.add(jp);
        frame1.setExtendedState(frame1.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        //frame.add(table1);
        frame1.setVisible(true);
       


	}
	 public static void generateExcel(File xmlDocument) 
     {
        try {
          // Creating a Workbook
        	xlf=xmlDocument;
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
           HSSFSheet spreadSheet = wb.createSheet("File Properties");

           spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));
           spreadSheet.setColumnWidth((short) 1, (short) (256 * 15));
           spreadSheet.setColumnWidth((short) 2, (short) (256 * 35));
           spreadSheet.setColumnWidth((short) 3, (short) (256 * 17));
           spreadSheet.setColumnWidth((short) 4, (short) (256 * 8));
           spreadSheet.setColumnWidth((short) 5, (short) (256 * 13));
           spreadSheet.setColumnWidth((short) 6, (short) (256 * 17));
           spreadSheet.setColumnWidth((short) 7, (short) (256 * 33));
           spreadSheet.setColumnWidth((short) 8, (short) (256 * 42));
           spreadSheet.setColumnWidth((short) 9, (short) (256 * 17));
           // Parsing XML Document
           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
           DocumentBuilder builder = factory.newDocumentBuilder();
           Document document = builder.parse(xmlDocument);
           NodeList nodeList = document.getElementsByTagName("Entity");
           // Creating Rows
           HSSFRow row = spreadSheet.createRow(0);

           HSSFCell cell = row.createCell((short) 0);
          cell.setCellStyle(cellStyle);
           cell.setCellValue("Dir/File name");//column names
           cell.setCellStyle(cellStyle);
           cell = row.createCell((short) 1);
           cell.setCellValue("Type of File");
           cell.setCellStyle(cellStyle);
           cell = row.createCell((short) 2);
           cell.setCellValue("Path");
           cell.setCellStyle(cellStyle);
           cell = row.createCell((short) 3);
           cell.setCellValue("No of classes");
           cell.setCellStyle(cellStyle);
           cell = row.createCell((short) 4);
           cell.setCellValue("LOC");
           cell.setCellStyle(cellStyle);
           cell = row.createCell((short) 5);
           cell.setCellValue("Comments");
           cell.setCellStyle(cellStyle);
           cell = row.createCell((short) 6);
           cell.setCellValue("No of Methods");
           cell.setCellStyle(cellStyle);
           cell = row.createCell((short) 7);
           cell.setCellValue("Date of Creation(mm/dd/yyyy)");
           cell.setCellStyle(cellStyle);
           cell = row.createCell((short) 8);
           cell.setCellValue("Last Date of Modification(mm/dd/yyyy)");
           cell.setCellStyle(cellStyle);
           cell = row.createCell((short) 9);
           cell.setCellValue("Ageing(Days)");
           cell.setCellStyle(cellStyle);
           //loop row count

           HSSFRow row1 = null;

           System.out.println(nodeList.getLength());
           for (int i = 0; i < nodeList.getLength(); i++) {
               
              cellStyle = wb.createCellStyle();
              cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
              cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
              cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
              cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
              
              row1 = spreadSheet.createRow(i+1);
                  cell = row1.createCell((short) 0);
                  cell.setCellValue(((Element) (nodeList.item(i))).getElementsByTagName("Name").item(0).getFirstChild().getNodeValue());
                  table[i][0]=cell.getStringCellValue();
                  cell = row1.createCell((short) 1);
                  cell.setCellValue(((Element) (nodeList.item(i))).getElementsByTagName("Type").item(0).getFirstChild().getNodeValue());
                  table[i][1]=cell.getStringCellValue();
                  cell = row1.createCell((short) 2);
                  cell.setCellValue(((Element) (nodeList.item(i))).getElementsByTagName("Path").item(0).getFirstChild().getNodeValue());
                  table[i][2]=cell.getStringCellValue();
                  cell = row1.createCell((short) 3);
                  cell.setCellValue(((Element) (nodeList.item(i))).getElementsByTagName("Classes").item(0).getFirstChild().getNodeValue());
                  table[i][3]=cell.getStringCellValue();
                  cell = row1.createCell((short) 4);
                  cell.setCellValue(((Element) (nodeList.item(i))).getElementsByTagName("LOC").item(0).getFirstChild().getNodeValue());
                  table[i][4]=cell.getStringCellValue();
                  cell = row1.createCell((short) 5);
                  cell.setCellValue(((Element) (nodeList.item(i))).getElementsByTagName("Comments").item(0).getFirstChild().getNodeValue());
                  table[i][5]=cell.getStringCellValue();
                  cell = row1.createCell((short) 6);
                  cell.setCellValue(((Element) (nodeList.item(i))).getElementsByTagName("Methods").item(0).getFirstChild().getNodeValue());
                  table[i][6]=cell.getStringCellValue();
                  cell = row1.createCell((short) 7);
                  if(!(((Element) (nodeList.item(i))).getElementsByTagName("CreationDate").item(0).getFirstChild().getNodeValue().equals("null")))
                  {
                  cell.setCellValue(((Element) (nodeList.item(i))).getElementsByTagName("CreationDate").item(0).getFirstChild().getNodeValue());
                  table[i][7]=cell.getStringCellValue();
                  }
                  else
                  {
                	  cell.setCellValue("Not available");
                      table[i][7]=cell.getStringCellValue();  
                  }
                  cell = row1.createCell((short) 8);
                  cell.setCellValue(((Element) (nodeList.item(i))).getElementsByTagName("LastModifiedDate").item(0).getFirstChild().getNodeValue());
                  table[i][8]=cell.getStringCellValue();
                  cell = row1.createCell((short) 9);
                  cell.setCellValue(((Element) (nodeList.item(i))).getElementsByTagName("Ageing").item(0).getFirstChild().getNodeValue());
                  table[i][9]=cell.getStringCellValue();
              



        }
      
        } 
        catch (IOException e) 
        {
           System.out.println("IOException " + e.getMessage());
        } 
        catch (ParserConfigurationException e) 
        {
           System.out
              .println("ParserConfigurationException " + e.getMessage());
        } 
        catch (SAXException e) 
        {
           System.out.println("SAXException " + e.getMessage());
        }

     }


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		 String src=e.getActionCommand();
       
           
           System.out.println("inside action performd");
           if(src.equals("Cancel"))
           {
        	   int confirm = JOptionPane.showOptionDialog(frame1,
                       "Are You Sure to Close this Application?",
                       "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                       JOptionPane.QUESTION_MESSAGE, null, null, null);
               if (confirm == JOptionPane.YES_OPTION) 
               {
                   System.exit(1);
               }
           }
           else if(src.equals("Class Hierarchy Report"))
           {
        	  //frame.setVisible(false);
        	   
        	   
        	   try {
        		   
        		String dr="";
        		//frame1.setVisible(false);
				//frame = new Report2("Class Hierarchy Report");
        		//Report2.main(path);
        		fileBackup f=new fileBackup();
        		File ijf=new File(intr_bk_path); 
        		System.out.println("Parameter got:"+intr_bk_path); 
        		if(ijf.isFile())
        		{
        			this.frame1.setVisible(false);
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
					 obji = new ImageZoomerFrame(img, 10, "Class Hierarchy",this.frame1,obj1); 
					 

					
					
        		}
        		if(ijf.isDirectory())
        		{
        			dr=ijf.getPath();
        		}
        		
        		
        		
        		
        		
			} 
        	   catch (Exception e1) 
        	   {
				e1.printStackTrace();
        	   }
       		
       		
           }
           else if(src.equals("Control Flow Report"))
           {
        	
        	   try 
        	   {
        		   
        		// frame1.setVisible(false);
        		// frame1.disable();
        		/* String a[]=new String[2];
        		 a[0]=input;//main i/p for tree
           		 FileTreeDemo.main(a);*/
        		   this.frame1.setVisible(false);
           		 ControlFlowReport cfr=new ControlFlowReport(out);
           		 cfr.setExtendedState(frame1.getExtendedState() | JFrame.MAXIMIZED_BOTH);
           		 cfr.setVisible(true); 
           		 
			  
        	   }
        	   catch(Exception e1) 
        	   {
				e1.printStackTrace();
        	   }
       		
       		
           }
           else if(src.equals("Save Report"))
          {
        	     // Outputting to Excel spreadsheet
              /*	FileOutputStream output=null;
              	String h1="";
              	try
              	{
                  if(className.equals("BrowsePath"))
       	      	 {
       	        	outpath=xlf.getParent();
       	        output = new FileOutputStream(outpath+"\\FileProperties.xls");
       	      	 }
                  else
                  {
               	  h1=new File(out).getParent();
       	           if((r>=65 && r<=90)||(r>=97 && r<122))
       	      	 {
       		      	 h1= h1+"\\FileProperties.xls";
       		      output = new FileOutputStream(h1);
       	      	 }
       	      	 else
       	      	 {
       		      		 h1= h1+"FileProperties.xls";
       		      		 output = new FileOutputStream(h1);
       	      	 }
                  }
                  System.out.println(h1); 
                  JOptionPane.showMessageDialog(this,"Report saved as:"+h1);
                  wb.write(output);
                  output.flush();
                  output.close();
              	}catch(IOException ioe)
              	{
              		System.out.println(ioe);
               	}*/
        	   JFileChooser chooser = new JFileChooser();
   			chooser.setMultiSelectionEnabled(false);
   			
   			chooser.setAcceptAllFileFilterUsed(false);
   			int retVal = chooser.showSaveDialog(this);
   			File fileToSave = chooser.getSelectedFile();
   			
       	   FileOutputStream output=null;
             	try
             	{
             		if (retVal == JFileChooser.APPROVE_OPTION) {
                        String p=fileToSave.getAbsolutePath();
                   
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
             		} catch (FileNotFoundException e1) {
           			// TODO Auto-generated catch block
           			e1.printStackTrace();
           		} catch (IOException e1) {
           			// TODO Auto-generated catch block
           			e1.printStackTrace();
           		}
           }


}
}


