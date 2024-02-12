package com.dish.perfect.menuBoard.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.dto.request.MenuBoardRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class InMemoryMenuBoardRepository implements MenuBoardRepository{

    private List<Menu> commonMenus = new ArrayList<>();
    private Optional<List<Menu>> discountMenus = Optional.empty();
    
    List<Menu> allMenuList = new ArrayList<>();

    @Override
    public List<Menu> addCommonMenu(MenuBoardRequest menuBoardRequest) {
        Menu menu = menuBoardRequest.getMenu();
        if(commonMenus.isEmpty()){
            commonMenus = new ArrayList<>();
        }
        commonMenus.add(menu);
        log.info("{}", commonMenus);
        return commonMenus;
    }

    @Override
    public Optional<List<Menu>> addDiscountMenu(MenuBoardRequest menuBoardRequest) {
        Menu menu = menuBoardRequest.getMenu();
        if(discountMenus.isEmpty()){
            List<Menu> newList = new ArrayList<>();
            menu.addDiscount(true);
            newList.add(menu);
            discountMenus = Optional.of(newList);
        } else {
            discountMenus.ifPresent(menus -> menus.add(menu));
        }
        log.info("{}", discountMenus);
        return discountMenus;
    }

    @Override
    public List<Menu> getCommonMenus() {
        log.info("{}", commonMenus);
        return commonMenus;
    }

    @Override
    public Optional<List<Menu>> getDiscountMenus() {
        log.info("{}", commonMenus);
        return discountMenus;
    }

    @Override
    public List<Menu> getAllMenus() {
        if(!commonMenus.isEmpty()){
            allMenuList.addAll(commonMenus);
        }
        discountMenus.ifPresent(allMenuList::addAll);
        log.info("{}", allMenuList);
        return allMenuList;
    }

    public void clear() {
        this.commonMenus.clear();
        Optional.empty();
    }
}
