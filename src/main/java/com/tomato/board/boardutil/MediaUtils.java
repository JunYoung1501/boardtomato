package com.tomato.board.boardutil;

import java.util.HashMap;

import java.util.Map;

import org.springframework.http.MediaType;

/**
 *  업로드 파일 형식 ex) jpeg , jpg , gif , png*/
public class MediaUtils {
    
    private static Map<String, MediaType> mediaMap;
    
    static {
        mediaMap = new HashMap<>();
        
        mediaMap.put("JPEG", MediaType.IMAGE_JPEG);
        mediaMap.put("JPG", MediaType.IMAGE_JPEG);
        mediaMap.put("GIF", MediaType.IMAGE_GIF);
        mediaMap.put("PNG", MediaType.IMAGE_PNG);
    }
    
    public static MediaType getMediaType(String type) {
        return mediaMap.get(type.toUpperCase());
    }
    
    public static boolean containsImageMediaType(String mediaType) {
        return mediaMap.values().contains(MediaType.valueOf(mediaType));
    }
    
}
