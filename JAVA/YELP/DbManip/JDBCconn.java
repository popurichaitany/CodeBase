/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbManip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author anand
 */
public class JDBCconn {
    // JDBC driver name and database URL
    public static Connection conn1 = null;
    public Connection conn = null;
    public JDBCconn() {
        System.out.println("-------- Oracle JDBC Connection Testing ------");
        

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;

        } catch (InstantiationException ex) {
            Logger.getLogger(JDBCconn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(JDBCconn.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Oracle JDBC Driver Registered!");

       
    }
    
    public void connect(){
         //Connection connection = null;
        try {

             //String connString="jdbc:oracle:thin:@prodHost:1521:orcl";
            String connString = "jdbc:oracle:thin:@localhost:1521:orcl";

            /*OracleDataSource ods = new OracleDataSource();
            ods.setURL(connString);
            ods.setUser("system");
            ods.setPassword("Psd123pun");*/
            conn = DriverManager.getConnection(connString, "system", "Psd123pun");
            //connection = DriverManager.getConnection(
            //		"jdbc:oracle:thin:@localhost:1521:xe", "system",
            //		"Psd123pun");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            System.out.println(e.getMessage());
            return;

        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        //YELP	system@//localhost:1521/orcl
        if (conn != null) {
            //System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }
    
    public void close(){
        try{
            conn.close();
        } catch(Exception e){}
    }
    
    public boolean insert(PreparedStatement psIns){
        try{
            boolean inserted = psIns.execute();
            return inserted;
        }catch(Exception e){System.out.println("Error:"+e.getMessage());}
        return false;
    }
    
    public int update(PreparedStatement psUpd){
        try{
            int updated = psUpd.executeUpdate();
            return updated;
        } catch(Exception e){}
        return -1;
    }
    
    public int delete(PreparedStatement psDel){
        try{
            int deleted = psDel.executeUpdate();
            return deleted;
        } catch(Exception e){}
        return -1;
    }
    
    public ResultSet select(PreparedStatement psSel){
        ResultSet selectionResult = null;
        try{
        selectionResult = psSel.executeQuery();
        return selectionResult;
        } catch(SQLException se){se.printStackTrace();}
        return null;
    }
    
    public static void create(String sqlCreate){
        JDBCconn jdbc = new JDBCconn();
        try{
                String connString = "jdbc:oracle:thin:@localhost:1521:orcl";
                conn1 = DriverManager.getConnection(connString, "system", "Psd123pun");
                PreparedStatement psCreate = conn1.prepareStatement(sqlCreate);
                psCreate.executeUpdate();
                psCreate.close();
        }catch(Exception e){ e.printStackTrace();
        }finally{
            try{
                jdbc.close();
            }catch(Exception ex){ ex.printStackTrace();}
        }
    }
}
