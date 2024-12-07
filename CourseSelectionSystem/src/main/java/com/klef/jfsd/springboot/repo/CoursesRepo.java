package com.klef.jfsd.springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.klef.jfsd.springboot.model.Courses;

import jakarta.transaction.Transactional;

public interface CoursesRepo extends JpaRepository<Courses, String> {
	
	@Modifying
    @Transactional
    @Query("UPDATE Courses c SET c.seats = c.seats - 1 WHERE c.courseid = :courseid AND c.seats > 0")
    int decrementSeats(@Param("courseid") String courseid);

    @Query("SELECT c FROM Courses c WHERE c.courseid = :courseid")
    Courses findByCourseid(@Param("courseid") String courseid);
    
}
