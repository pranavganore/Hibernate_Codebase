package com.pbg.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pbg.hibernate.demo.entity.Student;

public class PrimaryKeyDemo {
	

	// For this to work uncomment line 
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
			//	Create 3 student objects
			System.out.println("Creating 3 student objects ..");
			
			Student tempStudent1 = new Student("Aniket", "Chandak" , "ac@gmail.com");
			Student tempStudent2 = new Student("Shreyash", "Baradiya" , "sb@gmail.com");
			Student tempStudent3 = new Student("Spandan", "Gupta" , "sg@gmail.com");
			
			//	Start a Transaction
			session.beginTransaction();
			
			//	Save the student object
			System.out.println("Saving the Students .... ");
			session.save(tempStudent1);
			session.save(tempStudent2);
			session.save(tempStudent3);
			
			//	Commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done ! (Transaction committed successfully!)");
			//	This should have added a row in student table in your database - go check it!
			
		}finally {
			factory.close();
		}
	}
}

	
	

