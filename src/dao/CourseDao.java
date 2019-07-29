
package dao;

import entities.Course;
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

public class CourseDao {
    private final String URL = "jdbc:mysql://localhost:3306/privateschool?serverTimezone=UTC";
    private final String USERNAME = "someone";
    private final String PASS = "1234567";
    private Connection conn;
    private final String coursesList = "SELECT * FROM listOfCourses";
    //private final String insertCourse = "INSERT INTO courses(title_id,stream_id,type_id,start_date,end_date) VALUES(?,?,?,?,?)";
    
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
    
    public List<Course> getCourses() {
        List<Course> list = new ArrayList();
        try {
            Statement st = getConnection().createStatement();                                 
            ResultSet rs = st.executeQuery(coursesList);                                    
            do {
                rs.next();                                                              
                Course c = new Course(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),LocalDate.parse(rs.getString(5)),LocalDate.parse(rs.getString(6)));
                list.add(c);
            } while (!rs.isLast());
            closeConnections(rs, st);                                        
        } catch (SQLException ex) {
            System.out.println("There are no course yet.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    
    public void insertCourse(Course c) {
        try {
            CallableStatement pst = getConnection().prepareCall("{call insertCourse(?,?,?,?,?)}");
            pst.setString(1, c.getTitle());
            pst.setString(2, c.getStream());
            pst.setString(3, c.getType());
            pst.setDate(4, java.sql.Date.valueOf(c.getStart_date()));
            pst.setDate(5, java.sql.Date.valueOf(c.getEnd_date()));
            int rs = pst.executeUpdate();
            if (rs > 0) {
                System.out.println("Course inserted successfully");
            } else {
                System.out.println("Course not inserted");
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Could not establish connection.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
