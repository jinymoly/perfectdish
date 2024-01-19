package com.dish.perfect.menu.domain.repository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;

import com.dish.perfect.menu.domain.CourseType;
import com.dish.perfect.menu.domain.Menu;
import com.dish.perfect.menu.domain.MenuImg;
import com.dish.perfect.menu.dto.request.MenuRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {"file.dir=src/test/resources/img/"})
public class InMemoryMenuRepositoryTest {
    
    @Autowired
    private InMemoryMenuRepository menuRepository;

    @AfterEach
    void clearMenuImgInTest(){
        String dirPath = "src/test/resources/img/";
        deleteFileAfterTest(dirPath);
    }

    @Test
    @DisplayName("hashMap과 hashSet의 속도 비교")
    void which_collection_is_better() throws IOException {
        int virtualMenus = 1000;

        long mapPerformance = hashMapPerformance(virtualMenus);
        long setPerformance = hashSetPerformance(virtualMenus);

        log.info("mapPerformance={}", mapPerformance+"ms");
        log.info("setPerformance={}", setPerformance+"ms");
    }

    /**
     * 테스트 종료 후 생성된 이미지 파일을 삭제
     */
    private void deleteFileAfterTest(String dirPath){
        File dirFile = new File(dirPath);
        File[] savedTestFiles = dirFile.listFiles();
        if(savedTestFiles != null){
            for(File file : savedTestFiles){
                file.delete();
            }
        }

    }
    /**
     * hashMap의 성능을 확인
     * @param virtualMenus
     * @return
     * @throws IOException
     */
    private long hashMapPerformance(int virtualMenus) throws IOException{
        long startTime = System.currentTimeMillis();

        Map<Long, Menu> menuMap = new HashMap<>();
        for(int i = 0; i < virtualMenus; i++){
            Map<Long, Menu> fixtureMap = fixtureMap();
            long idCount = fixtureMap.keySet().iterator().next();
            menuMap.put(idCount, fixtureMap.get(idCount));
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;

    }

    /**
     * hashSet의 성능 확인
     * @param virtualMenus
     * @return
     * @throws IOException
     */
    private long hashSetPerformance(int virtualMenus) throws IOException{
        long startTime = System.currentTimeMillis();

        Set<Menu> menuSet = new HashSet<>();
        for(int i = 0; i < virtualMenus; i++){
            menuSet.add(fixtureSet());
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private Menu fixtureSet() throws IOException {
        //String imgPath = "src/test/resources/img/testImg.png";
        MockMultipartFile mockMFile = new MockMultipartFile("menuImg","testImg.png", MediaType.IMAGE_PNG_VALUE, "testImg".getBytes());


        MenuRequest menuDto = MenuRequest.builder()
                                        .title("도미 카르파치오")
                                        .description("오늘의 소스와 레몬주스가 들어간 애피타이저")
                                        .courseType(CourseType.T_EPPETIZER)
                                        .price(23000)
                                        .menuImgFile(mockMFile)
                                        .build();
            Menu saveMenu = menuRepository.save(menuDto);
            log.info("saveMenu={}", saveMenu.toString());
            return saveMenu;
                                        
    }

    private Map<Long, Menu> fixtureMap() throws IOException {
        Map<Long, Menu> menuMap = new HashMap<>();
        AtomicLong idCount = new AtomicLong(0);
        long incrementAndGet = idCount.incrementAndGet();

        MockMultipartFile mockMFile = new MockMultipartFile("menuImg","testImg.png", MediaType.IMAGE_PNG_VALUE, "testImg".getBytes());

        MenuRequest menuDto = MenuRequest.builder()
                                        .title("도미 카르파치오")
                                        .description("오늘의 소스와 레몬주스가 들어간 애피타이저")
                                        .courseType(CourseType.T_EPPETIZER)
                                        .price(23000)
                                        .menuImgFile(mockMFile)
                                        .build();
        Menu menu = Menu.builder()
                        .courseType(menuDto.getCourseType())
                        .menuName(menuDto.getTitle())
                        .description(menuDto.getDescription())
                        .price(menuDto.getPrice())
                        .menuImg(new MenuImg(menuDto.getMenuImgUrl(), "서버 저장 이미지"))
                        .build();
        menuMap.put(incrementAndGet, menu);
        return menuMap;
        
                                        
    }

    
}
