package com.dish.perfect.menuBoard.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.MenuBoardFixture;
import com.dish.perfect.menuBoard.dto.request.MenuBoardRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MenuBoardRepositoryTest {

    @Autowired
    private InMemoryMenuBoardRepository menuBoardRepository;

    @Autowired
    private static MenuBoardFixture fixture = new MenuBoardFixture();

    public static MenuBoardRequest requestCommonsA() {
        MenuBoardRequest menuBoardRequest = MenuBoardRequest.builder()
                                                            .menu(fixture.menuA())
                                                            .commonMenus(fixture.commonMenus())
                                                            .build();
        return menuBoardRequest;
    }

    public static MenuBoardRequest requestCommonsB() {
        MenuBoardRequest menuBoardRequest = MenuBoardRequest.builder()
                                                            .menu(fixture.menuB())
                                                            .commonMenus(fixture.commonMenus())
                                                            .build();
        return menuBoardRequest;
    }

    public static MenuBoardRequest requestDiscountsA() {
        MenuBoardRequest menuBoardRequest = MenuBoardRequest.builder()
                                                            .menu(fixture.menuC())
                                                            .discountMenus(fixture.discountMenus())
                                                            .build();
        return menuBoardRequest;
    }

    public static MenuBoardRequest requestDiscountsB() {
        MenuBoardRequest menuBoardRequest = MenuBoardRequest.builder()
                                                            .menu(fixture.menuD())
                                                            .discountMenus(fixture.discountMenus())
                                                            .build();
        return menuBoardRequest;
    }

    @Test
    @DisplayName("common 메뉴 리스트 생성 및 조회")
    void createCommonMenus() {
        List<Menu> commons = menuBoardRepository.addCommonMenu(requestCommonsA());
        List<Menu> result = menuBoardRepository.findCommonMenus();

        assertEquals(commons, result);
    }

    @Test
    @DisplayName("discount 메뉴 리스트 생성 및 조회")
    void createDiscountMenus() {
        Optional<List<Menu>> discounts = menuBoardRepository.addDiscountMenu(requestDiscountsA());
        Optional<List<Menu>> result = menuBoardRepository.findDiscountMenus();
        
        assertEquals(discounts, result);
    }

    @Test
    @DisplayName("모든 메뉴 조회")
    void getAllMenuList() {
        menuBoardRepository.addCommonMenu(requestCommonsA());
        menuBoardRepository.addCommonMenu(requestCommonsB());
        menuBoardRepository.addDiscountMenu(requestDiscountsA());
        menuBoardRepository.addDiscountMenu(requestDiscountsB());

        List<Menu> findAllMenus = menuBoardRepository.findAllMenus();
        log.info("{}", findAllMenus);
    }
}
