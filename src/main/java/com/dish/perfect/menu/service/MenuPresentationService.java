package com.dish.perfect.menu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
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
        if(menu != null){
            return MenuResponse.fromMenuResponse(menu);
        } else {
            throw new GlobalException(ErrorCode.NOT_FOUND_MENU, "해당 메뉴가 존재하지 않습니다.");
        }
    }

    public List<MenuResponse> findAllByAvailability() {
        return menuRepository.findAll()
                            .stream()
                            .filter(Menu::isAvailability)
                            .map(MenuResponse::fromMenuResponse)
                            .toList();
    }

    public List<MenuResponse> findAll(){
        return menuRepository.findAll()
                            .stream()
                            .map(MenuResponse::fromMenuResponse)
                            .toList();
    }
}
