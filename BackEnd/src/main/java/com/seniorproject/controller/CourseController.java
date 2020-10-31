package com.seniorproject.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.seniorproject.model.student_has_course;
import com.seniorproject.payload.request.LoginRequest;
import com.seniorproject.payload.request.SignupRequest;
import com.seniorproject.payload.response.JwtResponse;
import com.seniorproject.payload.response.MessageResponse;
import com.seniorproject.repository.student_has_courseRepository;
import com.seniorproject.model.Courses;
import com.seniorproject.model.Students;
import com.seniorproject.security.jwt.JwtUtils;
import com.seniorproject.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class CourseController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	com.seniorproject.repository.CourseRepository CourseRepository;
	@Autowired
	com.seniorproject.repository.StudentRepository StudentRepository;
	@Autowired
	student_has_courseRepository shcRepository;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				userDetails.getEmail(),
				userDetails.getFirstName(),
				userDetails.getLastName()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (StudentRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (StudentRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		Students user = new Students(signUpRequest.getId(),signUpRequest.getUsername(),
				signUpRequest.getEmail(), signUpRequest.getFirstName(), signUpRequest.getLastName(),
				encoder.encode(signUpRequest.getPassword()));

		StudentRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@GetMapping("/courses")
	public ResponseEntity<List<Courses>> getAllCourses() {
		try {
			List<Courses> courses = new ArrayList<Courses>();

			CourseRepository.findAll().forEach(courses::add);

			if (courses.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(courses, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/courses/{id}")
	public ResponseEntity<Courses> getCoursesById(@PathVariable("id") int id) {
		Optional<Courses> courseData = CourseRepository.findById(id);

		if (courseData.isPresent()) {
			return new ResponseEntity<>(courseData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/courses")
	public ResponseEntity<Courses> createCourses(@RequestBody Courses course) {
		try {
			Courses _course = CourseRepository
					.save(new Courses(course.getCourse_id(), course.getTitle(), course.getUnits(), course.isCore(), course.getDescription(), course.getPre_req()));
			return new ResponseEntity<>(_course, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/courses/{id}")
	public ResponseEntity<Courses> updateCourse(@PathVariable("id") int id, @RequestBody Courses course) {
		Optional<Courses> courseData = CourseRepository.findById(id);

		if (courseData.isPresent()) {
			Courses _course = courseData.get();
			_course.setCourse_id(course.getCourse_id());
			_course.setTitle(course.getTitle());
			_course.setDescription(course.getDescription());
			_course.setPre_req(course.getPre_req());
			return new ResponseEntity<>(CourseRepository.save(_course), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/courses/{id}")
	public ResponseEntity<HttpStatus> deleteCourses(@PathVariable("id") int id) {
		try {
			CourseRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// STUDENT API

	@GetMapping("/students")
	public ResponseEntity<List<Students>> getAllStudents() {
		try {
			List<Students> students = new ArrayList<Students>();

			StudentRepository.findAll().forEach(students::add);

			if (students.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(students, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Students> updateStudent(@PathVariable("id") int id, @RequestBody Students student) {
		Optional<Students> studentData = StudentRepository.findById(id);

		if (studentData.isPresent()) {
			Students _student = studentData.get();
			_student.setStudent_id(student.getStudent_id());
			_student.setEmail(student.getEmail());
			_student.setFirstname(student.getFirstname());
			_student.setLastname(student.getLastname());
			return new ResponseEntity<>(StudentRepository.save(_student), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// STUDENT HAS COURSE

	@GetMapping("/SHC")
	public ResponseEntity<List<student_has_course>> getAllSHC() {
		try {
			List<student_has_course> SHC = new ArrayList<student_has_course>();

			shcRepository.findAll().forEach(SHC::add);

			if (SHC.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(SHC, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/findSHC/{student_id}")
	public List<student_has_course> findStudentsClasses(@PathVariable("student_id") int student_id) {
		List<student_has_course> SHC = new ArrayList<>();
		shcRepository.findStudentsClasses(student_id).forEach(SHC::add);

		return SHC;
	}

	@PostMapping("/SHC")
	public ResponseEntity<student_has_course> addSHC(@RequestBody student_has_course student_has_course) {
		try {
			student_has_course _SHC = shcRepository
					.save(new student_has_course(student_has_course.getStatus(),student_has_course.getSemester(),student_has_course.getStudent(),student_has_course.getCourse()));
			return new ResponseEntity<>(_SHC, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updateSHC/{id}")
	public ResponseEntity<student_has_course> updateSHC(@PathVariable("id") long id, @RequestBody student_has_course student_has_course) {
		Optional<student_has_course> courseData = shcRepository.findById(id);

		if (courseData.isPresent()) {
			student_has_course _SHC = courseData.get();
			_SHC.setStatus(student_has_course.getStatus());
			_SHC.setSemester(student_has_course.getSemester());
			_SHC.setStudent(student_has_course.getStudent());
			_SHC.setCourse(student_has_course.getCourse());
			return new ResponseEntity<>(shcRepository.save(_SHC), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/build/{id}")
	public ResponseEntity<student_has_course> buildSchedule(@PathVariable("id") int id) {
		List<Courses> courses = new ArrayList<Courses>();
		List<Courses> onHold = new ArrayList<>();
		List<String> currentSemester = new ArrayList<>();
		HashSet<String> enrolled = new HashSet<>();
		CourseRepository.findAll().forEach(courses::add);
		Optional<Students> studentData = StudentRepository.findById(id);

		courses.sort(Comparator.comparing(o -> o.getCourse_id().substring(3, 6)));
		int counter = 0;
		String timeStamp = new SimpleDateFormat("yyyy").format(new Date());
		String semesterWYear = "Fall " + timeStamp;
		timeStamp = Integer.toString(Integer.parseInt(timeStamp)+1);
		int semesterCount = 1;

		//System.out.println(semesterWYear);
		for(int i = 0; i < courses.size(); i++) {
			boolean flag = false;
			boolean secondCheck = false;

			// ADD ELECTIVES!
			if(!courses.get(i).isCore()){
				student_has_course _SHC = shcRepository
						.save(new student_has_course("Electives", "Electives",studentData.get(), courses.get(i)));
				continue;
			}

			String[] values = courses.get(i).getPre_req().split("\\s*,\\s*");
			for(int j = 0;j<values.length;j++){
				if(values[j].equals("0")){
					break;
				}
				if(!enrolled.contains(values[j])){
					// PRE REQ NOT MET SO WE CANT ADD COURSE
					flag = true;
					onHold.add(courses.get(i));
					break;
				}
			}

			if(onHold.size()>0) {
				String[] secondValues = onHold.get(0).getPre_req().split("\\s*,\\s*");
				for (int j = 0; j < secondValues.length; j++) {
					if (secondValues[j].equals("0")) {
						break;
					}
					if (!enrolled.contains(secondValues[j])) {
						// PRE REQ NOT MET SO WE CANT ADD COURSE
						secondCheck = true;
					}
				}
				if (!secondCheck) {
					currentSemester.add(onHold.get(0).getCourse_id());
					//System.out.println(onHold.get(0).getCourse_id());
					counter += onHold.get(0).getUnits();
					student_has_course _SHC = shcRepository
							.save(new student_has_course(semesterWYear, semesterWYear,studentData.get(), onHold.get(0)));
					onHold.remove(0);
				}
			}


			// pre-req not met
			if(flag){
				continue;
			}

			currentSemester.add(courses.get(i).getCourse_id());
			counter += courses.get(i).getUnits();
			//System.out.println(courses.get(i).getCourse_id());
			student_has_course _SHC = shcRepository
						.save(new student_has_course(semesterWYear, semesterWYear,studentData.get(), courses.get(i)));
			if(counter >= 8){
				if(semesterCount == 1){
					semesterWYear = "Spring " +timeStamp;
					//System.out.println("Spring "+timeStamp);
					semesterCount = 0;
				} else {
					semesterWYear = "Fall " + timeStamp;
					//System.out.println("Fall "+timeStamp);
					semesterCount++;
					timeStamp = Integer.toString(Integer.parseInt(timeStamp)+1);
				}
				for(int k = 0;k<currentSemester.size();k++){
					enrolled.add(currentSemester.get(k));
				}
				currentSemester = new ArrayList<>();
				counter = 0;
			}
		}
		//System.out.println(onHold.get(0).getCourse_id());
		student_has_course _SHC = shcRepository
				.save(new student_has_course(semesterWYear, semesterWYear,studentData.get(), onHold.get(0)));


		if (studentData.isPresent()) {
			return new ResponseEntity<student_has_course>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
