package com.dish.perfect.menuBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dish.perfect.imageManager.domain.ImageFile;
import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;

public class MenuBoardFixture {

    public Menu fixtureMenu(String menuName, int price, String description, Availability availability,
            CourseType courseType, String imageName, String imageDescription) {
        return Menu.builder()
                .menuName(menuName)
                .price(price)
                .description(description)
                .availability(availability)
                .courseType(courseType)
                .menuImg(new ImageFile(imageName, imageDescription))
                .build();
    }

    public Menu menuA() {
        return fixtureMenu("메뉴A", 33000, "설명AA", Availability.AVAILABLE, CourseType.T_MAIN, "menu.A", "메뉴A 서버 저장");
    }

    public Menu menuB() {
        return fixtureMenu("메뉴B", 25000, "설명BB", Availability.AVAILABLE, CourseType.T_MAIN, "menu.B", "메뉴B 서버 저장");
    }

    public Menu menuC() {
        return fixtureMenu("메뉴C", 20000, "설명CC", Availability.AVAILABLE, CourseType.T_MAIN, "menu.C", "메뉴C 서버 저장");
    }

    public Menu menuD() {
        return fixtureMenu("메뉴D", 28000, "설명DD", Availability.AVAILABLE, CourseType.T_MAIN, "menu.D", "메뉴D 서버 저장");
    }

    public Menu menuE() {
        return fixtureMenu("메뉴E", 30000, "설명EE", Availability.AVAILABLE, CourseType.T_MAIN, "menu.E", "메뉴E 서버 저장");
    }

    public Menu menuF() {
        return fixtureMenu("메뉴F", 27000, "설명FF", Availability.AVAILABLE, CourseType.T_MAIN, "menu.F", "메뉴F 서버 저장");
    }

    public Menu menuG() {
        return fixtureMenu("메뉴G", 32000, "설명GG", Availability.AVAILABLE, CourseType.T_MAIN, "menu.G", "메뉴G 서버 저장");
    }

    public Menu menuH() {
        return fixtureMenu("메뉴H", 35000, "설명HH", Availability.AVAILABLE, CourseType.T_MAIN, "menu.H", "메뉴H 서버 저장");
    }

    public List<Menu> commonMenus() {
        List<Menu> commonMenus = new ArrayList<>();
        commonMenus.add(menuA());
        commonMenus.add(menuB());
        commonMenus.add(menuC());
        commonMenus.add(menuD());
        return commonMenus;
    }

    public Optional<List<Menu>> discountMenus() {
        List<Menu> discountMenus = new ArrayList<>();
        discountMenus.add(menuE());
        discountMenus.add(menuF());
        discountMenus.add(menuG());
        discountMenus.add(menuH());
        return Optional.of(discountMenus);
    }
}
