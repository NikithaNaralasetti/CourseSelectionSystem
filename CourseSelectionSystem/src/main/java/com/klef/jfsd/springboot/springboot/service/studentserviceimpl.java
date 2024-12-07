package com.klef.jfsd.springboot.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.klef.jfsd.springboot.model.Admin;
import com.klef.jfsd.springboot.model.Courses;
import com.klef.jfsd.springboot.model.ProfilePicture;
import com.klef.jfsd.springboot.model.Register;
import com.klef.jfsd.springboot.repo.AdminRepo;
import com.klef.jfsd.springboot.repo.CoursesRepo;
import com.klef.jfsd.springboot.repo.ProfileImageRepo;
import com.klef.jfsd.springboot.repo.StudentRepo;

@Component
public class studentserviceimpl implements StudentService {

	
	@Autowired
	StudentRepo studentrepository;

	@Autowired
	ProfileImageRepo profileImageRepo;
	
	@Autowired
	CoursesRepo coursesRepo;
	
	@Autowired
	AdminRepo adminRepo;

	@Override
	public Register registrationPageDetails(Register register) {
		Register register1 = studentrepository.save(register);
		return register1;
	}

	@Override
	public Register loginPage(String id, String password) {
		Register register = studentrepository.IdOrNameAndPassword(id, id, password);
		return register;
	}

	@Override
	public ProfilePicture saveprofileImage(ProfilePicture pic) {
			ProfilePicture image =profileImageRepo.save(pic);
		 
		return image;
	}

	@Override
	public ProfilePicture getProfilepic(Long id) {
		ProfilePicture profilePicture = profileImageRepo.getById(id);
		return profilePicture;
	}

	@Override
	public List<Courses> getAllCoursesData() {
		List<Courses> listOfCourses = coursesRepo.findAll();
		return listOfCourses;
	}

	@Override
	public Admin adminLoginPage(Long id, String password) {
		Admin admin = adminRepo.IdAndPassword(id,password);
		return admin;
	}


}
