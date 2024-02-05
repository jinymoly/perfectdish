package com.dish.perfect.menuBoard.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.dto.request.MenuBoardRequest;

@Repository
public class InMemoryMenuBoardRepository implements MenuBoardRepository{

    private List<Menu> commonMenus;
    private Optional<List<Menu>> discountMenus;
    
    List<Menu> allMenuList;

    @Override
    public void addCommonMenu(MenuBoardRequest menuBoardRequest) {
        Menu menu = menuBoardRequest.getMenu();
        if(commonMenus.isEmpty()){
            commonMenus = new ArrayList<>();
        }
        commonMenus.add(menu);
    }

    @Override
    public void addDiscountMenu(MenuBoardRequest menuBoardRequest) {
        Menu menu = menuBoardRequest.getMenu();
        if(discountMenus.isEmpty()){
            List<Menu> newList = new ArrayList<>();
            newList.add(menu);
            discountMenus = Optional.of(newList);
        } else {
            discountMenus.ifPresent(menus -> menus.add(menu));
        }
    }

    @Override
    public List<Menu> findCommonMenus() {
        return commonMenus;
    }

    @Override
    public Optional<List<Menu>> findDiscountMenus() {
        return discountMenus;
    }

    @Override
    public List<Menu> findAllMenus() {
        if(allMenuList.isEmpty()){
            allMenuList = new ArrayList<>();
        } else{
            allMenuList.addAll(commonMenus);
            discountMenus.ifPresent(allMenuList::addAll);
        }
        return allMenuList;
    }
}
