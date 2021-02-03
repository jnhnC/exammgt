package com.freewheelin.exammgt.service;

import com.freewheelin.exammgt.domian.Member;
import com.freewheelin.exammgt.domian.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SubjectSerivceTest {
    @Autowired
    SubjectSerivce subjectSerivce;

    @Test
    public void saveSubject() {
        //given
        Subject subject = new Subject("스페인어");

        //when
        Subject saveSubject = subjectSerivce.save(subject);

        //then
        Subject result = subjectSerivce.findBySubjectName(saveSubject.getSubjectName());
        assertThat(subject.getSubjectName()).isEqualTo(result.getSubjectName());

    }

    @Test
    public void validationDuplication(){
        //given
        Subject subject = new Subject("스페인어");
        Subject subject2 = new Subject("스페인어");

        //when
        subjectSerivce.save(subject);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> subjectSerivce.save(subject2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 등록된 과목");

    }

    @Test
    public void deleteSubject(){
        //given
        Subject subject = subjectSerivce.findBySubjectName("수학");

        //when
        subjectSerivce.delete(subject.getId());
        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> subjectSerivce.findBySubjectName("수학"));

        //then
        assertThat(e.getMessage()).isEqualTo("등록되지 않은 과목");
    }

}