package com.dish.perfect.menu;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.dto.request.MenuRequest;

public class MenuFixture {

    public MenuRequest fixReqeustA() {

        MockMultipartFile mockMFile = new MockMultipartFile("Test", "TestMock.jpeg", MediaType.IMAGE_JPEG_VALUE,
                "TestMock".getBytes());

        MenuRequest dtoA = MenuRequest.builder()
                .title("이것은 A메뉴다")
                .description("이것은 메뉴 설명")
                .courseType(CourseType.T_MAIN)
                .price(500000)
                .menuImgFile(mockMFile)
                .availability(Availability.UNAVAILABLE)
                .build();

        return dtoA;
    }

    public MenuRequest fixRequestB() {
        MockMultipartFile mockMFile = new MockMultipartFile("Test", "TestMock.jpeg", MediaType.IMAGE_JPEG_VALUE,
                "TestMock".getBytes());

        MenuRequest dtoB = MenuRequest.builder()
                .title("이것은 B메뉴다")
                .description("이것은 메뉴 설명")
                .courseType(CourseType.T_DESSERT)
                .price(24000)
                .menuImgFile(mockMFile)
                .availability(Availability.UNAVAILABLE)
                .build();
        return dtoB;
    }

    public MenuRequest fixReqeustC() {
        MockMultipartFile mockMFile = new MockMultipartFile("Test", "TestMock.jpeg", MediaType.IMAGE_JPEG_VALUE,
                "TestMock".getBytes());

        MenuRequest dtoC = MenuRequest.builder()
                .title("이것은 C메뉴다")
                .description("이것은 메뉴 설명")
                .courseType(CourseType.T_MAIN)
                .price(60000)
                .menuImgFile(mockMFile)
                .availability(Availability.AVAILABLE)
                .build();
        return dtoC;
    }

}
