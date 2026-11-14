package com.example.sms.controller;

import com.example.sms.model.Student;
import com.example.sms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Server-rendered UI (Thymeleaf + Bootstrap). */
@Controller
@RequestMapping("/students")
public class StudentController {

    private static final int PAGE_SIZE = 5;
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public String list(@RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "0") int page, Model model) {
        Page<Student> result = service.search(keyword, page, PAGE_SIZE);
        model.addAttribute("students", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("totalItems", result.getTotalElements());
        model.addAttribute("keyword", keyword);
        return "students";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("courses", service.allCourses());
        model.addAttribute("courseId", null);
        return "student-form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Student s = service.get(id);
        model.addAttribute("student", s);
        model.addAttribute("courses", service.allCourses());
        model.addAttribute("courseId", s.getCourse() != null ? s.getCourse().getId() : null);
        return "student-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") Student student, BindingResult result,
                       @RequestParam(required = false) Long courseId,
                       Model model, RedirectAttributes redirect) {
        if (!result.hasFieldErrors("email") && !result.hasFieldErrors("rollNumber")) {
            String dup = service.checkDuplicate(student);
            if ("email".equals(dup)) result.rejectValue("email", "dup", "Email already exists");
            if ("rollNumber".equals(dup)) result.rejectValue("rollNumber", "dup", "Roll number already exists");
        }
        if (result.hasErrors()) {
            model.addAttribute("courses", service.allCourses());
            model.addAttribute("courseId", courseId);
            return "student-form";
        }
        service.save(student, courseId);
        redirect.addFlashAttribute("message", "Student saved successfully");
        return "redirect:/students";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        service.delete(id);
        redirect.addFlashAttribute("message", "Student deleted");
        return "redirect:/students";
    }
}
