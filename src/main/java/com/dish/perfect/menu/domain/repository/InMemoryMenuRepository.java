package com.dish.perfect.menu.domain.repository;

import java.io.IOException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.domain.MenuImg;
import com.dish.perfect.menu.dto.request.MenuRequest;
import com.dish.perfect.menu.service.MenuImgStore;

@Repository
public class InMemoryMenuRepository implements MenuRepository{
    
    private final Set<Menu> menus = new HashSet<>();

    @Autowired
    private MenuImgStore imgStore;
    
    @Override
    public Menu save(MenuRequest menuRequestDto) throws IOException{

        MenuImg menuImg = imgStore.storeFile(menuRequestDto.getMenuImgFile());

        Menu menu = Menu.builder()
                        .menuName(menuRequestDto.getTitle())
                        .description(menuRequestDto.getDescription())
                        .courseType(menuRequestDto.getCourseType())
                        .price(menuRequestDto.getPrice())
                        .menuImg(menuImg)
                        .build();
        menus.add(menu);

        return menu;
    }
    
    @Override
    public Menu findByName(String menuName) {
        return menus.stream()
                    .filter(menu -> menu.getMenuName().equals(menuName))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("일치하는 메뉴가 없습니다."));
    }

    @Override
    public Set<Menu> findByCourseType(CourseType type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCourseType'");
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }
    
}