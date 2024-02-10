package com.dish.perfect.menuBoard.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.MenuBoardFixture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MenuBoardRepositoryTest {

    @Autowired
    private InMemoryMenuBoardRepository menuBoardRepository;

    private MenuBoardFixture fixture = new MenuBoardFixture();

    @AfterEach
    void clear(){
        menuBoardRepository.clear();
    }
    
    @Test
    @DisplayName("common 메뉴 리스트 생성 및 조회")
    void createCommonMenus() {
        List<Menu> commons = menuBoardRepository.addCommonMenu(fixture.requestCommonsA());
        List<Menu> result = menuBoardRepository.getCommonMenus();
        
        assertEquals(commons, result);
    }

    @Test
    @DisplayName("discount 메뉴 리스트 생성 및 조회")
    void createDiscountMenus() {
        Optional<List<Menu>> discounts = menuBoardRepository.addDiscountMenu(fixture.requestDiscountsA());
        Optional<List<Menu>> result = menuBoardRepository.getDiscountMenus();
        
        assertEquals(discounts, result);
    }

    @Test
    @DisplayName("모든 메뉴 조회")
    void getAllMenuList() {
        menuBoardRepository.addCommonMenu(fixture.requestCommonsA());
        menuBoardRepository.addCommonMenu(fixture.requestCommonsB());
        menuBoardRepository.addDiscountMenu(fixture.requestDiscountsA());
        menuBoardRepository.addDiscountMenu(fixture.requestDiscountsB());
        
        List<Menu> findAllMenus = menuBoardRepository.getAllMenus();
        log.info("{}", findAllMenus);
    }

    
}
