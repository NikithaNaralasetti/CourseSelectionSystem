package com.klef.jfsd.springboot.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.klef.jfsd.springboot.model.Admin;
import com.klef.jfsd.springboot.model.Courses;
import com.klef.jfsd.springboot.model.ProfilePicture;
import com.klef.jfsd.springboot.model.Register;
import com.klef.jfsd.springboot.repo.CoursesRepo;
import com.klef.jfsd.springboot.repo.StudentRepo;
import com.klef.jfsd.springboot.springboot.service.StudentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class SController {

	@Autowired
	StudentService service;

	@Autowired
	CoursesRepo coursesRepo;

	@Autowired
	StudentRepo studentRepo;

	HttpSession session;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/admin")
	public String adminLogin() {
		return "admin";
	}
	
	@GetMapping("/addnewcourse")
	public String showAddNewCoursesPage() {
	    return "addnewcourse"; // Maps to addnewcourses.html in templates
	}

	@PostMapping("/admindetails")
	public String adminDetailsPage(@RequestParam("id") String id, @RequestParam("password") String password,
			Model model, HttpServletRequest request) {
		System.out.println(id);
		System.out.println(password);
		Admin admin = service.adminLoginPage(Long.valueOf(id), password);
		if (admin == null) {
			model.addAttribute("message", "Invalid details");
			return "admin";
		} else {
			model.addAttribute("name", "Admin");
			return "admindashboard";
		}
	}
	@GetMapping("/admindashboard")
	public String admindashboardPage() {
		return "admindashboard";
	}
	
	@PostMapping("/addnewcourses")
	public String addNewCoursesPage(Model model, HttpServletRequest request) {
		
		return "";
	}
	
	
	@GetMapping("/register")
	public String registerpage() {
		return "register";
	}
	@GetMapping("/Timetable")
	public String Timetable() {
		return "Timetable";
	}

	@GetMapping("/loginpage")
	public String LoginPage() {
		return "Loginpage";
	}

	@GetMapping("/Dashboard")
	public String Dashboard() {
		return "Dashboard";
	}

	@GetMapping("/forgetpassword")
	public String forgetPassword() {
		return "forgetpassword";
	}
	@GetMapping("/java-full-stack")
    public String showJavaFullStack() {
        return "java-full-stack"; // This maps to src/main/resources/templates/java-full-stack.html
    }
	@GetMapping("/enterprise-programming")
    public String showEnterpriseProgramming() {
        return "enterprise-programming"; // This maps to src/main/resources/templates/enterprise-programming.html
    }

    // Mapping for the Introduction to Blockchain course detail
    @GetMapping("/introduction-to-blockchain")
    public String showIntroductionToBlockchain() {
        return "introduction-to-blockchain"; // This maps to src/main/resources/templates/introduction-to-blockchain.html
    }

	@PostMapping("/dashboard")
	public String dashboardmapping(HttpServletRequest request, Model model) {
		model.addAttribute("name", request.getSession().getAttribute("name"));
		model.addAttribute("id", request.getSession().getAttribute("id"));
		model.addAttribute("image", request.getSession().getAttribute("image"));
		return "Dashboard";
	}

	@PostMapping("/userdetails")
	public String registerDetails(@ModelAttribute Register register,
			@RequestParam(name = "profilepic") MultipartFile profilePicture) {
		System.out.println(register.toString());
		if (register != null && profilePicture != null) {
			Register register2 = service.registrationPageDetails(register);
			long sutdentId = register2.getId();
			System.out.println(sutdentId);
			try {
				byte[] imageData = profilePicture.getBytes();
				ProfilePicture pic = new ProfilePicture();
				pic.setProfileid(sutdentId);
				pic.setProfileimage(imageData);
				ProfilePicture profileimage = service.saveprofileImage(pic);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("object saved successfully");
			return "Loginpage";
		} else {
			return "index";
		}
	}

	@PostMapping("/login")
	public String LoginDetails(@RequestParam String id, @RequestParam String password, Model model,
			HttpServletRequest request) {
		Register register = service.loginPage(id, password);
		if (register == null) {
			model.addAttribute("message", "Invalid details");
			return "Loginpage";
		} else {
			session = request.getSession();
			session.setAttribute("name", register.getName());
			session.setAttribute("id", register.getId());
			session.setAttribute("password", register.getPassword());
			session.setAttribute("email", register.getEmailid());
			session.setAttribute("type", register.getType());
			session.setAttribute("imageUrl", "/image/" + id);

			// Pass user data to the model
			model.addAttribute("name", register.getName());
			model.addAttribute("id", register.getId());
			model.addAttribute("imageUrl", "/image/" + id); // Use URL for image
			if (register.getType().equalsIgnoreCase("student")) {
				return "Dashboard";
			}
			return "Facultydashboard";
		}
	}

	@GetMapping("/image/{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
		Optional<ProfilePicture> picture = Optional.ofNullable(service.getProfilepic(id));

		if (picture.isPresent()) {
			byte[] image = picture.get().getProfileimage();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);
			return new ResponseEntity<>(image, headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/profile")
	public void ProfilePage(HttpServletRequest request, Model model) {
		model.addAttribute("name", request.getSession().getAttribute("name"));
		model.addAttribute("id", request.getSession().getAttribute("id"));
		model.addAttribute("email", request.getSession().getAttribute("email"));
		model.addAttribute("password", request.getSession().getAttribute("password"));
		model.addAttribute("type", request.getSession().getAttribute("type"));
		model.addAttribute("image", request.getSession().getAttribute("image"));
		System.out.println(request.getSession().getAttribute("name"));
	}

	@PostMapping("/courses")
	public void getCoursesInCoursePage(HttpServletRequest request, Model model) {
		List<Courses> listOfCourses = service.getAllCoursesData();
		if (!listOfCourses.isEmpty()) {
			model.addAttribute("listofcourses", listOfCourses);
		} else {
			model.addAttribute("nodata", "No Data Found");
		}
		model.addAttribute("listofcourses", listOfCourses);
	}

	@PostMapping("/courseregistration")
	public void getCoursesInCourseregistrationPage(HttpServletRequest request, Model model) {
		List<Courses> listOfCourses = service.getAllCoursesData();
		if (!listOfCourses.isEmpty()) {
			model.addAttribute("listofcourses", listOfCourses);
		} else {
			model.addAttribute("nodata", "No Data Found");
		}
		model.addAttribute("listofcourses", listOfCourses);
	}

	@PostMapping("/registeredcourses")
	@Transactional
	public String registerSelectedCourses(
	    @RequestParam("courseids") List<String> courseIds,
	    HttpServletRequest request,
	    Model model) {

	    // Get the student ID from the session
	    Long studentId = (Long) request.getSession().getAttribute("id");

	    // Fetch the student from the database
	    Register student = studentRepo.findById(studentId)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + studentId));

	    for (String courseId : courseIds) {
	        // Find the course by courseId
	        Courses course = coursesRepo.findByCourseid(courseId);

	        if (course != null) {
	            // Check if the student is already registered for the course
	            if (student.getCourses().contains(course)) {
	                model.addAttribute("message", "You are already registered for course: " + courseId);
	                return "courseregisteredsuccess"; // Return an error page if already registered
	            }

	            // Check if seats are available
	            if (course.getSeats() > 0) {
	                // Register the student for the course
	                student.addCourse(course);

	                // Decrement seat count
	                course.setSeats(course.getSeats() - 1);
	                coursesRepo.save(course);
	            } else {
	                model.addAttribute("message", "No seats available for course: " + courseId);
	                return "courseregisteredsuccess"; // Return an error page if no seats are available
	            }
	        } else {
	            model.addAttribute("message", "Course not found: " + courseId);
	            return "courseregisteredsuccess"; // Return an error page if course is not found
	        }
	    }
	    

	    // Save the updated student entity
	    studentRepo.save(student);
	    

	    model.addAttribute("message", "Courses registered successfully!");
	    return "courseregisteredsuccess";
	}


}
