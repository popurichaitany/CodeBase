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
import Model.userobject;
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
 * @author anand
 */
public class userobjparser {
    
static String FileName="C:\\Users\\prath\\Downloads\\PRATHAMESH_HW2\\PRATHAMESH_HW2\\YelpDataset\\YelpDataset-CptS451\\yelp_user.json";
/*public static void main(String x[]){
    
    try {
        List<userobject> jsons=ReadJSON(new File(FileName),"UTF-8");
    } catch (FileNotFoundException | ParseException e) {
    }
}*/
    public static synchronized List<userobject> ReadJSON(String Encoding) throws FileNotFoundException, ParseException {
    Scanner scn=new Scanner(new File(FileName),Encoding);
    ArrayList<JSONObject> json=new ArrayList<>();
    int iterate = 0;
    List<userobject> UObjList = new ArrayList<>();
    JDBCconn jdbcIns = new JDBCconn();
    jdbcIns.connect();
    
//Reading and Parsing Strings to Json
    while(scn.hasNext()){
        JSONObject obj= (JSONObject) new JSONParser().parse(scn.nextLine());
        json.add(obj);
    
        userobject temp = new userobject();
       
      //  JSONObject jobj = null;
        
        if(obj.containsKey("user_id")) {
             String uid = (String) obj.get("user_id");
             temp.setUserid(uid); 
             //String bid = jo.toJSONString();
             //System.out.println("UserID:"+uid);
        }
            
        if(obj.containsKey("yelping_since"))
        {
            String ys = obj.get("yelping_since").toString();
            temp.setYelping_since(ys); 
            //System.out.println("Yelping Since:"+obj.get("yelping_since"));
        }
                if(obj.containsKey("name"))
        {
            String nm = obj.get("name").toString();
            temp.setName(nm); 
            //System.out.println("Name:"+obj.get("name"));
        }
                if(obj.containsKey("type"))
        {
            String type = obj.get("type").toString();
            //if(status.equals("true"))
                temp.setType(type);
            //else if(status.equals("false"))
              //  temp.setOpen(false); 
            //System.out.println("Type:"+obj.get("type"));
        }    
                if(obj.containsKey("avg_stars"))
        {
            float as = Float.parseFloat(obj.get("avg_stars").toString());
            temp.setAvg_stars(as);
            //System.out.println("Avg_stars:"+obj.get("avg_stars"));
        }
                if(obj.containsKey("fans"))
        {
          int fan = Integer.parseInt(obj.get("fans").toString());
            temp.setFans(fan);
            //System.out.println("Fans:"+obj.get("fans"));
        }
            if(obj.containsKey("review_count"))
        {
          int rc = Integer.parseInt(obj.get("review_count").toString());
            temp.setReview_count(rc);
            //System.out.println("Reviewcount:"+obj.get("review_count"));
        }
            /*iterate++;
            if(iterate % 100 == 0){
                jdbcIns.close();
                jdbcIns.connect();
            }*/
            try{
                String uid = temp.getUserid();
                String yelp_since = temp.getYelping_since();
                int rccount = temp.getReview_count();
                String name = temp.getName();
                float fan = temp.getFans();
                float avgstar = temp.getAvg_stars();
                String type = temp.getType();
                             
                PreparedStatement psSelCObj = jdbcIns.conn.prepareStatement("Insert into UserObject1 values(?,?,?,?,?,?,?)");
                
                psSelCObj.setString(1,uid);
                psSelCObj.setString(2,yelp_since);
                psSelCObj.setInt(3,rccount);
                psSelCObj.setString(4,name);
                psSelCObj.setFloat(5,fan);
                psSelCObj.setFloat(6,avgstar);
                psSelCObj.setString(7,type);
                           
                jdbcIns.insert(psSelCObj);
                psSelCObj.getConnection().commit();
                psSelCObj.close();
                UObjList.add(temp);
            }catch(Exception e){}
        
    }
    jdbcIns.close();
    return UObjList;
}

//    public static List<reviewobject> ReadJSON(String utF8) {
  //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    


    
