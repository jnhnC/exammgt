package com.freewheelin.exammgt.api;

import com.freewheelin.exammgt.domian.Grade;
import com.freewheelin.exammgt.domian.Member;
import com.freewheelin.exammgt.domian.Subject;
import com.freewheelin.exammgt.dto.GradeDto;
import com.freewheelin.exammgt.service.GradeService;
import com.freewheelin.exammgt.service.MemberService;
import com.freewheelin.exammgt.service.SubjectSerivce;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class GradeApiController {
    private final GradeService gradService;
    private final MemberService memberService;
    private final SubjectSerivce subjectSerivce;



    @GetMapping("/api/grade")
    public Page<GradeDto> gradeCollectionPaging(Pageable pageable){
        Page<Grade> scores = gradService.findAll(pageable);
        Page<GradeDto> toMap = scores.map(GradeDto::new);

        return toMap;
    }

    @GetMapping("/api/grade/member/{name}/subject/{subjectName}")
    public GradeDto grad(@PathVariable("name") String name, @PathVariable("subjectName") String subjectName ){
        Member member = memberService.findByName(name);
        Subject subject = subjectSerivce.findBySubjectName(subjectName);

        Grade grade = gradService.findByMemberAndSubject(member.getId(),subject.getId());
        GradeDto gradeDto = new GradeDto(grade);

        return gradeDto;

    }

    @PostMapping("/api/grade")
    public GradeDto saveGrade(@RequestBody @Valid GradeRequest request) {

        Member member = memberService.findByName(request.getName());
        Subject subject = subjectSerivce.findBySubjectName(request.getSubjectName());
        Grade grade = new Grade(member, subject, request.getScore());
        Grade saveGrade = gradService.save(grade);
        GradeDto gradeDto = new GradeDto(saveGrade);

        return gradeDto;

    }
    @PutMapping("/api/grade/member/{name}/subject/{subjectName}")
    public GradeDto updateGrade(@PathVariable("name") String name, @PathVariable("subjectName") String subjectName,
                                @RequestBody @Valid UpdateScoreRequest request ){
        Member member = memberService.findByName(name);
        Subject subject = subjectSerivce.findBySubjectName(subjectName);

        Grade grade = gradService.findByMemberAndSubject(member.getId(), subject.getId());
        gradService.update(grade.getId(), request.getScore());
        Optional<Grade> byId = gradService.findById(grade.getId());
        return new GradeDto(byId.get());
    }

    @DeleteMapping("/api/grade/member/{name}/subject/{subjectName}")
    public ResponseEntity<Map<String, Object>> deleteGrade(@PathVariable("name") String name, @PathVariable("subjectName") String subjectName){

        Member member = memberService.findByName(name);
        Subject subject = subjectSerivce.findBySubjectName(subjectName);

        Grade grade = gradService.findByMemberAndSubject(member.getId(), subject.getId());
        gradService.delete(grade.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("status", "200");
        map.put("msg" , "삭제 되었습니다");

        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    //---

    @Data
    @NoArgsConstructor
    static class GradeRequest {
        private int score;
        private String name;
        private String subjectName;

        public GradeRequest(Grade grade) {
            this.score = grade.getScore();

        }
    }

    @Data
    @AllArgsConstructor
    static class UpdateScoreRequest{
        private Long id;
        private int score;

    }
}
