package com.freewheelin.exammgt.domian;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private int age;


    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Grade> grades = new ArrayList<>();


    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Member(Long id) {
        this.id = id;
    }

    public void updateName(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
