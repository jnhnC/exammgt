package com.freewheelin.exammgt.api;

import com.freewheelin.exammgt.domian.Subject;
import com.freewheelin.exammgt.dto.GradeDto;
import com.freewheelin.exammgt.dto.SubjectDto;
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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SubjectApiController {

    private final SubjectSerivce subjectSerivce;


    @GetMapping("/api/subject")
    public Page<SubjectDto> subjectsCollectionPaging(Pageable pageable) {
        Page<Subject> subjects = subjectSerivce.findAll(pageable);
        Page<SubjectDto> toMap = subjects.map(SubjectDto::new);
        return toMap;
    }

    @PostMapping("/api/subject")
    public SubjectDto saveSubject(@RequestBody @Valid SubjectRequest request) {

        Subject subject = new Subject(request.getSubjectName());
        Subject saveMember = subjectSerivce.save(subject);
        SubjectDto subjectDto = new SubjectDto(saveMember);

        return subjectDto;

    }
    @PutMapping("/api/subject/{subjectName}")
    public SubjectDto updateSubject(@PathVariable("subjectName") String subjectName, @RequestBody @Valid UpdateSubjectRequest request ){
        Subject subject = subjectSerivce.findBySubjectName(subjectName);
        subjectSerivce.update(subject.getId(), request.getSubjectName());
        Optional<Subject> byId = subjectSerivce.findById(subject.getId());
        return new SubjectDto(byId.get());
    }

    @DeleteMapping("/api/subject/{subjectName}")
    public ResponseEntity<Map<String, Object>> deleteSubject(@PathVariable("subjectName") String subjectName){
        Subject subject = subjectSerivce.findBySubjectName(subjectName);
        subjectSerivce.delete(subject.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("status", "200");
        map.put("msg" , "삭제 되었습니다");

        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @GetMapping("/api/subject/avg/{subjectName}")
    public SubjectDto subjectAvgSubjectName(@PathVariable("subjectName") String subjectName) {
        Subject subjects = subjectSerivce.findBySubjectName(subjectName);
        SubjectDto toMap = new SubjectDto(subjects);

        double sum = 0;
        double avg = 0;

        for(GradeDto gradeDto: toMap.getGradeDtos()){
            sum += gradeDto.getScore();
        }

        if(sum != 0){
            avg = sum / toMap.getGradeDtos().size();
        }

        toMap.setAvg(avg);
        return toMap;
    }


    //---

    @Data
    @NoArgsConstructor
    static class SubjectRequest{
        private String subjectName;

        public SubjectRequest(String subjectName) {
            this.subjectName = subjectName;
        }
    }

    @Data
    @AllArgsConstructor
    static class UpdateSubjectRequest {
        private Long id;
        private String subjectName;

    }


}
