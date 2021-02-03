package com.freewheelin.exammgt.repository;

import com.freewheelin.exammgt.domian.Member;
import com.freewheelin.exammgt.domian.Subject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SubjectRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void saveSubject() {
        // given
        Subject subject = new Subject("국어");
        entityManager.persist(subject);
        entityManager.flush();

        // when
        Subject findSubject = subjectRepository.findBySubjectName(subject.getSubjectName()).get();

        // then
        assertThat(findSubject.getSubjectName())
                .isEqualTo(subject.getSubjectName());
    }

    @Test
    public void deleteSubject() {
        // given
        Subject subject = new Subject("국어");
        entityManager.persist(subject);
        entityManager.flush();

        // when
        subjectRepository.deleteById(subject.getId());
        // then
        assertThat(subjectRepository.findBySubjectName("국어")).isEmpty();
    }

}