package io.github.zicenter.shortener.models.rest;

import java.util.List;

public class UrlRequest {
    UrlConfig config;
    List<String> requests;

    public UrlRequest(UrlConfig config, List<String> requests) {
        this.config = config;
        this.requests = requests;
    }
}
