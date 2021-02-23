package com.pbg.hibernate.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/* ----- Hibernate Advanced Mapping - One-to-Many Mapping demo app  ----- */
/*------ Fetch Type - Eager vs Lazy ------*/

//Annotate the class as an entity and map to database table
@Entity
@Table(name="instructor")
public class Instructor {


	//	Define the fields
	//	Annotate the fields with database column names
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;


	//	** Setup mapping to InstructorDetail Entity
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="instructor_detail_id")
	private InstructorDetail instructorDetail;
	
	//	Create a collection to hold multiple courses associated. **One-to-Many
//	@OneToMany(mappedBy="instructor",	//	mappedBby Refers to "instructor" property in "course" class
//			cascade= {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	//	Add/ Override the Fetch type
	@OneToMany(fetch=FetchType.LAZY,	//	Setting it LAZY or EAGER Refer Note below V.IMP 
			mappedBy="instructor",		//	mappedBby Refers to "instructor" property in "course" class
			cascade= {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	private List<Course> courses;
	
	
	//	Create constructors
	public Instructor() {
		
	}

	public Instructor(String firstName, String lastName, String email/*, InstructorDetail instructorDetail*/) {
		//super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		//this.instructorDetail = instructorDetail;
	}
	
	//	Generate Getter/Setter methods

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

	public InstructorDetail getInstructorDetail() {
		return instructorDetail;
	}

	public void setInstructorDetail(InstructorDetail instructorDetail) {
		this.instructorDetail = instructorDetail;
	}

	// Generate getter and setter for the Course
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	
	//	Generate toString() Method
	
	@Override
	public String toString() {
		return "Instructor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", instructorDetail=" + instructorDetail + "]";
	}


	// Add a convenience method for bi-directional relationship
	
	public void add(Course tempCourse) {
		
		if(courses == null) {
			courses = new ArrayList<>();
		}
		
		courses.add(tempCourse);
		
		tempCourse.setInstructor(this);
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