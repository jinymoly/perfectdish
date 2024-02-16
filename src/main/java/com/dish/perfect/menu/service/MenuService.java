package com.dish.perfect.menu.service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.domain.repository.MenuRepository;
import com.dish.perfect.menu.dto.request.MenuRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService {
    
    private final MenuRepository menuRepository;

    public Menu save(MenuRequest menuDto) throws IOException{
        try {
            Menu menu = menuRepository.save(menuDto);
            log.info("save menu={}", menu);
            return menu;
            
        } catch (IOException e) {
            log.error("🚨 {}", e.getMessage());
            throw new GlobalException(ErrorCode.FAIL_CREATE_MENU);
        }
    }
    
    public Menu findByMenuName(String menuName){
        try {
            return menuRepository.findByName(menuName);
        } catch (NoSuchElementException | NullPointerException e){
            log.error("🚨 {}", e.getMessage());
            throw new GlobalException(ErrorCode.NOT_FOUND_MENU);
        }
    }
    
    public List<Menu> findByCourseType(CourseType courseType){
        if(courseType == null){
            log.error("🚨 {}", "courseType이 올바르지 않습니다.");
            throw new GlobalException(ErrorCode.INVALID_INPUT_ERROR);
        }
        return menuRepository.findByCourseType(courseType);
    }

    public List<Menu> findByAvailability(Availability availability){
        if(availability == null){
            log.error("🚨 {}", "availability가 올바르지 않습니다.");
            throw new GlobalException(ErrorCode.INVALID_INPUT_ERROR);
        }
        return menuRepository.findByAvaility(availability);
    }

    public void changeDiscount(String menuName, boolean isDiscount){
        Menu menu = menuRepository.findByName(menuName);
        menu.addDiscount(isDiscount);
        log.info("[{}] is discount menu.", menu.getMenuName());
    }
    // TODO 01 
    // 메뉴 삭제 - isVisible 옵션으로 수정? soft delete 

    // TODO 02
    // 메뉴 이미지 삭제 ? == 보류 ==
}
