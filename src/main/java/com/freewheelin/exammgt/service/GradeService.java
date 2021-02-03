package com.freewheelin.exammgt.service;


import com.freewheelin.exammgt.domian.Grade;
import com.freewheelin.exammgt.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;

    @Transactional
    public Grade save(Grade grade) {

        validationDuplicationMember(grade);
        return gradeRepository.save(grade);
    }

    @Transactional
    public void update(Long id, int score) {

        try{
            Optional<Grade> grade = gradeRepository.findById(id);
            grade.get().updateScore(score);

        }catch (Exception e){
            throw new NoSuchElementException("");
        }
    }

    @Transactional
    public void delete(Long id) {
        try{
            gradeRepository.deleteById(id);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public Grade findByMemberAndSubject(Long memberId, Long subjectId) {
        try{
            return  gradeRepository.findByMemberIdAndSubjectId(memberId, subjectId).get();

        }catch (Exception e){
            throw new NoSuchElementException("학생과 과목을 다시 확인");
        }
    }

    public Page<Grade> findAll(Pageable pageable) {
        return gradeRepository.findAll(pageable);
    }

    public Optional<Grade> findById(Long id) {
        return gradeRepository.findById(id);
    }

    private void validationDuplicationMember(Grade grade) {
        gradeRepository.findByMemberIdAndSubjectId(grade.getMember().getId(), grade.getSubject().getId())
                .ifPresent(g ->{
                    throw new IllegalStateException("이미 등록된 점수");
                });
    }


}
