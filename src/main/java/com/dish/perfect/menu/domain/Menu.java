package com.dish.perfect.menu.domain;

import org.springframework.data.annotation.Id;

import com.dish.perfect.imageManager.domain.ImageFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
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

    @OneToOne(fetch = FetchType.LAZY)
    private ImageFile menuImg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Availability availability;

    @Column(nullable = false)
    private boolean isDiscounted;

    @Builder
    public Menu(CourseType courseType, String menuName, Integer price, boolean isDiscounted, String description,
            ImageFile menuImg, Availability availability) {
        this.courseType = courseType;
        this.menuName = menuName;
        this.price = price;
        this.isDiscounted = false;
        this.description = description;
        this.menuImg = menuImg;
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "[" + typeConverter(courseType) + "] "
                + menuName + " || " + description + " (" + price + ") " + availability + ", D: " + isDiscounted;
    }

    private String typeConverter(CourseType type) {
        return type.toString().replace("T_", "");
    }

    public void addDiscount(boolean discount) {
        this.isDiscounted = true;
    }
    
    public void addImgFile(ImageFile file){
        this.menuImg = file;
    }
}
