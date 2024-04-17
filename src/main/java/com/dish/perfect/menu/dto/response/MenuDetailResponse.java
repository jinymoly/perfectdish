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
public class MenuDetailResponse {
    
    private final String menuName;
    private final CourseType courseType;
    private final String description;
    private final Integer price;

    private final String menuImgUrl;
    private final Availability availability;
    private final boolean isDiscount;

    public static MenuDetailResponse fromMenuResponse(final Menu menu){
        return MenuDetailResponse.builder()
                    .menuName(menu.getMenuName())
                    .courseType(menu.getCourseType())
                    .price(menu.getPrice())
                    .description(menu.getDescription())
                    .menuImgUrl(menu.getMenuImg().getImgUrl())
                    .availability(menu.getAvailability())
                    .isDiscount(menu.isDiscounted())
                    .build();
    }
}
