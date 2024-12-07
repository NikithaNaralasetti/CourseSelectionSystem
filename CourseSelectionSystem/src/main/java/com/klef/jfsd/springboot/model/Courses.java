package com.klef.jfsd.springboot.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Courses {
	
	@Id
	private String courseid;
	private String coursename;
	private String coursecoordinator;
	private Integer seats;

	
	 public String getCourseid() {
		return courseid;
	}


	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}


	public String getCoursename() {
		return coursename;
	}


	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}


	public String getCoursecoordinator() {
		return coursecoordinator;
	}


	public void setCoursecoordinator(String coursecoordinator) {
		this.coursecoordinator = coursecoordinator;
	}


	public Integer getSeats() {
		return seats;
	}


	public void setSeats(Integer seats) {
		this.seats = seats;
	}


	public Set<Register> getStudents() {
		return students;
	}


	public void setStudents(Set<Register> students) {
		this.students = students;
	}


	@ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
	    private Set<Register> students = new HashSet<>();
}
