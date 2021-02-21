package com.pbg.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pbg.hibernate.demo.entity.Student;


/* ----- Hibernate working app Demo ----- */

/* ----- Hibernate Query Language HQL -----*/
/* ----- Querying Objects with Hibernate -----*/



public class QueryStudentDemo {

	public static void main(String[] args) {
		
		//	Create Session Factory		(-	Refer Note 1 below)
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")		// Now not needed to specify the file name here if you give this default file name
								.addAnnotatedClass(Student.class)
								.buildSessionFactory();
		
		
		//	Create Session
		Session session = factory.getCurrentSession();

		
		try {
			
			//	Start a Transaction
			session.beginTransaction();
			
			//	1.	Query Students
			List<Student> theStudents = session.createQuery("from Student").list();	//use getResultList() for ver 5.2+
														//	Object name not the Table name
			
			//	Display All Students
			System.out.println("1.	Displaying all students : ");
			displayStudents(theStudents);
			
			
			// 2.	Query Students : whose lastname = 'ganore'
			theStudents = session.createQuery("from Student s where s.lastName='ganore'").getResultList();
			
			//	Display All Students :  whose last name is 'Ganore'
			System.out.println("\n2.	Displaying all students : whose last name is Ganore :  ");
			displayStudents(theStudents);
			
			//	3.	Query Students : lastName = 'ganore' OR firstName = 'Aniket'
			theStudents = session.createQuery("from Student s where" 
							+ " s.lastName='ganore' OR s.firstName='Aniket'").getResultList();
			
			//	Display All Students :  whose last name is 'Ganore' OR firstName is 'Aniket'
			System.out.println("\n3.	Displaying all students : whose last name is Ganore or first name Aniket:  ");
			displayStudents(theStudents);
			
			//	4.	Query Students : where email LIKE '%gmail.com' '
			theStudents = session.createQuery("from Student s where" 
							+ " s.email LIKE '%gmail.com'").getResultList();
			
			//	Display All Students :  whose last name is 'Ganore' OR firstName is 'Aniket'
			System.out.println("\n4.	Displaying all students : whose email ends with gmail.com :  ");
			displayStudents(theStudents);
			
			//	Commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done ! (Transaction committed successfully!)");
			//	This should have added a row in student table in your database - go check it!
			
		}finally {
			factory.close();
		}
	}

	private static void displayStudents(List<Student> theStudents) {
		for (Student tempStudent : theStudents) {
			System.out.println(tempStudent);
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
