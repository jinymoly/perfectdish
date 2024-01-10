package com.dish.perfect.menu.dto.response;

import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class MenuResponse {
    
    private final String menuName;
    private final CourseType courseType;
    private final String description;
    private final Integer price;

    private final String menuImgUrl; // TODO : uuid -> 메뉴 이름으로 변환 

    public static MenuResponse toMenuResponse(final Menu menu){
        return MenuResponse.builder()
                    .menuName(menu.getMenuName())
                    .courseType(menu.getCourseType())
                    .price(menu.getPrice())
                    .description(menu.getDescription())
                    .menuImgUrl(menu.getMenuImg().getStoredUrl())
                    .build();
    }
}
