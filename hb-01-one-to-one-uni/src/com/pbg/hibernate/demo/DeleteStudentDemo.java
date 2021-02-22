package com.pbg.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pbg.hibernate.demo.entity.Student;


/* ----- Hibernate working app Demo ----- */

/* ----- CRUD - Delete Operations  ----- */


public class DeleteStudentDemo {

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
			
			// Delete Operation - Approach 1
			System.out.println("1.	Deleting Student : " + myStudent );
			//session.delete(myStudent);	//	Uncomment to check or set studentID to 3 if any null error
			
			// Delete Operation - Alternate approach 2 (Query)
			System.out.println("1.	Deleting Student ID 2 : " );
			
			session.createQuery("delete from Student where id=2").executeUpdate();
		
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
