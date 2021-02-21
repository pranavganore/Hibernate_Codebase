package com.pbg.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pbg.hibernate.demo.entity.Student;


/* ----- Hibernate working app Demo ----- */

/* ----- CRUD - Update Operations  ----- */


public class UpdateStudentDemo {

	public static void main(String[] args) {
		
		//	Create Session Factory		(-	Refer Note 1 below)
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")		// Now not needed to specify the file name here if you give this default file name
								.addAnnotatedClass(Student.class)
								.buildSessionFactory();
		
		
		//	Create Session
		Session session = factory.getCurrentSession();

		
		try {
			
			int  studentID = 1 ;
			
			//	Now get a new session and start a Transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			//	Retrieve student based on the ID : Primary key
			System.out.println("\n Getting Student with ID : " + studentID);
			
			Student myStudent = session.get(Student.class, studentID);
			
			// Update Operation
			System.out.println("1.	Updating Student ... ");
			myStudent.setFirstName("Ani");
			
			//Commit the Transaction
			session.getTransaction().commit();
			System.out.println("Done ! (Transaction committed successfully!)");
			
			//	NEW CODE
			//	Now get a new session and start a Transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			//	Update email 
			System.out.println("\n2.	Updating email of id1 -");
			session.createQuery("update Student set email='anic@yahoo.com' where id='1'").executeUpdate();
				//	Similarly we can bult update by removing where clause here
			
			//Commit the Transaction
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
