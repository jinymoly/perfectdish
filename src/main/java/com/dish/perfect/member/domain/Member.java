package com.dish.perfect.member.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String phoneNumber;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status;

    @Builder
    private Member(Long id, String userName, String phoneNumber, LocalDateTime createdAt, LocalDateTime modifiedAt,
            MemberStatus status) {
        this.id = id;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // @Override
    // public String toString() {
    //     return "id='" + id + '\'' +
    //             "  userName='" + userName + '\'' +
    //             ", phoneNumber='" + phoneNumber + '\'' +
    //             ", status='" + status + '\'' +
    //             ", createdAt=" + timeFomatter(createdAt) + '\'' +
    //             ", updatedAt=" + timeFomatter(modifiedAt);
    // }

    public static Member of(String userName, String phoneNumber, MemberStatus status) {
        return Member.builder()
                    .userName(userName)
                    .phoneNumber(phoneNumber)
                    .status(status)
                    .build();
    }

    private String timeFomatter(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd/hh:MM:ss"));
    }

    public void updateMemberInfo(String userName, String phoneNumber, LocalDateTime modifiedAt) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.modifiedAt = modifiedAt;
    }

    public boolean isNewUserName(final String inputName) {
        return !(userName.equals(inputName));
    }

    public boolean isNewPhoneNumber(final String inputNumber){
        return !(phoneNumber.equals(inputNumber));
    }

    public void updateMemberStatus(MemberStatus status, LocalDateTime modifiedAt) {
        this.status = status;
        this.modifiedAt = modifiedAt;
    }

    public boolean isActive(){
        if(this.getStatus().equals(MemberStatus.ACTIVE)){
            return true;
        }
        return false;
    }
}
