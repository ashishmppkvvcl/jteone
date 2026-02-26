package com.mppkvvcl.jteone.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpConfiguration {

    private RestTemplate restTemplate;

    public RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
            clientHttpRequestFactory.setConnectionRequestTimeout(5000);
            clientHttpRequestFactory.setReadTimeout(5000);

            restTemplate = new RestTemplate(clientHttpRequestFactory);
        }
        return restTemplate;
    }
}
