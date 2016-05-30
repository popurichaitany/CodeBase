package user;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import beans.AccountPojo;
import beans.UserPojo;
import utils.HibernateConnUtil;


public class UserUtility {

	private static Logger log=Logger.getLogger(UserUtility.class.getName());
	//private static SessionFactory factory = HibernateConnUtil.getSessionFactory();
	
	public UserUtility(){
		//factory = new Configuration().configure().buildSessionFactory();
	}
	
	public boolean validateUser(String email , String password){
		boolean userAuthenticated = false;
		
		Transaction tx = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			tx=session.beginTransaction();
			Criteria cr = session.createCriteria(AccountPojo.class);
			Criterion emailcheck = Restrictions.eq("emailId", email);
			Criterion passwordcheck = Restrictions.eq("password", password);
			
			LogicalExpression andExp = Restrictions.and(emailcheck, passwordcheck);
			cr.add( andExp );

			List results = cr.list();
			
			if(results!=null && results.size()>0){
				log.info("User Found ");
				userAuthenticated=true;
			}else{
				log.info("User not found");
			}
			tx.commit();
			
		}catch(HibernateException e){
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return userAuthenticated;
		
	}
	
	public String fetchUsername(String email){
		String name=null;
		
		Transaction tx = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			tx=session.beginTransaction();
			Criteria cr = session.createCriteria(UserPojo.class);
			cr.add(Restrictions.eq("emailId", email));
			List results = cr.list();
			
			if(results!=null && results.size()>0){
				
				for (Iterator iterator = results.iterator(); iterator.hasNext();){
					UserPojo pobj = (UserPojo) iterator.next(); 
					name= pobj.getFirstName();
				}
			}else{
				log.info("fetchUsername :: Result not  found");
			}
			tx.commit();
			
		}catch(HibernateException e){
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return name;
		
	}
	
	public String fetchNamefromUserId(int userId){
		String name=null;
		
		Transaction tx = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			tx=session.beginTransaction();
			Criteria cr = session.createCriteria(UserPojo.class);
			cr.add(Restrictions.eq("userId", userId));
			List results = cr.list();
			
			if(results!=null && results.size()>0){
				
				for (Iterator iterator = results.iterator(); iterator.hasNext();){
					UserPojo pobj = (UserPojo) iterator.next(); 
					String fname= pobj.getFirstName();
					String lname = pobj.getLastName();
					name= fname+" "+lname;
				}
			}else{
				log.info("fetchNamefromUserId :: Result not  found");
			}
			tx.commit();
			
		}catch(HibernateException e){
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return name;
		
	}
	
	public String fetchContactfromUserId(int userId){
		String contact=null;
		
		Transaction tx = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			tx=session.beginTransaction();
			Criteria cr = session.createCriteria(UserPojo.class);
			cr.add(Restrictions.eq("userId", userId));
			List results = cr.list();
			
			if(results!=null && results.size()>0){
				
				for (Iterator iterator = results.iterator(); iterator.hasNext();){
					UserPojo pobj = (UserPojo) iterator.next(); 
					contact = pobj.getPhone();
				}
			}else{
				log.info("fetchContactfromUserId :: Result not  found");
			}
			tx.commit();
			
		}catch(HibernateException e){
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return contact;
		
	}
	
	public int fetchUserIdFromEmail(String email){
		int userId=0;
		
		Transaction tx = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			tx=session.beginTransaction();
			Criteria cr = session.createCriteria(UserPojo.class);
			cr.add(Restrictions.eq("emailId", email));
			List results = cr.list();
			
			if(results!=null && results.size()>0){
				
				for (Iterator iterator = results.iterator(); iterator.hasNext();){
					UserPojo pobj = (UserPojo) iterator.next(); 
					userId= pobj.getUserId();
				}
			}else{
				log.info("fetchUserIdFromEmail :: No result found");
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
	
	public int fetchUserRatingFromUserId(int userId){
		int userRating=0;
		
		Transaction tx = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			tx=session.beginTransaction();
			Criteria cr = session.createCriteria(UserPojo.class);
			cr.add(Restrictions.eq("userId", userId));
			List results = cr.list();
			
			if(results!=null && results.size()>0){
				
				for (Iterator iterator = results.iterator(); iterator.hasNext();){
					UserPojo pobj = (UserPojo) iterator.next(); 
					userRating= pobj.getUserRating();
				}
			}else{
				log.info("fetchUserRatingFromUserId :: No result found");
			}
			tx.commit();
			
		}catch(HibernateException e){
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return userRating;
		
	}
	
	public int addNewUser(UserPojo userObj){
		int  userId = 0;
		Transaction tx = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			tx=session.beginTransaction();
			userId = (Integer)session.save(userObj);			
			if(userId > 0){
				log.info("New User Created !! with id :: "+userId);	
			}else{
				log.info("User Creation Failed");
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
	
	public boolean addNewUserDetailsToAccount(AccountPojo account){
		boolean result = false;
		Transaction tx = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			tx=session.beginTransaction();
			
			int id = (Integer)session.save(account);			
			if(id > 0){
				log.info("Account Updated for new user !! with id :: "+id);
				result=true;
			}else{
				log.info("Account update Failed");
			}
			tx.commit();
		}catch(HibernateException e){
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		
		return result;
	}
	
	public UserPojo fetchUserProfile(String email){
		log.info("Fetching user profile for Editing");
		UserPojo obj = new UserPojo();
		
		Transaction tx = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			tx=session.beginTransaction();
			Criteria cr = session.createCriteria(UserPojo.class);
			cr.add(Restrictions.eq("emailId", email));
			List results = cr.list();
			
			if(results!=null && results.size()>0){
				
				for (Iterator iterator = results.iterator(); iterator.hasNext();){
					UserPojo pobj = (UserPojo) iterator.next(); 
					obj = pobj;
					log.info("Profile found ::"+obj.getUserId());
				}
			}else{
				log.info("fetchUserIdFromEmail :: No result found");
			}
			tx.commit();
			
		}catch(HibernateException e){
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return obj;
		
	}
	
	public void updateFinalUserRating(int userId,int rating){
		log.info("Updating Final User Rating");
		
		Transaction tx = null;
		Session session = HibernateConnUtil.getSessionFactory().openSession();
		try{
			tx=session.beginTransaction();
			Criteria cr = session.createCriteria(UserPojo.class);
			cr.add(Restrictions.eq("userId", userId));
			List results = cr.list();
			
			if(results!=null && results.size()>0){
				
				for (Iterator iterator = results.iterator(); iterator.hasNext();){
					UserPojo pobj = (UserPojo) iterator.next();
					int oldRating = pobj.getUserRating();
					double tempRating = Math.ceil((rating+oldRating)/2);
					int newRating = (int)tempRating;
					pobj.setUserRating(newRating);
					session.update(pobj);
				}
			}else{
				log.info("updateFinalUserRating :: Result not  found");
			}
			tx.commit();
			
		}catch(HibernateException e){
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		
		
	}
}
