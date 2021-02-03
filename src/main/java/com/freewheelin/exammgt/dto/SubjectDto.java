package com.freewheelin.exammgt.dto;

import com.freewheelin.exammgt.domian.Subject;
import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class SubjectDto {
    private String subjectName;
    private List<GradeDto> gradeDtos;

    private double avg;

    public SubjectDto(Subject subject){
        subjectName = subject.getSubjectName();
        gradeDtos = subject.getGrades().stream()
                .map(GradeDto::new)
                .collect(toList());

    }

}
