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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


// Update Course class to configure One-To-Many Relationship in a unidirectinal way

@Entity
@Table(name="course")
public class Course {
	
	//	Define our fields
	//	Annotate Fields
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="title")
	private String title;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="instructor_id")
	private Instructor instructor;
	//	Using cascade types fine grained to make sure that none of the either objects are removed or deleted
	
	//	Add a Collection to hold the reviews - ** One to Many Unidirectional
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)	// Set up oneToMany relationship from Course to review
	@JoinColumn(name="course_id")		//	We need to tell hibernate how to associate reviews with the course
	private List<Review> reviews;
	
	//	Define Constructors
	
	public Course() {
		
	}


	public Course(String title) {
		//super();
		this.title = title;
	}

	
	//	Define getters and setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	//	Define toString
	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + "]";
	}



	// Define getter and setters for reviews
	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	//	Add convinience method for adding reviews
	public void addReview(Review theReview) {
		if(reviews == null) {
			reviews = new ArrayList<>();
		}
		
		reviews.add(theReview);
	}
	


	

}
