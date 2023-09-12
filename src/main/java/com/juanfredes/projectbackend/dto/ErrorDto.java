package com.juanfredes.projectbackend.dto;

import java.util.HashMap;
import java.util.Map;

public record ErrorDto(
        Map<String,String> error
) {

    public ErrorDto() {
        this(new HashMap<>());
    }

    public ErrorDto( String key, String value ) {
        this( Map.of(key, value) );
    }

    public void add( String key, String value ) {
        this.error.put( key, value );
    }

}
