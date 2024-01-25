package com.dish.perfect.menu.domain.repository;

import java.io.IOException;
import java.util.List;

import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.dto.request.MenuRequest;

public interface MenuRepository {
    
    Menu save(MenuRequest menuRequestDto) throws IOException;

    //Menu modify(MenuRequest menurequestDto);

    Menu findByName(String menuName);
    
    List<Menu> findByCourseType(CourseType type);
    
    void clear();
    
}
