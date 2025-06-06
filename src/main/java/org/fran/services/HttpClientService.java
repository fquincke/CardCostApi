package org.fran.services;

import org.fran.dtos.SimpleHttpResponse;
import org.fran.services.interfaces.IHttpClientService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientService implements IHttpClientService {
    @Override
    public SimpleHttpResponse sendRequest(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept-Version", "3")
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return new SimpleHttpResponse(response.statusCode(), response.body());
    }
}