package dao;

import entities.Student;
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

public class StudentDao {

    private final String URL = "jdbc:mysql://localhost:3306/privateschool?serverTimezone=UTC";
    private final String USERNAME = "someone";
    private final String PASS = "1234567";
    private Connection conn;
    private final String studentsList = "SELECT * FROM students ";
    private final String insertStudent = "INSERT INTO students VALUES(?,?,?,?,?)";
    private final String insertStudentPerCourse = "INSERT INTO studentspercourse(st_id,cr_id) VALUES(?,?)";
    private final String studentsPerCourse = "SELECT spc.st_id,firstName,lastName,dateOfBirth,tuitionFees "
                                                + "FROM studentspercourse AS spc,students AS st "
                                                + "WHERE spc.st_id=st.st_id AND cr_id= ? ";
    
    private final String studentsWithMoreThanOneCourse = "SELECT st.st_id,firstName,lastName,dateOfBirth,tuitionFees\n" +
                                                            "FROM students AS st, (SELECT st_id \n" +
                                                            "                            FROM studentspercourse \n" +
                                                            "                            GROUP BY st_id \n" +
                                                            "                            HAVING count(cr_id)>1) \n" +
                                                            "                            AS spc1\n" +
                                                            "WHERE st.st_id=spc1.st_id ";

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

    public List<Student> getStudents() {
        List<Student> list = new ArrayList();
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(studentsList);
            do {
                rs.next();
                Student s = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), LocalDate.parse(rs.getString(4)), rs.getDouble(5));
                list.add(s);
            } while (!rs.isLast());
            closeConnections(rs, st);
        } catch (SQLException ex) {
            System.out.println("There are no students yet.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }

    public List<Student> getStudentsPerCourse(int cr_id) {
        List<Student> list = new ArrayList();
        try {
            PreparedStatement pst = getConnection().prepareStatement(studentsPerCourse);
            pst.setInt(1, cr_id);
            ResultSet rs = pst.executeQuery();
            do {
                rs.next();
                Student s = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), LocalDate.parse(rs.getString(4)), rs.getDouble(5));
                list.add(s);
            } while (!rs.isLast());
            closeConnections(rs, pst);
        } catch (SQLException ex) {
            System.out.println("There are no students in this course.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public List<Student> getStudentsWithMoreThanOneCourse() {
        List<Student> list = new ArrayList();
        try {
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(studentsWithMoreThanOneCourse);
            do {
                rs.next();
                Student s = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), LocalDate.parse(rs.getString(4)), rs.getDouble(5));
                list.add(s);
            } while (!rs.isLast());
            closeConnections(rs, st);
        } catch (SQLException ex) {
            System.out.println("There are no students that have more than one course.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    
    public void insertStudent(Student s) {
        try {
            PreparedStatement pst = getConnection().prepareStatement(insertStudent);
            pst.setInt(1, s.getSt_id());
            pst.setString(2, s.getFirstName());
            pst.setString(3, s.getLastName());
            pst.setDate(4, java.sql.Date.valueOf(s.getDateOfBirth()));
            pst.setDouble(5, s.getTuitionFees());
            int rs = pst.executeUpdate();
            if (rs > 0) {
                System.out.println("Student inserted successfully");
            } else {
                System.out.println("Student not inserted");
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Could not establish connection.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertStudentPerCourse(int st_id,int cr_id) {
        try {
            PreparedStatement pst = getConnection().prepareStatement(insertStudentPerCourse);
            pst.setInt(1, st_id);
            pst.setInt(2, cr_id);
            int rs = pst.executeUpdate();
            if (rs > 0) {
                System.out.println("Student inserted into course successfully");
            } else {
                System.out.println("Student not inserted into course");
            }
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Could not establish connection.");
            //Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
