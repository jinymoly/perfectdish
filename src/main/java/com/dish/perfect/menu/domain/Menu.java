package com.dish.perfect.menu.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "menuName")
public class Menu {

    private CourseType courseType;
    private String menuName;
    private Integer price;
    private String description;

    private MenuImg menuImg;

    private List<Menu> menus;

    public Menu(){
        this.menus = new ArrayList<>();
    }

    public void createMenuList(Menu menu){
        menus.add(menu);
    }

    @Builder
    public Menu(CourseType courseType, String menuName, Integer price, String description, MenuImg menuImg) {
        this.courseType = courseType;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
        this.menuImg = menuImg;
    }

    
    @Override
    public String toString() {
        return "[" + typeConverter(courseType) + "] " + "Menu : " + menuName + " || " + description + " (" + price + ")";
    }


    private String typeConverter(CourseType type){
        return type.toString().replace("T_", "");
    }
}
