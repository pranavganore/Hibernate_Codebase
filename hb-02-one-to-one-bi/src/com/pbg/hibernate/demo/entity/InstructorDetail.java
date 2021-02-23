package com.pbg.hibernate.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/*----- Hibernate advanced mappings - 'Bidirectional' One-to-One Mappings Demo -----*/

//	Annotate the class as an entity and map to database table
@Entity
@Table(name="instructor_detail")
public class InstructorDetail {
	
	//	Define the fields
	//	Annotate the fields with database column names

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="youtube_channel")
	private String youtubeChannel;
	
	@Column(name="hobby")
	private String hobby;
	
	
	//	Create constructors

	public InstructorDetail() {
		
	}

	public InstructorDetail(String youtubeChannel, String hobby) {
		//super();
		this.youtubeChannel = youtubeChannel;
		this.hobby = hobby;
	}

	
	
	//	Generate Getter/Setter methods

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYoutubeChannel() {
		return youtubeChannel;
	}

	public void setYoutubeChannel(String youtubeChannel) {
		this.youtubeChannel = youtubeChannel;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	//	Generate toString() Method
	
	@Override
	public String toString() {
		return "InstructorDetail [id=" + id + ", youtubeChannel=" + youtubeChannel + ", hobby=" + hobby + "]";
	}
	
	
	
	/* ----- Code for Bidirectional One-To-One Mapping ----- */
	
	//	Add New field for instructor (also need to add getters and setters)
	//	Add @OneToOne Annotation
	
	@OneToOne(mappedBy="instructorDetail", cascade=CascadeType.ALL)	// See Note 1 below IMP
	private Instructor instructor; 
	
	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

}


/* 
 *	Note 1 :  Disabling cascade.ALL in order not to delete the Instructor when we delete InstructorDemo
 *	How to do it - > 
 *
 * 	Step 1 : In order to achieve this simply manage the cascade in a fine grained manner instead of allowing ALL 
 * 		cascades.  
 * 	:: Use this code on line 88-89 (replace)
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
