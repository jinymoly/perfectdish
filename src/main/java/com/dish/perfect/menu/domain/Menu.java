package com.dish.perfect.menu.domain;


import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.FlashMap;

import com.dish.perfect.imageManager.domain.Image;
import com.dish.perfect.imageManager.domain.ImageFile;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "menuName")
public class Menu {

    @Id
    @Column(name = "menu_name")
    private String menuName;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseType courseType;

    private String description;

    @Embedded
    private Image menuImg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Availability availability;

    @Column(nullable = false)
    private boolean isDiscounted;

    @Builder
    public Menu(CourseType courseType, String menuName, Integer price, boolean isDiscounted, String description,
            Image menuImg, Availability availability) {
        this.courseType = courseType;
        this.menuName = menuName;
        this.price = price;
        this.isDiscounted = false;
        this.description = description;
        this.menuImg = createImage(menuImg.getImgUrl());
        this.availability = Availability.AVAILABLE;
    }

    

    @Override
    public String toString() {
        String imgUrl = menuImg != null ? menuImg.getImgUrl() : "No image";
        return "[" + typeConverter(courseType) + "]" + '\n' +
        "menuName=" + menuName +"/"+ availability + '\n' +
        "description=" + description + "(" + price + ")" + '\n' +
        "img=" + imgUrl + '\n' +
        "isDiscounted=" + isDiscounted;
    }

    private String typeConverter(CourseType type) {
        return type.toString().replace("T_", "");
    }

    public void activeDiscount(boolean discount) {
        this.isDiscounted = true;
    }

    public Image createImage(String imgUrl){
        return new Image(imgUrl);
    }

    public void markMenuAsUnavailable(Availability availability){
        this.availability = Availability.UNAVAILABLE;
    }

    public boolean isAvailability(){
        return this.getAvailability().equals(Availability.AVAILABLE);
    }

}
