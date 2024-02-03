package com.dish.perfect.menuBoard.service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menuBoard.domain.repository.MenuBoardRepository;
import com.dish.perfect.menuBoard.dto.request.MenuBoardRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuBoardService {
    
    private final MenuBoardRepository menuBoardRepository;

    public void saveCommonMenus(MenuBoardRequest mBoardRequest){
        menuBoardRepository.addCommonMenu(mBoardRequest);
        
    }

    public void saveDiscountMenus(MenuBoardRequest mBoardRequest){
        menuBoardRepository.addDiscountMenu(mBoardRequest);
    }


    public List<Menu> commonMenus(){
        return menuBoardRepository.findCommonMenus();
    }

    public List<Menu> discountMenus(){
        Optional<List<Menu>> optionalMenus = menuBoardRepository.findDiscountMenus();
        return optionalMenus.orElseThrow(() -> new NoSuchElementException("오늘의 할인 메뉴가 존재하지 않습니다."));
    }

    public List<Menu> getAllMenus(){
        return menuBoardRepository.findAllMenus();
    }

}
