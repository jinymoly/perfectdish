package com.dish.perfect.menu.domain;

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

    private MenuImg menuImg;

    @Builder
    public Menu(CourseType courseType, String menuName, Integer price, String description, MenuImg menuImg) {
        this.courseType = courseType;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
        this.menuImg = menuImg;
    }
}
