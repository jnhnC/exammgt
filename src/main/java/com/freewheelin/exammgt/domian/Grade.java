package com.freewheelin.exammgt.domian;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int score;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;


    public Grade(int score) {
        this.score = score;

    }


    public Grade(Member member, Subject subject, int score) {
        this.member = member;
        this.subject = subject;
        this.score =score;

    }

    public void updateScore(int score) {
        this.score = score;


    }
}
