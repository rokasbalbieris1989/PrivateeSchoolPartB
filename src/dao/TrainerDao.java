
package dao;

import entities.Trainer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import privateeschoolpartb.MainClass;

public class TrainerDao {
    private final String URL = "jdbc:mysql://localhost:3306/privateschool?serverTimezone=UTC";
    private final String USERNAME = "someone";
    private final String PASS = "1234567";
    private Connection conn;
    private final String trainersList = "SELECT * FROM listOfTrainers";
    private final String trainersPerCourse = "SELECT tr_id,firstName,lastName,subjectName \n" +
                                                "FROM trainers as tr,subjects as sb\n" +
                                                "WHERE tr.subject_id=sb.sb_id and tr.cr_id= ? ;";
    //private final String insertTrainer = "INSERT INTO trainers(firstName,lastName,subject_id) VALUES(?,?,?)";
    private final String insertTrainerPerCourse = "UPDATE trainers SET cr_id=? WHERE tr_id=?";
    
    private Connection getConnection() {                                                
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASS); 
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    private void closeConnections(ResultSet rs, Statement st) {
        try {
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Trainer> getTrainers() {
        List<Trainer> list = new ArrayList();
        try {
            Statement st = getConnection().createStatement();                                 
            ResultSet rs = st.executeQuery(trainersList);                                    
            do {
                rs.next();                                                              
                Trainer t = new Trainer(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4));
                list.add(t);
            } while (!rs.isLast());
            closeConnections(rs, st);                                        
        } catch (SQLException ex) {
            System.out.println("There are no trainers yet.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    public List<Trainer> getTrainersPerCourse(int cr_id) {
        List<Trainer> list = new ArrayList();
        try {
            PreparedStatement pst = getConnection().prepareStatement(trainersPerCourse);                    
            pst.setInt(1, cr_id);
            ResultSet rs = pst.executeQuery();                                    
            do {
                rs.next();
                Trainer t = new Trainer(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4));
                list.add(t);
            } while (!rs.isLast());
            closeConnections(rs, pst);                                        
        } catch (SQLException ex) {
            System.out.println("There are no trainers in this course yet.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public void insertTrainer(Trainer t) {
        try {
            CallableStatement pst = getConnection().prepareCall("{call insertTrainer(?,?,?)}");
            pst.setString(1, t.getFirstName());
            pst.setString(2, t.getLastName());
            pst.setString(3, t.getSubject());
            int rs = pst.executeUpdate();
            if (rs > 0) {
                System.out.println("Trainer inserted successfully");
            } else {
                System.out.println("Trainer not inserted");
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Could not establish connection.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertTrainerPerCourse(int tr_id,int cr_id) {
        try {
            PreparedStatement pst = getConnection().prepareStatement(insertTrainerPerCourse);
            pst.setInt(1, cr_id);
            pst.setInt(2, tr_id);
            int rs = pst.executeUpdate();
            if (rs > 0) {
                System.out.println("Trainer inserted into course successfully");
            } else {
                System.out.println("Trainer not inserted into course");
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Could not establish connection.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
