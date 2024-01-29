package com.dish.perfect.imageManager;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.dish.perfect.imageManager.domain.ImageFile;

@Component
public class ImageUtil {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullpath(String menuFilename) {
        return fileDir + menuFilename;
    }

    /**
     * 실제 파일을 정보와 함께 저장
     * 
     * @param multipartFile
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public ImageFile storeFile(MultipartFile menuImgFile) throws IllegalStateException, IOException {
        if (menuImgFile.isEmpty()) {
            return null;
        }
        String originalFilename = menuImgFile.getOriginalFilename();
        String storedFilename = createStoreFilename(originalFilename);
        menuImgFile.transferTo(new File(getFullpath(storedFilename)));

        return new ImageFile(originalFilename, storedFilename);
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
    public String extractExtension(String originalFilename) {
        int position = originalFilename.lastIndexOf(".");
        return originalFilename.substring(position + 1);
    }

    /**
     * 확장자를 제외한 파일 이름만 추출
     * 
     * @param originalFilename
     * @return
     */
    public String extractFilename(String originalFilename) {
        int position = originalFilename.indexOf(".");
        return originalFilename.substring(0, position);

    }
}
