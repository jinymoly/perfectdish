package com.dish.perfect.imageManager;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dish.perfect.imageManager.domain.ImageFile;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ImageUtil {

    @Value("${file.dir}")
    private String fileUrl;

    /**
     * 실제 저장된 경로
     * @param menuFilename
     * @return
     */
    public String getFullpath(String menuFilename) {
        return fileUrl + menuFilename;
    }

    /**
     * 실제 이미지 파일 업로드
     * @param imageFile
     * @throws IOException
     */
    public void uploadFile(ImageFile imageFile) throws IOException {
        String uploadUrl = imageFile.getName();
        File file = new File(getFullpath(uploadUrl));
        imageFile.transferTo(file);
    }
}
