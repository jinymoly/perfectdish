package com.dish.perfect.menuBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dish.perfect.imageManager.domain.ImageFile;
import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.dto.request.MenuBoardRequest;

public class MenuBoardFixture {

    public Menu fixtureMenu(String menuName, int price, String description, Availability availability,
            CourseType courseType, String imageName, String imageDescription, boolean isDiscount) {
        return Menu.builder()
                .menuName(menuName)
                .price(price)
                .description(description)
                .availability(availability)
                .courseType(courseType)
                .menuImg(new ImageFile(imageName, imageDescription))
                .isDiscounted(isDiscount)
                .build();
    }

    public Menu menuA() {
        return fixtureMenu("메뉴A", 33000, "설명AA", Availability.AVAILABLE, CourseType.T_MAIN, "menu.A", "메뉴A 서버 저장",
                false);
    }

    public Menu menuB() {
        return fixtureMenu("메뉴B", 25000, "설명BB", Availability.UNAVAILABLE, CourseType.T_EPPETIZER, "menu.B",
                "메뉴B 서버 저장", false);
    }

    public Menu menuC() {
        return fixtureMenu("메뉴C", 20000, "설명CC", Availability.AVAILABLE, CourseType.T_DESSERT, "menu.C", "메뉴C 서버 저장",
                false);
    }

    public Menu menuD() {
        return fixtureMenu("메뉴D", 28000, "설명DD", Availability.UNAVAILABLE, CourseType.T_MAIN, "menu.D", "메뉴D 서버 저장",
                false);
    }

    public Menu menuE() {
        return fixtureMenu("메뉴E", 30000, "설명EE", Availability.UNAVAILABLE, CourseType.T_DESSERT, "menu.E", "메뉴E 서버 저장",
                false);
    }

    public Menu menuF() {
        return fixtureMenu("메뉴F", 27000, "설명FF", Availability.AVAILABLE, CourseType.T_MAIN, "menu.F", "메뉴F 서버 저장",
                false);
    }

    public Menu menuG() {
        return fixtureMenu("메뉴G", 32000, "설명GG", Availability.UNAVAILABLE, CourseType.T_EPPETIZER, "menu.G",
                "메뉴G 서버 저장", false);
    }

    public Menu menuH() {
        return fixtureMenu("메뉴H", 35000, "설명HH", Availability.AVAILABLE, CourseType.T_DESSERT, "menu.H", "메뉴H 서버 저장",
                false);
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

    public MenuBoardRequest requestCommonsA() {
        MenuBoardRequest menuBoardRequest = MenuBoardRequest.builder()
                .menu(menuA())
                // .commonMenus(commonMenus())
                .build();
        return menuBoardRequest;
    }

    public MenuBoardRequest requestCommonsB() {
        MenuBoardRequest menuBoardRequest = MenuBoardRequest.builder()
                .menu(menuB())
                // .commonMenus(commonMenus())
                .build();
        return menuBoardRequest;
    }

    public MenuBoardRequest requestCommonsC() {
        MenuBoardRequest menuBoardRequest = MenuBoardRequest.builder()
                .menu(menuC())
                // .commonMenus(commonMenus())
                .build();
        return menuBoardRequest;
    }

    public MenuBoardRequest requestCommonsD() {
        MenuBoardRequest menuBoardRequest = MenuBoardRequest.builder()
                .menu(menuD())
                // .commonMenus(commonMenus())
                .build();
        return menuBoardRequest;
    }

    public MenuBoardRequest requestDiscountsA() {
        MenuBoardRequest menuBoardRequest = MenuBoardRequest.builder()
                .menu(menuE())
                // .discountMenus(discountMenus())
                .build();
        return menuBoardRequest;
    }

    public MenuBoardRequest requestDiscountsB() {
        MenuBoardRequest menuBoardRequest = MenuBoardRequest.builder()
                .menu(menuF())
                // .discountMenus(discountMenus())
                .build();
        return menuBoardRequest;
    }

    public MenuBoardRequest requestDiscountsC() {
        MenuBoardRequest menuBoardRequest = MenuBoardRequest.builder()
                .menu(menuG())
                // .discountMenus(discountMenus())
                .build();
        return menuBoardRequest;
    }

    public MenuBoardRequest requestDiscountsD() {
        MenuBoardRequest menuBoardRequest = MenuBoardRequest.builder()
                .menu(menuH())
                // .discountMenus(discountMenus())
                .build();
        return menuBoardRequest;
    }
}
