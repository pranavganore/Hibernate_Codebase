package com.pbg.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pbg.hibernate.demo.entity.Course;
import com.pbg.hibernate.demo.entity.Instructor;
import com.pbg.hibernate.demo.entity.InstructorDetail;


/* ----- Hibernate Advanced Mapping - One-to-Many Mapping demo app  ----- */

/* ----- CRUD - Creating Objects ----- */


public class CreateCoursesDemoApp {

	public static void main(String[] args) {
		
		//	Create Session Factory		(-	Refer Note 1 below)
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")		// Now not needed to specify the file name here if you give this default file name
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)
								.buildSessionFactory();
		
		
		//	Create Session
		Session session = factory.getCurrentSession();

		
		try {
			
			//	Start a Transaction
			session.beginTransaction();
			
			//	Get the instructor from the database
			int theId = 1;
			Instructor tempInstructor = session.get(Instructor.class, theId);
			
			//	Create some courses
			Course tempCourse1 = new Course("Assasins Creed");
			Course tempCourse2 = new Course("Civilizations");
			
			
			//	Add courses to instructor
			tempInstructor.add(tempCourse1);
			tempInstructor.add(tempCourse2);
			
			
			//	Save the courses
			session.save(tempCourse1);
			session.save(tempCourse2);

			//	Commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done ! (Transaction committed successfully!)");
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
