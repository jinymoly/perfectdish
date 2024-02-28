package com.dish.perfect.menuBoard.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.domain.repository.MenuBoardRepository;
import com.dish.perfect.menuBoard.dto.request.MenuBoardRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuBoardService {

    private final MenuBoardRepository menuBoardRepository;

    public List<Menu> saveCommonMenus(MenuBoardRequest mBoardRequest) {
        if (mBoardRequest.getMenu() == null) {
            log.error("🚨 {}", "commMenu 생성 불가.");
            throw new GlobalException(ErrorCode.FAIL_CREATE_MENU_BOARD);
        }
        if (!isMenuNameDuplicate(mBoardRequest.getMenu().getMenuName())) {
            List<Menu> newCommonMenu = menuBoardRepository.addCommonMenu(mBoardRequest);
            log.info("saveCommonMenus() {}", newCommonMenu);
            return newCommonMenu;
        } else {
            log.error("🚨 {}", "commMenu 생성 불가. 메뉴리스트에 추가할 메뉴가 이미 존재");
            throw new GlobalException(ErrorCode.FAIL_CREATE_MENU_BOARD);
        }
    }

    public Optional<List<Menu>> saveDiscountMenus(MenuBoardRequest mBoardRequest) {
        if (!isMenuNameDuplicate(mBoardRequest.getMenu().getMenuName())) {
            Optional<List<Menu>> newDiscountMenu = menuBoardRepository.addDiscountMenu(mBoardRequest);
            log.info("saveDiscountMenus() {}", newDiscountMenu);
            return newDiscountMenu;
        }else{
            log.error("🚨 {}", "discountMenu 생성 불가. 해당 메뉴가 이미 존재");
            throw new GlobalException(ErrorCode.FAIL_CREATE_MENU_BOARD);
        }

    }

    public List<Menu> findCommonMenus() {
        List<Menu> commonMenus = menuBoardRepository.getCommonMenus();
        if (commonMenus == null) {
            log.error("🚨 {}", "commonMenu를 찾을 수 없습니다.");
            throw new GlobalException(ErrorCode.NOT_FOUND_MENU_BOARD_COMMONS);
        }
        log.info("saveDiscountMenus() {}", commonMenus);
        return commonMenus;
    }

    public Optional<List<Menu>> findDiscountMenus() {
        Optional<List<Menu>> optionalMenus = menuBoardRepository.getDiscountMenus();
        log.info("findDiscountMenus() {}", optionalMenus);
        return optionalMenus;
    }

    public List<Menu> getAllMenus() {
        try {
            List<Menu> allMenus = menuBoardRepository.getAllMenus();
            log.info("getAllMenus() {}", allMenus);
            return allMenus;
        } catch (NoSuchElementException e) {
            log.error("🚨 {}", "menuBoard가 존재하지 않습니다.");
            throw new GlobalException(ErrorCode.NOT_FOUND_MENU_BOARD);
        }
    }

    // private List<String> getAllMenuName(String menuName) {
    //     List<Menu> menus = getAllMenus();
    //     List<String> menuNameList = new ArrayList<>();
    //     for (Menu menu : menus) {
    //         String nameByMenu = menu.getMenuName();
    //         menuNameList.add(nameByMenu);
    //     }
    //     return menuNameList;
    // }

    private boolean isMenuNameDuplicate(String menuName) {
        List<Menu> menus = getAllMenus();
        for(Menu menu : menus){
            if(menu.getMenuName().equals(menuName)){
                return true;
            }
        }
        return false;
        
    }

    public void clear(){
        menuBoardRepository.clear();
    }
    // 메뉴의 상태를 변경한다

}
