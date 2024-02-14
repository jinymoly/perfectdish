package com.dish.perfect.menuBoard.dto.request;

import java.util.List;
import java.util.Optional;

import com.dish.perfect.menu.domain.Menu;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuBoardRequest {
    
    private final Menu menu;
    private List<Menu> commonMenus;
    private Optional<List<Menu>> discountMenus;

    @Builder
    private MenuBoardRequest(Menu menu){
        this.menu = menu;
    }

}
