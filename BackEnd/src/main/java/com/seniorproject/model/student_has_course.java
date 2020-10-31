package com.seniorproject.model;

import javax.persistence.*;

@Entity
@Table(name = "student_has_course")
public class student_has_course {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    Students student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Courses course;

    private String status;
    private String semester;

    public student_has_course(){

    }
    public student_has_course(String status, String semester, Students student, Courses course) {
        this.id = getId();
        this.status = status;
        this.semester = semester;
        this.student = student;
        this.course = course;
    }

    public Long getId(){return id;}

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
