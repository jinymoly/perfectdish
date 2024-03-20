package com.dish.perfect.menu.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.dto.request.MenuRequest;
import com.dish.perfect.menu.dto.response.MenuResponse;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestPropertySource(properties = {"file.dir=src/test/resources/img/"})
@Slf4j
public class MenuPresentationServiceTest {

    @Autowired
    private MenuCoreService menuCoreService;

    @Autowired
    private MenuPresentationService menuPresentationService;

    private MenuFixture menuFixture = new MenuFixture();

    @BeforeEach
    void setData() throws IOException{
        MenuRequest menuE = menuFixture.fixRequestE();
        MenuRequest menuB = menuFixture.fixRequestB();
        MenuRequest menuF = menuFixture.fixRequestF();
        MenuRequest menuI = menuFixture.fixRequestI();
        MenuRequest menuC = menuFixture.fixRequestC();

        menuCoreService.createMenu(menuE);
        menuCoreService.createMenu(menuF);
        menuCoreService.createMenu(menuC);
        menuCoreService.createMenu(menuB);
        menuCoreService.createMenu(menuI);

        menuCoreService.markMenuAsUnavailable(menuC.getMenuName());
    }

    @Test
    @DisplayName("지금 주문 가능? 성능 조회")
    void findByAvailability() {
        
        long startTimeA = System.currentTimeMillis();
        List<MenuResponse> resultA = menuPresentationService.findAllByAvailability();
        long endTimeA = System.currentTimeMillis();
        long durationA = endTimeA - startTimeA;
        
        long startTimeB = System.currentTimeMillis();
        List<MenuResponse> resultB = menuPresentationService.findByAvailability();
        long endTimeB = System.currentTimeMillis();
        long durationB = endTimeB - startTimeB;
        
        for(MenuResponse menu : resultA){
            log.info("MENU_LIST_A={}/{}", menu.getMenuName(), menu.getAvailability());
        }
        for(MenuResponse menu : resultB){
            log.info("MENU_LIST_B={}/{}", menu.getMenuName(), menu.getAvailability());
        }
        log.info("resultA={}ms / resultB={}ms", durationA, durationB);

        assertThat(durationA > durationB).isTrue();
        
    }

    @Test
    @DisplayName("availability 먼저? courseType이 먼저? 성능 조회")
    void findByCourseType(){

        long startTimeA = System.currentTimeMillis();
        List<MenuResponse> epListA = menuPresentationService.findAllByCourseType(CourseType.T_MAIN);
        long endTimeA = System.currentTimeMillis();
        long durationA = endTimeA - startTimeA;
        
 
        long startTimeB = System.currentTimeMillis();
        List<MenuResponse> epListB = menuPresentationService.findByCourseType(CourseType.T_MAIN);
        long endTimeB = System.currentTimeMillis();
        long durationB = endTimeB - startTimeB;

        for(MenuResponse menu : epListA){
            log.info("MENU_LIST_A={}/{}", menu.getMenuName(), menu.getAvailability());
        }
        for(MenuResponse menu : epListB){
            log.info("MENU_LIST_B={}/{}", menu.getMenuName(), menu.getAvailability());
        }
        log.info("availabilityFirst={}ms / courseTypeFirst={}ms", durationA, durationB);
        
        assertThat(durationA > durationB).isTrue();

    }
}
