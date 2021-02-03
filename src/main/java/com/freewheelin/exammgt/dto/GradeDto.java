package com.freewheelin.exammgt.dto;

import com.freewheelin.exammgt.domian.Grade;
import lombok.Data;

@Data
public class GradeDto {
        private int score;
        public GradeDto(Grade grade){
            score = grade.getScore();
        }
}
