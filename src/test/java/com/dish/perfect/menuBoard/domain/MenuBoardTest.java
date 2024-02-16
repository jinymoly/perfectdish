package com.dish.perfect.menuBoard.domain;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dish.perfect.menuBoard.MenuBoardFixture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuBoardTest {

  @Autowired
  private MenuBoardFixture fixture = new MenuBoardFixture();

  @Test
  @DisplayName("menuBoard toString")
  void testToString(){
       MenuBoard menuBoard = MenuBoard.builder()
                                      .commonMenuList(fixture.commonMenus())
                                      .discountMenuList(fixture.discountMenus())
                                      .createAt(LocalDateTime.now())
                                      .build();
        String text = menuBoard.toString();
        log.info("{}", text);
                                      

    }

  
}
