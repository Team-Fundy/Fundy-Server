package com.fundy.domain.devnote.vos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.net.MalformedURLException;
import java.net.URL;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Image {
    private URL url;

    public static Image of(String url) {
        try {
            return new Image(new URL(url));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("URL 형식이 맞지 않습니다");
        }
    }

    @Override
    public String toString() {
        return url.toString();
    }
}