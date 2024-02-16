package com.dish.perfect.menuBoard.dto.response;

import java.util.List;
import java.util.Optional;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.domain.MenuBoard;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class MenuBoardResponse {
    
    private final List<Menu> commonMenus;
    private final Optional<List<Menu>> discountMenus;

    private final List<Menu> allMenuList;

    public static MenuBoardResponse toMenuResponse(final MenuBoard menuBoard){
        return MenuBoardResponse.builder()
                                .commonMenus(menuBoard.getCommonMenuList())
                                .discountMenus(menuBoard.getDiscountMenuList())
                                .allMenuList(menuBoard.getAllMenuList())
                                .build();
    }
}
