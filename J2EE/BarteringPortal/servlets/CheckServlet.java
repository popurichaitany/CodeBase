package servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;



/**
 * Servlet implementation class CheckServlet
 */
@WebServlet("/CheckServlet")
@MultipartConfig
public class CheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log=Logger.getLogger(CheckServlet.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckServlet() {
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
		//doGet(request, response);
		log.info("Inside Check servlet");
		
			File file ;
		   int maxFileSize = 5000 * 1024;
		   int maxMemSize = 5000 * 1024;
		  // ServletContext context = pageContext.getServletContext();
		   //String filePath = context.getInitParameter("file-upload");
		   String filePath = "D:\\imageupload";
		   // Verify the content type
		   String contentType = request.getContentType();
		   if ((contentType.indexOf("multipart/form-data") >= 0)) {

		      DiskFileItemFactory factory = new DiskFileItemFactory();
		      // maximum size that will be stored in memory
		      factory.setSizeThreshold(maxMemSize);
		      // Location to save data that is larger than maxMemSize.
		     // factory.setRepository(new File("c:\\temp"));

		      // Create a new file upload handler
		      ServletFileUpload upload = new ServletFileUpload(factory);
		      // maximum file size to be uploaded.
		      upload.setSizeMax( maxFileSize );
		      try{ 
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
		            //boolean isInMemory = fi.isInMemory();
		          //  long sizeInBytes = fi.getSize();
		            // Write the file
		            /**if( fileName.lastIndexOf("\\") >= 0 ){
			            file = new File( filePath + 
			            fileName.substring( fileName.lastIndexOf("\\"))) ;
		            }else{
			            file = new File( filePath + 
			            fileName.substring(fileName.lastIndexOf("\\")+1)) ;
		            }
		            fi.write( file ) ;*/
		            String name = new File(fi.getName()).getName();
		            log.info("new "+fileName);
		            String fp = filePath + File.separator + name;
		            log.info("dir "+fp);
		            fi.write( new File(fp));

	}else{
		log.info("Not is formm field");
	}

}
		      }catch(Exception ex) {
		          System.out.println(ex);
		      }
}
	}
}