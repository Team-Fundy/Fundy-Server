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

    public static Image newInstance(String url) throws MalformedURLException {
        return new Image(new URL(url));
    }

    @Override
    public String toString() {
        return url.toString();
    }
}
