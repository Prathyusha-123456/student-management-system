package com.example.sms;

import com.example.sms.model.Course;
import com.example.sms.model.Student;
import com.example.sms.repository.CourseRepository;
import com.example.sms.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class StudentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
    }

    /** Seeds sample data on first run. */
    @Bean
    CommandLineRunner seed(CourseRepository courses, StudentRepository students) {
        return args -> {
            if (courses.count() > 0) return;
            Course cse = courses.save(new Course("CSE", "Computer Science Engineering"));
            Course ece = courses.save(new Course("ECE", "Electronics & Communication"));
            courses.save(new Course("ME", "Mechanical Engineering"));

            students.save(new Student("Asha", "Rao", "asha.rao@example.com", "CSE2201",
                    LocalDate.of(2004, 3, 14), cse));
            students.save(new Student("Rahul", "Kumar", "rahul.kumar@example.com", "ECE2202",
                    LocalDate.of(2003, 11, 2), ece));
        };
    }
}
