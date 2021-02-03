package com.freewheelin.exammgt;

import com.freewheelin.exammgt.domian.Grade;
import com.freewheelin.exammgt.domian.Member;
import com.freewheelin.exammgt.domian.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class initDB {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dataInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dataInit() {

            //--

            Member member = new Member("김철수",17);
            em.persist(member);

            Member member2 = new Member("이영희",15);
            em.persist(member2);

            Member member3 = new Member("오영주",15);
            em.persist(member3);

            //--

            Subject subject = new Subject("수학");
            em.persist(subject);

            Subject subject2 = new Subject("과학");
            em.persist(subject2);

            Subject subject3 = new Subject("영어");
            em.persist(subject3);


            //---

            Grade grade = new Grade(member,subject,80);
            em.persist(grade);

            Grade grade2 = new Grade(member,subject2,95);
            em.persist(grade2);

            Grade grade3 = new Grade(member,subject3,100);
            em.persist(grade3);


            //---

            Grade grade4 = new Grade(member2,subject,90);
            em.persist(grade4);

            Grade grade5 = new Grade(member2,subject2,99);
            em.persist(grade5);

            Grade grade6 = new Grade(member2,subject3,89);
            em.persist(grade6);

            //---

            Grade grade7 = new Grade(member3,subject,100);
            em.persist(grade7);

            Grade grade8 = new Grade(member3,subject2,90);
            em.persist(grade8);

            Grade grade9 = new Grade(member3,subject3,95);
            em.persist(grade9);

        }



    }
}
