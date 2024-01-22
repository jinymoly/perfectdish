package com.dish.perfect.menuBoard.domain;

import java.util.HashSet;
import java.util.Set;

import com.dish.perfect.menu.domain.Menu;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuBoard {
    
    private Long id;

    private Set<Menu> menuSet = new HashSet<>();

    @Builder
    private MenuBoard(Long id, Set<Menu> menuSet){
        this.id = id;
        this.menuSet = menuSet;
    }
}
