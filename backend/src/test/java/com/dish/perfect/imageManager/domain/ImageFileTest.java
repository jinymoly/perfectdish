package com.dish.perfect.imageManager.domain;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dish.perfect.imageManager.ImgFixture;

@SpringBootTest
public class ImageFileTest {

    @Autowired
    private ImageFile imageFile;
    private ImgFixture imgFixture = new ImgFixture();

    @Test
    @DisplayName("uploadFile을 생성시 original/stored filename이 다르다")
    void createUploadFile() throws IOException{
        ImageFile newFile = imageFile.createUploadFile(imgFixture.fixtureMultiA());

        assertNotEquals(newFile.getOriginalFilename(), newFile.getName());
    }

}

