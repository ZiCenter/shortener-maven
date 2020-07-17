package com.leucoth.zicenter.shortener.models.rest;

import com.leucoth.zicenter.shortener.models.ShortenUrl;

import java.util.List;

public class UrlResponse {
    private int status;
    private List<ShortenUrl> urls;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ShortenUrl> getUrls() {
        return urls;
    }

    public void setUrls(List<ShortenUrl> urls) {
        this.urls = urls;
    }
}
