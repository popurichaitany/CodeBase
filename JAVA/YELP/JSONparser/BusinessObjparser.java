
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package JSONparser;

import DbManip.JDBCconn;
import Model.BusinessCategory;
import Model.BusinessObject;
import org.json.simple.JSONObject; 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
//import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;

/**
 *
 * @author prath
 */

public class BusinessObjparser {
    
    static String FileName="C:\\Users\\prath\\Downloads\\PRATHAMESH_HW2\\PRATHAMESH_HW2\\YelpDataset\\YelpDataset-CptS451\\yelp_business.json";
    static Map<String,Set<String>> categoMap;
    static JDBCconn jdbc = new JDBCconn();
    
    
public static synchronized List<BusinessObject> ReadJSON(String Encoding) throws FileNotFoundException, ParseException {
    Scanner scn=new Scanner(new File(FileName),Encoding);
    ArrayList<JSONObject> json=new ArrayList<>();
    jdbc.connect();
    int iterate=0;
    List<BusinessObject> bObjList = new ArrayList<>();
    List<BusinessCategory> bCategoList = new ArrayList<>();
    ArrayList<String> attrList = new ArrayList<>();
    Set<String> subCategoList = new HashSet<>();
    //String [][]subCategoList = new String[28][28];
    ArrayList<String> categoList = new ArrayList<>();
    categoList.add("Active Life");
    categoList.add("Arts & Entertainment");
    categoList.add("Automotive"); 
    categoList.add("Car Rental");
    categoList.add("Cafes");
    categoList.add("Beauty & Spas");
    categoList.add("Convenience Stores");
    categoList.add("Dentists");
    categoList.add("Doctors");
    categoList.add("Drugstores");
    categoList.add("Department Stores");
    categoList.add("Education");
    categoList.add("Event Planning & Services");
    categoList.add("Flowers & Gifts");
    categoList.add("Food");
    categoList.add("Health & Medical");
    categoList.add("Home Services");
    categoList.add("Home & Garden");
    categoList.add("Hospitals");
    categoList.add("Hotels & Travel");
    categoList.add("Hardware Stores");
    categoList.add("Grocery");
    categoList.add("Medical Centers");
    categoList.add("Nurseries & Gardening");
    categoList.add("Nightlife");
    categoList.add("Restaurants");
    categoList.add("Shopping");
    categoList.add("Transportation");
    
    attrList.add("Goodfor_Desert");
    attrList.add("Goodfor_latenight");
    attrList.add("Goodfor_lunch");
    attrList.add("Goodfor_dinner");
    attrList.add("Goodfor_breakfast");
    attrList.add("Goodfor_brunch");
    attrList.add("Goodfor_caters");
    attrList.add("park_garage");
    attrList.add("park_street");
    attrList.add("park_validated");
    attrList.add("park_lot");
    attrList.add("park_valet");
    attrList.add("diet_dairyfree");
    attrList.add("diet_glutenfree");
    attrList.add("diet_vegan");
    attrList.add("diet_soyafree");
    attrList.add("diet_vegeterian");
    attrList.add("ambience_romantic");
    attrList.add("ambience_intimate");
    attrList.add("ambience_touristy");
    attrList.add("ambience_hipster");
    attrList.add("ambience_divey");
    attrList.add("ambience_classy");
    attrList.add("ambience_trendy");
    attrList.add("ambience_upscale");
    attrList.add("ambience_casual");
    attrList.add("music_DJ");
    attrList.add("music_background");
    attrList.add("music_jukebox");
    attrList.add("music_live");
    attrList.add("music_video");
    attrList.add("music_karaoke");
    attrList.add("By appointment only_false");
    attrList.add("By appointment only");
    attrList.add("Take out");
    attrList.add("Take out_false");
    attrList.add("order at counter");
    attrList.add("order at counter_false");
    attrList.add("caters");
    attrList.add("caters_false");
    attrList.add("noiselevel_average");
    attrList.add("noiselevel_quiet");
    attrList.add("noiselevel_loud");
    attrList.add("noiselevel_veryloud");
    attrList.add("Takes reservations");
    attrList.add("Takes reservations_false");
    attrList.add("Delivery");
    attrList.add("Delivery_false");
    attrList.add("HasTV");
    attrList.add("HasTV_false");
    attrList.add("OutdoorSeating");
    attrList.add("OutdoorSeating_false");
    attrList.add("Attire_casual");
    attrList.add("Alcohol_none");
    attrList.add("Alcohol_fullbar");
    attrList.add("Alcohol_Beer&Wine");
    attrList.add("WaiterService");
    attrList.add("WaiterService_false");
    attrList.add("Accepts Credit Cards");
    attrList.add("Accepts Credit Cards_false");
    attrList.add("Good for kids");
    attrList.add("Good for kids_false");
    attrList.add("Good for groups");
    attrList.add("Good for groups_false");
    attrList.add("price range_1");
    attrList.add("price range_2");
    attrList.add("price range_3");
    attrList.add("price range_4");
    attrList.add("Dogs allowed");
    attrList.add("Dogs allowed_false");
    attrList.add("Wifi_free");
    attrList.add("Wifi_none");
    attrList.add("Wheelchair accessible");
    attrList.add("Wheelchair accessible_false");
    attrList.add("Good for dancing");
    attrList.add("Good for dancing_false");
    attrList.add("Smoking_outdoor");
    attrList.add("Coat check");
    attrList.add("Coat check_false");
    attrList.add("Happy Hour");
    attrList.add("Happy Hour_false");
    attrList.add("BYOB");
    attrList.add("BYOB_false");
    attrList.add("Corkage");
    attrList.add("Corkage_false");
    attrList.add("BYOB/Corkage_no");
    attrList.add("BYOB/Corkage_yes_free");
    attrList.add("BYOB/Corkage_yes_corkage");
    attrList.add("payment_mastercard");
    attrList.add("payment_visa");
    attrList.add("payment_amex");
    attrList.add("payment_discover");
    attrList.add("payment_cashonly");
    attrList.add("Open 24hrs");
    attrList.add("Open 24hrs_false");
    
    
    categoMap = new HashMap();
    categoMap.put("Active Life", new HashSet<String>());
    categoMap.put("Arts & Entertainment", new HashSet<String>());
    categoMap.put("Automotive", new HashSet<String>());
    categoMap.put("Car Rental", new HashSet<String>());
    categoMap.put("Cafes", new HashSet<String>());
    categoMap.put("Beauty & Spas", new HashSet<String>());
    categoMap.put("Convenience Stores", new HashSet<String>());
    categoMap.put("Dentists", new HashSet<String>());
    categoMap.put("Drugstores", new HashSet<String>());
    categoMap.put("Department Stores", new HashSet<String>());
    categoMap.put("Education", new HashSet<String>());
    categoMap.put("Event Planning & Services", new HashSet<String>());
    categoMap.put("Flowers & Gifts", new HashSet<String>());
    categoMap.put("Doctors", new HashSet<String>());
    categoMap.put("Food", new HashSet<String>());
    categoMap.put("Health & Medical", new HashSet<String>());
    categoMap.put("Home Services", new HashSet<String>());
    categoMap.put("Home & Garden", new HashSet<String>());
    categoMap.put("Hospitals", new HashSet<String>());
    categoMap.put("Hotels & Travel", new HashSet<String>());
    categoMap.put("Hardware Stores", new HashSet<String>());
    categoMap.put("Grocery", new HashSet<String>());
    categoMap.put("Medical Centers", new HashSet<String>());
     categoMap.put("Nurseries & Gardening", new HashSet<String>());
      categoMap.put("Nightlife", new HashSet<String>());
       categoMap.put("Restaurants", new HashSet<String>());
        categoMap.put("Shopping", new HashSet<String>());
    categoMap.put("Transportation", new HashSet<String>());
    
    
    
    
//Reading and Parsing Strings to Json
    while(scn.hasNext()){
        JSONObject obj= (JSONObject) new JSONParser().parse(scn.nextLine());
        json.add(obj);
    
         
        BusinessObject temp = new BusinessObject();
       
             if(obj.containsKey("business_id")) {
             String bid = (String) obj.get("business_id");
             temp.setBusinessID(bid); 
             System.out.println("Businees ID:"+bid);
             getHours(obj,bid);
             getCategory(obj,bid);
             getAttribute(obj, bid);
        }
            
        if(obj.containsKey("full_address"))
        {
            String Address = obj.get("full_address").toString();
            temp.setAddress(Address); 
                        }
                if(obj.containsKey("city"))
        {
            String city = obj.get("city").toString();
            temp.setCity(city); 
                   }
                
                if(obj.containsKey("attributes")){
                    
                }

                if(obj.containsKey("open"))
        {
            String status = obj.get("open").toString();
            if(status.equals("true"))
                temp.setOpen(true);
            else if(status.equals("false"))
                temp.setOpen(false); 
                    }    
                if(obj.containsKey("review_count"))
        {
            int rCnt = Integer.parseInt(obj.get("review_count").toString());
            temp.setReviewCount(rCnt);
                    }
                if(obj.containsKey("name"))
        {
            String name = obj.get("name").toString();
            temp.setBusinessName(name); 
            }
        if(obj.containsKey("longitude"))
        {
            float longitude = Float.parseFloat(obj.get("longitude").toString());
            temp.setLongitude(longitude); 
                    }
        if(obj.containsKey("latitude"))
        {
            float latitude = Float.parseFloat(obj.get("latitude").toString());
            temp.setLatitiude(latitude);
              }
        if(obj.containsKey("state"))
        {
            String state = obj.get("state").toString();
            temp.setState(state); 
                    }
        if(obj.containsKey("stars"))
        {
            float stars = Float.parseFloat(obj.get("stars").toString());
            temp.setStars(stars); 
                    }
        if(obj.containsKey("type"))
        { 
            String type = obj.get("type").toString();
            temp.setBusinessType(type); 
        }
        
       try{
                String bid = temp.getBusinessID();
                String addr = temp.getAddress();
                String city = temp.getCity();
                String state = temp.getState();
                String bName = temp.getBusinessName();
                String bType = temp.getBusinessType();
                String open = "";
                        boolean status = temp.isOpen();
                        if(status)
                            open = "true";
                        else
                            open = "false";
                float latitude = temp.getLatitiude();
                float longitude = temp.getLongitude();
                float stars = temp.getStars();
                int revCount = temp.getReviewCount();
                
                PreparedStatement psInsBObj = jdbc.conn.prepareStatement("Insert into BusinessObject1 values(?,?,?,?,?,?,?,?,?,?,?)");
                psInsBObj.setString(1,bid);
                psInsBObj.setString(2,addr);
                psInsBObj.setString(3,open);
                psInsBObj.setString(4,city);
                psInsBObj.setString(5,state);
                psInsBObj.setFloat(6,latitude);
                psInsBObj.setFloat(7,longitude);
                psInsBObj.setInt(8,revCount);
                psInsBObj.setString(9,bName);
                psInsBObj.setFloat(10,stars);
                psInsBObj.setString(11,bType);
                
                jdbc.insert(psInsBObj);                
               
                psInsBObj.getConnection().commit();
                psInsBObj.close();
                bObjList.add(temp);
       } catch(Exception e){}
       
    }
    
 for(String key: categoMap.keySet()){
                   System.out.println("-------------"+key+"----------------");
                   for(String value: categoMap.get(key)){
                       System.out.println(""+value);
                   }
               }
     try{
        jdbc.close();
     } catch(Exception e){}
    return bObjList;
}

private static synchronized void getHours(JSONObject j,String businessID){
    String close,open;
    if(j.containsKey("hours")){
        JSONObject joHours = (JSONObject) j.get("hours");
        if(joHours.containsKey("Monday")){
            JSONObject joMonday = (JSONObject) joHours.get("Monday");
            if(joMonday.containsKey("close")){
               close = joMonday.get("close").toString();
               open = joMonday.get("open").toString();
                                             
                try{
                    PreparedStatement psInsBHoursObj = jdbc.conn.prepareStatement("Insert into BusinessHrs values(?,?,?,?)");
                    psInsBHoursObj.setString(1,businessID);
                    psInsBHoursObj.setString(2,"Monday");
                    psInsBHoursObj.setString(3,open);
                    psInsBHoursObj.setString(4,close);
                    jdbc.insert(psInsBHoursObj);
                    psInsBHoursObj.close();
                }catch(SQLException se){}
            }
        }
        if(joHours.containsKey("Tuesday")){
            JSONObject joMonday = (JSONObject) joHours.get("Tuesday");
            if(joMonday.containsKey("close")){
               close = joMonday.get("close").toString();
               open = joMonday.get("open").toString();
               
                
                try{
                    PreparedStatement psInsBHoursObj = jdbc.conn.prepareStatement("Insert into BusinessHrs values(?,?,?,?)");
                    psInsBHoursObj.setString(1,businessID);
                    psInsBHoursObj.setString(2,"Tuesday");
                    psInsBHoursObj.setString(3,open);
                    psInsBHoursObj.setString(4,close);
                    jdbc.insert(psInsBHoursObj);
                    psInsBHoursObj.close();
                }catch(SQLException se){}
            }
        }
        if(joHours.containsKey("Wednesday")){
            JSONObject joMonday = (JSONObject) joHours.get("Wednesday");
            if(joMonday.containsKey("close")){
               close = joMonday.get("close").toString();
               open = joMonday.get("open").toString();
               
                    try{
                    PreparedStatement psInsBHoursObj = jdbc.conn.prepareStatement("Insert into BusinessHrs values(?,?,?,?)");
                    psInsBHoursObj.setString(1,businessID);
                    psInsBHoursObj.setString(2,"Wednesday");
                    psInsBHoursObj.setString(3,open);
                    psInsBHoursObj.setString(4,close);
                    jdbc.insert(psInsBHoursObj);
                    psInsBHoursObj.close();
                
                }catch(SQLException se){}
            }
        }
        if(joHours.containsKey("Thursday")){
            JSONObject joMonday = (JSONObject) joHours.get("Thursday");
            if(joMonday.containsKey("close")){
               close = joMonday.get("close").toString();
               open = joMonday.get("open").toString();
               
                
                try{
                    PreparedStatement psInsBHoursObj = jdbc.conn.prepareStatement("Insert into BusinessHrs values(?,?,?,?)");
                    psInsBHoursObj.setString(1,businessID);
                    psInsBHoursObj.setString(2,"Thursday");
                    psInsBHoursObj.setString(3,open);
                    psInsBHoursObj.setString(4,close);
                    jdbc.insert(psInsBHoursObj);
                    psInsBHoursObj.close();
                
                }catch(SQLException se){}
            }
        }
        if(joHours.containsKey("Friday")){
            JSONObject joMonday = (JSONObject) joHours.get("Friday");
            if(joMonday.containsKey("close")){
               close = joMonday.get("close").toString();
               open = joMonday.get("open").toString();
               
                
                try{
                    PreparedStatement psInsBHoursObj = jdbc.conn.prepareStatement("Insert into BusinessHrs values(?,?,?,?)");
                    psInsBHoursObj.setString(1,businessID);
                    psInsBHoursObj.setString(2,"Friday");
                    psInsBHoursObj.setString(3,open);
                    psInsBHoursObj.setString(4,close);
                    jdbc.insert(psInsBHoursObj);
                    psInsBHoursObj.close();
                
                }catch(SQLException se){}
            }
        }
        if(joHours.containsKey("Saturday")){
            JSONObject joMonday = (JSONObject) joHours.get("Saturday");
            if(joMonday.containsKey("close")){
               close = joMonday.get("close").toString();
               open = joMonday.get("open").toString();
               
                
                try{
                    PreparedStatement psInsBHoursObj = jdbc.conn.prepareStatement("Insert into BusinessHrs values(?,?,?,?)");
                    psInsBHoursObj.setString(1,businessID);
                    psInsBHoursObj.setString(2,"Saturday");
                    psInsBHoursObj.setString(3,open);
                    psInsBHoursObj.setString(4,close);
                    jdbc.insert(psInsBHoursObj);
                    psInsBHoursObj.close();
                
                }catch(SQLException se){}
            }
        }
        if(joHours.containsKey("Sunday")){
            JSONObject joMonday = (JSONObject) joHours.get("Sunday");
            if(joMonday.containsKey("close")){
               close = joMonday.get("close").toString();
               open = joMonday.get("open").toString();

                try{
                    PreparedStatement psInsBHoursObj = jdbc.conn.prepareStatement("Insert into BusinessHrs values(?,?,?,?)");
                    psInsBHoursObj.setString(1,businessID);
                    psInsBHoursObj.setString(2,"Sunday");
                    psInsBHoursObj.setString(3,open);
                    psInsBHoursObj.setString(4,close);
                    jdbc.insert(psInsBHoursObj);
                    psInsBHoursObj.close();
                }catch(SQLException se){}
            }
        }
        
        
        
    }
}

private synchronized static void getCategory(JSONObject j,String bid){
              if(j.containsKey("categories")){
                    JSONArray categoArray = (JSONArray) j.get("categories");
                    ArrayList<String> tempCategoryList = new ArrayList<String>();
                    ArrayList<String> tempSubCategoryList = new ArrayList<String>();
                    for(int i=0;i<categoArray.size();i++){
                        String arrCategory = categoArray.get(i).toString();
                        if (categoMap.containsKey(arrCategory)){
                            tempCategoryList.add(arrCategory);
               
                            try{
                                    PreparedStatement psInsBCategoryObj = jdbc.conn.prepareStatement("Insert into BCategory values(?,?)");
                                    psInsBCategoryObj.setString(1,bid);
                                    psInsBCategoryObj.setString(2,arrCategory);
                                    jdbc.insert(psInsBCategoryObj);
               psInsBCategoryObj.close();
                            }catch(SQLException se){}
                        }else{
                            
                            tempSubCategoryList.add(arrCategory);
                            
                            try{
                            PreparedStatement psInsBSubCategoryObj = jdbc.conn.prepareStatement("Insert into BSubCategory values(?,?)");
                                    psInsBSubCategoryObj.setString(1,bid);
                                    psInsBSubCategoryObj.setString(2,arrCategory);
                                    jdbc.insert(psInsBSubCategoryObj);
               psInsBSubCategoryObj.close();
                            }catch(SQLException se){}
                                    
                        }
                    } 
                    for(String key: tempCategoryList)
                                categoMap.get(key).addAll(tempSubCategoryList);
               }
        }
 private static void getAttribute(JSONObject joAttr,String bid){
                    String newAttr="";
                    JSONObject attr = (JSONObject) joAttr.get("attributes");
                    Set<String> keys = attr.keySet();
                    Iterator<String> itr =keys.iterator();
                    while(itr.hasNext())
                    {
                      newAttr = itr.next();
                        
                        String subAttr="",result="",r="";
                        if(newAttr != null){
                            if(newAttr.equals("Dietary Restrictions") || newAttr.equals("Ambience")  || newAttr.equals("Parking")  ||  newAttr.equals("Music") || newAttr.equals("Payment Types")|| newAttr.equals("Good For")){
                            switch(newAttr){
                                case "Dietary Restrictions":
                                     JSONObject dietRestriction = (JSONObject) attr.get("Dietary Restriction");
                                     if(dietRestriction != null){
                                         if(dietRestriction.size()>0){
                                     Set<String> restrictions = dietRestriction.keySet();
                                     Iterator<String> itr2 =restrictions.iterator();
                                     while(itr2.hasNext()){
                                         r = itr2.next();
                                         switch(r){
                                           case "dairy-free":
                                            subAttr = dietRestriction.get("dairy-free").toString();
                                            if(subAttr.equals("true")){
                                                result="dairy-free";
                                            } else if(subAttr.equals("false")){
                                                result="dairy-free_false";
                                            }
                                            break;
                                         case "gluten-free":
                                            subAttr = dietRestriction.get("gluten-free").toString();
                                            if(subAttr.equals("true"))
                                            {
                                                result="gluten-free";
                                            } else if(subAttr.equals("false"))
                                            {
                                                result="gluten-free_false";
                                            }
                                            break;
                                         case "soy-free":
                                             subAttr = dietRestriction.get("soy_free").toString();
                                            if(subAttr.equals("true"))
                                            {
                                                result="soy-free";
                                            } else if(subAttr.equals("false"))
                                            {
                                                result="soy-free_false";
                                            }
                                            break; 
                                         case "vegetarian":
                                             subAttr = dietRestriction.get("vegetarian").toString();
                                            if(subAttr.equals("true"))
                                            {
                                                result="vegetarian";
                                            } else if(subAttr.equals("false"))
                                            {
                                                result="vegetarian_false";
                                            }
                                            break; 
                                         case "vegan":
                                             subAttr = dietRestriction.get("vegan").toString();
                                            if(subAttr.equals("true"))
                                            {
                                                result="vegan";
                                            } else if(subAttr.equals("false"))
                                            {
                                                result="vegan_false";
                                            }
                                            break; 
                                         case "kosher":
                                             subAttr = dietRestriction.get("kosher").toString();
                                            if(subAttr.equals("true"))
                                            {
                                                result="kosher";
                                            } else if(subAttr.equals("false"))
                                            {
                                                result="kosher_false";
                                            }
                                            break; 
                                         case "halal":
                                             subAttr = dietRestriction.get("halal").toString();
                                            if(subAttr.equals("true"))
                                            {
                                                result="halal";
                                            } else if(subAttr.equals("false"))
                                            {
                                                result="halal_false";
                                            }
                                            break;     
                                         }
                                         try{
                                                PreparedStatement psInsBAttributeObj = jdbc.conn.prepareStatement("Insert into BAttribute values(?,?,?,?)");
                                                psInsBAttributeObj.setString(1,bid);
                                                psInsBAttributeObj.setString(2,"Dietary Restriction");
                                                psInsBAttributeObj.setString(3,r);
                                                psInsBAttributeObj.setString(4,result);
                                                jdbc.insert(psInsBAttributeObj);
                                                psInsBAttributeObj.close();
                                        } catch(SQLException se){
                                        }
                                     }
                                     } 
                                }
                                    break;
                                case "Ambience": 
                                    JSONObject ambience = (JSONObject) attr.get("Ambience");
                                    if(ambience != null){
                                    if(ambience.size()>0){
                                     Set<String> amb = ambience.keySet();
                                     Iterator<String> itr3 =amb.iterator();
                                     while(itr3.hasNext()){
                                         r = itr3.next();
                                         switch(r){
                                         case "romantic":
                                            subAttr = ambience.get("romantic").toString();
                                            if(subAttr.equals("true")){
                                                result="romantic";
                                            } else if(subAttr.equals("false")){
                                                result="romantic_false";
                                            }
                                            break;    
                                           case "intimate":
                                            subAttr = ambience.get("intimate").toString();
                                            if(subAttr.equals("true")){
                                                result="intimate";
                                            } else if(subAttr.equals("false")){
                                                result="intimate_false";
                                            }
                                            break;  
                                            case "touristy":
                                            subAttr = ambience.get("touristy").toString();
                                            if(subAttr.equals("true")){
                                                result="touristy";
                                            } else if(subAttr.equals("false")){
                                                result="touristy_false";
                                            }
                                            break;
                                            case "hipster":
                                            subAttr = ambience.get("hipster").toString();
                                            if(subAttr.equals("true")){
                                                result="hipster";
                                            } else if(subAttr.equals("false")){
                                                result="hipster_false";
                                            }
                                            break;
                                            case "divey":
                                            subAttr = ambience.get("divey").toString();
                                            if(subAttr.equals("true")){
                                                result="divey";
                                            } else if(subAttr.equals("false")){
                                                result="divey_false";
                                            }
                                            break;
                                            case "classy":
                                            subAttr = ambience.get("classy").toString();
                                            if(subAttr.equals("true")){
                                                result="classy";
                                            } else if(subAttr.equals("false")){
                                                result="classy_false";
                                            }
                                            break;
                                            case "trendy":
                                            subAttr = ambience.get("trendy").toString();
                                            if(subAttr.equals("true")){
                                                result="trendy";
                                            } else if(subAttr.equals("false")){
                                                result="trendy_false";
                                            }
                                            break;
                                            
                                            case "upscale":
                                            subAttr = ambience.get("upscale").toString();
                                            if(subAttr.equals("true")){
                                                result="upscale";
                                            } else if(subAttr.equals("false")){
                                                result="upscale_false";
                                            }
                                            break;
                                            case "casual":
                                            subAttr = ambience.get("casual").toString();
                                            if(subAttr.equals("true")){
                                                result="casual";
                                            } else if(subAttr.equals("false")){
                                                result="casual_false";
                                            }
                                            break;         
                                         }
                                        try{
                                                PreparedStatement psInsBAttributeObj = jdbc.conn.prepareStatement("Insert into BAttribute values(?,?,?,?)");
                                                psInsBAttributeObj.setString(1,bid);
                                                psInsBAttributeObj.setString(2,"Ambience");
                                                psInsBAttributeObj.setString(3,r);
                                                psInsBAttributeObj.setString(4,result);
                                                jdbc.insert(psInsBAttributeObj);
                                                psInsBAttributeObj.close();
                                        } catch(SQLException se){
                                        }
                                   
                                     }
                                     }
                                    }
                                    break;
                                case "Parking":                                                 
                                     JSONObject parking = (JSONObject) attr.get("Parking");
                                     if(parking != null){
                                     if(parking.size()>0){
                                     Set<String> park = parking.keySet();
                                     Iterator<String> itr4 = park.iterator();
                                     while(itr4.hasNext()){
                                         r = itr4.next();
                                         switch(r){
                                             
                                         case "garage":
                                            subAttr = parking.get("garage").toString();
                                            if(subAttr.equals("true")){
                                                result="garage";
                                            } else if(subAttr.equals("false")){
                                                result="garage_false";
                                            }
                                            break;
                                         case "street":
                                            subAttr = parking.get("street").toString();
                                            if(subAttr.equals("true")){
                                                result="street";
                                            } else if(subAttr.equals("false")){
                                                result="street_false";
                                            }
                                            break;                                         
                                         case "validated":
                                            subAttr = parking.get("validated").toString();
                                            if(subAttr.equals("true")){
                                                result="validated";
                                            } else if(subAttr.equals("false")){
                                                result="validated_false";
                                            }
                                            break;  
                                         case "lot":
                                            subAttr = parking.get("lot").toString();
                                            if(subAttr.equals("true")){
                                                result="lot";
                                            } else if(subAttr.equals("false")){
                                                result="lot_false";
                                            }
                                            break;  
                                         case "valet":
                                            subAttr = parking.get("valet").toString();
                                            if(subAttr.equals("true")){
                                                result="valet";
                                            } else if(subAttr.equals("false")){
                                                result="valet_false";
                                            }
                                            break;
                                         }
                                         try
                                         {
                                                PreparedStatement psInsBAttributeObj = jdbc.conn.prepareStatement("Insert into BAttribute values(?,?,?,?)");
                                                psInsBAttributeObj.setString(1,bid);
                                                psInsBAttributeObj.setString(2,"Parking");
                                                psInsBAttributeObj.setString(3,r);
                                                psInsBAttributeObj.setString(4,result);
                                                jdbc.insert(psInsBAttributeObj);
                                                psInsBAttributeObj.close();
                                        } catch(SQLException se){
                                        }
                                    
                                     }
                                     }
                                     }
                                    break; 
                                    case "Music":
                                     JSONObject music = (JSONObject) attr.get("Music");
                                     if(music != null){
                                     if(music.size()>0){
                                     Set<String> mus = music.keySet();
                                     
                                     Iterator<String> itr5 = mus.iterator();
                                     while(itr5.hasNext()){
                                         r = itr5.next();
                                         switch(r){
                                            case "dj":
                                            subAttr = music.get("dj").toString();
                                            if(subAttr.equals("true")){
                                                result="dj";
                                            } else if(subAttr.equals("false")){
                                                result="dj_false";
                                            }
                                            break;
                                            case "background_music":
                                            subAttr = music.get("background_music").toString();
                                            if(subAttr.equals("true")){
                                                result="background_music";
                                            } else if(subAttr.equals("false")){
                                                result="backgound_music_false";
                                            }
                                            break;
                                        case "jukebox":
                                            subAttr = music.get("jukebox").toString();
                                            if(subAttr.equals("true")){
                                                result="jukebox";
                                            } else if(subAttr.equals("false")){
                                                result="jukebox_false";
                                            }
                                            break;
                                            case "live":
                                            subAttr = music.get("live").toString();
                                            if(subAttr.equals("true")){
                                                result="live";
                                            } else if(subAttr.equals("false")){
                                                result="live_false";
                                            }
                                            break;
                                            case "video":
                                            subAttr = music.get("video").toString();
                                            if(subAttr.equals("true")){
                                                result="video";
                                            } else if(subAttr.equals("false")){
                                                result="video_false";
                                            }
                                            break;
                                            case "karaoke":
                                            subAttr = music.get("karaoke").toString();
                                            if(subAttr.equals("true")){
                                                result="karaoke";
                                            } else if(subAttr.equals("false")){
                                                result="karaoke_false";
                                            }
                                            break;
                                         }
                                         try
                                         {
                                                PreparedStatement psInsBAttributeObj = jdbc.conn.prepareStatement("Insert into BAttribute values(?,?,?,?)");
                                                psInsBAttributeObj.setString(1,bid);
                                                psInsBAttributeObj.setString(2,"Music");
                                                psInsBAttributeObj.setString(3,r);
                                                psInsBAttributeObj.setString(4,result);
                                                jdbc.insert(psInsBAttributeObj);
                                                psInsBAttributeObj.close();
                                        } catch(SQLException se){
                                        }
                                                                            
                                    }
                                }
                            }
                                     break;
                                case "Payment Types":
                                     JSONObject payType = (JSONObject) attr.get("Payment Types");
                                     if(payType != null){
                                     if(payType.size()>0){
                                     Set<String> payTypes = payType.keySet();
                                     Iterator<String> itrPay =payTypes.iterator();
                                     while(itrPay.hasNext()){
                                         r = itrPay.next();
                                         switch(r){
                                           case "amex":
                                            subAttr = payType.get("amex").toString();
                                            if(subAttr.equals("true")){
                                                result="amex";
                                            } else if(subAttr.equals("false")){
                                                result="amex_false";
                                            }
                                            break;
                                         case "discover":
                                            subAttr = payType.get("discover").toString();
                                            if(subAttr.equals("true")){
                                                result="discover";
                                            } else if(subAttr.equals("false")){
                                                result="discover_false";
                                            }
                                            break;
                                         case "visa":
                                            subAttr = payType.get("visa").toString();
                                            if(subAttr.equals("true")){
                                                result="visa";
                                            } else if(subAttr.equals("false")){
                                                result="visa_false";
                                            }
                                             break;
                                         case "mastercard":
                                             subAttr = payType.get("mastercard").toString();
                                            if(subAttr.equals("true")){
                                                result="mastercard";
                                            } else if(subAttr.equals("false")){
                                                result="mastercard_false";
                                            }
                                             break;
                                         case "cash_only":
                                            subAttr = payType.get("cash_only").toString();
                                            if(subAttr.equals("true")){
                                                result="cash_only";
                                            } else if(subAttr.equals("false")){
                                                result="cash_only_false";
                                            }
                                             break;
                                         }
                                          try
                                         {      PreparedStatement psInsBAttributeObj = jdbc.conn.prepareStatement("Insert into BAttribute values(?,?,?,?)");
                                                psInsBAttributeObj.setString(1,bid);
                                                psInsBAttributeObj.setString(2,"Payment Types");
                                                psInsBAttributeObj.setString(3,r);
                                                psInsBAttributeObj.setString(4,result);
                                                jdbc.insert(psInsBAttributeObj);
                                                psInsBAttributeObj.close();
                                        } catch(SQLException se){
                                        }
                                     }
                                     }
                                     }
                                    break;
                               case "Good For":
                                JSONObject good = (JSONObject) attr.get("Good For");
                                if(good != null){
                                if(good.size()>0){
                                     Set<String> gf = good.keySet();
                                     Iterator<String> itr6 = gf.iterator();
                                     while(itr6.hasNext()){
                                         r = itr6.next();
                                         switch(r){
                                            case "dessert":
                                            subAttr = good.get("dessert").toString();
                                            if(subAttr.equals("true")){
                                                result="dessert";
                                            } else if(subAttr.equals("false")){
                                                result="dessert_false";
                                            }
                                            break;    
                                            case "latenight":
                                            subAttr = good.get("latenight").toString();
                                            if(subAttr.equals("true")){
                                                result="latenight";
                                            } else if(subAttr.equals("false")){
                                                result="latenight_false";
                                            }
                                            break;
                                            case "lunch":
                                            subAttr = good.get("lunch").toString();
                                            if(subAttr.equals("true")){
                                                result="lunch";
                                            } else if(subAttr.equals("false")){
                                                result="lunch_false";
                                            }
                                            break;
                                            case "dinner":
                                            subAttr = good.get("dinner").toString();
                                            if(subAttr.equals("true")){
                                                result="dinner";
                                            } else if(subAttr.equals("false")){
                                                result="dinner_false";
                                            }
                                            break;
                                            case "breakfast":
                                            subAttr = good.get("breakfast").toString();
                                            if(subAttr.equals("true")){
                                                result="breakfast";
                                            } else if(subAttr.equals("false")){
                                                result="breakfast_false";
                                            }
                                            break;
                                            case "brunch":
                                            subAttr = good.get("brunch").toString();
                                            if(subAttr.equals("true")){
                                                result="brunch";
                                            } else if(subAttr.equals("false")){
                                                result="brunch_false";
                                            }
                                            break;
                                       }
                                        try{
                                                PreparedStatement psInsBMultAttributeObj = jdbc.conn.prepareStatement("Insert into BAttribute values(?,?,?,?)");
                                                psInsBMultAttributeObj.setString(1,bid);
                                                psInsBMultAttributeObj.setString(2,"Good For");
                                                psInsBMultAttributeObj.setString(3,r);
                                                psInsBMultAttributeObj.setString(4,result);
                                                jdbc.insert(psInsBMultAttributeObj);
                                                psInsBMultAttributeObj.close();
                                        } catch(SQLException se){}     
                             }
                            }
                           }
                           break;     
                        } 
                            
                        }  else if(newAttr.equals("Ages Allowed") || newAttr.equals("Noise Level") || newAttr.equals("Attire") || newAttr.equals("Alcohol") || newAttr.equals("Price Range") || newAttr.equals("Wi-Fi") || newAttr.equals("Smoking") || newAttr.equals("BYOB/Corkage")){
                            String nonBoolResult="";
                            switch(newAttr){
                                case "Noise Level":
                                    nonBoolResult = attr.get("Noise Level").toString();
                                    break;
                                case "Wi-Fi":
                                    nonBoolResult = attr.get("Wi-Fi").toString();
                                    break;
                                case "Attire":
                                    nonBoolResult = attr.get("Attire").toString();
                                    break;
                                case "Alcohol":
                                    nonBoolResult = attr.get("Alcohol").toString();
                                    break;
                                case "Price Range":
                                    nonBoolResult = attr.get("Price Range").toString();
                                    break;
                                case "Smoking":
                                    nonBoolResult = attr.get("Smoking").toString();
                                    break;
                                case "BYOB/Corkage":
                                    nonBoolResult = attr.get("BYOB/Corkage").toString();
                                    break;
                                case "Ages Allowed":
                                    nonBoolResult = attr.get("Ages Allowed").toString();
                                    break;
                                    
                            }
                            try{
                                PreparedStatement psInsBoolAttributeObj = jdbc.conn.prepareStatement("Insert into BAttribute values(?,?,?,?)");
                                psInsBoolAttributeObj.setString(1,bid);
                                psInsBoolAttributeObj.setString(2,newAttr);
                                psInsBoolAttributeObj.setString(3,"NA");
                                psInsBoolAttributeObj.setString(4,nonBoolResult);
                                jdbc.insert(psInsBoolAttributeObj);
                                psInsBoolAttributeObj.close();
                            }catch(SQLException se){}
                        }  else if(newAttr.equals("Good for Kids") || newAttr.equals("Good For Kids")){
                            String gfkBool="",gfk="";
                            if(newAttr.equals("Good For Kids")){
                                    gfkBool = attr.get("Good For Kids").toString();
                                    if(gfkBool.equals("true"))
                                        gfk="Good_For_Kids";
                                    else
                                        gfk="Good_For_Kids_false";
                            } 
                            if(newAttr.equals("Good for Kids")){
                                    gfkBool = attr.get("Good for Kids").toString();
                                    if(gfkBool.equals("true"))
                                        gfk="Good_For_Kids";
                                    else
                                        gfk="Good_For_Kids_false";
                            } 
                            try{
                                PreparedStatement psInsBoolAttributeObj1 = jdbc.conn.prepareStatement("Insert into BAttribute values(?,?,?,?)");
                                psInsBoolAttributeObj1.setString(1,bid);
                                psInsBoolAttributeObj1.setString(2,newAttr);
                                psInsBoolAttributeObj1.setString(3,"NA");
                                psInsBoolAttributeObj1.setString(4,gfk);
                                jdbc.insert(psInsBoolAttributeObj1);
                                psInsBoolAttributeObj1.close();
                            }catch(SQLException se){}
                        }  else{
                            String boolResult="",bool="";
                            switch(newAttr){
                                case "Dogs Allowed":
                                    bool = attr.get("Dogs Allowed").toString();
                                    if(bool.equals("true"))
                                        boolResult="Dogs_Allowed";
                                    else
                                        boolResult="Dogs_Allowed_false";
                                break;
                                                              
                                                                       
                                    case "By Appointment Only":
                                    bool = attr.get("By Appointment Only").toString();
                                    if(bool.equals("true"))
                                        boolResult="By_Appointment_Only";
                                    else
                                        boolResult="By_Appointment_Only_false";
                                break;
                                    case "Take-out":
                                    bool = attr.get("Take-out").toString();
                                    if(bool.equals("true"))
                                        boolResult="Take_Out";
                                    else
                                        boolResult="Take_Out_false";
                                break;
                                    case "Order at Counter":
                                    bool = attr.get("Order at Counter").toString();
                                    if(bool.equals("true"))
                                        boolResult="Order_At_Counter";
                                    else
                                        boolResult="Order_At_Counter_false";
                                break;
                                    case "Caters":
                                    bool = attr.get("Caters").toString();
                                    if(bool.equals("true"))
                                        boolResult="Caters";
                                    else
                                        boolResult="Caters_false";
                                    break;
                                    
                                    case "Drive-Thru":
                                    bool = attr.get("Drive-Thru").toString();
                                    if(bool.equals("true"))
                                        boolResult="Drive-Thru";
                                    else
                                        boolResult="Drive-Thru_false";
                                break;
                                
                                case "Open 24 Hours":
                                    bool = attr.get("Open 24 Hours").toString();
                                    if(bool.equals("true"))
                                        boolResult="Open_24_Hours";
                                    else
                                        boolResult="Open_24_Hours_false";
                                    break;
                                    case "Takes Reservations":
                                    bool = attr.get("Takes Reservations").toString();
                                    if(bool.equals("true"))
                                        boolResult="Takes_Reservsations";
                                    else
                                        boolResult="Takes_Reservations_false";
                                break;
                                    case "Delivery":
                                    bool = attr.get("Delivery").toString();
                                    if(bool.equals("true"))
                                        boolResult="Delivery";
                                    else
                                        boolResult="Delivery_false";
                                break;
                                    case "Has TV":
                                    bool = attr.get("Has TV").toString();
                                    if(bool.equals("true"))
                                        boolResult="Has_TV";
                                    else
                                        boolResult="Has_TV_false";
                                break;
                                    case "Outdoor Seating":
                                    bool = attr.get("Outdoor Seating").toString();
                                    if(bool.equals("true"))
                                        boolResult="Outdoor Seating";
                                    else
                                        boolResult="Outdoor_Seating_false";
                                break;
                                    case "Waiter Service":
                                    bool = attr.get("Waiter Service").toString();
                                    if(bool.equals("true"))
                                        boolResult="Waiter_Service";
                                    else
                                        boolResult="Waiter_Service_false";
                                break;
                                    case "Accepts Credit Cards":
                                    bool = attr.get("Accepts Credit Cards").toString();
                                    if(bool.equals("true"))
                                        boolResult="Accepts_Credit_Cards";
                                    else
                                        boolResult="Accepts_Credit_Cards_false";
                                break;
                                    case "Good For Groups":
                                    bool = attr.get("Good For Groups").toString();
                                    if(bool.equals("true"))
                                        boolResult="Good_For_Groups";
                                    else
                                        boolResult="Good_For_Groups_false";
                                break;
                                case "Wheelchair Accessible":
                                    bool = attr.get("Wheelchair Accessible").toString();
                                    if(bool.equals("true"))
                                        boolResult="Wheelchair_Accessible";
                                    else
                                        boolResult="Wheelchair_Accessible_false";
                                break;
                                case "Good For Dancing":
                                    bool = attr.get("Good For Dancing").toString();
                                    if(bool.equals("true"))
                                        boolResult="Good_For_Dancing";
                                    else
                                        boolResult="Good_For_Dancing_false";
                                break;
                                case "Coat Check":
                                    bool = attr.get("Coat Check").toString();
                                    if(bool.equals("true"))
                                        boolResult="Coat_Check";
                                    else
                                        boolResult="Coat_Check_false";
                                break;
                                case "Happy Hour":
                                    bool = attr.get("Happy Hour").toString();
                                    if(bool.equals("true"))
                                        boolResult="Happy Hour";
                                    else
                                        boolResult="Happy_Hour_false";
                                break;
                                case "BYOB":
                                    bool = attr.get("BYOB").toString();
                                    if(bool.equals("true"))
                                        boolResult="BYOB";
                                    else
                                        boolResult="BYOB_false";
                                break;
                                case "Corkage":
                                    bool = attr.get("Corkage").toString();
                                    if(bool.equals("true"))
                                        boolResult="Corkage";
                                    else
                                        boolResult="Corkage_false";
                                break;
                                
                            }
                            try{
                                PreparedStatement psInsBoolAttributeObj1 = jdbc.conn.prepareStatement("Insert into BAttribute values(?,?,?,?)");
                                psInsBoolAttributeObj1.setString(1,bid);
                                psInsBoolAttributeObj1.setString(2,newAttr);
                                psInsBoolAttributeObj1.setString(3,"NA");
                                psInsBoolAttributeObj1.setString(4,boolResult);
                                jdbc.insert(psInsBoolAttributeObj1);
                                psInsBoolAttributeObj1.close();
                            }catch(SQLException se){}
                        }
                       
                        }
         
                      }
 }
}
