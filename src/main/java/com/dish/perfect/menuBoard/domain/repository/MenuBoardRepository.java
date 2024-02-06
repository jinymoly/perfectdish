package com.dish.perfect.menuBoard.domain.repository;

import java.util.List;
import java.util.Optional;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.dto.request.MenuBoardRequest;

public interface MenuBoardRepository {
    
    List<Menu> addCommonMenu(MenuBoardRequest menuBoardRequest);
    Optional<List<Menu>> addDiscountMenu(MenuBoardRequest menuBoardRequest);

    List<Menu> findCommonMenus();
    Optional<List<Menu>> findDiscountMenus();

    List<Menu> findAllMenus();


    
}
