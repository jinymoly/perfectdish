package com.dish.perfect.menu.domain;

import com.dish.perfect.imageManager.domain.ImageFile;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "menuName")
public class Menu {

    private CourseType courseType;
    private String menuName;
    private Integer price;
    private String description;

    private ImageFile menuImg;

    private Availability availability;

    private boolean isDiscounted;

    @Builder
    public Menu(CourseType courseType, String menuName, Integer price, boolean isDiscounted, String description, ImageFile menuImg, Availability availability) {
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
                + menuName + " || " + description + " (" + price + ") "+ availability +", D: "+ isDiscounted;
    }

    private String typeConverter(CourseType type){
        return type.toString().replace("T_", "");
    }

    public void addDiscount(boolean discount){
        this.isDiscounted = true;
    }
}
