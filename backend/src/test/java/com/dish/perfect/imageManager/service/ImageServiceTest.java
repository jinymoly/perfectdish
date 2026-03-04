package com.dish.perfect.imageManager.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.imageManager.ImgFixture;
import com.dish.perfect.imageManager.domain.ImageFile;

@SpringBootTest
public class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageFile imageFile;
    
    private ImgFixture imgFixture = new ImgFixture();

    @Test
    @DisplayName("파일을 업로드하고 저장 경로를 반환한다.")
    void getUploadUrl() throws IOException{
        String uploadPath = imageService.uploadImg(imgFixture.fixtureMultiA());
        
        assertThat(uploadPath).contains("/img/");
    }

    

}
