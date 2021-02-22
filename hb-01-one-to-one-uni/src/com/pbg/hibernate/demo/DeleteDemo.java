package com.pbg.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pbg.hibernate.demo.entity.Instructor;
import com.pbg.hibernate.demo.entity.InstructorDetail;
import com.pbg.hibernate.demo.entity.Student;


/* ----- Hibernate Advanced Mapping - One-to-One Mapping demo app  ----- */

/* ----- CRUD - Creating Objects ----- */


public class DeleteDemo {

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
			
			//	Create the Objects
			Instructor tempInstructor = new Instructor("Pranav","Ganore","pranavbganore@gmail.com");
			
			InstructorDetail tempInstructorDetail = new InstructorDetail(
										"https://www.youtube.com/channel/UC-CxH7xvNYYkx_KTXkYa-ow",
										"Singing");
			
//			Instructor tempInstructor = new Instructor("Aniket","Chandak","achandak@gmail.com");
//			
//			InstructorDetail tempInstructorDetail = new InstructorDetail(
//										"https://www.youtube.com/channel/chandukaka",
//										"Eating");
			
			//	Associate the objects
			tempInstructor.setInstructorDetail(tempInstructorDetail);
			
			//	Start a Transaction
			session.beginTransaction();
			
			//	Save the Instructor
			System.out.println("Saving Instructor : " + tempInstructor);
			session.save(tempInstructor);
			//	Note: This will ALSO save the InstructorDetail Objects because of the CascadeType.ALL
			
			
			//	Commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done ! (Transaction committed successfully!)");
			//	This should have added a row in student table in your database - go check it!
			
		}finally {
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
