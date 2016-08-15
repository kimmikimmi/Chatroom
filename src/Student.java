import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import java.util.ArrayList;

/**
 * Created by Jaden on 21/10/2015.
 */
public class Student extends Thread{

    StudentInfo studentInfo;

    ArrayList<Course> holds; // The arraylist which has courses this object has added.

    public Student(String name, String passWord, String iD) {
        holds = new ArrayList<>();
        studentInfo = new StudentInfo(name, passWord, iD);
    }


    public void addCourse(String title, String professor, String time) {
        /* Semaphore is going to be used here.
         this function write the shared collection of data(Courses)
         while this method performing, no other method can access to data.
          */

        // Wait semaphore and get semaphore
        // 2 if statements read the resources
        try {
            Courses.courseSem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(!Courses.hasCourse(title, professor, time)) {
            return;
        } else {
            if(Courses.getCourse(title, professor, time).isFull()) {
                return;
            }
        }

        // check duplicated registration happens.
        if(holds.contains(Courses.getCourse(title, professor, time))) {
            System.err.println(title + " is already registered");
            return;
        }

        /*
            만약 한 학생이 세마포를 선점하고 있다고 하자. 그 학생이 단순히 읽고 있다면. 우선순위는 add/delete 보다 낮으므로
            읽는 작업을 interrupt 한다. 하지만 course 를 add 하거나 delete 하는 경우라면 semaphore 를 기다려야 한다.
            여기서 사용될 semaphore 는 binary semaphore 이며 courseList.size()만큼 세마포가 존재해야 한다.

         */

        Courses.getCourse(title, professor, time).addStudent(this.studentInfo);
        holds.add(Courses.getCourse(title, professor, time));
        //release semaphore.
        Courses.courseSem.release();


    }

    public void dropCourse(String title, String professor, String time) {

        try {
            Courses.courseSem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        if(!Courses.hasCourse(title, professor, time)) {
            return;
        }
        /* Semaphore is going to be used here.
         this function write the shared collection of data(Courses)
         while this method performing, no other method can access to data.
          */


          /*
            만약 한 학생이 세마포를 선점하고 있다고 하자. 그 학생이 단순히 읽고 있다면. 우선순위는 add/delete 보다 낮으므로
            읽는 작업을 interrupt 한다. 하지만 course 를 add 하거나 delete 하는 경우라면 semaphore 를 기다려야 한다.
            여기서 사용될 semaphore 는 binary semaphore 이며 courseList.size()만큼 세마포가 존재해야 한다.

         */

        //wait semaphore.

        Courses.getCourse(title, professor, time).deleteStudent(this.studentInfo);
        holds.remove(Courses.getCourse(title, professor, time));
        //release semaphore
        Courses.courseSem.release();
    }

    public void lookupCourses() {
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
        for(Course course :Courses.getCourseList()) {
            System.out.println("course title : " + course.getTitle() + " \tTime : " + course.getTime()
                    + "\t occupation : " + course.getStudentList().size() +" out of " + course.getCapacity());
        }
        //release semaphore
        Courses.courseSem.release();
    }

    public void viewHolds() {
        System.out.println("ViewHolds---------------------------------------------------------");
        System.out.println("name : " + studentInfo.getName() + "\tID = " + studentInfo.getiD());
        System.out.println("Registered courses :");

        for(Course course : holds) {
            System.out.println(course.getTitle() + "| " + course.getProfessor() + "| " + course.getTime());
        }
    }

    @Override
    public void run() {
        super.run();


        for(int i=0;i <100; i++) {
            //lookupCourses();
            addCourse("CSC380", "James E Early", "MWF0800");
            //addCourse("CSC375", "Doug Lea", "TR0800");
            //viewHolds();
            //lookupCourses();
            dropCourse("CSC380", "James E Early", "MWF0800");
            //dropCourse("CSC375", "Doug Lea", "TR0800");
            //viewHolds();
            Courses.showCourses();
            System.out.println();
            //System.out.println();
        }

    }
}
