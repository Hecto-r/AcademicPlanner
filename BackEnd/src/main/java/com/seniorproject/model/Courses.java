package com.seniorproject.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Courses {

	@Id
	private String course_id;

	private String title;
	private int units;
	private boolean core;
	private String description;
	private String pre_req;

	@OneToMany(mappedBy = "course")
	private Set<student_has_course> studentCourses;

	public Courses(){

	}

	public Courses(String course_id, String title, int units, boolean core, String description, String pre_req) {
		this.course_id = course_id;
		this.title = title;
		this.units = units;
		this.core = core;
		this.description = description;
		this.pre_req = pre_req;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public boolean isCore() {
		return core;
	}

	public void setCore(boolean core) {
		this.core = core;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPre_req() {
		return pre_req;
	}

	public void setPre_req(String pre_req) {
		this.pre_req = pre_req;
	}
}
