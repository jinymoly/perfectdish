package com.dish.perfect.menu.service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

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
            Menu menu = menuRepository.create(menuDto);
            log.info("save : menu={}", menu);
            return menu;
            
        } catch (IOException e) {
            log.error("IO 예외 발생: {}", e.getMessage());
            throw new RuntimeException("메뉴를 저장하는 동안 예외 발생!");
        }
    }
    
    public Menu findByMenuName(String menuName){
        try {
            log.info("menu={}", menuName);
            return menuRepository.findByName(menuName);
        } catch (NoSuchElementException | NullPointerException e){
            log.error("메뉴를 찾는 동안 예외 발생: {}", e.getMessage());
            throw new RuntimeException("해당 메뉴를 찾을 수 없음!", e);
        }
    }
    
    public List<Menu> findByCourseType(CourseType courseType){
        if(courseType == null){
            throw new IllegalArgumentException("courseType을 입력하세요.");
        }
        return menuRepository.findByCourseType(courseType);
    }

    // TODO 01 
    // 메뉴 삭제 - isVisible 옵션으로 수정? soft delete 

    // TODO 02
    // 메뉴 이미지 삭제 ? == 보류 ==
}
