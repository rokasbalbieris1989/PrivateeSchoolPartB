
package privateeschoolpartb;

import dao.AssignmentDao;
import dao.CourseDao;
import dao.StudentDao;
import dao.TrainerDao;
import entities.Assignment;
import entities.Course;
import entities.Student;
import entities.Trainer;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainClass {

    
    public static void main(String[] args) {
        //Greating
        System.out.println("\t Welcome to Private School Database ");
        System.out.println();
        Scanner input = new Scanner(System.in);
        startMenu(input);
        input.close();
    }
//-------------------------------------------------------------------------------------------------------------------------------------------------
//------START MENU--------------------------------------------------------------
    public static void startMenu(Scanner in2) {
        System.out.println("\n 1. Insert data into Database \n 2. Read data from Database\n 3. Exit the program");
        int choise = checkIntBetweenAandB(in2, 1, 3);
        switch (choise) {
            case 1:
                insertData(in2);
                break;
            case 2:
                goToMenu(in2);
                break;
            case 3:
                System.out.println("Bye Bye!!!");
                System.exit(0);
        }
    }
//-------------------------------------------------------------------------------------------------------------------------------------------------
//------CHOISE FOR CREATING DATA-----------------------------------------------
    public static void insertData(Scanner in2) {
        System.out.println("\nPlease enter the number of your choise: "
                + "\n 1. Insert a Course "
                + "\n 2. Insert a Student "
                + "\n 3. Insert an Assignment "
                + "\n 4. Insert a Trainer "
                + "\n 5. Insert a Student into Course"
                + "\n 6. Insert a Trainer into Course"
                + "\n 7. Insert an Assignment per Student per Course"
                + "\n 8. Go back to previous menu"
                + "\n 9. Exit the program");
        
        int choise = checkIntBetweenAandB(in2, 1, 9);//---CHECKING INPUT-------
        StudentDao sd=new StudentDao();
        AssignmentDao ad=new AssignmentDao();
        TrainerDao td=new TrainerDao();
        CourseDao cd = new CourseDao();
        int cr_id;
        int st_id;
        int oralMark;
        int totalMark;

        switch (choise) {
            case 1://-----CREATING COURSES--------------
                System.out.println("Give a number of Courses that you want to create.");
                choise = checkIntPositive(in2);
                for (int i = 0; i < choise; i++) {

                    System.out.println("For Course number " + (i + 1));
                    System.out.println("Enter the title of the Course");
                    String title = in2.next();
                    System.out.println("Enter the stream of the Course (full-time,part-time or other");
                    String stream = in2.next();
                    System.out.println("Enter the type of the Course (beginner,advanced or other)");
                    String type = in2.next();
                    System.out.println("Enter the start date of the Course ");
                    LocalDate start_date = getDate(in2);
                    System.out.println("Enter the end date of the Course ");
                    LocalDate end_date = getDate(in2);
                    Course c=new Course( title, stream, type, start_date, end_date);
                    cd.insertCourse(c);
                }
                insertData(in2);
                break;

            case 2://-----CREATING STUDENTS--------------
                System.out.println("Give a number of Students that you want to create.");
                choise = checkIntPositive(in2);//---CHECKING INPUT-------
                for (int i = 0; i < choise; i++) {
                    System.out.println("For Student number " + (i + 1));
                    System.out.println("Enter the first name of the Student");
                    String firstName = in2.next();
                    System.out.println("Enter the last name of the Student");
                    String lastName = in2.next();
                    System.out.println("Enter the date of birth of the Student ");
                    LocalDate dateOfBirth = getDate(in2);
                    System.out.println("Enter the amount of tuition fees of the Student");
                    int tuitionFees = in2.nextInt();
                    Student s = new Student(firstName, lastName, dateOfBirth, tuitionFees);
                    sd.insertStudent(s);
                }
                insertData(in2);
                break;
            case 3://-----CREATING ASSIGNMENTS--------------
                System.out.println("Give a number of Assignments that you want to create.");
                choise = checkIntPositive(in2);//---CHECKING INPUT-------
                for (int i = 1; i <= choise; i++) {
                    System.out.println("For Assignment number " + i);
                    System.out.println("Enter the Title of the Assignment");
                    String title = in2.next();
                    System.out.println("Enter the Description of the Assignment");
                    String description = in2.next();
                    System.out.println("Enter the Submission Date of the Assignment ");
                    LocalDate subDateTime = getDate(in2);
                    Assignment a = new Assignment(title, description, subDateTime);
                    ad.insertAssignment(a);
                }
                insertData(in2);
                break;
            case 4://-----CREATING TRAINERS--------------
                System.out.println("Give a number of Trainers that you want to create.");
                choise = checkIntPositive(in2);//---CHECKING INPUT-------
                for (int i = 1; i <= choise; i++) {
                    System.out.println("For Trainer number " + i);
                    System.out.println("Enter the First Name  of the Trainer");
                    String firstName = in2.next();
                    System.out.println("Enter the Last Name of the Trainer");
                    String lastName = in2.next();
                    System.out.println("Enter the Subject of the Trainer");
                    String subject = in2.next();
                    Trainer t = new Trainer(firstName, lastName,subject);
                    td.insertTrainer(t);
                }
                insertData(in2);
                break;
            case 5:  //-- Insert a Student into Course
                System.out.println("Select a Student");
                st_id=chooseStudentID(in2);
                System.out.println("Enter the number of the Course which to add to.");
                cr_id=chooseCourseID(in2);
                sd.insertStudentPerCourse(st_id, cr_id);
                insertData(in2);
                break;
            case 6://--Insert a Trainer into Course
                System.out.println("Select a trainer");
                int tr_id=chooseTrainerID(in2);
                System.out.println("Enter the number of the Course which to add to.");
                cr_id=chooseCourseID(in2);
                td.insertTrainerPerCourse(tr_id, cr_id);
                insertData(in2);
                break;
            case 7://--Insert an Assignment per Student per Course
                System.out.println("Select the Assignment");
                int as_id=chooseAssignmentID(in2);
                if (as_id==0){
                    goToMenu(in2);
                }
                System.out.println("Enter the number of the Course which to add to.");
                cr_id=chooseCourseID(in2);
                if (cr_id==0){
                    goToMenu(in2);
                }
                System.out.println("Enter the number of the Student which to add to.");
                st_id=chooseStudentID(in2);
                if (st_id==0){
                    goToMenu(in2);
                }
                System.out.println("Enter the oralMark of the Assignment for this Student (0 to 100)");
                oralMark =checkIntBetweenAandB(in2, 0, 100);
                System.out.println("Enter the totalMark of the Assignment for this Student (0 to 100)");
                totalMark =checkIntBetweenAandB(in2, 0, 100);
                ad.insertAssignmentPerStudentPerCourse(as_id, st_id, cr_id,oralMark,totalMark);
                insertData(in2);
                break;
            case 8://to menu
                startMenu(in2);
                break;
            case 9:
                System.out.println("Bye Bye!!!");
                System.exit(0);
        }
    }
//-------------------------------------------------------------------------------------------------------------------------------------------------
//----------------MENU TO EXTRACT INFORMATION----------------------------------
    public static void goToMenu(Scanner input) {
        System.out.println("\nPlease enter the number of your choise:"
                + "\n 1. List of all Students "
                + "\n 2. List of all Trainers "
                + "\n 3. List of all Assignments "
                + "\n 4. List of all Courses "
                + "\n 5. List of Students per Course"
                + "\n 6. List of Trainers per Course "
                + "\n 7. List of Assignments per Course  "
                + "\n 8. List of Assignments per Course per Student "
                + "\n 9. List of Students that belong to more than one Course"
                + "\n 10. Go back to previous menu"
                + "\n 11. Exit the program");
        int choise = checkIntBetweenAandB(input, 1, 11);//---CHECKING INPUT-------
        StudentDao sd=new StudentDao();
        TrainerDao td=new TrainerDao();
        AssignmentDao ad=new AssignmentDao();
        CourseDao cd = new CourseDao();
        int cr_id=0;
        int st_id=0;
        switch (choise) {
            case 1: //List of all Students
                System.out.println("\n----List of Students----");
                List<Student> list1 = sd.getStudents();
                for (Student c:list1){
                   System.out.println(c);
                }
                goToMenu(input);
                break;
            case 2://List of all Trainers
                System.out.println("\n----List of Trainers----");
                List<Trainer> list2 = td.getTrainers();
                for (Trainer c:list2){
                    System.out.println(c);
                }
                goToMenu(input);
                break;
            case 3://List of all Assignments
                System.out.println("\n----List of Assignments----");
                List<Assignment> list3 = ad.getAssignments();
                for (Assignment c:list3){
                    System.out.println(c);
                }
                goToMenu(input);
                break;
            case 4://List of all Courses
                System.out.println("\n----List of Courses----");
                List<Course> list4 = cd.getCourses();
                for (Course c:list4){
                    System.out.println(c);
                }
                goToMenu(input);
                break;
            case 5://List of Students per Course
                System.out.println("Select a Course");
                cr_id=chooseCourseID(input);
                if (cr_id==0){
                    goToMenu(input);
                }
                System.out.println("\n----List of Students per Course (id"+cr_id+")----");
                List<Student> list5 = sd.getStudentsPerCourse(cr_id);
                for (Student c:list5){
                    System.out.println(c);
                }
                goToMenu(input);
                break;
            case 6://List of Trainers per Course
                System.out.println("Select a Course");
                cr_id=chooseCourseID(input);
                if (cr_id==0){
                    goToMenu(input);
                }
                if (cr_id==0){
                    goToMenu(input);
                }
                System.out.println("\n----List of Trainers per Course (id"+cr_id+")----");
                List<Trainer> list6 = td.getTrainersPerCourse(cr_id);
                for (Trainer c:list6){
                    System.out.println(c);
                }
                goToMenu(input);
                break;
            case 7://List of Assignments per Course
                System.out.println("Select a Course");
                cr_id=chooseCourseID(input);
                if (cr_id==0){
                    goToMenu(input);
                }
                System.out.println("\n----List of Assignments per Course (id "+cr_id+")----");
                List<Assignment> list7 = ad.getAssignmentsPerCourse(cr_id);
                for (Assignment c:list7){
                    System.out.println(c);
                }
                goToMenu(input);
                break;
            case 8://List of Assignments per Course per Student
                System.out.println("Select a Course");
                cr_id=chooseCourseID(input);
                if (cr_id==0){
                    goToMenu(input);
                }
                System.out.println("Select a Student");
                st_id=chooseStudentID(input);
                if (st_id==0){
                    goToMenu(input);
                }
                System.out.println("\n----List of Assignments per Course (id"+cr_id+") per Student (id"+st_id+")----");
                List<Assignment> list8 = ad.getAssignmentsPerCoursePerStudent(cr_id,st_id);
                for (Assignment c:list8){
                    System.out.println(c);
                }
                goToMenu(input);
                break;
            case 9://List of Students that belong to more than one Course
                System.out.println("\n----List of Students With More Than 1 Course----");
                List<Student> list9 = sd.getStudentsWithMoreThanOneCourse();
                for (Student c:list9){
                    System.out.println(c);
                }
                goToMenu(input);
                break;
            case 10://Go back to previous menu
                startMenu(input);
                break;
            case 11://Exit
                System.out.println("Bye Bye!!!");
                System.exit(0);
        }
        input.close();
    }
//-------------------------------------------------------------------------------------------------------------------------------------------------
//---------METHOD FOR INSERTING A DATE-----------------------------------------
    public static LocalDate getDate(Scanner s) {
        int day;                                //------------------------------------checking input day
        do {
            System.out.println("Give me the number of the day (between 1 and 31) ");
            while (!s.hasNextInt()) {
                System.out.println("Must be a number");
                s.next();
            }
            day = s.nextInt();
        } while (day < 1 || day > 31);

        int month;
        do {                                    //--------------------------------------checking input month
            System.out.println("Give me the number of the month (between 1 and 12) ");
            while (!s.hasNextInt()) {
                System.out.println("Must be a number");
                s.next();
            }
            month = s.nextInt();
        } while (month < 1 || month > 12);

        int year;
        do {                                    //--------------------------------------checking input year
            System.out.println("Give me the number of the year (between 1970 and 2050) ");
            while (!s.hasNextInt()) {
                System.out.println("Must be a number");
                s.next();
            }
            year = s.nextInt();
        } while (year < 1970 || year > 2050);
        LocalDate date = LocalDate.of(year, month, day);
        return date;
    }
//-------------------------------------------------------------------------------------------------------------------------------------------------
//---------METHOD FOR CHECKING INPUT IF POSITIVE--------------------------------
    public static int checkIntPositive(Scanner in2) {
        int choise;
        if (in2.hasNextInt()) {//-----------------------------------------------------control input
            choise = in2.nextInt();
        } else {
            in2.next();   // get the inputted non integer from scanner
            choise = 0;
        }
        while (choise < 1) {
            System.out.print("Invalid entry! Must be a positive number \n");
            if (in2.hasNextInt()) {
                choise = in2.nextInt();
            } else {
                String dummy = in2.next();
                choise = 0;
            }
        }
        return choise;
    }
//-------------------------------------------------------------------------------------------------------------------------------------------------
//---------METHOD FOR CHECKING INPUT IF IS BETWEEN TWO NUMBERS-----------------
    public static int checkIntBetweenAandB(Scanner in2, int a, int b) {
        int choise;
        do {
            System.out.println("Please enter the number between " + a + " and " + b + " of your choise: ");

            while (!in2.hasNextInt()) {
                System.out.println("Invalid entry! Must be a number ");
                in2.next();
            }
            choise = in2.nextInt();
        } while (choise < a || choise > b);
        return choise;
    }
//--------------------------------------------------------------------------------------------------------------------------------------------------
//----------METHOD FOR CHOOSING ID OF A COURSE---------------------------------
        public static int chooseCourseID(Scanner input){
            CourseDao cd=new CourseDao();
            List<Course> list=cd.getCourses();
            if (cd.getCourses().isEmpty()) {
                        System.out.println("Go back to create Courses first!!!!");
                        return 0;
                    } else {
                        for(int i=1;i<=list.size();i++){
                            System.out.println(i+". "+ list.get(i-1).toString());
                        }
                        int choise=checkIntPositive(input);
                        return choise;
                    }
       
        }
        
//--------------------------------------------------------------------------------------------------------------------------------------------------       
//----------METHOD FOR CHOOSING ID OF A STUDENT---------------------------------
        public static int chooseStudentID(Scanner input){
            StudentDao sd=new StudentDao();
            List<Student> list=sd.getStudents();
            if (sd.getStudents().isEmpty()) {
                        System.out.println("Go back to create Student first!!!!");
                        return 0;
                    } else {
                        for(int i=1;i<=list.size();i++){
                            System.out.println(i+". "+ list.get(i-1).toString());
                        }
                        int choise=checkIntPositive(input);
                        return choise;
                    }
        }        

//--------------------------------------------------------------------------------------------------------------------------------------------------       
 
        //----------METHOD FOR CHOOSING ID OF A TRAINER------------------------
        public static int chooseTrainerID(Scanner input){
            TrainerDao td=new TrainerDao();
            List<Trainer> list=td.getTrainers();
            if (td.getTrainers().isEmpty()) {
                        System.out.println("Go back to create Trainer first!!!!");
                        return 0;
                    } else {
                        for(int i=1;i<=list.size();i++){
                            System.out.println(i+". "+ list.get(i-1).toString());
                        }
                        int choise=checkIntPositive(input);
                        return choise;
                    }
        }        

//--------------------------------------------------------------------------------------------------------------------------------------------------  
//----------METHOD FOR CHOOSING ID OF A ASSIGNMENT-----------------------------
        public static int chooseAssignmentID(Scanner input){
            AssignmentDao ad=new AssignmentDao();
            List<Assignment> list=ad.getAssignments();
            if (ad.getAssignments().isEmpty()) {
                        System.out.println("Go back to create Assignment first!!!!");
                        return 0;
                    } else {
                        for(int i=1;i<=list.size();i++){
                            System.out.println(i+". "+ list.get(i-1).toString());
                        }
                        int choise=checkIntPositive(input);
                        return choise;
                    }
        }        

//--------------------------------------------------------------------------------------------------------------------------------------------------   
}
