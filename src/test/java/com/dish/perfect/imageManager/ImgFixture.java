package com.dish.perfect.imageManager;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class ImgFixture {

    public MultipartFile fixtureMultiA() {

        MockMultipartFile iFile = new MockMultipartFile("TestA",
                "TestMockA.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "TestMockA".getBytes());
        return iFile;
    }

    public MultipartFile fixtureMultiB() {

        MockMultipartFile iFile = new MockMultipartFile("TestB",
                "TestMockB.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "TestMockB".getBytes());
        return iFile;
    }

    public MultipartFile fixtureMultiC() {

        MockMultipartFile iFile = new MockMultipartFile("TestC", "TestMockC".getBytes());
        return iFile;
    }
}
