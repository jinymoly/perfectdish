package com.dish.perfect.imageManager.domain;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@AllArgsConstructor
public class ImageFile implements MultipartFile{

    private String originalName; 
    private String storedName;

    private byte[] bytes;

    private ImageFile(String originalName, byte[] bytes, String storedName) {
        this.originalName = originalName;
        this.storedName = storedName;
        this.bytes = bytes;
    }

    /**
     * uploadFile 생성 (확장자 포함)
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public ImageFile createUploadFile(MultipartFile multipartFile) throws IOException{
        String originName= multipartFile.getOriginalFilename();
        byte[] mulipartbytes = multipartFile.getBytes();
        String storedName = createNewFileName(originName);
        return new ImageFile(originName, mulipartbytes, storedName);
    }
    
    /**
     * uploadFileName 생성 (uuid + 확장자 포함)
     * @param originalFilename
     * @return
     */
    private String createNewFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString() + extractFilename(originalFilename);
        String ext = extractExtension(originalFilename);
        return uuid + "." + ext;
    }

    /**
     * 확장자 추출
     * @param originalFilename
     * @return
     */
    private String extractExtension(String originalFilename) {
        int position = originalFilename.lastIndexOf(".");
        return originalFilename.substring(position + 1);
    }

    /**
     * uploadFileName만 추출 (확장자 제외)
     * @param originalFilename
     * @return
     */
    private String extractFilename(String storedFilename) {
        int position = storedFilename.indexOf(".");
        return storedFilename.substring(0, position);
    }

    @Override
    public String getName() {
        return storedName;
    }

    @Override
    public String getOriginalFilename() {
        return originalName;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return bytes;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileOutputStream fos = new FileOutputStream(dest);
        fos.write(bytes);
        fos.close();
    }
}
