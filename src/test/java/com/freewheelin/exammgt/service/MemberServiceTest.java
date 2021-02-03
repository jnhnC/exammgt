package com.freewheelin.exammgt.service;

import com.freewheelin.exammgt.domian.Member;
import org.assertj.core.api.Assertions;

import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jmx.export.metadata.ManagedMetric;

import javax.transaction.Transactional;


import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    public void saveMember() {
        //given
        Member member = new Member("김현우",17);

        //when
        Member saveMember = memberService.saveMember(member);

        //then
        Member result = memberService.findByName(saveMember.getName());
        assertThat(member.getName()).isEqualTo(result.getName());

    }

    @Test
    public void validationDuplication(){
        //given
        Member member = new Member("김현우",17);
        Member member2 = new Member("김현우",17);

        //when
        memberService.saveMember(member);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.saveMember(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 등록된 학생");

    }

    @Test
    public void deleteMember(){
        //given
        Member member = memberService.findByName("김철수");

        //when
        memberService.delete(member.getId());
        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> memberService.findByName("김철수"));

        //then
        assertThat(e.getMessage()).isEqualTo("등록되지 않은 학생");
    }



}