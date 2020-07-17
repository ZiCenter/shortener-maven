package com.leucoth.shorten.client.utils;

import java.util.List;

import com.google.gson.Gson;
import com.leucoth.shorten.client.exceptions.CallFailedException;
import com.leucoth.shorten.client.exceptions.BadCredentialsException;
import com.leucoth.shorten.client.exceptions.UnauthorizedException;
import com.leucoth.shorten.client.models.ShortenUrl;
import com.leucoth.shorten.client.models.rest.UrlRequest;
import com.leucoth.shorten.client.models.rest.UrlResponse;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClient {

    private static Gson g = new Gson();

    private HttpClient() {
        super();
    }

    public static CloseableHttpClient getHttpClient(String url) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(1000).setSocketTimeout(20000).build();
        return HttpClients.custom().setDefaultRequestConfig(config).build();
    }

    public static List<ShortenUrl> generateToken(String token, UrlRequest req)
            throws CallFailedException, UnauthorizedException {
        UrlResponse resp;
        String url = "https://shorten.leucoth.com";
        try (CloseableHttpClient c = HttpClient.getHttpClient(url)) {
            HttpPost post = new HttpPost(url);
            post.addHeader(HttpHeaders.ACCEPT, "application/json");
            post.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            post.addHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token));
            post.setEntity(new StringEntity(g.toJson(req)));
            HttpResponse response = c.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String respStr = EntityUtils.toString(response.getEntity());
                resp = g.fromJson(respStr, UrlResponse.class);
            } else {
                throw new CallFailedException();
            }
        } catch (Exception e) {
            throw new CallFailedException();
        }
        if (resp.getStatus() == 0) {
            return resp.getUrls();
        } else {
            throw new UnauthorizedException();
        }
    }

}
