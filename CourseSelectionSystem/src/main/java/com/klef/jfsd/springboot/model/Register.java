package com.klef.jfsd.springboot.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Register {
	@Id
	private Long id;
	private String name;
	private String emailid;
	private String password;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Courses> getCourses() {
		return courses;
	}

	public void setCourses(Set<Courses> courses) {
		this.courses = courses;
	}

	private String type;
	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { 
	        CascadeType.PERSIST, 
	        CascadeType.MERGE
	    })
	    @JoinTable(
	        name = "student_courses",
	        joinColumns = { @JoinColumn(name = "student_id") },
	        inverseJoinColumns = { @JoinColumn(name = "course_id") }
	    )
	    private Set<Courses> courses = new HashSet<>();

	
    // Helper methods to manage bi-directional relationship
    public void addCourse(Courses course) {
        this.courses.add(course);
        course.getStudents().add(this);
    }

    public void removeCourse(Courses course) {
        this.courses.remove(course);
        course.getStudents().remove(this);
    }
}