package com.pbg.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pbg.hibernate.demo.entity.Course;
import com.pbg.hibernate.demo.entity.Instructor;
import com.pbg.hibernate.demo.entity.InstructorDetail;
import com.pbg.hibernate.demo.entity.Review;


/* ----- Hibernate Advanced Mapping - One-to-Many Unidirectional Mapping demo app  ----- */

/* ----- CRUD - Creating Objects ----- */


public class CreateCourseAndReviewsDemoApp {

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
			

			//	Create a Course
			Course tempCourse = new Course("CMSC - 611");
			
			//	Add some reviews
			tempCourse.addReview(new Review("Great Course... Loved It!!"));
			tempCourse.addReview(new Review("Cool Course !!"));
			tempCourse.addReview(new Review("Nicely Planned and spread out evenly."));
			tempCourse.addReview(new Review("What a dumb course :( "));
			
			//	Save the Course .. and leverage the cascade all :-)
			System.out.println("Saving the Course " + tempCourse + "\nAlong with the reviews : "
					+ tempCourse.getReviews());
			
			session.save(tempCourse);

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
