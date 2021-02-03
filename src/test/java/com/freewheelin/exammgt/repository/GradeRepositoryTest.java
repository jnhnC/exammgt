package com.freewheelin.exammgt.repository;

import com.freewheelin.exammgt.domian.Grade;
import com.freewheelin.exammgt.domian.Member;
import com.freewheelin.exammgt.domian.Subject;
import com.freewheelin.exammgt.service.GradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GradeRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void saveGrade() {
        // given
        Member member = new Member("홍길동",17);
        entityManager.persist(member);

        Subject subject = new Subject("국어");
        entityManager.persist(subject);

        Grade grade = new Grade(member,subject,98);
        entityManager.persist(grade);

        entityManager.flush();

        // when
        Grade findGrade = gradeRepository.findByMemberIdAndSubjectId(member.getId(),subject.getId()).get();

        // then
        assertThat(findGrade.getScore())
                .isEqualTo(grade.getScore());
    }

    @Test
    public void deleteGrade() {
        // given
        Member member = new Member("홍길동",17);
        entityManager.persist(member);

        Subject subject = new Subject("국어");
        entityManager.persist(subject);

        Grade grade = new Grade(member,subject,98);
        entityManager.persist(grade);

        entityManager.flush();

        // when
        gradeRepository.deleteById(grade.getId());

        // then
        assertThat(gradeRepository.findByMemberIdAndSubjectId(member.getId(),subject.getId())).isEmpty();
    }

    @Test
    public void updateGrade() {

        //given
        Member member = new Member("홍길동",17);
        entityManager.persist(member);

        Subject subject = new Subject("국어");
        entityManager.persist(subject);

        Grade grade = new Grade(member,subject,98);
        entityManager.persist(grade);

        entityManager.flush();

        Grade updateGrade = gradeRepository.findByMemberIdAndSubjectId(member.getId(), subject.getId()).get();

        //when
        updateGrade.updateScore(100);
        Grade findGrade = gradeRepository.findByMemberIdAndSubjectId(member.getId(), subject.getId()).get();

        //then
        assertThat(findGrade.getScore())
                .isEqualTo(100);
    }
}