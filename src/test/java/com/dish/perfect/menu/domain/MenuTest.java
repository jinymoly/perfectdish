package com.dish.perfect.menu.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dish.perfect.imageManager.domain.Image;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuTest {

    @Test
    @DisplayName("toString 확인")
    void testToString() {
        Menu menu1 = Menu.builder()
                                .courseType(CourseType.T_EPPETIZER)
                                .menuName("어니언 수프")
                                .description("최고급 버터로 4시간 정성스레 카라멜라이징한 양파로 만든 수프")
                                .price(13000)
                                .isDiscounted(false)
                                .availability(Availability.AVAILABLE)
                                .menuImg(new Image("imgUrl.jpg"))
                                .build();
        
        String savedText = menu1.toString();

        log.info("{}", savedText.toString());
        String expected = "[EPPETIZER]\n" + 
                        "menuName=어니언 수프/AVAILABLE\n" +
                        "description=최고급 버터로 4시간 정성스레 카라멜라이징한 양파로 만든 수프(13000)\n" +
                        "img=imgUrl.jpg\n" +
                        "isDiscounted=false";
        
        assertEquals(expected, savedText);
    }
}
