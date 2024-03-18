package com.dish.perfect.menu.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dish.perfect.global.error.GlobalException;
import com.dish.perfect.global.error.exception.ErrorCode;
import com.dish.perfect.imageManager.domain.Image;
import com.dish.perfect.imageManager.service.ImageService;
import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.domain.repository.MenuRepository;
import com.dish.perfect.menu.dto.request.MenuRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MenuCoreService {

    private final MenuRepository menuRepository;
    private final ImageService imageService;
    
    public Menu createMenu(MenuRequest menuRequest) throws IOException{
        Menu menu = menuRequest.toEntity();
        validMenunameDuplicated(menu);
        String uploadImgUrl = imageService.uploadImg(menuRequest.getMenuImgFile());
        menu.addImgUrl(uploadImgUrl);
        Menu saved = menuRepository.save(menu);
        log.info("{}\nuploadImgUrl={}\nmenuSavedurl={}", saved.getMenuName(), uploadImgUrl,saved.getMenuImg().getImgUrl());
        return saved;

    }
    public void activeDiscount(final String menuName){
        Menu menu = findMenuByMenuName(menuName);
        menu.activeDiscount(true);
        log.info("{}/{}", menu.getMenuName(), menu.isDiscounted());
    }

    public void markMenuAsUnavailable(final String menuName){
        Menu menu = findMenuByMenuName(menuName);
        if(!menu.getAvailability().equals(Availability.UNAVAILABLE)){
            menu.markMenuAsUnavailable(Availability.UNAVAILABLE);
        }
        new GlobalException(ErrorCode.ALREADY_UNAVAILABLE_MENU, "이미 UNAVAILABLE한 메뉴입니다.");
        log.info("{}/{}", menu.getMenuName(), menu.isAvailability());
    }

    private Menu findMenuByMenuName(String menuName) {
        return menuRepository.findByMenuName(menuName);
    }

    private void validMenunameDuplicated(Menu menu){
        if(menuRepository.existsByMenuName(menu.getMenuName())){
            throw new GlobalException(ErrorCode.DUPLICATED_MENU, "해당 메뉴가 존재합니다.");
        }
    }
}
