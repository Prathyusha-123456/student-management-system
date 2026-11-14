package com.example.sms.repository;

import com.example.sms.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Page<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrRollNumberContainingIgnoreCase(
            String firstName, String lastName, String email, String rollNumber, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByRollNumber(String rollNumber);
}
