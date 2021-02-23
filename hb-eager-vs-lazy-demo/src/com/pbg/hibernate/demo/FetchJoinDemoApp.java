package com.pbg.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.pbg.hibernate.demo.entity.Course;
import com.pbg.hibernate.demo.entity.Instructor;
import com.pbg.hibernate.demo.entity.InstructorDetail;


/* ----- Hibernate Advanced Mapping - One-to-Many Mapping demo app  ----- */

/* ----- Loading Data using HQL queries ----- */


public class FetchJoinDemoApp {

	public static void main(String[] args) {
		
		//	Create Session Factory		(-	Refer Note 1 below)
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")		// Now not needed to specify the file name here if you give this default file name
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)
								.buildSessionFactory();
		
		
		//	Create Session
		Session session = factory.getCurrentSession();

		
		try {
			
			//	Start a Transaction
			session.beginTransaction();
			
			// **	Option 2 : Hibernate Query with HQL
			
			//	Get the instructor from the database
			int theId = 1;
			
			//	Create the Query (HQL)
			Query<Instructor> query = session.createQuery("select i from Instructor i "
										+ "JOIN FETCH i.courses "
										+ "where i.id=:tehInstructorId",
										Instructor.class);
			
			//	Set parameter on the Query
			query.setParameter("tehInstructorId", theId);
			
			//	Execute query and get Instructor
			Instructor tempInstructor = query.getSingleResult();
			
			System.out.println("PBG : Instructor : " + tempInstructor);
			
			
			//	Commit transaction
			session.getTransaction().commit();
			System.out.println("PBG : Transaction committed successfully!");
			
			//	Closing the session early
			session.close();
			System.out.println("\nPBG : Session is now CLOSED Succesfully !!\n");
			
			
			//	Get Courses for the Instructor
			System.out.println("PBG : Courses : " + tempInstructor.getCourses());
				// Now this should work even in the FetchType.LAZY as we are using HQL
			
			System.out.println("PBG : Done ! ");
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
 * 	Fetch Types : EAGER vs LAZY laoding
 * 	
 * 	When we fetch / retrieve data , should we retrieve EVERYTHING ? 
 * 	+	'EAGER' 
 * 		Will Retrieve everything(all dependent entites) at once 
 * 		Can be gruesomely slowing the apps when fetching large data, and turn out to be a performace nightmare
 * 				NOT GOOD always
 * 
 * BEST PRACTICE IN INDUSTRY : "ONLY LOAD DATA WHEN ABSOLUTELY NEEDED" - thus prefer LAZY loading over EAGER loading
 * 	
 * 	+	LAZY will retrieve data only on request (Recommended way for making the FETCH requests)
 * 		Will load the main entity first, and then load the dependent entities later ON DEMAND(lazy)
 * 		Thus LAZY loading is a PREFFERED method.
 * 
 * ** Defaults for different types of the relationships
 * 
 * 	MAPPING				DEFAULT FETCH TYPES
 * @OneToOne				FetchType.EAGER
 * @OneToMany				FetchType.LAZY
 * @ManyToOne				FetchType.EAGER
 * @ManyToMany				FetchType.LAZY
 * 
 * All of these can be overridden (as shown on line 53 of Instructor.Java Class)
 * 
 * 	
 */
