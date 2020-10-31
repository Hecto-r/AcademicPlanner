package com.seniorproject.repository;

import com.seniorproject.model.student_has_course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface student_has_courseRepository extends JpaRepository<student_has_course, Long> {
    @Query(value = "SELECT * FROM student_has_course WHERE student_id = ?1", nativeQuery = true)
    List<student_has_course> findStudentsClasses(@Param("student_id") int student_id);
}
