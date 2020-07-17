package io.github.zicenter.shortener.utils;

import java.util.List;

import com.google.gson.Gson;
import io.github.zicenter.shortener.exceptions.CallFailedException;
import io.github.zicenter.shortener.exceptions.BadCredentialsException;
import io.github.zicenter.shortener.exceptions.UnauthorizedException;
import io.github.zicenter.shortener.models.ShortenUrl;
import io.github.zicenter.shortener.models.rest.LoginRequest;
import io.github.zicenter.shortener.models.rest.LoginResponse;
import io.github.zicenter.shortener.models.rest.UrlRequest;
import io.github.zicenter.shortener.models.rest.UrlResponse;
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
    private static String baseUrl = "http://www.zicenter.com/api/v1";

    private HttpClient() {
        super();
    }

    private static CloseableHttpClient getHttpClient() {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(1000).setSocketTimeout(20000).build();
        return HttpClients.custom().setDefaultRequestConfig(config).build();
    }

    public static List<ShortenUrl> generateToken(String token, UrlRequest req)
            throws CallFailedException, UnauthorizedException {
        UrlResponse resp;
        String url = baseUrl + "/urls/generate";
        HttpPost post = new HttpPost(url);
        post.addHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token));
        try {
            post.setEntity(new StringEntity(g.toJson(req)));
            resp = executeCall(post, UrlResponse.class);
        } catch (Exception e) {
            throw new CallFailedException();
        }
        if (resp.getStatus() == 0) {
            return resp.getUrls();
        } else if (resp.getStatus() == 1) {
            throw new UnauthorizedException();
        } else {
            throw new CallFailedException();
        }
    }

    public static String login(String username, String password)
            throws CallFailedException, BadCredentialsException {
        LoginResponse resp;
        String url = baseUrl + "/login";
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new StringEntity(g.toJson(new LoginRequest(username, password))));
            resp = executeCall(post, LoginResponse.class);
        } catch (Exception e) {
            throw new CallFailedException();
        }
        if (resp.getStatus() == 0) {
            return resp.getSession().getToken();
        } else {
            throw new BadCredentialsException();
        }
    }

    private static <T> T executeCall(HttpPost post, Class<T> tClass) throws CallFailedException {
        try (CloseableHttpClient c = HttpClient.getHttpClient()) {
            post.addHeader(HttpHeaders.ACCEPT, "application/json");
            post.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            HttpResponse response = c.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String respStr = EntityUtils.toString(response.getEntity());
                return g.fromJson(respStr, tClass);
            }
        } catch (Exception e) {}
        throw new CallFailedException();
    }

}
