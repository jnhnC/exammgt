package com.freewheelin.exammgt.service;


import com.freewheelin.exammgt.domian.Subject;
import com.freewheelin.exammgt.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectSerivce {

    private final SubjectRepository subjectRepository;


    @Transactional
    public Subject save(Subject subject) {
        validationDuplicationMember(subject);
        return subjectRepository.save(subject);
    }

    @Transactional
    public void update(Long id, String subjectName) {
        try{
            Optional<Subject> subject = subjectRepository.findById(id);
            subject.get().updateSubjectName(subjectName);
        }catch (Exception e){
            throw new NoSuchElementException("등록되지 않은 과목");
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            subjectRepository.deleteById(id);
        }catch (Exception e){
            throw new NoSuchElementException("등록되지 않은 과목");
        }

    }

    public Page<Subject> findAll(Pageable pageable) {
        return  subjectRepository.findAll(pageable);
    }

    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }

    public Subject findBySubjectName(String subjectName) {
        try {
            return subjectRepository.findBySubjectName(subjectName).get();
        }catch (Exception e){
            throw new NoSuchElementException("등록되지 않은 과목");
        }
    }

    private void validationDuplicationMember(Subject subject) {
        subjectRepository.findBySubjectName(subject.getSubjectName())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 등록된 과목");
                });
    }
}
