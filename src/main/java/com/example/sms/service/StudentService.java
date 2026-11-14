package com.example.sms.service;

import com.example.sms.model.Course;
import com.example.sms.model.Student;
import com.example.sms.repository.CourseRepository;
import com.example.sms.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository students;
    private final CourseRepository courses;

    public StudentService(StudentRepository students, CourseRepository courses) {
        this.students = students;
        this.courses = courses;
    }

    public Page<Student> search(String keyword, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (keyword == null || keyword.isBlank()) {
            return students.findAll(pageable);
        }
        String k = keyword.trim();
        return students
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrRollNumberContainingIgnoreCase(
                        k, k, k, k, pageable);
    }

    public Student get(Long id) {
        return students.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found: " + id));
    }

    public List<Course> allCourses() {
        return courses.findAll();
    }

    /** Returns "email" or "rollNumber" if already used by another student, else null. */
    public String checkDuplicate(Student s) {
        if (s.getId() == null) {
            if (students.existsByEmail(s.getEmail())) return "email";
            if (students.existsByRollNumber(s.getRollNumber())) return "rollNumber";
        } else {
            Student existing = get(s.getId());
            if (!existing.getEmail().equalsIgnoreCase(s.getEmail()) && students.existsByEmail(s.getEmail())) return "email";
            if (!existing.getRollNumber().equals(s.getRollNumber()) && students.existsByRollNumber(s.getRollNumber())) return "rollNumber";
        }
        return null;
    }

    public Student save(Student student, Long courseId) {
        student.setCourse(courseId != null ? courses.findById(courseId).orElse(null) : null);
        return students.save(student);
    }

    public Student update(Long id, Student incoming) {
        Student s = get(id);
        s.setFirstName(incoming.getFirstName());
        s.setLastName(incoming.getLastName());
        s.setEmail(incoming.getEmail());
        s.setRollNumber(incoming.getRollNumber());
        s.setDateOfBirth(incoming.getDateOfBirth());
        if (incoming.getCourse() != null && incoming.getCourse().getId() != null) {
            s.setCourse(courses.findById(incoming.getCourse().getId()).orElse(null));
        }
        return students.save(s);
    }

    public void delete(Long id) {
        students.delete(get(id));
    }
}
