package com.dish.perfect.menu.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.domain.repository.MenuRepositoryTest;

@SpringBootTest
@TestPropertySource(properties = {"file.dir=src/test/resources/img/"})
public class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    private MenuFixture fixture = new MenuFixture();

    @AfterEach
    void clear(){
        String dirPath = "src/test/resources/img/";
        MenuRepositoryTest.deleteFileAfterTest(dirPath);
    }

    @Test
    @DisplayName("메뉴 이름 조회시 찾는 메뉴가 없다")
    void findMenuByNameError() throws IOException{
        menuService.save(fixture.fixReqeustA());

        assertThrows(GlobalException.class, () -> {
            menuService.findByMenuName("없는 메뉴");
        });
    }

    @Test
    @DisplayName("코스 타입으로 메뉴 조회 시도")
    void findMenuByCourseType() throws IOException{
        Menu menuA = menuService.save(fixture.fixReqeustA());
        Menu menuB = menuService.save(fixture.fixRequestB());

        assertNotEquals(menuA.getCourseType(), CourseType.T_EPPETIZER);
        assertTrue(menuA.getCourseType() != menuB.getCourseType());
    }

    @Test
    @DisplayName("메뉴 주문 가능 여부 확인")
    void findMenuByAbailability() throws IOException{
        Menu menuA = menuService.save(fixture.fixReqeustA());
        Menu menuC = menuService.save(fixture.fixReqeustC());

        assertEquals(menuA.getAvailability(), Availability.UNAVAILABLE);
        assertEquals(menuC.getAvailability(), Availability.AVAILABLE);
    }

    @Test
    @DisplayName("discount 플래그 적용")
    void addDiscount() throws IOException{
        Menu menuA = menuService.save(fixture.fixReqeustA());
        Menu menuC = menuService.save(fixture.fixReqeustC());
        menuService.changeDiscount(menuC.getMenuName(), true);
        
        assertEquals(menuA.isDiscounted(), false);
        assertEquals(menuC.isDiscounted(), true);
    }
}
