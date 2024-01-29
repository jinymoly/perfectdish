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

import com.dish.perfect.imageManager.ImageUtil;
import com.dish.perfect.imageManager.domain.ImageFile;
import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.dto.request.MenuRequest;

@Repository
public class InMemoryMenuRepository implements MenuRepository{
    
    private final Map<String, Menu> menus = new HashMap<>();

    @Autowired
    private ImageUtil imgStore;
    
    @Override
    public Menu save(MenuRequest menuRequestDto) throws IOException{

        ImageFile menuImg = imgStore.storeFile(menuRequestDto.getMenuImgFile());

        Menu menu = Menu.builder()
                        .menuName(menuRequestDto.getTitle())
                        .description(menuRequestDto.getDescription())
                        .courseType(menuRequestDto.getCourseType())
                        .price(menuRequestDto.getPrice())
                        .menuImg(menuImg)
                        .availability(menuRequestDto.getAvailability())
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
    public List<Menu> findByCourseType(CourseType type) {
        return menus.entrySet().stream()
                    .filter(entry -> entry.getValue().getCourseType().equals(type))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
    }
    
    
    
    @Override
    public void clear() {
        menus.clear();
    }

    @Override
    public List<Menu> findByAvaility(Availability availability) {
        return menus.entrySet().stream()
                    .filter(entry -> entry.getValue().getAvailability().equals(availability))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
    }

    // TODO - 메뉴 수정 (soft delete)
    // @Override
    // public Menu modify(MenuRequest menurequestDto) {
    //     Menu menu = Menu.builder().
    //     throw new UnsupportedOperationException("Unimplemented method 'modify'");
    // }
    
    
}
