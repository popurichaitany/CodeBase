package user;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import beans.AccountPojo;
import beans.BarterPostPojo;
import beans.TradePojo;
import beans.UserPojo;
import utils.HibernateConnUtil;


public class BarterPostUtility {
	
	private static Logger log=Logger.getLogger(BarterPostUtility.class.getName());
	//private static SessionFactory factory = HibernateConnUtil.getSessionFactory();
	
	public BarterPostUtility(){
		//factory = new Configuration().configure().buildSessionFactory();
	}
	
	
	public int addNewBarterPost(BarterPostPojo barterPostObj){
		//log.info("Save image from path :: "+imagePath);
		int reqId = 0;
		Transaction tx = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			
			/**File file = new File(imagePath);
	        FileInputStream inputStream = new FileInputStream(file);
	        Blob blob = Hibernate.getLobCreator(session)
	                            .createBlob(inputStream, file.length());
	        barterPostObj.setUploadedimg(blob);
			blob.free();
			*/tx=session.beginTransaction();
			
			reqId = (Integer)session.save(barterPostObj);			
			if(reqId > 0){
				log.info("New Barter Post Uploaded with reqId :: "+reqId);
				
			}else{
				log.info("Barter Post Upload Failed");
			}
			tx.commit();
		}catch(HibernateException e){
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}catch(Exception e){
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
		}
		
		
		return reqId;
	}
	
	
	public List<BarterPostPojo> fetchBarteringPostsForGuestUser(String category){
		log.info("fetchBarteringPostsForGuestUser :: "+category);
		List<BarterPostPojo> barterList = new ArrayList<BarterPostPojo>();
		int userId = 0;
		int catId = 0;
		Transaction tx = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			
			tx=session.beginTransaction();
			Criteria cr = session.createCriteria(BarterPostPojo.class);
			if("All".equalsIgnoreCase(category)){
				List results = cr.list();
				if(results!=null && results.size()>0){
					for (Iterator iterator = results.iterator(); iterator.hasNext();){
						BarterPostPojo pobj = (BarterPostPojo) iterator.next(); 
						log.info("Barter Post ID :: "+pobj.getReqId());
						barterList.add(pobj);
					}
				}else{
					log.info("No Barter Post Found");
				}
			}else{
				int value = Integer.parseInt(category);
				cr.add(Restrictions.eq("offeringCatId", value));
				List results = cr.list();
				if(results!=null && results.size()>0){
					for (Iterator iterator = results.iterator(); iterator.hasNext();){
						BarterPostPojo pobj = (BarterPostPojo) iterator.next(); 
						log.info("Barter Post ID :: "+pobj.getReqId());
						barterList.add(pobj);
					}
				}else{
					log.info("No Barter Post Found");
				}
			}
			/*Finding Total Pages for Pagination*/
			int maxResultsPerPage = 9;
			int totalRecords = barterList.size();
			int totalPages=(totalRecords%maxResultsPerPage==0?totalRecords/maxResultsPerPage:(totalRecords/maxResultsPerPage)+1);
			log.info("Total Records :: "+totalRecords+" No of pages required ::"+totalPages);
			tx.commit();
		}catch(HibernateException e){
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return barterList;
	}
	
	public List<BarterPostPojo> fetchAllPostForLoginUser(String category,String email){
		log.info("fetchAllPostForLoginUser :: "+category+" "+email);
		List<BarterPostPojo> barterList = new ArrayList<BarterPostPojo>();
		int userId = 0;
		int catId = 0;
		Transaction tx = null;
		List results = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			
			log.info("Email obtained in param :: "+email);
			
			tx=session.beginTransaction();
			Criteria cr = session.createCriteria(BarterPostPojo.class);
			UserUtility utilObj = new UserUtility();
			userId = utilObj.fetchUserIdFromEmail(email);
			if("All".equalsIgnoreCase(category)){
				
				cr.add(Restrictions.ne("userId", userId));
				results = cr.list();
				
			}else{
				
				
				Criterion usercheck = Restrictions.ne("userId", userId);
				int value = Integer.parseInt(category);
				Criterion categorycheck = Restrictions.eq("offeringCatId", value);
				LogicalExpression andExp = Restrictions.and(usercheck, categorycheck);
				cr.add( andExp );
				results = cr.list();
				
				
			}
			
			if(results!=null && results.size()>0){
					for (Iterator iterator = results.iterator(); iterator.hasNext();){
						BarterPostPojo pobj = (BarterPostPojo) iterator.next(); 
						log.info("Barter Post ID :: "+pobj.getReqId());
						barterList.add(pobj);
					}
			}else{
					log.info("No Barter Post Found");
			}
			
			/*Finding Total Pages for Pagination*/
			int maxResultsPerPage =9 ;
			int totalRecords = barterList.size();
			int totalPages=(totalRecords%maxResultsPerPage==0?totalRecords/maxResultsPerPage:(totalRecords/maxResultsPerPage)+1);
			log.info("Total Records :: "+totalRecords+" No of pages required ::"+totalPages);
			tx.commit();
		}catch(HibernateException e){
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return barterList;
		
		
	}
	
	public List<BarterPostPojo> fetchMyBarterPosts(String email){

	log.info("fetchMyBarterPosts :: "+email);
	List<BarterPostPojo> barterList = new ArrayList<BarterPostPojo>();
	int userId = 0;
	int catId = 0;
	Transaction tx = null;
	List results = null;
	Session session = HibernateConnUtil.getSessionFactory().openSession();
	try{
		
		log.info("Email obtained in param :: "+email);
		
		tx=session.beginTransaction();
		Criteria cr = session.createCriteria(BarterPostPojo.class);
		UserUtility utilObj = new UserUtility();
		userId = utilObj.fetchUserIdFromEmail(email);
		cr.add(Restrictions.eq("userId", userId));
		results = cr.list();
	
		if(results!=null && results.size()>0){
				for (Iterator iterator = results.iterator(); iterator.hasNext();){
					BarterPostPojo pobj = (BarterPostPojo) iterator.next(); 
					log.info("Barter Post ID :: "+pobj.getReqId());
					barterList.add(pobj);
				}
		}else{
				log.info("No Barter Post Found");
		}
		
		/*Finding Total Pages for Pagination*/
		int maxResultsPerPage =9 ;
		int totalRecords = barterList.size();
		int totalPages=(totalRecords%maxResultsPerPage==0?totalRecords/maxResultsPerPage:(totalRecords/maxResultsPerPage)+1);
		log.info("Total Records :: "+totalRecords+" No of pages required ::"+totalPages);
		tx.commit();
	}catch(HibernateException e){
		if(tx!=null)
			tx.rollback();
		e.printStackTrace();
	}finally{
		session.close();
	}
	
	return barterList;
	
	
}
	
	
	public int fetchUserIdFromReqId(int reqId){
		int userId=0;
		
		Transaction tx = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			tx=session.beginTransaction();
			Criteria cr = session.createCriteria(BarterPostPojo.class);
			cr.add(Restrictions.eq("reqId", reqId));
			List results = cr.list();
			
			if(results!=null && results.size()>0){
				
				for (Iterator iterator = results.iterator(); iterator.hasNext();){
					BarterPostPojo pobj = (BarterPostPojo) iterator.next(); 
					userId= pobj.getUserId();
				}
			}else{
				log.info("fetchUserIdFromReqId :: No result found");
			}
			tx.commit();
			
		}catch(HibernateException e){
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return userId;
		
	}
	
	public int addBarterTradeRequest(String primReqId , String secReqId){
		int tradeId = 0; 
		TradePojo obj = new TradePojo();
		UserUtility userUtil = new UserUtility();
		int primId = Integer.parseInt(primReqId);
		int secId = Integer.parseInt(secReqId);
		
		obj.setPrimaryReqId(primId);
		obj.setSecReqId(secId);
		obj.setRequestStatus("Request-Sent");
		
		BarterPostUtility util = new BarterPostUtility();
		int primUserId = util.fetchUserIdFromReqId(primId);
		
		int primUserRating = userUtil.fetchUserRatingFromUserId(primUserId);
		
		int secUserId = util.fetchUserIdFromReqId(secId);
		
		int secUserRating = userUtil.fetchUserRatingFromUserId(secUserId);
		
		obj.setPrimaryUserRating(primUserRating);
		obj.setSecUserRating(secUserRating);
		obj.setPrimaryTraderUserId(primUserId);
		obj.setSecTraderUserId(secUserId);
		Date date = new Date();
		obj.setTradeStatusTime(date);
		Transaction tx = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			tx=session.beginTransaction();
			tradeId = (Integer)session.save(obj);
			log.info("Trade Id :: "+tradeId);
			tx.commit();
			
		}catch(HibernateException e){
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return tradeId;
	}

}
