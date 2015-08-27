/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problem.solver.pkg3000;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Karl
 */
public class Task {
    
    private int id;
    private int parent;
    private int cat;
    private int est;
    private int used;
    private int pri;
    private int tech;
    private String status;
    private String com;
    
    //Task related information
    public int getParent() { return parent; }
    public void setParent(int parent) { this.parent = parent; }    
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }  
    
    public int getCat() { return cat; }
    public void setCat(int cat) { this.cat = cat; }
    
    public int getEst() { return est; }
    public void setEst(int est) { this.est = est; }

    public int getUsed() { return used; }
    public void setUsed(int used) { this.used = used; }
    
    public int getPri() { return pri; }
    public void setPri(int pri) { this.pri = pri; }

    public int getTech() { return tech; }
    public void setTech(int tech) { this.tech = tech; }
    
    public String getCom() { return com; }
    public void setCom(String com) { this.com = com; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    /*@Override
    public String toString(){
        return this.fname + " " + this.lname + " " + this.ideal;
        
    }*/
    public ArrayList<Task> getTasks(int id) throws SQLException{
        //Läs in information om db-uppkoppling
        Connection cn = null;
        //håller en lista med ett student-objekt för varje post i db
        ArrayList taskList = new ArrayList<>();
        try{
            //Laddar db-library (derby) till applikationens minne
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            //specificerar uppgifter som behövs för att koppla upp sig mot databasen
            cn = DriverManager.getConnection("jdbc:derby://localhost:1527/ps3;create=true;user=ps3;password=ps3"); 
            //Kontrollera uppkoppling mot db
            if (cn == null){
                throw new SQLException("Couldn´t connect to database!!");
            }
            PreparedStatement stmt = cn.prepareStatement("SELECT * FROM TASK WHERE PROBLEM_ID=?");
            //Kör SQL-uttryck och ladda upp posterna i ResultSet variabel rs 
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            //Ladda lista med data från ResultSet (rs)
            while (rs.next()){
                //För varje post i db skapas ett student-objekt
                Task t = new Task();
                t.setParent(id);
                t.setId(rs.getInt("ID"));
                t.setCat(rs.getInt("CATEGORY"));
                t.setEst(rs.getInt("ESTTIME"));
                t.setUsed(rs.getInt("USEDTIME"));
                t.setPri(rs.getInt("PRIORITY"));
                t.setTech(rs.getInt("TECH_ID"));
                t.setStatus(rs.getString("STATUS"));
                t.setCom(rs.getString("COMMENTS"));
                //Lägg till student-objekt i arraylist
                taskList.add(t);
            }
            return taskList;
        }catch (ClassNotFoundException | SQLException ex) {
            throw new SQLException("Problem with db:" + ex.getMessage());
        }finally{
            if (cn!=null) 
                cn.close();
        }
        
    }
}

