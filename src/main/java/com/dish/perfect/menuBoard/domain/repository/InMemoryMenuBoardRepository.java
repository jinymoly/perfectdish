package com.dish.perfect.menuBoard.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.dto.request.MenuBoardRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class InMemoryMenuBoardRepository implements MenuBoardRepository {

    private List<Menu> commonMenus = new ArrayList<>();
    private Optional<List<Menu>> discountMenus = Optional.empty();

    List<Menu> allMenuList = new ArrayList<>();

    @Override
    public List<Menu> addCommonMenu(MenuBoardRequest menuBoardRequest) {
        Menu menu = menuBoardRequest.getMenu();
        List<Menu> newCommon = new ArrayList<>(commonMenus);
        newCommon.add(menu);
        commonMenus = newCommon;
        log.info("addCommonMenu() {}", commonMenus);
        return commonMenus;
    }

    @Override
    public Optional<List<Menu>> addDiscountMenu(MenuBoardRequest menuBoardRequest) {
        Menu menu = menuBoardRequest.getMenu();
        menu.addDiscount(true);
        discountMenus.ifPresentOrElse(discountMenus -> discountMenus.add(menu),
                () -> {
                    List<Menu> newDiscountMenu = new ArrayList<>();
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
        if (!commonMenus.isEmpty()) {
            allMenuList.addAll(commonMenus);
        }
        discountMenus.ifPresent(allMenuList::addAll);
        log.info("getAllMenus() {}", allMenuList);
        return allMenuList;
    }

    @Override
    public Menu getMenuByName(String menuName) {
        List<Menu> menuList = getAllMenus();
        for (Menu menu : menuList) {
            if (menu.getMenuName().equals(menuName)) {
                return menu;
            }
        }
        throw new GlobalException(ErrorCode.NOT_FOUND_MENU, "해당 메뉴가 존재하지 않습니다.");
    }

    public void clear() {
        this.commonMenus.clear();
        this.discountMenus.ifPresent(list -> list.clear());
        this.allMenuList.clear();
    }
}
