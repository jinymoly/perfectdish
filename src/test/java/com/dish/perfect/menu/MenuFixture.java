package com.dish.perfect.menu;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.dish.perfect.menu.domain.Availability;
import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.dto.request.MenuRequest;

public class MenuFixture {

        public MenuRequest fixRequestA() {
                MockMultipartFile mockMFile = new MockMultipartFile("TestA", "TestMockA.jpeg",
                                MediaType.IMAGE_JPEG_VALUE, "TestMockA".getBytes());
            
                MenuRequest dtoA = MenuRequest.builder()
                                .menuName("이탈리아식 스파게티")
                                .description("부드러운 스파게티면과 풍부한 토마토 소스가 어우러진 이탈리아 전통 요리")
                                .courseType(CourseType.T_MAIN)
                                .price(500000)
                                .menuImgFile(mockMFile)
                                .availability(Availability.AVAILABLE)
                                .build();
            
                return dtoA;
            }
            
            public MenuRequest fixRequestB() {
                MockMultipartFile mockMFile = new MockMultipartFile("TestB", "TestMockB.jpeg",
                                MediaType.IMAGE_JPEG_VALUE, "TestMockB".getBytes());
            
                MenuRequest dtoB = MenuRequest.builder()
                                .menuName("크림 브륄레")
                                .description("달콤한 바닐라 크림과 부드러운 푸딩이 담긴 고급스러운 프렌치 디저트")
                                .courseType(CourseType.T_DESSERT)
                                .price(24000)
                                .menuImgFile(mockMFile)
                                .availability(Availability.AVAILABLE)
                                .build();
                return dtoB;
            }
            
            public MenuRequest fixRequestC() {
                MockMultipartFile mockMFile = new MockMultipartFile("TestC", "TestMockC.jpeg",
                                MediaType.IMAGE_JPEG_VALUE, "TestMockC".getBytes());
            
                MenuRequest dtoC = MenuRequest.builder()
                                .menuName("허니 레몬 수비드 치킨 샐러드")
                                .description("상큼한 레몬과 달콤한 꿀이 어우러진 치킨 샐러드")
                                .courseType(CourseType.T_EPPETIZER)
                                .price(27000)
                                .menuImgFile(mockMFile)
                                .availability(Availability.AVAILABLE)
                                .build();
                return dtoC;
            }

        public MenuRequest fixRequestD() {
                MockMultipartFile mockMFile = new MockMultipartFile("TestD", "TestMockD.png",
                                MediaType.IMAGE_PNG_VALUE, "TestMockD".getBytes());

                MenuRequest dtoD = MenuRequest.builder()
                                .menuName("아그작사과구름스테이크")
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
                                .menuName("로맨틱 허니 갈릭 쉬림프")
                                .description("신선한 새우에 특제 허니 갈릭 소스가 어우러진 로맨틱한 디너")
                                .courseType(CourseType.T_MAIN)
                                .price(45000)
                                .menuImgFile(mockMFile)
                                .availability(Availability.AVAILABLE)
                                .build();
                return dtoE;
        }

        public MenuRequest fixRequestF() {
                MockMultipartFile mockMFile = new MockMultipartFile("TestF", "TestMockF.png",
                                MediaType.IMAGE_PNG_VALUE, "TestMockF".getBytes());

                MenuRequest dtoF = MenuRequest.builder()
                                .menuName("트러플 머쉬룸 스테이크")
                                .description("진한 트러플 향과 신선한 버섯이 어우러진 고급스러운 스테이크")
                                .courseType(CourseType.T_MAIN)
                                .price(55000)
                                .menuImgFile(mockMFile)
                                .availability(Availability.AVAILABLE)
                                .build();
                return dtoF;
        }

        public MenuRequest fixRequestG() {
                MockMultipartFile mockMFile = new MockMultipartFile("TestG", "TestMockG.png",
                                MediaType.IMAGE_PNG_VALUE, "TestMockG".getBytes());

                MenuRequest dtoG = MenuRequest.builder()
                                .menuName("시그니처 레몬 파스타")
                                .description("달콤한 레몬 향과 쫄깃한 파스타의 조화로운 맛")
                                .courseType(CourseType.T_MAIN)
                                .price(40000)
                                .menuImgFile(mockMFile)
                                .availability(Availability.AVAILABLE)
                                .build();
                return dtoG;
        }
        public MenuRequest fixRequestH() {
                MockMultipartFile mockMFile = new MockMultipartFile("TestH", "TestMockH.jpeg",
                                MediaType.IMAGE_JPEG_VALUE, "TestMockH".getBytes());
            
                MenuRequest dtoH = MenuRequest.builder()
                                .menuName("가든 샐러드")
                                .description("신선한 야채와 풍부한 드레싱이 어우러진 건강하고 맛있는 샐러드")
                                .courseType(CourseType.T_EPPETIZER)
                                .price(28000)
                                .menuImgFile(mockMFile)
                                .availability(Availability.AVAILABLE)
                                .build();
                return dtoH;
            }
            
            public MenuRequest fixRequestI() {
                MockMultipartFile mockMFile = new MockMultipartFile("TestI", "TestMockI.jpeg",
                                MediaType.IMAGE_JPEG_VALUE, "TestMockI".getBytes());
            
                MenuRequest dtoI = MenuRequest.builder()
                                .menuName("감바스 알 아히요")
                                .description("스페인 특색 있는 감바스 알 아히요의 풍부한 향과 맛")
                                .courseType(CourseType.T_EPPETIZER)
                                .price(35000)
                                .menuImgFile(mockMFile)
                                .availability(Availability.AVAILABLE)
                                .build();
                return dtoI;
            }

}
