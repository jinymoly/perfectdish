package com.dish.perfect.menuBoard.domain.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.MenuBoardFixture;

@SpringBootTest
public class MenuBoardRepositoryTest {

    @Autowired
    private InMemoryMenuBoardRepository menuBoardRepository;

    private MenuBoardFixture fixture = new MenuBoardFixture();

    @AfterEach
    void clearTest(){
        menuBoardRepository.clear();
    }
    
    @Test
    @DisplayName("common 메뉴 리스트 생성 및 조회")
    void createCommonMenus() {
        List<Menu> commonsA = menuBoardRepository.addCommonMenu(fixture.requestCommonsA());
        List<Menu> commonsB = menuBoardRepository.addCommonMenu(fixture.requestCommonsB());
        
        List<Menu> result = menuBoardRepository.getCommonMenus();
        
        assertTrue(result.containsAll(commonsA));
        assertTrue(result.containsAll(commonsB));
    }

    @Test
    @DisplayName("discount 메뉴 리스트 생성 및 조회")
    void createDiscountMenus() {
        Optional<List<Menu>> discountsA = menuBoardRepository.addDiscountMenu(fixture.requestDiscountsA());
        Optional<List<Menu>> discountsB = menuBoardRepository.addDiscountMenu(fixture.requestDiscountsB());
        
    Optional<List<Menu>> result = menuBoardRepository.getDiscountMenus();
        
        assertTrue(result.get().containsAll(discountsA.orElse(Collections.emptyList())));
        assertTrue(result.get().containsAll(discountsB.orElse(Collections.emptyList())));
    }

    @Test
    @DisplayName("모든 메뉴 조회")
    void getAllMenuList() {
        List<Menu> commonsA = menuBoardRepository.addCommonMenu(fixture.requestCommonsA());
        List<Menu> commonsB = menuBoardRepository.addCommonMenu(fixture.requestCommonsB());
        Optional<List<Menu>> discountsA = menuBoardRepository.addDiscountMenu(fixture.requestDiscountsA());
        Optional<List<Menu>> discountsB = menuBoardRepository.addDiscountMenu(fixture.requestDiscountsB());
        
        List<Menu> findAllMenus = menuBoardRepository.getAllMenus();

        assertTrue(findAllMenus.containsAll(discountsA.orElse(Collections.emptyList())));
        assertTrue(findAllMenus.containsAll(commonsB));
    }
    
}
