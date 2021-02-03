package com.freewheelin.exammgt.service;

import com.freewheelin.exammgt.domian.Member;
import com.freewheelin.exammgt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(Member member) {

        validationDuplicationMember(member);
        return memberRepository.save(member);
    }

    @Transactional
    public void update(Long id, String name, int age) {
        try{
            Optional<Member> member = memberRepository.findById(id);
            member.get().updateName(name, age);
        }catch (Exception e){
            throw new NoSuchElementException("등록되지 않는 학생");
        }
    }

    @Transactional
    public void delete(Long id) {
        try{
            memberRepository.deleteById(id);
        }catch(Exception e){
            throw new NoSuchElementException("등록되지 않은 학생");
        }
    }
    //---

    public Page<Member> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public Optional<Member> findById(Long id) {
        try{
            return memberRepository.findById(id);
        }catch(Exception e){
            throw new NoSuchElementException("등록되지 않은 학생");
        }
    }

    public Member findByName(String name) {
        try{
            return  memberRepository.findByName(name).get();
        }catch(Exception e){
            throw new NoSuchElementException("등록되지 않은 학생");
        }
    }

    private void validationDuplicationMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 등록된 학생");
                });
    }
}
