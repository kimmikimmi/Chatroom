import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import javax.xml.transform.Source;
import java.util.ArrayList;

/**
 * Created by Jaden on 21/10/2015.
 */


// Course class is a data structure which each object means a course.
public class Course {

    private String title; // the title of the course
    private int capacity; // the maximum number available.
    private ArrayList<StudentInfo> studentList;

    // not gonna be used currently.
    private String professor; // the name of professor in charge of this course.
    private String department; // the name of department associated with this course.
    private String term; // the term this course is available.

    private String time;

    public int getCapacity() {
        return capacity;
    }
    public Course(String title, int capacity, String professor, String department, String term, String time) {
        this.title = title;
        this.capacity = capacity;
        this.professor = professor;
        this.department = department;
        this.term = term;
        this.time = time;

        studentList = new ArrayList<>(capacity);


    }

    public ArrayList<StudentInfo> getStudentList() {
        return studentList;
    }

    public void addStudent(StudentInfo studentInfo) {

        // check if the list is full or not
        if(isFull()) {
            System.err.println("list is full");
            return;
        }
        // check duplication of same student.
        if(studentList.contains(studentInfo)) {
            return;
        }

        studentList.add(studentInfo);
        /*
            sort 하여 자료를 저장하면 좋지만 그것은 차후에 고려하자.
         */
        //studentList.sort(null);
    }

    public void deleteStudent(StudentInfo studentInfo) {
        if(studentList.isEmpty()) {
            return;
        }
        studentList.remove(studentInfo);
    }


    public boolean isFull() {
        if(studentList.size() == capacity) {
            return true;
        }
        return false;
    }

    public String getTitle() {
        return title;
    }

    public String getProfessor() {
        return professor;
    }

    public String getTime() {
        return time;
    }
}
