package com.dish.perfect.imageManager.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dish.perfect.imageManager.ImageUtil;
import com.dish.perfect.imageManager.domain.ImageFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {
    
    @Value("${file.dir}")
    private String fileFullPath;

    private final ImageUtil imageUtil;
    private final ImageFile imgFile;

    /**
     * 파일을 업로드하고 최종 경로를 반환(uuid + 확장자) 
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public String uploadImg(MultipartFile multipartFile) throws IOException{
        ImageFile createUploadFile = imgFile.createUploadFile(multipartFile);
        imageUtil.uploadFile(createUploadFile);
        String uploadPath = getUploadPath(createUploadFile);
        //Image img = new Image(uploadPath);
        return uploadPath;
    }

    public String getUploadPath(ImageFile file){
        return fileFullPath + file.getName();
    }
   
}
