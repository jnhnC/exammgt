package com.freewheelin.exammgt.service;

import com.freewheelin.exammgt.domian.Grade;
import com.freewheelin.exammgt.domian.Member;
import com.freewheelin.exammgt.domian.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.plaf.metal.MetalMenuBarUI;
import javax.transaction.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GradeServiceTest {
    @Autowired
    GradeService gradeService;

    @Autowired
    MemberService memberService;

    @Autowired
    SubjectSerivce subjectSerivce;

    @Test
    public void saveGrade() {
        //given
        Member member = new Member("홍길동",18);
        memberService.saveMember(member);
        Subject subject = subjectSerivce.findBySubjectName("수학");
        Grade grade = new Grade(member, subject, 100 );

        //when
        Grade saveGrade = gradeService.save(grade);

        //then
        Grade result = gradeService.findByMemberAndSubject(member.getId(), subject.getId());
        assertThat(grade.getScore()).isEqualTo(result.getScore());

    }

    @Test
    public void validationDuplication(){
        //given
        Member member = memberService.findByName("김철수");
        Subject subject = subjectSerivce.findBySubjectName("수학");
        
        Grade grade = gradeService.findByMemberAndSubject(member.getId(), subject.getId());

        //when
        IllegalStateException e = assertThrows(IllegalStateException.class, () ->  gradeService.save(grade));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 등록된 점수");

    }

    @Test
    public void deleteGrade(){
        //given
        Member member = memberService.findByName("김철수");
        Subject subject = subjectSerivce.findBySubjectName("수학");

        Grade grade = gradeService.findByMemberAndSubject(member.getId(), subject.getId());

        //when
        gradeService.delete(grade.getId());
        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> gradeService.findByMemberAndSubject(member.getId(), subject.getId()));

        //then
        assertThat(e.getMessage()).isEqualTo("학생과 과목을 다시 확인");
    }
    
    
    @Test
    public void updateGrade() {
        //given
        Member member = memberService.findByName("김철수");
        Subject subject = subjectSerivce.findBySubjectName("수학");

        Grade grade = gradeService.findByMemberAndSubject(member.getId(), subject.getId());

        //when
        gradeService.update(grade.getId(),100);
        Grade result = gradeService.findByMemberAndSubject(member.getId(), subject.getId());

        //then
        assertThat(result.getScore()).isEqualTo(100);
            
    }

}