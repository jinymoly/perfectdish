package com.dish.perfect.menuBoard.domain.repository;

import java.util.List;
import java.util.Optional;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.dto.request.MenuBoardRequest;

public interface MenuBoardRepository {
    
    void addCommonMenu(MenuBoardRequest menuBoardRequest);
    void addDiscountMenu(MenuBoardRequest menuBoardRequest);

    List<Menu> findCommonMenus();
    Optional<List<Menu>> findDiscountMenus();

    List<Menu> findAllMenus();


    
}
