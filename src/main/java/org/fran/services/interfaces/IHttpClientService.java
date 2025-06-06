package org.fran.services.interfaces;

import org.fran.dtos.SimpleHttpResponse;

public interface IHttpClientService {
    SimpleHttpResponse sendRequest(String url) throws Exception;
}