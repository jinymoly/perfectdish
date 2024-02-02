package com.dish.perfect.menuBoard.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.dish.perfect.menu.domain.Menu;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuBoard {
    
    private List<Menu> commonMenuList;

    private Optional<List<Menu>> discountMenuList;

    private List<Menu> allMenuList;
    
    private LocalDateTime createAt;

    @Builder
    private MenuBoard(List<Menu> commonMenuList, Optional<List<Menu>> discountMenuList, List<Menu> allMenuList, LocalDateTime createAt){
        this.commonMenuList = commonMenuList;
        this.discountMenuList = discountMenuList;
        this.allMenuList = allMenuList;
        this.createAt = createAt;
    }
}
