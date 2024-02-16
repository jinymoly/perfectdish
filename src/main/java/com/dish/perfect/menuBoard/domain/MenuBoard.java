package com.dish.perfect.menuBoard.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Override
    public String toString(){
        return "🥗" + currentDateFommater(createAt) + "의 메뉴 리스트 " + commonMenuList + " ✨오늘의 할인 메뉴✨ " + discountMenuList + "입니다.";
    }

    private String currentDateFommater(LocalDateTime today){
        return today.format(DateTimeFormatter.ofPattern("MM월dd일"));
    }
}
