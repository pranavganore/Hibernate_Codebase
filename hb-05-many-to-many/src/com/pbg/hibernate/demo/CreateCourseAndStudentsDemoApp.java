package com.pbg.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pbg.hibernate.demo.entity.Course;
import com.pbg.hibernate.demo.entity.Instructor;
import com.pbg.hibernate.demo.entity.InstructorDetail;
import com.pbg.hibernate.demo.entity.Review;
import com.pbg.hibernate.demo.entity.Student;


/* ----- Hibernate Advanced Mapping - One-to-Many Unidirectional Mapping demo app  ----- */

/* ----- CRUD - Creating Objects ----- */


public class CreateCourseAndStudentsDemoApp {

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
			

			//	Create a Course
			Course tempCourse = new Course("CMSC - 611");
			
			
			//	Save the Course .. and leverage the cascade all :-)
			System.out.println("PBG : Saving the Course " + tempCourse );
			
			session.save(tempCourse);
			
			System.out.println("PBG : Saved the course : " + tempCourse + " Successfully !!!! ");
			
			//	Create the Students
			
			Student tempStudent1 = new Student("John", "Doe", "john@xyz.com");
			Student tempStudent2 = new Student("Mary", "Curati", "mary@jjo.com");
			
			//	Add the Students to the course
			tempCourse.addStudent(tempStudent1);
			tempCourse.addStudent(tempStudent2);
			
			//	Save the Students
			System.out.println("\nSaving Students ... ");
			session.save(tempStudent1);
			session.save(tempStudent2);
			System.out.println("\nSaved Students : " + tempCourse.getStudents() + " \n Successfully!!");
			
			
			
			session.save(tempCourse);

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
