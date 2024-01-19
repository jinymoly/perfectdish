package com.dish.perfect.menu.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.dish.perfect.menu.domain.MenuImg;

@Component
public class MenuImgStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullpath(String menuFilename) {
        return fileDir + menuFilename;
    }

    /**
     * 실제 파일을 정보와 함께 저장(파일)
     * @param multipartFile
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public MenuImg storeFile(MultipartFile menuImgFile) throws IllegalStateException, IOException {
        if (menuImgFile.isEmpty()) {
            return null;
        }
        String originalFilename = menuImgFile.getOriginalFilename();
        String storedFilename = createStoreFilename(originalFilename);
        menuImgFile.transferTo(new File(getFullpath(storedFilename)));
        
        return new MenuImg(originalFilename, storedFilename);
    }

    /**
     * 서버 저장용 파일 이름 생성
     * 
     * @param originalFilename
     * @return
     */
    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExtension(originalFilename);
        return uuid + "." + ext;
    }

    /**
     * 파일 확장자 추출
     * 
     * @param originalFilename
     * @return
     */
    private String extractExtension(String originalFilename) {
        int position = originalFilename.lastIndexOf(".");
        return originalFilename.substring(position + 1);
    }

    /**
     * 파일 삭제
     * @param storedFilename
     */
    public void deleteImgFile(String storedFilename){
        String filename = getFullpath(storedFilename);
        Path filePath = Paths.get(filename);
        
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일이 삭제되지 않았습니다.", e);
        }
    }

}
