package com.dish.perfect.member.presentation;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.dto.request.MemberRequest;
import com.dish.perfect.member.dto.request.MemberUpdateRequest;
import com.dish.perfect.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class memberController {

    private final MemberService memberService;

    @GetMapping("/save")
    public String addMemberRequest(@ModelAttribute("memberRequest") MemberRequest memberRequest) {
        return "memberRequest 페이지로 이동";
    }

    @PostMapping("/save")
    public ResponseEntity<Member> addMember(@RequestBody @Valid MemberRequest memberRequest) {
        Member save = memberService.save(memberRequest);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/allmember")
    public ResponseEntity<List<Member>> getAllMember() {
        List<Member> findAll = memberService.findAll();
        return ResponseEntity.ok(findAll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> findMemberByPhoneNumber(@PathVariable("id") Long id, @RequestParam("phoneNumber")String phoneNumber) {
        Optional<Member> findMember = memberService.findMemberByOnlyPhoneNumber(phoneNumber);
        return findMember.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/list")
    public ResponseEntity<List<Member>> findMemberByName(@RequestParam("userName") String userName) {
        List<Member> members = memberService.findMemberWithName(userName);
        if (!members.isEmpty()) {
            return ResponseEntity.ok(members);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/edit")
    public String updateMemberInfo(@PathVariable String id) {
        return "MemberUpdateRequest 페이지로 이동";
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<Void> updateMemberInfo(@PathVariable Long id,
            @RequestBody @Valid MemberUpdateRequest memberUrequest) {
                Member member = memberService.findById(id);
        if (member.getId().equals(id)) {
            memberService.updateMemberInfo(id, memberUrequest);
            log.info("update member:{}님/{}", memberUrequest.getUserName(), memberUrequest.getPhoneNumber());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
