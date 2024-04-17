package com.dish.perfect.menu.presentation;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.dto.request.MenuRequest;
import com.dish.perfect.menu.dto.response.MenuCommonResponse;
import com.dish.perfect.menu.dto.response.MenuDetailResponse;
import com.dish.perfect.menu.service.MenuCoreService;
import com.dish.perfect.menu.service.MenuPresentationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/menu")
public class MenuController {
    
    private MenuCoreService menuCoreService;
    private MenuPresentationService menuPresentationService;

    @GetMapping("/add")
    public String addMenuRequest() {
        return "menuRequest 페이지로 이동";
    }
    
    @PostMapping("/add")
    public ResponseEntity<Menu> addMenu(@RequestBody @Valid MenuRequest menuRequest) throws IOException {
        Menu menu = menuCoreService.createMenu(menuRequest);
        return ResponseEntity.ok(menu);
    }
    @GetMapping("/{menuName}")
    public ResponseEntity<MenuDetailResponse> getmenuInfo(@PathVariable("menuName") String menuName){
        MenuDetailResponse menu = menuPresentationService.getMenuInfo(menuName);
        return ResponseEntity.ok(menu);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MenuCommonResponse>> getAllMenu(){
        List<MenuCommonResponse> findAll = menuPresentationService.findAll();
        return ResponseEntity.ok(findAll);
    }

    @GetMapping("/available")
    public ResponseEntity<List<MenuDetailResponse>> getAllByAvailable() {
        List<MenuDetailResponse> findAllByAvailability = menuPresentationService.findAllByAvailability();
        return ResponseEntity.ok(findAllByAvailability);
    }

    @PatchMapping("/{menuName}/discount")
    public ResponseEntity<Void> discountMenu(@PathVariable("menuName")String menuName){
        menuCoreService.activeDiscount(menuName);
        MenuDetailResponse menuInfo = menuPresentationService.getMenuInfo(menuName);
        log.info("menu:{} / isDiscount:{}", menuInfo.getMenuName(), menuInfo.isDiscount());
        return ResponseEntity.ok().build();

    }

    @PatchMapping("/{menuName}/available")
    public ResponseEntity<Void> switchMenuAsUnavailable(@PathVariable("menuName")String menuName){
        menuCoreService.markMenuAsUnavailable(menuName);
        MenuDetailResponse menuInfo = menuPresentationService.getMenuInfo(menuName);
        log.info("menu:{} / {}", menuInfo.getMenuName(), menuInfo.getAvailability());
        return ResponseEntity.ok().build();
    }

    
    
}
