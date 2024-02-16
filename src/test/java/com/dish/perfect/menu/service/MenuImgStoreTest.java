package com.dish.perfect.menu.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;

import com.dish.perfect.imageManager.ImageUtil;
import com.dish.perfect.imageManager.domain.ImageFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {"file.dir=src/test/resources/img/"})
public class MenuImgStoreTest {
    
    @Autowired
    private ImageUtil imgStore;

    private final String fixtureFileName = "TESTONLYtestImg.png";

    @Test
    @DisplayName("이미지 파일 최종 경로 반환")
    void getImgFullpath() {

        String fullpath = imgStore.getFullpath(fixtureFileName);
        log.info("fullpath={}", fullpath);
        
        assertEquals("src/test/resources/img/" + fixtureFileName, fullpath);
    }

    @Test
    @DisplayName("파일 확장자 추출")
    void extractExtension() {
        int position = fixtureFileName.lastIndexOf(".");
        String expectedResult = fixtureFileName.substring(position + 1);
        log.info("filename={}, position={}, result={}", fixtureFileName, position, expectedResult);
        assertEquals(expectedResult, "png");
        
    }

    @Test
    @DisplayName("서버 저장용 파일 이름 생성")
    void createStoreFilename(){
        //String uuid = UUID.randomUUID().toString();
        String fakeUUID = "5a854912f8c8";
        String ext = imgStore.extractExtension(fixtureFileName);
        String expectedResult = fakeUUID + "." + ext;
        log.info("originalFilename={}", fixtureFileName);
        log.info("expectedResult={}", expectedResult);
        assertEquals(expectedResult,"5a854912f8c8.png");
        
    }

    
    @Test
    @DisplayName("파일 저장시 정보(uploadUrl, storedUrl)가 포함")
    void saveFileWithInfo() throws IllegalStateException, IOException{
        MockMultipartFile mockMultipartFile = new MockMultipartFile("오늘의메뉴00","fixture00.png", MediaType.IMAGE_PNG_VALUE, "fixture00".getBytes());
        
        String originalFilename = mockMultipartFile.getOriginalFilename();
        String storedFilename = createStoreFilename(originalFilename);
        mockMultipartFile.transferTo(new File(imgStore.getFullpath(storedFilename)));
        log.info("getFullpath={}", imgStore.getFullpath(storedFilename));
        ImageFile menuImg = new ImageFile(originalFilename, storedFilename);
        log.info("파일 저장 완료 : {}", menuImg);
        String expected = "MenuImg [uploadUrl=fixture00.png, storedUrl=5a854912f8c8.png]";
        assertEquals(expected, menuImg.toString());
        
    }

    private static String createStoreFilename(String fixtureFileName) {
        String fakeUUID = "5a854912f8c8";
        String ext = extensionFixture(fixtureFileName);
        return fakeUUID + "." + ext;
    }

    private static String extensionFixture(String fixtureFileName) {
        int position = fixtureFileName.lastIndexOf(".");
        return fixtureFileName.substring(position + 1);
    }

    // 파일을 삭제 ? 필요한가? 
    // 파일이름에서 확장자 빼고 추출 
    // Full path에 더한다 
    // 위치에 해당 파일이 존재하면 삭제 
    private void deleteImg(String storedFilename){
        if(storedFilename.equals(imgStore.extractFilename(storedFilename))){

        }
    }
}
