package com.seniorproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.seniorproject.model.Courses;


public interface CourseRepository extends JpaRepository<Courses, Integer> {

}
