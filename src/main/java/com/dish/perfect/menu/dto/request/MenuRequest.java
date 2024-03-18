package com.dish.perfect.menu.dto.request;

import org.springframework.web.multipart.MultipartFile;

import com.dish.perfect.imageManager.domain.Image;
import com.dish.perfect.imageManager.domain.ImageFile;
import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuRequest {

    @NotBlank(message = "요리 이름은 필수입니다.")
    private final String menuName;
    private final CourseType courseType;
    
    @Pattern(regexp = "^(?=.*[0-9])\\S+$", message = "숫자만 입력 가능합니다.")
    private final Integer price;
    private final String description;

    private MultipartFile menuImgFile;

    private final Availability availability;

    @Builder
    private MenuRequest(String menuName, CourseType courseType, Integer price, String description, MultipartFile menuImgFile, Availability availability)  {
        this.menuName = menuName;
        this.courseType = courseType;
        this.price = price;
        this.description = description;
        this.menuImgFile = menuImgFile;
        this.availability = availability;
    }
    
    public Menu toEntity(){
        return Menu.builder()
                    .menuName(menuName)
                    .courseType(courseType)
                    .price(price)
                    .description(description)
                    .availability(availability)
                    .isDiscounted(false)
                    .menuImg(new Image(menuImgFile.getName()))
                    .build();
    }
}
