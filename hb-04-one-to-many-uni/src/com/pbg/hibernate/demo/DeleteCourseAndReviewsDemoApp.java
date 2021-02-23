package com.pbg.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pbg.hibernate.demo.entity.Course;
import com.pbg.hibernate.demo.entity.Instructor;
import com.pbg.hibernate.demo.entity.InstructorDetail;
import com.pbg.hibernate.demo.entity.Review;


/* ----- Hibernate Advanced Mapping - One-to-Many Unidirectional Mapping demo app  ----- */

/* ----- CRUD - Reading / Getting/ Viewing / Fetching Objects ----- */


public class DeleteCourseAndReviewsDemoApp {

	public static void main(String[] args) {
		
		//	Create Session Factory		(-	Refer Note 1 below)
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")		// Now not needed to specify the file name here if you give this default file name
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)
								.addAnnotatedClass(Review.class)
								.buildSessionFactory();
		
		
		//	Create Session
		Session session = factory.getCurrentSession();

		
		try {
			
			//	Start a Transaction
			session.beginTransaction();
			
			//	Get the Course
			int theId = 10;		// Make sure you check if the id is present in the database table 'Course'
			Course tempCourse = session.get(Course.class, theId);
			
			//	Print the Course
			System.out.println("Deleting the Course : " + tempCourse);

			
			//	Print the Course REVIEWS
			System.out.println(tempCourse.getReviews());

			//	Delete the Course
			session.delete(tempCourse);
				// It will cascade delete the reviews as well -- do check
			
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
