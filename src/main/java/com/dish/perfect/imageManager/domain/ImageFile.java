package com.dish.perfect.imageManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageFile {

    private String uploadUrl;
    private String storedUrl;

    @Override
    public String toString() {
        return "MenuImg [uploadUrl=" + uploadUrl + ", storedUrl=" + storedUrl + "]";
    }
}
