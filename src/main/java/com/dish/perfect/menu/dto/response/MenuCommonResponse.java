package com.dish.perfect.menu.dto.response;

import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class MenuCommonResponse {
    
    private final String menuName;
    private final CourseType courseType;
    private final Integer price;

    private final Availability availability;
    private final boolean isDiscount;

    public static MenuCommonResponse fromMenuResponse(final Menu menu){
        return MenuCommonResponse.builder()
                    .menuName(menu.getMenuName())
                    .courseType(menu.getCourseType())
                    .price(menu.getPrice())
                    .availability(menu.getAvailability())
                    .isDiscount(menu.isDiscounted())
                    .build();
    }
}
