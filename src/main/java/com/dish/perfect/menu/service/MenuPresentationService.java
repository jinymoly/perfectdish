package com.dish.perfect.menu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.domain.repository.MenuRepository;
import com.dish.perfect.menu.dto.response.MenuCommonResponse;
import com.dish.perfect.menu.dto.response.MenuDetailResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuPresentationService {

    private final MenuRepository menuRepository;

    public MenuDetailResponse getMenuInfo(final String menuName) {
        Menu menu = menuRepository.findByMenuName(menuName);
        if (menu != null) {
            return MenuDetailResponse.fromMenuResponse(menu);
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_MENU, "해당 메뉴가 존재하지 않습니다.");
        }
    }

    public List<MenuDetailResponse> findAllByAvailability() {
        return menuRepository.findAll()
                .stream()
                .filter(menu -> menu.getAvailability().equals(Availability.AVAILABLE))
                .map(MenuDetailResponse::fromMenuResponse)
                .toList();
    }

    /**
     * AVAILABLE 메뉴 리스트
     * 
     * @return
     */
    public List<MenuCommonResponse> findByAvailability() {
        return menuRepository.findByAvailability(Availability.AVAILABLE)
                .stream()
                .map(MenuCommonResponse::fromMenuResponse)
                .toList();
    }

    /**
     * AVAILABLE -> courseType
     */
    public List<MenuDetailResponse> findAllByCourseType(CourseType type) {
        return menuRepository.findAll()
                .stream()
                .filter(Menu::isAvailability)
                .filter(menu -> menu.getCourseType().equals(type))
                .map(MenuDetailResponse::fromMenuResponse)
                .toList();
    }

    /**
     * courseType -> AVAILABLE
     * 
     * @param type
     * @return
     */
    public List<MenuCommonResponse> findByCourseType(CourseType type) {
        return menuRepository.findByCourseType(type)
                .stream()
                .filter(Menu::isAvailability)
                .map(MenuCommonResponse::fromMenuResponse)
                .toList();
    }

    public List<MenuCommonResponse> findAll() {
        return menuRepository.findAll()
                .stream()
                .map(MenuCommonResponse::fromMenuResponse)
                .toList();
    }

    /**
     * 메뉴가 있는지 확인 (OrderItem)
     */
    public boolean menuNameExists(String menuName){
        Menu menu = menuRepository.findByMenuName(menuName);
        return menu != null;
    }

    /**
     * 메뉴 이름으로 메뉴 정보 불러오기
     * @param menuName
     * @return
     */
    public Menu getMenuFrom(String menuName){
        Menu menu = menuRepository.findByMenuName(menuName);
        return menu;
    }
}
