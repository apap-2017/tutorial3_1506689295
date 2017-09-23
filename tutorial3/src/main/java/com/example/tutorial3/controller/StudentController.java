package com.example.tutorial3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.tutorial3.service.InMemoryStudentService;
import com.example.tutorial3.service.StudentService;
import java.util.List;
import java.util.Optional;

import com.example.tutorial3.model.StudentModel;

@Controller
public class StudentController {
	private final StudentService studentService;
	
	public StudentController()
	{
		studentService = new InMemoryStudentService();
	}
	
	@RequestMapping("/student/add")
	public String add(@RequestParam(value = "npm", required = true) String npm,
						@RequestParam(value = "name", required = true) String name,
						@RequestParam(value = "gpa", required = true) double gpa)
	{
		StudentModel student = new StudentModel(npm, name, gpa);
		studentService.addStudent(student);
		return "add";
	}
	
	@RequestMapping(value = {"/student/delete", "/student/delete/{npm}"})
	public String detele(@PathVariable Optional<String> npm, Model model)
	{
		if(npm.isPresent()) {
			StudentModel student = studentService.selectStudent(npm.get());
			if(student == null) {
				return "viewerror";
			}else {
				studentService.deleteStudent(student);
				return "deleted";
			}
		}else {
			return "viewerror";
		}
	}
	
	@RequestMapping("/student/viewp")
	public String view(Model model,
					   @RequestParam(value = "npm", required = true) String npm)
	{
		StudentModel student = studentService.selectStudent(npm);
		model.addAttribute("student", student);
		return "view";
	}
	
	@RequestMapping(value = {"/student/view", "/student/view/{npm}"})
	public String viewPath(@PathVariable Optional<String> npm, Model model)
	{
		if(npm.isPresent()) {
			StudentModel student = studentService.selectStudent(npm.get());
			if(student == null) {
				return "viewerror";
			}else {
				model.addAttribute("student", student);
				return "view";
			}
		}else {
			return "viewerror";
		}
	}
	
	@RequestMapping("/student/viewall")
	public String viewAll(Model model)
	{
		List<StudentModel> students = studentService.selectAllStudents();
		model.addAttribute("students", students);
		return "viewall";
	}

}
