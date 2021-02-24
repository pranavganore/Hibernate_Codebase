package com.pbg.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pbg.hibernate.demo.entity.Course;
import com.pbg.hibernate.demo.entity.Instructor;
import com.pbg.hibernate.demo.entity.InstructorDetail;
import com.pbg.hibernate.demo.entity.Review;
import com.pbg.hibernate.demo.entity.Student;


/* ----- Hibernate Advanced Mapping - Many-to-Many  Mapping demo app  ----- */

/* ----- CRUD - Add Courses Objects ----- */


public class AddCoursesForMaryDemo {

	public static void main(String[] args) {
		
		//	Create Session Factory		(-	Refer Note 1 below)
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")		// Now not needed to specify the file name here if you give this default file name
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)
								.addAnnotatedClass(Review.class)
								.addAnnotatedClass(Student.class)
								.buildSessionFactory();
		
		
		//	Create Session
		Session session = factory.getCurrentSession();

		
		try {
			
			//	Start a Transaction
			session.beginTransaction();
			
			//	Get the student 'Mary' from the Database
			int studentId = 2;
			Student tempStudent = session.get(Student.class, studentId);
			
			System.out.println("\nStudent Loaded : " + tempStudent);
			System.out.println("Courses : " + tempStudent.getCourses());
			
			//	Create more courses for Mary
			Course tempCourse1 = new Course("CMSC 420");
			Course tempCourse2 = new Course("CMSC 210");
			Course tempCourse3 = new Course("CMSC 600");
			Course tempCourse4 = new Course("CMSC 101");
			
			//	Add student Mary to the Courses
			tempCourse1.addStudent(tempStudent);
			tempCourse2.addStudent(tempStudent);
			tempCourse3.addStudent(tempStudent);
			tempCourse4.addStudent(tempStudent);
			
			//	Save the Courses
			System.out.println("\nSaving the Courses ... ");
			
			session.save(tempCourse1);
			session.save(tempCourse2);
			session.save(tempCourse3);
			session.save(tempCourse4);
			
			
			//	Commit transaction
			session.getTransaction().commit();
			
			System.out.println("PBG : Done ! (Transaction committed successfully!)");
			//	This should have added a row in student table in your database - go check it!
			
		}finally {
			// Add cleanup code
			
			session.close();
			factory.close();
		}
	}

}


/*	Note 1:
 * 
 * Two Key players in Hibernate :	(from slide 188 UDEMY Chad Darby)
 * 
 * 	1.	Session Factory
 * 		-	Reads the hibernate configuration file
 * 		-	Creates session Objects
 * 		-	It's a Heavy Weight Object 
 * 		-	Only created once in your app & reused over and over again
 * 
 * 	2.	Session
 * 		-	Wraps a JDBC connection
 * 		-	Main Object used to save/retrieve Objects
 * 		-	Short-lived object
 * 		-	Retrieved from SessionFactory
 */
