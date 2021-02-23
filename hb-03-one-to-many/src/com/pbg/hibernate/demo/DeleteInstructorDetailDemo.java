package com.pbg.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.pbg.hibernate.demo.entity.Instructor;
import com.pbg.hibernate.demo.entity.InstructorDetail;
import com.pbg.hibernate.demo.entity.Student;


/* ----- Hibernate Advanced Mapping - 'Bidirectional 'One-to-One Mapping demo app  ----- */

/* ----- Delete the Instructor Obj using Instructor_Detail Obj in a Bidirectional Manner ----- */


public class DeleteInstructorDetailDemo {

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
			
			//	Now let's delete the instructor details :
			System.out.println("\n\nDeleteing tempInstructorDetail (Bidirectional) : " + tempInstructorDetail);
			
			//	Refer to note 2 below IMP if you want to reove the association
			
			
			session.delete(tempInstructorDetail);
			
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

/* 
 *	Note 2 :  Disabling cascade.ALL in order not to delete the Instructor when we delete InstructorDemo
 *	How to do it - > 
 *
 * 	Step 1 : In order to achieve this simply manage the cascade in a fine grained manner instead of allowing ALL 
 * 		cascades. in InstructorDetail.java class 
 * 	:: Use this code on line 88-89 in InstructorDetail.java class (replace)
 * 	
 * 	@OneToOne(mappedBy="instructorDetail", cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH} )	// See Note 1 below IMP
 * 	private Instructor instructor; 
 * 
 * 	Step 2 : Remove the association i.e Break the bidirectional Link in your main app ('DeleteInstructorDetailDemo.java')
 * 				(Refer to slide 222 & 223 UDEMY Chad Darby for details)
 * 	:: Add this code before you do session.delete();(add on line 53) in your main app ('DeleteInstructorDetailDemo.java')
 * 	//	Remove the Associated object reference
 *	//	Break the Bi-directional link
 *	tempInstructorDetail.getInstructor().setInstructorDetail(null);
 *
 * */
