package com.freewheelin.exammgt.dto;

import com.freewheelin.exammgt.domian.Member;
import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class MemberDto {

        private Long member_id;
        private String name;
        private int age;
        private List<GradeDto> gradeDtos;

        private double avg;

        public MemberDto(Member member){
            member_id = member.getId();
            name = member.getName();
            age = member.getAge();
            gradeDtos = member.getGrades().stream()
                    .map(GradeDto::new)
                    .collect(toList());
        }
}
