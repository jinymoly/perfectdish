package com.dish.perfect.global.log.domain;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Trace {

    private String id;
    private int depth;

    public Trace(String id, int depth) {
        this.id = id + "/" + createId();
        this.depth = depth;
    }

    private String createId(){
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public Trace createNextId(){
        return new Trace(id, depth + 1);
    }

    public Trace createPreId(){
        return new Trace(id, depth - 1);
    }

    public boolean isInitialCall(){
        return depth == 0;
    }

}
