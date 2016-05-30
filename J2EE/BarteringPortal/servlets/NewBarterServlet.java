package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import beans.BarterPostPojo;
import user.BarterPostUtility;
import user.UserUtility;

/**
 * Servlet implementation class NewBarterServlet
 */
@WebServlet("/NewBarterServlet")
@MultipartConfig
public class NewBarterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//private static final String DATA_DIRECTORY = "D:\\EclipseWorkspace\\BarteringSystem\\WebContent\\uploadeddata";
	private static final String DATA_DIRECTORY = "D:\\apache-tomcat-7.0.64\\webapps\\barter\\uploadeddata";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;
    
    private static Logger log=Logger.getLogger(NewBarterServlet.class.getName());
    
    private BarterPostUtility barterPostObj = new BarterPostUtility();
    private UserUtility userUtility = new UserUtility(); 
    
    
    public UserUtility getUserUtility() {
		return userUtility;
	}

	public void setUserUtility(UserUtility userUtility) {
		this.userUtility = userUtility;
	}

	public BarterPostUtility getBarterPostObj() {
		return barterPostObj;
	}

	public void setBarterPostObj(BarterPostUtility barterPostObj) {
		this.barterPostObj = barterPostObj;
	}

	/**
     * @see HttpServlet#HttpServlet()
     */
    public NewBarterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		log.info("In New Barter Servlet :: doPost Method");
		
		NewBarterServlet ser = new NewBarterServlet();
		
		try {
	        
		
		String imageName=ser.uploadFile(request,response);
		
		String title=request.getParameter("title");
		String offeringProductCategory=request.getParameter("offeringProductCategory");
		String myOffer=request.getParameter("myOffer");
		String expectedProductCategory=request.getParameter("expectedProductCategory");
		String askOffer=request.getParameter("askOffer");
		String contactDetail=request.getParameter("contactDetail");
		
		log.info("title :: "+title+" category offer ::"+offeringProductCategory+" myoffer :: "+myOffer+" expected cate ::"+expectedProductCategory+" askOffer :: "+askOffer+" contact :: "+contactDetail);
		BarterPostPojo barterPostPojo = new BarterPostPojo();
		
		barterPostPojo.setTitle(title);
		int offeringProductCategoryValue = Integer.parseInt(offeringProductCategory);
		barterPostPojo.setOfferingCatId(offeringProductCategoryValue);
		barterPostPojo.setItemOfferedDesc(myOffer);
		int expectedProductCategoryValue = Integer.parseInt(expectedProductCategory);
		barterPostPojo.setExpectedCatId(expectedProductCategoryValue);
		barterPostPojo.setExpectedItemDesc(askOffer);
		barterPostPojo.setTradeContact(contactDetail);
		barterPostPojo.setItemImage(imageName);
		String emailLoggedIn = (String)request.getSession().getAttribute("email");
		
		if(emailLoggedIn!=null && emailLoggedIn.length()>0){
			log.info("Email ::"+emailLoggedIn);
			int id = getUserUtility().fetchUserIdFromEmail(emailLoggedIn);
			barterPostPojo.setUserId(id);
		}	
		else
			log.info("email not found in session");
		
		// Code to upload image
		
		// byte[] photoBytes = readBytesFromFile("D:\\apache-tomcat-7.0.64\\webapps\\barter\\uploadeddata\\"+imageName);
		// barterPostPojo.setUploadedimg(photoBytes);
        
			int requestId = 0 ;
    		if(barterPostPojo!=null)
    			requestId = getBarterPostObj().addNewBarterPost(barterPostPojo);
    		
           
    		String returnUrl="/pages/newBarterForm.jsp";
			request.setAttribute("message","Barter Post updated successfully");
			RequestDispatcher view = request.getRequestDispatcher(returnUrl);
	        view.forward(request, response);
    		
          
        }  catch (Exception ex) {
            throw new ServletException(ex);
        }
	}
	
/**	public void uploadFileGoodCode(HttpServletRequest request){
		 File file ;
		   int maxFileSize = 5000 * 1024;
		   int maxMemSize = 5000 * 1024;
		   String filePath = "D:\\imageupload";
		  
		 try{ 

		      DiskFileItemFactory factory = new DiskFileItemFactory();
		      factory.setSizeThreshold(maxMemSize);
		      
		      ServletFileUpload upload = new ServletFileUpload(factory);
		      upload.setSizeMax( maxFileSize );
		      
		         // Parse the request to get file items.
		         List fileItems = upload.parseRequest(request);
		         log.info("uploaded items ::"+fileItems.size());
		         // Process the uploaded file items
		         Iterator i = fileItems.iterator();

		         
		         while ( i.hasNext () ) 
		         {
		        	 
		            FileItem fi = (FileItem)i.next();
		            if ( !fi.isFormField () )	
		            {
		            // Get the uploaded file parameters
		            	log.info("is formm field");
		            String fieldName = fi.getFieldName();
		            String fileName = fi.getName();
		            log.info(fileName);
		            String name = new File(fi.getName()).getName();
		            log.info("new "+fileName);
		            String fp = filePath + File.separator + name;
		            log.info("dir "+fp);
		            fi.write( new File(fp));
		            fileItems.clear();
		            }
		         }
		 }catch (FileUploadException ex) {
	            ex.printStackTrace();
	        }catch (Exception ex) {
	        	 ex.printStackTrace();
	        }
		 
		 } */
	
	public String uploadFile(HttpServletRequest request,HttpServletResponse response){
		
		// Create path components to save the file
	   // final String path = "D:\\EclipseWorkspace\\BarteringSystem\\WebContent\\uploadeddata";
	    String fileNameInDB = null;

	    OutputStream out = null;
	    InputStream filecontent = null;
	    try {
	    	final Part filePart = request.getPart("file");
		    String fileName = getFileName(filePart);
		    
		    
		    Random r = new Random(System.currentTimeMillis());
		    int tempRandom = 10000+r.nextInt(20000);
		    log.info("Random value::"+tempRandom+"  File NAME in upload :: "+fileName);
		    fileNameInDB =tempRandom+"_"+fileName;
		    log.info("File Name for DB :: "+fileNameInDB);
		    fileName=fileNameInDB;
		    log.info("File to be pushed :: "+fileNameInDB);
		    log.info("File NAME in upload :: "+fileName);
		    final PrintWriter writer = response.getWriter();
		    String dir = DATA_DIRECTORY + File.separator + fileName;
		    
		    
		   // fileNameInDB = dir;
		    
	        out = new FileOutputStream(new File(dir));
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	    } catch (Exception fne) {
	        fne.printStackTrace();
	    } finally {
	    	try{
	    	 out.close();
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	    return fileNameInDB;
	}

	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    log.info("Part Header = {0}"+ partHeader);
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
		
	 private static byte[] readBytesFromFile(String filePath) throws IOException {
	        File inputFile = new File(filePath);
	        FileInputStream inputStream = new FileInputStream(inputFile);
	         
	        byte[] fileBytes = new byte[(int) inputFile.length()];
	        inputStream.read(fileBytes);
	        inputStream.close();
	         
	        return fileBytes;
	    }

}
