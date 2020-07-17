package com.leucoth.shorten.client;

import com.leucoth.shorten.client.exceptions.CallFailedException;
import com.leucoth.shorten.client.exceptions.InvalidUrlException;
import com.leucoth.shorten.client.exceptions.BadCredentialsException;
import com.leucoth.shorten.client.exceptions.UnauthorizedException;
import com.leucoth.shorten.client.models.ShortenUrl;
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

    public ShortenUrl generate(String shortUrl)
            throws CallFailedException, InvalidUrlException, UnauthorizedException {
        return generate(shortUrl, config);
    }

    public List<ShortenUrl> generate(List<String> shortUrlList)
            throws UnauthorizedException, CallFailedException, InvalidUrlException {
        return generate(shortUrlList, config);
    }

    public ShortenUrl generate(String shortUrl, UrlConfig config)
            throws UnauthorizedException, InvalidUrlException, CallFailedException {
        List<ShortenUrl> su = generate(Collections.singletonList(shortUrl), config);
        if (su == null || su.isEmpty()) {
            throw new CallFailedException();
        }
        return su.get(0);
    }

    public List<ShortenUrl> generate(List<String> shortUrlList, UrlConfig config)
            throws CallFailedException, InvalidUrlException, UnauthorizedException {
        if (shortUrlList.stream().allMatch(this::isValidUrl)) {
            try {
                return HttpClient.generateToken(token, new UrlRequest(config, shortUrlList));
            } catch (UnauthorizedException e) {
                token = connect(username, password);
                return HttpClient.generateToken(token, new UrlRequest(config, shortUrlList));
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
