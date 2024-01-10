package com.dish.perfect.menu.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
                                .build();
        
        String savedText = menu1.toString();

        log.info("{}", savedText.toString());
        String expected = "[EPPETIZER] Menu : 어니언 수프 || 최고급 버터로 4시간 정성스레 카라멜라이징한 양파로 만든 수프 (13000)";
        
        assertEquals(expected, savedText);
    }
}
