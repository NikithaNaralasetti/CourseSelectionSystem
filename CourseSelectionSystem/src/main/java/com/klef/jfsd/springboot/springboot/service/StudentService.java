package com.klef.jfsd.springboot.springboot.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.klef.jfsd.springboot.model.Admin;
import com.klef.jfsd.springboot.model.Courses;
import com.klef.jfsd.springboot.model.ProfilePicture;
import com.klef.jfsd.springboot.model.Register;

@Service
public interface StudentService {

	Register registrationPageDetails(Register register);
	
	Register loginPage(String id, String password);

	ProfilePicture saveprofileImage(ProfilePicture pic);

	ProfilePicture getProfilepic(Long id);
	
	List<Courses> getAllCoursesData();

	Admin adminLoginPage(Long id, String password);


	
}
