package com.dish.perfect.menu;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.dto.request.MenuRequest;

public class MenuFixture {

        public MenuRequest fixRequestA() {

                MockMultipartFile mockMFile = new MockMultipartFile("TestA", "TestMockA.jpeg", MediaType.IMAGE_JPEG_VALUE,
                                "TestMockA".getBytes());

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
                MockMultipartFile mockMFile = new MockMultipartFile("TestB", "TestMockB.jpeg", MediaType.IMAGE_JPEG_VALUE,
                                "TestMockB".getBytes());

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

        public MenuRequest fixRequestC() {
                MockMultipartFile mockMFile = new MockMultipartFile("TestC", "TestMockC.jpeg", MediaType.IMAGE_JPEG_VALUE,
                                "TestMockC".getBytes());

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

        public MenuRequest fixRequestD() {
                MockMultipartFile mockMFile = new MockMultipartFile("TestD", "TestMockD.png",
                                MediaType.IMAGE_PNG_VALUE, "TestMockD".getBytes());

                MenuRequest dtoD = MenuRequest.builder()
                                .title("아그작사과구름스테이크")
                                .description("특제 소스에 4시간 졸인 후 48시간 1도에서 숙성")
                                .courseType(CourseType.T_MAIN)
                                .price(52000)
                                .menuImgFile(mockMFile)
                                .availability(Availability.AVAILABLE)
                                .build();
                return dtoD;
        }

        public MenuRequest fixRequestE() {
                MockMultipartFile mockMFile = new MockMultipartFile("TestE", "TestMockE.png",
                                MediaType.IMAGE_PNG_VALUE, "TestMockE".getBytes());

                MenuRequest dtoE = MenuRequest.builder()
                                .title("로맨틱 허니 갈릭 쉬림프")
                                .description("신선한 새우에 특제 허니 갈릭 소스가 어우러진 로맨틱한 디너")
                                .courseType(CourseType.T_MAIN)
                                .price(45000)
                                .menuImgFile(mockMFile)
                                .availability(Availability.AVAILABLE)
                                .build();
                return dtoE;
        }

}
