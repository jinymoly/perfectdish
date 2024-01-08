package com.dish.perfect.menu.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Menu {

    private CourseType type;
    private String title;
    private String description;

    private MultipartFile menuImg; 

    @Builder
    public Menu(CourseType type, String title, String description, MultipartFile menuImg) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.menuImg = menuImg;
    }

    
}
