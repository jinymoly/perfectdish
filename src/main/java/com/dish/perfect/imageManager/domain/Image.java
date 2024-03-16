package com.dish.perfect.imageManager.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Image {
    
    @Column(name = "image_url")
    private String imgUrl;

    public Image(String imgUrl){
        this.imgUrl = imgUrl;
    }

    private String uploadUrl(ImageFile imageFile){
        return imageFile.getName();
    }

    private void addUrl(String storedImageUrl){
        this.imgUrl = storedImageUrl;
    }

}
