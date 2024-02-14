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
        commonMenus.add(menu);
        log.info("addCommonMenu() {}", commonMenus);
        return commonMenus;
    }

    @Override
    public Optional<List<Menu>> addDiscountMenu(MenuBoardRequest menuBoardRequest) {
        Menu menu = menuBoardRequest.getMenu();
            menu.addDiscount(true);
            discountMenus.ifPresentOrElse(discountMenus -> discountMenus.add(menu), 
                                            () -> { List<Menu> newDiscountMenu = new ArrayList<>();
                                                    newDiscountMenu.add(menu);
                                                    discountMenus = Optional.of(newDiscountMenu);
                                                });
        log.info("addDiscountMenu() {}", discountMenus);
        return discountMenus;
    }

    @Override
    public List<Menu> getCommonMenus() {
        log.info("getCommonMenus() {}", commonMenus);
        return commonMenus;
    }

    @Override
    public Optional<List<Menu>> getDiscountMenus() {
        log.info("getDiscountMenus() {}", discountMenus);
        return discountMenus;
    }

    @Override
    public List<Menu> getAllMenus() {
        if(!commonMenus.isEmpty()){
            allMenuList.addAll(commonMenus);
        }
        discountMenus.ifPresent(allMenuList::addAll);
        log.info("getAllMenus() {}", allMenuList);
        return allMenuList;
    }

    public void clear() {
        this.commonMenus.clear();
        Optional.empty();
    }
}
