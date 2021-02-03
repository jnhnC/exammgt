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
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;
    private String subjectName;

    @OneToMany(mappedBy = "subject")
    private List<Grade> grades = new ArrayList<>();



    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public Subject(Long id) {
        this.id = id;
    }

    public void updateSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
