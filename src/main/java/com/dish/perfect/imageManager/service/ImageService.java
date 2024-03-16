package com.dish.perfect.imageManager.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

    /**
     * 확장자 제외한 uploadFileName(uuid 적용됨)
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public String getUploadUrl(MultipartFile multipartFile) throws IOException{
        String extractFilename = extractFilename(multipartFile.getName());
        return extractFilename;
    }

    /**
     * uploadFileName만 추출 (확장자 제외)
     * @param originalFilename
     * @return
     */
    private String extractFilename(String originalFilename) {
        int position = originalFilename.indexOf(".");
        return originalFilename.substring(0, position);
    }

}
