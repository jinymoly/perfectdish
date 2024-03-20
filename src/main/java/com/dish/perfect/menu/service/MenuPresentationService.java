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
import com.dish.perfect.menu.dto.response.MenuResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuPresentationService {

    private final MenuRepository menuRepository;

    public MenuResponse getMenuInfo(final String menuName) {
        Menu menu = menuRepository.findByMenuName(menuName);
        if (menu != null) {
            return MenuResponse.fromMenuResponse(menu);
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_MENU, "해당 메뉴가 존재하지 않습니다.");
        }
    }

    public List<MenuResponse> findAllByAvailability() {
        return menuRepository.findAll()
                .stream()
                .filter(menu -> menu.getAvailability().equals(Availability.AVAILABLE))
                .map(MenuResponse::fromMenuResponse)
                .toList();
    }

    /**
     * AVAILABLE 메뉴 리스트
     * 
     * @return
     */
    public List<MenuResponse> findByAvailability() {
        return menuRepository.findByAvailability(Availability.AVAILABLE)
                .stream()
                .map(MenuResponse::fromMenuResponse)
                .toList();
    }

    /**
     * AVAILABLE -> courseType
     */
    public List<MenuResponse> findAllByCourseType(CourseType type) {
        return menuRepository.findAll()
                .stream()
                .filter(Menu::isAvailability)
                .filter(menu -> menu.getCourseType().equals(type))
                .map(MenuResponse::fromMenuResponse)
                .toList();
    }

    /**
     * courseType -> AVAILABLE
     * 
     * @param type
     * @return
     */
    public List<MenuResponse> findByCourseType(CourseType type) {
        return menuRepository.findByCourseType(type)
                .stream()
                .filter(Menu::isAvailability)
                .map(MenuResponse::fromMenuResponse)
                .toList();
    }

    public List<MenuResponse> findAll() {
        return menuRepository.findAll()
                .stream()
                .map(MenuResponse::fromMenuResponse)
                .toList();
    }
}
