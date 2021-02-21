package com.pbg.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pbg.hibernate.demo.entity.Student;


/* ----- Hibernate working app Demo ----- */

/* ----- CRUD - Reading Objects with Hibernate Demo ----- */


public class ReadStudentDemo {

	public static void main(String[] args) {
		
		//	Create Session Factory		(-	Refer Note 1 below)
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")		// Now not needed to specify the file name here if you give this default file name
								.addAnnotatedClass(Student.class)
								.buildSessionFactory();
		
		
		//	Create Session
		Session session = factory.getCurrentSession();

		
		try {
			//	Use the session object to save java object
			//	Create a student object
			System.out.println("Creating new student object ..");
			
			Student tempStudent = new Student("Atharva", "Ganore" , "aganore@gmail.com");
			
			//	Start a Transaction
			session.beginTransaction();
			
			//	Save the student object
			System.out.println("Saving the Student .... ");
			System.out.println(tempStudent);
			session.save(tempStudent);
			
			//	Commit transaction
			session.getTransaction().commit();
			System.out.println("Done ! (Transaction committed successfully!)");
			
			//	My New code
			
			//	Find out the student's id : primary key
			System.out.println("Saved Student. Generated ID : " + tempStudent.getId());
			
			//	Now get a new session and start a Transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			//	Retrieve student based on the ID : Primary key
			System.out.println("\n Getting Student with ID : " + tempStudent.getId());
			
			Student myStudent = session.get(Student.class, tempStudent.getId());
			
			System.out.println("Get Complete : " + myStudent);
			
			
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
