package com.dish.perfect.menu.domain.repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.domain.MenuImg;
import com.dish.perfect.menu.dto.request.MenuRequest;
import com.dish.perfect.menu.service.MenuImgStore;

@Repository
public class InMemoryMenuRepository implements MenuRepository{
    
    private final Map<String, Menu> menus = new HashMap<>();

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
        menus.put(menu.getMenuName(), menu);

        return menu;
    }
    
    @Override
    public Menu findByName(String menuName) {
        Stream<Entry<String, Menu>> menuStream = menus.entrySet().stream();
        return menuStream.filter(entry -> entry.getValue().getMenuName().equals(menuName))
                        .map(Map.Entry::getValue)
                        .findFirst()
                        .orElseThrow(() -> new NoSuchElementException("일치하는 메뉴가 없습니다."));
    }

    @Override
    public List<String> findByCourseType(CourseType type) {
        return menus.entrySet().stream()
                    .filter(entry -> entry.getValue().getCourseType().equals(type))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        menus.clear();
    }
    
    
}
