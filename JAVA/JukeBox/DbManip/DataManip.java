/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbManip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Anand
 */
public class DataManip {
    private Connection conn;
    Statement stmt;
    ResultSet rs;
    
    public void init(){
        try{
          String createSql = "CREATE TABLE IF NOT EXISTS songs(Name TEXT,Path TEXT,Album TEXT,Artist TEXT,Duration TEXT)";
          this.stmt.executeUpdate(createSql);
          this.stmt.close();
          this.getConn().commit();
          this.Close();
        }catch(Exception se)
        {
            System.out.println(se.getMessage());
        }
    }
    public DataManip()
    {
        try
        {
            this.Open();
            this.stmt=this.conn.createStatement();
        }catch(SQLException se)
        {
            System.out.println(se.getMessage());
        }
    }
    
    private void Open()
    {
        try
         {
            Class.forName("org.sqlite.JDBC");//com.mysql.jdbc.Driver
            this.setConn(DriverManager.getConnection("jdbc:sqlite:test.db"));//jdbc:mysql://localhost/test","root","H0pe&faith"
            this.getConn().setAutoCommit(false); 
         }catch(ClassNotFoundException cnf)
         {
             System.out.println(cnf.getMessage());
         }catch(SQLException se)
         {
             System.out.println(se.getMessage());
         }
    }
    
    public ResultSet ExecuteReader(String slct)
    {
        try
        {
            this.rs=this.stmt.executeQuery(slct);
            return this.rs;
        }catch(SQLException se)
        {
            System.out.println(se.getMessage());
        }
        return null;
    }
    
    public void ExecuteQuery(String dml)
    {
        try
        {
            this.stmt.executeUpdate(dml);
            this.stmt.close();
            
            this.getConn().commit();           
            this.Close();
        }catch(SQLException se)
        {
            System.out.println("DML Error:"+se.getMessage());
        }catch(Exception e)
        {
            System.out.println("DML Error:"+e.getMessage());
        }
    }
    
    private void Close()
    {
        try
        {
            this.getConn().close();
        }catch(SQLException se)
        {
            System.out.println(se.getMessage());
        }
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
