/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSONparser;
//import static JSONparser.BusinessObjparser.FileName;
//import Model.BusinessObject;
import DbManip.JDBCconn;
import Model.reviewobject;
//import org.json.simple.JSONObject; 
//import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;
//import java.sql.*;
import java.util.List;
/**
 *
 * @author prath
 */
public class reviewsobjparser {
   
    static String FileName="C:\\Users\\prath\\Downloads\\PRATHAMESH_HW2\\PRATHAMESH_HW2\\YelpDataset\\YelpDataset-CptS451\\yelp_review.json";
   /* public static void main(String x[]){
   
    try {
        List<reviewobject> jsons=ReadJSON(new File(FileName),"UTF-8");
    } catch (FileNotFoundException | ParseException e) {
    }
}*/
    public static synchronized List<reviewobject> ReadJSON(String Encoding) throws FileNotFoundException, ParseException {
    Scanner scn=new Scanner(new File(FileName) ,Encoding);
    ArrayList<JSONObject> json=new ArrayList<>();
//Reading and Parsing Strings to Json
    int iterate = 0;
    List<reviewobject> RObjList = new ArrayList<>();
    iterate = 0;
    JDBCconn jdbcIns = new JDBCconn();
    jdbcIns.connect();
    while(scn.hasNext()){
        JSONObject obj2= (JSONObject) new JSONParser().parse(scn.nextLine());
        //json.add(obj2);
    

    
    //  static String FileName="C:\\Users\\prath\\Downloads\\PRATHAMESH_HW2\\PRATHAMESH_HW2\\YelpDataset\\YelpDataset-CptS451\\yelp_review.json";
  /*    
      public static synchronized List<reviewobject> ReadJSON(String Encoding) throws FileNotFoundException, ParseException
      {
    Scanner scn=new Scanner(new File(FileName),Encoding);
    ArrayList<JSONObject> json=new ArrayList<>();
//Reading and Parsing Strings to Json
    while(scn.hasNext()){
        JSONObject obj= (JSONObject) new JSONParser().parse(scn.nextLine());
        json.add(obj);
    }*/
//Here Printing Json Objects
    
   // for(JSONObject obj : json){
    
        reviewobject temp = new reviewobject();
       
        //JSONObject jobj2 = null;
      
        if(obj2.containsKey("votes")){
            int useful;
            JSONObject votes = (JSONObject) obj2.get("votes");
            if(votes.containsKey("useful")){
                useful = Integer.parseInt(votes.get("useful").toString());
            } else
                useful = 0;
            temp.setUsefulVotes(useful);       
            //System.out.println("Useful votes:"+useful); 
        }
        if(obj2.containsKey("business_id")) {
             String bid = (String) obj2.get("business_id");
             temp.setbusiness_id(bid); 
             //String bid = jo.toJSONString();
             //System.out.println("Businees ID:"+bid);
        }
            
        if(obj2.containsKey("user_id"))
        {
            String uid = obj2.get("user_id").toString();
            temp.setUser_id(uid); 
            //System.out.println("UserID:"+obj.get("user_id"));
        }
                if(obj2.containsKey("review_id"))
        {
            String rid = obj2.get("review_id").toString();
            temp.setReview_id(rid); 
            System.out.println("ReviewID:"+obj2.get("review_id"));
        }
                if(obj2.containsKey("date"))
        {
            String date = obj2.get("date").toString();
            //if(status.equals("true"))
                temp.setDate(date);
            //else if(status.equals("false"))
              //  temp.setOpen(false); 
            //System.out.println("Date:"+date);//obj.get("date")
        }    
                if(obj2.containsKey("text"))
        {
            String txt = obj2.get("text").toString();
            temp.setText(txt);
            //System.out.println("Text:"+obj.get("text"));
        }
        if(obj2.containsKey("type")){
            String rType = obj2.get("type").toString();
            temp.setType(rType);
            //System.out.println("Type:"+obj.get("rtype"));
            
        }
                if(obj2.containsKey("stars"))
        {
          float star = Float.parseFloat(obj2.get("stars").toString());
            temp.setStars(star);
            //System.out.println("Stars:"+star);//obj.get("stars")
        }
        
        /*iterate++;
        if(iterate % 100 == 0){
            jdbcIns.close();
            jdbcIns.connect();
        }*/
        try{
                String bid = temp.getbusiness_id();
                String uid = temp.getUser_id();
                String rid = temp.getReview_id();
                String date = temp.getDate();
                String txt = temp.getText();
                String type = temp.getType();
                float stars = temp.getStars();
                
                PreparedStatement psInsRObj = jdbcIns.conn.prepareStatement("Insert into ReviewObject1 values(?,?,?,?,?,?,?,?)");
                psInsRObj.setString(1,rid);
                psInsRObj.setString(2,uid);
                psInsRObj.setFloat(3,stars);
                psInsRObj.setString(4,date);
                psInsRObj.setString(5,txt);
                psInsRObj.setString(6,type);
                psInsRObj.setString(7,bid);
                psInsRObj.setInt(8, temp.getUsefulVotes()); 
                
                jdbcIns.insert(psInsRObj);
                
                psInsRObj.getConnection().commit();
                psInsRObj.close();
                RObjList.add(temp);
        }catch(Exception e){ 
            System.out.println("error:" +e);
            e.printStackTrace();
        }
        
    }
    jdbcIns.close();
    return RObjList;
    }
}
    /*public static List<reviewobject> ReadJSON(String utF8) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}*/
