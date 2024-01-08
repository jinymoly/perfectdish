package com.dish.perfect.menu.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuImg {

    private String uploadName;
    private String storedName;

    @Override
    public String toString() {
        return "MenuImg [uploadName=" + uploadName + ", storedName=" + storedName + "]";
    }

    

}
