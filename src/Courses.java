import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Created by Jaden on 21/10/2015.
 */


/*
       Collection of courses.
       Currently, this class has only a member with private constructor.

 */
public class Courses {
    private Courses(){}
    public static Semaphore courseSem = new Semaphore(1);
    private static Queue<Course> courseList = new LinkedList<>();

    public static Queue<Course> getCourseList() {

        return courseList;
    }


    public static boolean hasCourse(String title, String professor, String time) {
        for(Course course : courseList) {
            if(course.getTitle() == title && course.getProfessor() == professor && course.getTime() == time) {
                return true;
            }
        }
        return false;
    }

    public static Course getCourse(String title, String professor, String time) {
        for(Course course : courseList) {
            if(course.getTitle() == title && course.getProfessor() == professor && course.getTime() == time) {
                return course;
            }
        }
        System.err.println("getCourse function doesn't seem to work!");
        return null;
    }

    public static void showCourses() {
        /* Semaphore is going to be used here.
         this function write the shared collection of data(Courses)
         while this method performing, no other method can access to data.
          */

        /*
            공유 자원을 읽는 작업을 하는 메소드. 한번에 여러 student objects가 동시에 lookupCourse() 를 호출할 수 있다. 최대 x명 까지.

         */
        //wait semaphore.
        try {
            Courses.courseSem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("lookupCourses---------------------------------------------------------");
        System.out.println("Courses available are all below");
        for(Course course :getCourseList()) {
            System.out.println("course title : " + course.getTitle() + " \tTime : " + course.getTime()
                    + "\t occupation : " + course.getStudentList().size() +" out of " + course.getCapacity());
        }
        //release semaphore
        Courses.courseSem.release();
    }
}
