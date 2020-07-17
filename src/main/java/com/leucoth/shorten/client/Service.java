package com.leucoth.shorten.client;

import com.leucoth.shorten.client.exceptions.CallFailedException;
import com.leucoth.shorten.client.exceptions.InvalidUrlException;
import com.leucoth.shorten.client.exceptions.BadCredentialsException;
import com.leucoth.shorten.client.exceptions.UnauthorizedException;
import com.leucoth.shorten.client.models.rest.UrlConfig;
import com.leucoth.shorten.client.models.rest.UrlRequest;
import com.leucoth.shorten.client.utils.HttpClient;

import java.net.URL;
import java.util.Collections;
import java.util.List;

public final class Service {

    private String token;
    private String username;
    private String password;
    private UrlConfig config;

    private Service() {
    }

    public static Service getInstance(String username, String password) {
        Service service = new Service();
        service.token = connect(username, password);
        service.username = username;
        service.password = password;
        return service;
    }

    private static String connect(String username, String password) {
        return null;
    }

    public void generate(String shortUrl)
            throws CallFailedException, InvalidUrlException, UnauthorizedException {
        generate(shortUrl, config);
    }

    public void generate(List<String> shortUrlList)
            throws UnauthorizedException, CallFailedException, InvalidUrlException {
        generate(shortUrlList, config);
    }

    public void generate(String shortUrl, UrlConfig config)
            throws UnauthorizedException, InvalidUrlException, CallFailedException {
        generate(Collections.singletonList(shortUrl), config);
    }

    public void generate(List<String> shortUrlList, UrlConfig config)
            throws CallFailedException, InvalidUrlException, UnauthorizedException {
        if (shortUrlList.stream().allMatch(this::isValidUrl)) {
            try {
                HttpClient.generateToken(token, new UrlRequest(config, shortUrlList));
            } catch (UnauthorizedException e) {
                token = connect(username, password);
                HttpClient.generateToken(token, new UrlRequest(config, shortUrlList));
            }
        } else {
            throw new InvalidUrlException();
        }
    }

    public UrlConfig getConfig() {
        return config;
    }

    public void setConfig(UrlConfig config) {
        this.config = config;
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
