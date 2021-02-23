package com.pbg.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pbg.hibernate.demo.entity.Instructor;
import com.pbg.hibernate.demo.entity.InstructorDetail;
import com.pbg.hibernate.demo.entity.Student;


/* ----- Hibernate Advanced Mapping - 'Bidirectional 'One-to-One Mapping demo app  ----- */

/* ----- View the Instructor Obj using Instructor_Detail Obj in a Bidirectional Manner ----- */


public class GetInstructorDetailDemo {

	public static void main(String[] args) {
		
		//	Create Session Factory		(-	Refer Note 1 below)
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")		// Now not needed to specify the file name here if you give this default file name
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.buildSessionFactory();
		
		
		//	Create Session
		Session session = factory.getCurrentSession();

		
		try {
			
			
			//	Start a Transaction
			session.beginTransaction();
			
			//	Get the instructor detail Object
			int theId = 1; 	// make sure you check the available ID's in your database
			InstructorDetail tempInstructorDetail =  session.get(InstructorDetail.class, theId);
			
			//	Print the instructor detail
			System.out.println("tempInstructorDetail : " + tempInstructorDetail);
			
			//	Print the Associated Instructor
			System.out.println("The Associated Instructor : " + tempInstructorDetail.getInstructor());		
			
			//	Commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done ! (Transaction committed successfully!)");
			//	This should have added a row in student table in your database - go check it!
			
		}catch(Exception E) {
			E.printStackTrace();
		}finally {
			
			// handle the connection Leak issue
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
