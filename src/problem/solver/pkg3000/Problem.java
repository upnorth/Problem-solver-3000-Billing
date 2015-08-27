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
public class Problem {
    private int id;
    private int man;
    private int cli;
    private int cat;
    private int pri;
    private String status;
    private String com;
    
    ArrayList<Task> tasks = new ArrayList<>();
    //Problem related information
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getMan() { return man; }
    public void setMan(int man) { this.man = man; }

    public int getCli() { return cli; }
    public void setCli(int cli) { this.cli = cli; }
    
    public int getCat() { return cat; }
    public void setCat(int cat) { this.cat = cat; }

    public int getPri() { return pri; }
    public void setPri(int pri) { this.pri = pri; }
    
    public String getCom() { return com; }
    public void setCom(String com) { this.com = com; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    /*@Override
    public String toString(){
        return this.fname + " " + this.lname + " " + this.ideal;
        
    }*/
    public ArrayList<Problem> getProblems(String status) throws SQLException{
        //Läs in information om db-uppkoppling
        Connection cn = null;
        //håller en lista med ett student-objekt för varje post i db
        ArrayList problemList = new ArrayList<>();
        try{
            //Laddar db-library (derby) till applikationens minne
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            //specificerar uppgifter som behövs för att koppla upp sig mot databasen
            cn = DriverManager.getConnection("jdbc:derby://localhost:1527/ps3;create=true;user=ps3;password=ps3"); 
            //Kontrollera uppkoppling mot db
            if (cn == null){
                throw new SQLException("Couldn´t connect to database!");
            }
            PreparedStatement stmt = cn.prepareStatement("SELECT * FROM PROBLEM WHERE STATUS=?");          
            //Kör SQL-uttryck och ladda upp posterna i ResultSet variabel rs 
            stmt.setString (1, status);
            ResultSet rs = stmt.executeQuery();
            //Ladda lista med data från ResultSet (rs)
            while (rs.next()){
                //För varje post i db skapas ett student-objekt
                Problem p = new Problem();
                p.setId(rs.getInt("ID"));
                p.setStatus(rs.getString("STATUS"));
                p.setMan(rs.getInt("MANAGER"));
                p.setCli(rs.getInt("CLIENT"));
                p.setPri(rs.getInt("PRIORITY"));
                p.setCat(rs.getInt("CATEGORY"));
                p.setCom(rs.getString("COMMENTS"));
                Task t = new Task();
                p.tasks = t.getTasks(rs.getInt("ID"));
                //Lägg till student-objekt i arraylist
                problemList.add(p);
            }
            return problemList;
        }catch (ClassNotFoundException | SQLException ex) {
            throw new SQLException("Problem with db:" + ex.getMessage());
        }finally{
            if (cn!=null) 
                cn.close();
        }
    }
}
