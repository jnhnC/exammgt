package com.freewheelin.exammgt.repository;

import com.freewheelin.exammgt.domian.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject,Long> {
    Optional<Subject> findBySubjectName(String subjectName);
}
