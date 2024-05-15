package com.dish.perfect.member.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.dish.perfect.global.base.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    @Builder
    private Member(Long id, String userName, String phoneNumber, MemberStatus status) {
        this.id = id;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    @Override
    public String toString() {
        return '\n' + "id=" + id + '\n' +
                "userName=" + userName + '\n' +
                "phoneNumber=" + phoneNumber + '\n' +
                "status=" + status + '\n' +
                "createdAt=" + timeFomatter(getCreatedAt());
    }

    private String timeFomatter(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm:ss"));
    }

    public void updateMemberInfo(String userName, String phoneNumber) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        updateLastModifiedAt();
    }

    public boolean isNewUserName(final String inputName) {
        return !(userName.equals(inputName));
    }

    public boolean isNewPhoneNumber(final String inputNumber){
        return !(phoneNumber.equals(inputNumber));
    }

    public void updateMemberStatus(MemberStatus status) {
        this.status = status;
        updateLastModifiedAt();
    }

    public boolean isActive(){
        return this.getStatus().equals(MemberStatus.ACTIVE);
    }

}
