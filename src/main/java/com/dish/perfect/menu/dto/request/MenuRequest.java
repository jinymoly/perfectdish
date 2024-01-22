package com.dish.perfect.menu.dto.request;

import org.springframework.web.multipart.MultipartFile;

import com.dish.perfect.menu.domain.CourseType;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuRequest {

    
    @NotBlank(message = "요리 이름은 필수입니다.")
    private final String title;
    private final CourseType courseType;
    
    private final Integer price;
    private final String description;

    private final String menuImgUrl; // uuid 전 

    private MultipartFile menuImgFile;

    @Builder
    private MenuRequest(String title, CourseType courseType, Integer price, String description, String menuImgUrl, MultipartFile menuImgFile)  {
        this.title = title;
        this.courseType = courseType;
        this.price = price;
        this.description = description;
        this.menuImgUrl = menuImgUrl;
        this.menuImgFile = menuImgFile;
    }
}
