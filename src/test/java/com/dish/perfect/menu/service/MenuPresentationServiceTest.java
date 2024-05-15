package com.dish.perfect.menu.service;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.repository.MenuRepository;
import com.dish.perfect.menu.dto.request.MenuRequest;
import com.dish.perfect.menu.dto.response.MenuCommonResponse;
import com.dish.perfect.menu.dto.response.MenuDetailResponse;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestPropertySource(properties = {"file.dir=src/test/resources/img/"})
@Slf4j
public class MenuPresentationServiceTest {

    @Autowired
    private MenuCoreService menuCoreService;

    @Autowired
    private MenuPresentationService menuPresentationService;

    @Autowired
    private MenuRepository menuRepository;

    private MenuFixture menuFixture = new MenuFixture();

    @BeforeEach
    void setData() throws IOException{
        menuRepository.deleteAllInBatch();

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

    @AfterEach
    void clear(){
        menuRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("지금 주문 가능? 성능 조회: all->availability / availability")
    void findByAvailability() {
        
        long startTimeA = System.currentTimeMillis();
        List<MenuDetailResponse> resultA = menuPresentationService.findAllByAvailability();
        long endTimeA = System.currentTimeMillis();
        long durationA = endTimeA - startTimeA;
        
        long startTimeB = System.currentTimeMillis();
        List<MenuCommonResponse> resultB = menuPresentationService.findByAvailability();
        long endTimeB = System.currentTimeMillis();
        long durationB = endTimeB - startTimeB;
        
        for(MenuDetailResponse menu : resultA){
            log.info("MENU_LIST_A={}/{}", menu.getMenuName(), menu.getAvailability());
        }
        for(MenuCommonResponse menu : resultB){
            log.info("MENU_LIST_B={}/{}", menu.getMenuName(), menu.getAvailability());
        }
        log.info("🚨resultA={}ms / resultB={}ms", durationA, durationB);

        //assertThat(durationA < durationB).isTrue();
        
    }

    @Test
    @DisplayName("availability 먼저? courseType이 먼저? 성능 조회")
    void findByCourseType(){

        long startTimeA = System.currentTimeMillis();
        List<MenuDetailResponse> epListA = menuPresentationService.findAllByCourseType(CourseType.T_MAIN);
        long endTimeA = System.currentTimeMillis();
        long availabilityFirst = endTimeA - startTimeA;
        
 
        long startTimeB = System.currentTimeMillis();
        List<MenuCommonResponse> epListB = menuPresentationService.findByCourseType(CourseType.T_MAIN);
        long endTimeB = System.currentTimeMillis();
        long courseTypeFirst = endTimeB - startTimeB;

        for(MenuDetailResponse menu : epListA){
            log.info("MENU_LIST_A={}/{}", menu.getMenuName(), menu.getAvailability());
        }
        for(MenuCommonResponse menu : epListB){
            log.info("MENU_LIST_B={}/{}", menu.getMenuName(), menu.getAvailability());
        }
        log.info("🤡 availabilityFirst={}ms / courseTypeFirst={}ms", availabilityFirst, courseTypeFirst);

    }
}
