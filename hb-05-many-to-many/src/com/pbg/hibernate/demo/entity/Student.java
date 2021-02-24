package com.pbg.hibernate.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/*----- Update the inverse side for Many-To-Many relationship -----*/

							//	Refer Note 2 & Note 3 Below
@Entity						// Annotation to denote that this is an Entity Class
@Table(name="student")		// Maps this Class(Entity- def see below) to the Database table
public class Student {

	@Id						// specifies that this filed will be a PRIMARY KEY
	@GeneratedValue(strategy=GenerationType.IDENTITY)	//	Optional (But Required for PrimaryKeyDemo.java) Refer to Note 5
	@Column(name="id")		// Maps this field to actual Database Column
	private int id;
	
	@Column(name="first_name")	
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")	// No need to specify this annotation if the col_name & variabe name are kept exactly same
	private String email;
	
	
	//	** Add a Collection to hold the Courses - ** and configure Many to Many relationship
	@ManyToMany(fetch=FetchType.LAZY, 
			cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(name="course_student",		// tell hibernate about the join column
				joinColumns=@JoinColumn(name="student_id"),		//	inverse the name from Courses.java
				inverseJoinColumns=@JoinColumn(name="course_id"))	//	inverse the values from Courses.java
	private List<Course> courses;
	
	
	// Define a no-arg constructor
	public Student() {
		
	}
	
	
	public Student(String firstName, String lastName, String email) {
		//super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}


//	Generate getters & setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	// generate getters and setters for courses
	
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}


	
	
	//	Add a toString method for debugging purposes
	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}





}

 
/*	Note 1:
 * 
 * Hibernate Development Process Overview :		(Slide 180 UDEMY Chad Darby)
 * 
 * 	1.	Add Hibernate Configuration file - 'hibernate.cfg.xml'
 * 
 * 	2.	Annotate Java Class		<- Done here
 * 
 * 	3.	Develop Dava code to perfoem database operations
 * 
 * */

/*	Note 2:
 * 
 * Object-to-Relational Mapping
 * 		i.e. We need to tell hibernate how to Map a Java class to Database Table , and it's fields to database's 
 * 			actual columns.
 * 
 * There are two options for mapping:
 * 	1.	XML Config file (Legacy approach)
 * 
 * 	2.	Java Annotations	(Modern and preffered approach covered here)
 * 		
 * 		Step 1: Map Class to database Table (using '@Entity' & '@Table' annotation)
 * 		Step 2: Map Fields to database Columns (using '@Column' annotation)
 * 
 */

/*	Note 3:
 * 
 * 	Entity class : Java class that is mapped to the database
 *	
 *	Annotating a class as @Entity - tells Hibernate that this is the Entity Class that we are mapping to a DB_Table
 * 
 * */


/*
 * Note 4 : FAQ: Why we are using JPA Annotation instead of Hibernate ?
 * 
 * QUESTION: Why we are using JPA Annotation instead of Hibernate ?
 * 				For example, why we are not using this org.hibernate.annotations.Entity?
 * 
 * ANSWER:	
 * 			JPA is a standard specification. Hibernate is an implementation of the JPA specification.
 * 			Hibernate implements all of the JPA annotations.
 * 			The Hibernate team recommends the use of JPA annotations as a best practice.
 * */

/*
 * 	Note 5 :	@GeneratedValue(strategy=GenerationType.IDENTITY)
 * 		Using this annotation we can actually explicitly tell Hibernate to leave it to mySQL 
 * 			how to generate this primary key, i.e. which strategy to use
 * 
 * 		There are different types of strategies like : 
 * 		GenerationType.AUTO     - Picks an automatic strategy for the particular database,
 * 		GenerationType.IDENTITY - Assigns primary keys using database identity column,
 * 		GenerationType.SEQUENCE - Assigns primary key using a database sequence,
 * 		GenerationType.TABLE    - Assigns primary key using an underlying database table to ensure uniqueness 
 * 
 * FAQ :  How to change this AUTO_INCREMENT values to start from custom counter ?
 * Answer :
 * 			Step 1 : in your mySQL - Run this query to start the counter from say 100 : 
 * 					mysql> ALTER TABLE hb_student_tracker.student AUTO_INCREMENT=100;
 * 					Query OK, 0 rows affected (0.01 sec)
 * 					Records: 0  Duplicates: 0  Warnings: 0
 * 			Step 2 : Try adding more rows from java code, you will see new entries will have ID starting from 100
 * 
 * FAQ : How do I reset the counter of AUTO_INCREMENT values to say 1?
 * Answer :
 * 			Step 1 : Truncate your table (i.e. remove all the rows)
 * 					mysql> truncate hb_student_tracker.student;
 * 					Query OK, 0 rows affected (0.01 sec)
 * 			Step 2 :Try adding the rows again from code , you will see the AUTO_INCREMENT counter is reset from 1;
 * 
 */
 