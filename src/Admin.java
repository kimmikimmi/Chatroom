/**
 * Created by Jaden on 21/10/2015.
 */

/*
    This class is for maintaining the courses.
    at the beginning when this object construct, Admin construct new collection of course
    The reason why this class exists is to improve maintainability.
 */
public class Admin {

    public Admin() {
        makeCourses();
    }

    private void makeCourses() {
        // class : Name | Capacity | Professor | Department | term | time
        Courses.getCourseList().add(new Course("CSC380", 10, "James E Early", "CSC", "fall", "MWF0800"));
        Courses.getCourseList().add(new Course("CSC375", 10, "Doug Lea", "CSC", "fall", "TR0800"));

    }

}
