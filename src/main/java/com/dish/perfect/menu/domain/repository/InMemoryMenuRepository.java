package com.dish.perfect.menu.domain.repository;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.imageManager.ImageUtil;
import com.dish.perfect.imageManager.domain.ImageFile;
import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.dto.request.MenuRequest;

@Repository
public class InMemoryMenuRepository implements MenuRepository {

    private final Map<String, Menu> menus = new HashMap<>();

    @Autowired
    private ImageUtil imgStore;

    @Override
    public Menu save(MenuRequest menuRequestDto) throws IOException {
        for(String menuName : menus.keySet()){
            if(menuName.equals(menuRequestDto.getTitle())){
                throw new GlobalException(ErrorCode.DUPLICATED_MENU, "이미 존재하는 메뉴입니다.");
            }
        }
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
        for(String name : menus.keySet()){
            if(name.equals(menuName)){
                return menus.get(name);
            }
        }
        throw new GlobalException(ErrorCode.NOT_FOUND_MENU, "일치하는 메뉴가 없습니다.");
    }

    @Override
    public List<Menu> findByCourseType(CourseType type) {
        List<Menu> resultMenu = menus.entrySet().stream()
                .filter(entry -> entry.getValue().getCourseType().equals(type))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        
        return resultMenu == null ? Collections.emptyList() : resultMenu;
    }

    @Override
    public List<Menu> findByAvaility(Availability availability) {
        List<Menu> resultMenu = menus.entrySet().stream()
                .filter(entry -> entry.getValue().getAvailability().equals(availability))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        return resultMenu == null ? Collections.emptyList() : resultMenu;
    }

    @Override 
    public void updateMenu(Menu menu){
        for(String menuName : menus.keySet()){
            if(menuName.equals(menu.getMenuName())){
                menus.put(menuName, menu);
            }
        }
    }

    @Override
    public void clear() {
        menus.clear();
    }

    // TODO - 메뉴 수정 (soft delete)
    // @Override
    // public Menu modify(MenuRequest menurequestDto) {
    // Menu menu = Menu.builder().
    // throw new UnsupportedOperationException("Unimplemented method 'modify'");
    // }

}
