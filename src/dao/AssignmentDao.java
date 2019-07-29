
package dao;

import entities.Assignment;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import privateeschoolpartb.MainClass;

public class AssignmentDao {
    private final String URL = "jdbc:mysql://localhost:3306/privateschool?serverTimezone=UTC";
    private final String USERNAME = "someone";
    private final String PASS = "1234567";
    private Connection conn;
    private final String assignmentsList = "SELECT * FROM assignments";
    private final String insertAssignment = "INSERT INTO assignments(title,description,subDateTime) VALUES(?,?,?)";
    //private final String insertAssignmentPerStudentPerCourse = "INSERT INTO assignmentspersperc(as_id,st_id,cr_id,oralMark,totalMark) VALUES(?,?,?,?,?)";
    private final String assignmentsPerCourse = "SELECT as_id,title,description,subDateTime \n" +
                                                    "FROM assignments AS ass,courses as cr\n" +
                                                    "WHERE ass.cr_id=cr.cr_id AND cr.cr_id= ? ";
    
    private final String assignmentsPerCoursePerStudent = "SELECT aps.as_id,title,description,subDateTime\n" +
                                                            "FROM assignmentspersperc AS aps,assignments AS ass, courses AS cr\n" +
                                                            "WHERE aps.as_id=ass.as_id AND ass.cr_id=cr.cr_id AND ass.cr_id= ? AND st_id= ?";

    
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
    
    public List<Assignment> getAssignments() {
        List<Assignment> list = new ArrayList();
        try {
            Statement st = getConnection().createStatement();                                 
            ResultSet rs = st.executeQuery(assignmentsList);                                    
            do {
                rs.next();                                                              
                Assignment s = new Assignment(rs.getInt(1), rs.getString(2),rs.getString(3),LocalDate.parse(rs.getString(4)));
                list.add(s);
            } while (!rs.isLast());
            closeConnections(rs, st);                                        
        } catch (SQLException ex) {
            System.out.println("There are no assignments.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    public List<Assignment> getAssignmentsPerCourse(int cr_id) {
        List<Assignment> list = new ArrayList();
        try {
            PreparedStatement pst = getConnection().prepareStatement(assignmentsPerCourse);                    
            pst.setInt(1, cr_id);
            ResultSet rs = pst.executeQuery();                                    
            do {
                rs.next();
                Assignment t = new Assignment(rs.getInt(1), rs.getString(2),rs.getString(3),LocalDate.parse(rs.getString(4)));
                list.add(t);
            } while (!rs.isLast());
            closeConnections(rs, pst);                                         
        } catch (SQLException ex) {
            System.out.println("There are no assignments for this course yet.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public List<Assignment> getAssignmentsPerCoursePerStudent(int cr_id,int st_id) {
        List<Assignment> list = new ArrayList();
        try {
            PreparedStatement pst = getConnection().prepareStatement(assignmentsPerCoursePerStudent);                    
            pst.setInt(1, cr_id);
            pst.setInt(2, st_id);
            ResultSet rs = pst.executeQuery();                                    
            do {
                rs.next();
                Assignment t = new Assignment(rs.getInt(1), rs.getString(2),rs.getString(3),LocalDate.parse(rs.getString(4)));
                list.add(t);
            } while (!rs.isLast());
            closeConnections(rs, pst);                                         
        } catch (SQLException ex) {
            System.out.println("There are no assignments for this Student in this Course");
        }
        return list;
    }
    
    public void insertAssignment(Assignment a) {
        try {
            PreparedStatement pst = getConnection().prepareStatement(insertAssignment);
            pst.setString(1, a.getTitle());
            pst.setString(2, a.getDescription());
            pst.setDate(3, java.sql.Date.valueOf(a.getSubDateTime()));
            int rs = pst.executeUpdate();
            if (rs > 0) {
                System.out.println("Assignment inserted successfully");
            } else {
                System.out.println("Assignment not inserted");
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Could not establish connection.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertAssignmentPerStudentPerCourse(int as_id,int st_id,int cr_id,int oralMark,int totalMark) {
        try {
            CallableStatement pst = getConnection().prepareCall("{call insertAssPerSPerC(?,?,?,?,?)}");
            pst.setInt(1, st_id);
            pst.setInt(2, cr_id);
            pst.setInt(3, as_id);
            pst.setInt(4, oralMark);
            pst.setInt(5, totalMark);
            int rs = pst.executeUpdate();
            if (rs > 0) {
                System.out.println("Assignment inserted successfully");
            } else {
                System.out.println("Assignment not inserted");
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Check first if this assignment is inserted into this course."
                    + "If not, please insert it first and then try again. Thank you for your patience.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
