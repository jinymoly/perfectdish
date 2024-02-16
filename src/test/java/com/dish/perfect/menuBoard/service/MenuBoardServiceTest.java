package com.dish.perfect.menuBoard.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.domain.repository.MenuRepositoryTest;
import com.dish.perfect.menuBoard.MenuBoardFixture;

@SpringBootTest
@TestPropertySource(properties = { "file.dir=src/test/resources/img/" })
public class MenuBoardServiceTest {

    @Autowired
    private MenuBoardService service;

    private MenuBoardFixture fixture = new MenuBoardFixture();

    @AfterEach
    void clear() {
        String dirPath = "src/test/resources/img/";
        MenuRepositoryTest.deleteFileAfterTest(dirPath);
        service.clear();
    }

    @Test
    @DisplayName("메뉴 보드 commonMenus 생성과 조회")
    void saveCommonMenus() throws IOException {
        List<Menu> commonMenuA = service.saveCommonMenus(fixture.requestCommonsA());
        List<Menu> commonMenuB = service.saveCommonMenus(fixture.requestCommonsB());
        List<Menu> commonMenuC = service.saveCommonMenus(fixture.requestCommonsC());

        List<Menu> expect = service.findCommonMenus();

        assertTrue(expect.containsAll(commonMenuA));
        assertTrue(expect.containsAll(commonMenuB));
        assertTrue(expect.containsAll(commonMenuC));
    }

    @Test
    @DisplayName("메뉴 보드 discountMenus 생성과 조회")
    void saveDiscountMenus() throws IOException {
        Optional<List<Menu>> discountMenusA = service.saveDiscountMenus(fixture.requestDiscountsA());
        Optional<List<Menu>> discountMenusB = service.saveDiscountMenus(fixture.requestDiscountsB());
        Optional<List<Menu>> discountMenusC = service.saveDiscountMenus(fixture.requestDiscountsC());

        List<Menu> expect = service.findDiscountMenus().orElse(Collections.emptyList());

        assertTrue(expect.containsAll(discountMenusA.orElse(Collections.emptyList())));
        assertTrue(expect.containsAll(discountMenusB.orElse(Collections.emptyList())));
        assertTrue(expect.containsAll(discountMenusC.orElse(Collections.emptyList())));
    }

    @Test
    @DisplayName("메뉴 보드 discountMenus의 discount flag 동작 여부")
    void discountFlag() throws IOException {
        service.saveDiscountMenus(fixture.requestDiscountsA());
        service.saveDiscountMenus(fixture.requestDiscountsB());
        service.saveDiscountMenus(fixture.requestDiscountsC());

        List<Menu> expect = service.findDiscountMenus().orElse(Collections.emptyList());

        assertTrue(expect.stream().allMatch(Menu::isDiscounted));
    }

    @Test
    @DisplayName("메뉴 보드의 모든 메뉴 조회")
    void getAllMenus() {
        List<Menu> commonMenuA = service.saveCommonMenus(fixture.requestCommonsA());
        service.saveCommonMenus(fixture.requestCommonsB());
        Optional<List<Menu>> discountMenus = service.saveDiscountMenus(fixture.requestDiscountsA());
        service.saveDiscountMenus(fixture.requestDiscountsB());
        service.saveDiscountMenus(fixture.requestDiscountsC());

        List<Menu> all = service.getAllMenus();

        assertTrue(all.containsAll(commonMenuA));
        assertTrue(all.containsAll(discountMenus.orElseThrow()));

    }

}
