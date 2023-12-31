package com.dish.perfect.member.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.dish.perfect.image.domain.UploadFile;

import lombok.Data;

@Data
public class Member {
    
    private Long id;

    private String nickname;
    private String password;

    private LocalDateTime createAt;

    private List<UploadFile> imageFiles;

    public Member(Long id, String nickname, String password, LocalDateTime createAt, List<UploadFile> imageFiles) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.createAt = LocalDateTime.now();
        this.imageFiles = imageFiles;
    }

    
    
}
