package com.dish.perfect.imageManager.domain;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;


public class ImageFile implements MultipartFile{

    private final String originalName; 
    private final String storedName;

    private final byte[] bytes;

    private ImageFile(String originalName, byte[] bytes, String storedName) {
        this.originalName = originalName;
        this.bytes = bytes;
        this.storedName = storedName;
    }

    /**
     * uploadFile 생성
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public ImageFile createUploadFile(MultipartFile multipartFile) throws IOException{
        String originName= multipartFile.getOriginalFilename();
        byte[] mulipartbytes = multipartFile.getBytes();
        String uploadName = createNewFileName(originName);

        return new ImageFile(originName, mulipartbytes, uploadName);
    }
    
    /**
     * uploadFileName 생성
     * @param originalFilename
     * @return
     */
    private String createNewFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString() + originalFilename;
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
