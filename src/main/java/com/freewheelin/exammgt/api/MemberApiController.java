package com.freewheelin.exammgt.api;


import com.freewheelin.exammgt.domian.Member;
import com.freewheelin.exammgt.dto.GradeDto;
import com.freewheelin.exammgt.dto.MemberDto;
import com.freewheelin.exammgt.service.MemberService;
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
public class MemberApiController {

    private final MemberService memberService;


    @GetMapping("/api/member")
    public Page<MemberDto> membersCollectionPaging(Pageable pageable) {
        Page<Member> members = memberService.findAll(pageable);
        Page<MemberDto> toMap = members.map(MemberDto::new);
        return toMap;
    }

    @PostMapping("/api/member")
    public MemberDto saveMember(@RequestBody @Valid MemberRequest request) {

        Member member = new Member(request.name, request.age );
        Member saveMember = memberService.saveMember(member);
        MemberDto memberDto = new MemberDto(saveMember);

        return memberDto;

    }
    @PutMapping("/api/member/{name}")
    public MemberDto updateMember(@PathVariable("name") String name, @RequestBody @Valid UpdateMemberRequest request ){
        Member member = memberService.findByName(name);
        memberService.update(member.getId(), request.getName(), request.getAge());
        Optional<Member> byId = memberService.findById(member.getId());
        return new MemberDto(byId.get());
    }

    @DeleteMapping("/api/member/{name}")
    public ResponseEntity<Map<String, Object>> deleteMember(@PathVariable("name") String name){
        Member member = memberService.findByName(name);
        memberService.delete(member.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("status", "200");
        map.put("msg" , "삭제 되었습니다");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //---

    @GetMapping("/api/member/avg/{name}")
    public MemberDto memberScorePaging(@PathVariable("name") String name) {
        Member members = memberService.findByName(name);
        MemberDto toMap = new MemberDto(members);

        double avg = 0;
        double sum = 0;


        for (GradeDto grade : toMap.getGradeDtos()) {
            sum += grade.getScore();
        }

        if(sum != 0){
            avg = sum/ toMap.getGradeDtos().size();
        }
        toMap.setAvg(avg);

        return toMap;
    }



    //--
    @Data
    @NoArgsConstructor
    static class MemberRequest{
        private String name;
        private int age;

        public MemberRequest(Member member){
            name = member.getName();
            age = member.getAge();

        }
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberRequest {
        private Long id;
        private String name;
        private int age;

    }
}
