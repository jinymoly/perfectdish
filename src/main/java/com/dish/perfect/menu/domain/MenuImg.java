package com.dish.perfect.menu.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuImg {

    private String uploadUrl;
    private String storedUrl;

    @Override
    public String toString() {
        return "MenuImg [uploadUrl=" + uploadUrl + ", storedUrl=" + storedUrl + "]";
    }
}
