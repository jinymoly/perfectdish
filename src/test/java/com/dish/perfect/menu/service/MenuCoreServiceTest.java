package com.dish.perfect.menu.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.dto.request.MenuRequest;
import com.dish.perfect.menu.dto.response.MenuResponse;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {"file.dir=src/test/resources/img/"})
@Slf4j
public class MenuCoreServiceTest {

    @Autowired
    private MenuCoreService menuCoService;

    @Autowired
    private MenuPresentationService menuPrService;

    private MenuFixture menuFixture = new MenuFixture();

    @Test
    @DisplayName("메뉴를 생성")
    void createMenu() throws IOException{
        MenuRequest requestA = menuFixture.fixRequestA();
        MenuRequest requestB = menuFixture.fixRequestB();
        Menu menuA = menuCoService.createMenu(requestA);
        Menu menuB = menuCoService.createMenu(requestB);

        List<MenuResponse> findAll = menuPrService.findAll();
        
        Assertions.assertThat(findAll.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("저장된 메뉴 상세 정보")
    void getMenuInfo() throws IOException{
        MenuRequest requestC = menuFixture.fixRequestC();
        menuCoService.createMenu(requestC);
        
        MenuResponse menuInfo = menuPrService.getMenuInfo(requestC.getMenuName());
        log.info("menuInfo={} menuImgUrl={}", menuInfo.getMenuName(), menuInfo.getMenuImgUrl());
    }

    @Test
    @DisplayName("할인 메뉴로 설정")
    void setDiscountMenu() throws IOException{
        MenuRequest requestD = menuFixture.fixRequestD();
        menuCoService.createMenu(requestD);

        menuCoService.activeDiscount(requestD.getMenuName());
        MenuResponse resultMenu = menuPrService.getMenuInfo(requestD.getMenuName());
        assertTrue(resultMenu.isDiscount());
    }

    @Test
    @DisplayName("주문 불가 메뉴로 설정")
    void setAvailable() throws IOException{
        MenuRequest requestD = menuFixture.fixRequestD();
        menuCoService.createMenu(requestD);

        menuCoService.markMenuAsUnavailable(requestD.getMenuName());
        MenuResponse resultMenu = menuPrService.getMenuInfo(requestD.getMenuName());

        assertEquals(resultMenu.getAvailability(), Availability.UNAVAILABLE);
    }
}
