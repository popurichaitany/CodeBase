package file_prop;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;


public class excel_create {
	FileOutputStream fileOut;
    HSSFWorkbook workbook;
    HSSFSheet worksheet;
    HSSFRow row1= null;//worksheet.createRow((short) 1);
    HSSFCell cellA1=null;//row1.createCell((short) 1);
    HSSFCellStyle cellStyle=null;
    HSSFFont font =null;
    HSSFCellStyle style=null;
    int nooftags=10000;
    String resData[] = new String[nooftags];
    String dateString;
	void create() throws FileNotFoundException
	{
	fileOut = new FileOutputStream("poi-test.xls");
      workbook = new HSSFWorkbook();
      worksheet =workbook.createSheet("POI Worksheet");
      row1= null;
     cellA1=null;
     cellStyle = null;
     
	}
	void close() throws IOException
	{
	  workbook.write(fileOut);
      fileOut.flush();
      fileOut.close();
      
	}
	
	void cell_create(int row, int col, String file_name)
	{
		row1=worksheet.createRow((short) row);
		cellA1=row1.createCell((short) col);
		cellA1.setCellValue(file_name);
	}	
	void cell_create(int row, int col, long file_name)
	{
		row1=worksheet.createRow((short) row);
		cellA1=row1.createCell((short) col);
		cellA1.setCellValue(file_name);
	}

	

	public String count_classes(String file_path){
		String s;
		String temp=file_path;
		int clcnt=0,cmcnt=0,cFlag=0,rdnFlag=0;
		try
		{
		File f1=new File(temp); //instantiate file to read
        BufferedReader br=new BufferedReader(new FileReader(f1)); //pass file object to buffer to read
        while((s=br.readLine())!=null)//while there are non null line
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
            if(s.contains("class") && cFlag==0 && cFlag!=3)
            {
              	clcnt++;
            }
            if(cFlag==3)
            	cFlag=0;
            rdnFlag=0;
        }
       
         
		}catch(FileNotFoundException fnf)
		{
			System.out.println(fnf.getMessage());
		}catch(IOException ioe) 
		{
		   System.out.println(ioe.getMessage()); 
		   
	}
		return ""+clcnt; 
	}
	
	public String count_methods(String file_path){
		String s;
		String temp=file_path;
		int mcnt=0,cFlag=0,rdnFlag=0;;
		try
		{
		File f1=new File(temp);
        BufferedReader br=new BufferedReader(new FileReader(f1));
        while((s=br.readLine())!=null)
        {
        	 if(s.contains("//") && !(s.contains(";")))
             {
             	
             	cFlag=3;
             }
             if(s.contains("/*"))
             {
             	cFlag=1;
             	
             	if(rdnFlag==0)
             	{
             	 rdnFlag=1;
             	}
             }
             if((s.contains("* @") || s.contains("*")) && rdnFlag==0)
             {
             	if(cFlag==1)
                 {
                 	
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
                 	 
                 	}
                 	cFlag=0; 
                 }
             	
             }
             //following if has regular expression for method signature and conditions include non commenting area check  
        	if((s.contains("public ") || s.contains("private ") || s.contains("protected ") || s.contains("void ") || s.contains("int") || 
        		s.contains("float ") || s.contains("long ") || s.contains("String ") || s.contains("double ")) 
                &&( s.contains("(") && s.contains(")") && !(s.contains(";"))) && cFlag==0 && cFlag!=3)
        	{ 
           		 mcnt++;
           	} 
           	if(cFlag==3)
                 cFlag=0;
           	 rdnFlag=0;
        }
       
         
		}catch(FileNotFoundException fnf)
		{
			System.out.println(fnf.getMessage());
		}catch(IOException ioe) 
		{
		   System.out.println(ioe.getMessage()); 
		   
	}
		return ""+mcnt; 
	}
	
	public String count_comment_lines(String file_path){
		String s;
		String temp=file_path;
		int cmcnt=0,cFlag=0,rdnFlag=0;
		try
		{
		File f1=new File(temp);
        BufferedReader br=new BufferedReader(new FileReader(f1));
        while((s=br.readLine())!=null)
        {
 
            if(s.contains("//") && !(s.contains(";")))
            {
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
            
            rdnFlag=0;//variable has functionality that if once commenting line encountered by any condition
            //then again that line should not be counted in commenting lines count. 
            
        }
       
         
		}catch(FileNotFoundException fnf)
		{
			System.out.println(fnf.getMessage());
		}catch(IOException ioe) 
		{
		   System.out.println(ioe.getMessage()); 
		   
	}
		return ""+cmcnt; 
	}
	
	
	public String count_LOC(String file_path){
		String s;
		String temp=file_path;
		int counter=0;
		try
		{
		File f1=new File(temp);
        BufferedReader br=new BufferedReader(new FileReader(f1));
        while((s=br.readLine())!=null)//until there are lines in file 
        {
        	counter++;
        }
        
        }
	catch(FileNotFoundException fnf)
	{
		System.out.println(fnf.getMessage());
	}catch(IOException ioe) 
	{
	   System.out.println(ioe.getMessage()); 
	}	
	return ""+counter;
}

	
	
	public String file_last_modified(String path){
		File f = new File(path);
        long datetime = f.lastModified();
        Date d = new Date(datetime);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
         dateString= sdf.format(d);//converting date to the format of MM/dd/yyyy
        return (dateString);
	}
	
	public String file_created(String path)
	{
	 String date=null;
        try{

            Process proc = 
               Runtime.getRuntime().exec("cmd /c dir "+path+ " /tc");//firing dir command from thread invocation

            BufferedReader br = 
               new BufferedReader(
                  new InputStreamReader(proc.getInputStream()));

            String data ="";

            //it's quite stupid but work
            for(int i=0; i<6; i++){
                            data = br.readLine();
                          
            }

          
         
            //split by space
           try
           {
            StringTokenizer st = new StringTokenizer(data);
            date = st.nextToken();//Get date
            }catch(NullPointerException np)
            {
            	date=null;
            }
           //System.out.println(date);

        }catch(IOException e){

            e.printStackTrace();

        }
		return date;

	}
	
	public String file_extension(String file_name){
		int i=file_name.lastIndexOf(".");
		String exstn=file_name.substring(i+1);
		return exstn;
	}
	
	public String Ageing(String file_path){
		
		File f = new File(file_path);
		Date date1=new Date();
		double intr=date1.getTime();//today's date
		Date date2=new Date(dateString);//last modification date
		double intr2=date2.getTime();
        double dnm=1000*3600*24;//total seconds in one day
        double days=0;
        days=(intr-intr2)/dnm;//difference between today's date and last modification date 
        String str=""+days;
		int k=str.indexOf('.');
		String str2=str.substring(0,k);
		
		     return str2;
	}
	
	void label(String label1,int i){
		row1=worksheet.createRow((short) 0);
   		cellA1 = row1.createCell((short) i);
   		cellA1.setCellValue(label1);
   		cellStyle = workbook.createCellStyle();
   		font = workbook.createFont(); 
   		font.setFontName(HSSFFont.FONT_ARIAL);         
   		font.setFontHeightInPoints((short) 12);         
   		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         
   		font.setColor(HSSFColor.BLACK.index); 
		cellStyle.setFont(font); 
   		cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        cellStyle.setFillPattern(HSSFCellStyle.SPARSE_DOTS);
        cellA1.setCellStyle(cellStyle);
	}
	

	
}

