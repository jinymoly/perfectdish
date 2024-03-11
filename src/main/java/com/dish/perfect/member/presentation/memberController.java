package com.dish.perfect.member.presentation;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dish.perfect.member.domain.Member;
import com.dish.perfect.member.domain.MemberStatus;
import com.dish.perfect.member.dto.request.MemberChangeStatusRequest;
import com.dish.perfect.member.dto.request.MemberRequest;
import com.dish.perfect.member.dto.request.MemberUpdateRequest;
import com.dish.perfect.member.dto.response.MemberResponse;
import com.dish.perfect.member.service.MemberCoreService;
import com.dish.perfect.member.service.MemberPresentationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class MemberController {

    private final MemberCoreService memberCoreService;
    private final MemberPresentationService memberPresentationService;

    @GetMapping("/join")
    public String addMemberRequest() {
        return "memberRequest 페이지로 이동";
    }

    @PostMapping("/join")
    public ResponseEntity<Member> addMember(@RequestBody @Valid MemberRequest memberRequest) {
        Member newMember = memberCoreService.join(memberRequest);
        return ResponseEntity.ok(newMember);
    }

    @GetMapping("/allmember")
    public ResponseEntity<List<MemberResponse>> getAllMember() {
        List<MemberResponse> findAll = memberPresentationService.findAllWithActive();
        return ResponseEntity.ok(findAll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> findMemberByPhoneNumber(@PathVariable("id") Long id,
                                                        @RequestParam("phoneNumber") String phoneNumber) {
        Member findMember = memberPresentationService.findByphoneNumber(phoneNumber);
        return ResponseEntity.ok(findMember);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Member>> findMemberByName(@RequestParam("userName") String userName) {
        List<Member> members = memberPresentationService.findMemberByName(userName);
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
                Member member = memberPresentationService.findById(id);
                memberCoreService.updateMemberInfo(member.getId(), memberUrequest);
        log.info("update member:{}님/{}", memberUrequest.getUserName(), memberUrequest.getPhoneNumber());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}/delete")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id, @RequestBody @Valid MemberChangeStatusRequest memberDrequest) {
        Member member = memberPresentationService.findById(id);
        if (member == null) {
            return ResponseEntity.noContent().build();
        }
        if (member.getStatus().equals(MemberStatus.DELETED)) {
            log.warn("already deleted:{}[{}]", member.getUserName(), member.getStatus());
            return ResponseEntity.noContent().build();
        } else {
            memberCoreService.deleteMemberByStatus(id, memberDrequest);
        }
        return ResponseEntity.noContent().build();
    }
}
