package com.dish.perfect.menu.domain.repository;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.dish.perfect.menu.MenuFixture;
import com.dish.perfect.menu.domain.Menu;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {"file.dir=src/test/resources/img/"})
public class MenuRepositoryTest {
    
    @Autowired
    private MenuRepository menuRepository;

    private MenuFixture menuFixture = new MenuFixture();

    @AfterEach
    void clearMenuImgInTestAndRepository(){
        String dirPath = "src/test/resources/img/";
        deleteFileAfterTest(dirPath);
    }

    @Test
    @DisplayName("Menu 저장")
    void createMenu(){
        menuRepository.save(menuFixture.fixRequestA().toMenuEntity());
        menuRepository.save(menuFixture.fixRequestB().toMenuEntity());
        menuRepository.save(menuFixture.fixRequestC().toMenuEntity());
        Menu findByMenuName = menuRepository.findByMenuName(menuFixture.fixRequestA().getMenuName());
        log.info("{}", findByMenuName);
    }

    /**
     * 테스트 종료 후 생성된 이미지 파일을 삭제
     */
    public static void deleteFileAfterTest(String dirPath){
        File dirFile = new File(dirPath);
        File[] savedTestFiles = dirFile.listFiles();
        if(savedTestFiles != null){
            for(File file : savedTestFiles){
                file.delete();
            }
        }
    }
    
}
