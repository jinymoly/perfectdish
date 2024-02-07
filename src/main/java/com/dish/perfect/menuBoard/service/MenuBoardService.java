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

    public void saveCommonMenus(MenuBoardRequest mBoardRequest){
        if(mBoardRequest.getCommonMenus() == null){
            log.error("🚨 {}", "commMenu 생성 불가.");
            throw new GlobalException(ErrorCode.FAIL_CREATE_MENU_BOARD);
        }
        menuBoardRepository.addCommonMenu(mBoardRequest);
    }        

    public void saveDiscountMenus(MenuBoardRequest mBoardRequest){
        menuBoardRepository.addDiscountMenu(mBoardRequest);
    }


    public List<Menu> commonMenus(){
        if(commonMenus() == null){
            log.error("🚨 {}", "commonMenu를 찾을 수 없습니다.");
            throw new  GlobalException(ErrorCode.NOT_FOUND_MENU_BOARD_COMMONS);
        }
        return menuBoardRepository.findCommonMenus();
    }

    public List<Menu> discountMenus(){
        Optional<List<Menu>> optionalMenus = menuBoardRepository.findDiscountMenus();
        return optionalMenus.orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_MENU_BOARD_DISCOUNTS));
    }

    public List<Menu> getAllMenus(){
        try {
            return menuBoardRepository.findAllMenus();
        } catch (NoSuchElementException e) {
            log.error("🚨 {}", "menuBoard가 존재하지 않습니다.");
            throw new GlobalException(ErrorCode.NOT_FOUND_MENU_BOARD);
        }
    }

}
