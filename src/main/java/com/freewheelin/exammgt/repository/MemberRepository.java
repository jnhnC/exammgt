package com.freewheelin.exammgt.repository;


import com.freewheelin.exammgt.domian.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByName(String name);

    void deleteByName(String name);
}
