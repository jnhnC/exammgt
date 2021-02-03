package com.freewheelin.exammgt.repository;

import com.freewheelin.exammgt.domian.Member;
import com.freewheelin.exammgt.service.MemberService;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    public void saveMember() {
        // given
        Member member = new Member("홍길동",17);
        entityManager.persist(member);
        entityManager.flush();

        // when
        Member findMember = memberRepository.findByName(member.getName()).get();

        // then
        assertThat(findMember.getName())
                .isEqualTo(member.getName());
    }

    @Test
    public void deleteMember() {
        // given
        Member member = new Member("홍길동",17);
        entityManager.persist(member);
        entityManager.flush();

        // when
        memberRepository.deleteByName(member.getName());

        // then
        assertThat(memberRepository.findByName("홍길동")).isEmpty();
    }
}