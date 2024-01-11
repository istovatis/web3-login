package com.emfisis.vcportal.utils.RestCalling;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RestApiClient {

    private static Logger logger = LogManager.getLogger(RestApiClient.class);

    public static ServerResponse sendDelete(String url, Optional<String> jwtToken) {
        return sendAppointmentsNonEnclosingRequest(new HttpDelete(url), jwtToken);
    }

    public static ServerResponse sendGET(String url, Optional<String> jwtToken) {
        return sendAppointmentsNonEnclosingRequest(new HttpGet(url), jwtToken);
    }

    public static ServerResponse sendGET(String url, List<NameValuePair> params, Optional<String> jwtToken) {
        URI uri = addParams(url, params);
        return sendAppointmentsNonEnclosingRequest(new HttpGet(uri), jwtToken);
    }

    public static ServerResponse sendPOST(String url, String data, Optional<String> jwtToken) {
        return sendAppointmentsHttpEnclosingRequest(data, new HttpPost(url), jwtToken);
    }

    public static ServerResponse sendPOST(String url, String data, Map<String, String> headers, Optional<String> jwtToken) {
        HttpPost httpPost = new HttpPost(url);

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }

        return sendAppointmentsHttpEnclosingRequest(data, httpPost, jwtToken);
    }

    public static ServerResponse sendPOST(String url, List<NameValuePair> params, String data, Optional<String> jwtToken) {
        URI uri = addParams(url, params);
        return sendAppointmentsHttpEnclosingRequest(data, new HttpPost(uri), jwtToken);
    }

    public static ServerResponse sendPOST(String url, List<NameValuePair> params, String data, Map<String,String> headers) {
        URI uri = addParams(url, params);
        HttpPost httpPost = new HttpPost(uri);
        headers.entrySet().forEach(h-> httpPost.setHeader(h.getKey(),h.getValue()));
        return doRequest(data,httpPost);
    }

    public static ServerResponse sendPOST(String url, String data, Map<String,String> headers) {
        HttpPost httpPost = new HttpPost(url);
        headers.entrySet().forEach(h-> httpPost.setHeader(h.getKey(),h.getValue()));
        return doRequest(data,httpPost);
    }

    public static ServerResponse sendPut(String url, String data, Optional<String> jwtToken) {
        return sendAppointmentsHttpEnclosingRequest(data, new HttpPut(url), jwtToken);
    }

    public static ServerResponse sendPut(String url, List<NameValuePair> params, String data, Optional<String> jwtToken) {
        URI uri = addParams(url, params);
        return sendAppointmentsHttpEnclosingRequest(data, new HttpPut(uri), jwtToken);
    }

    private static URI addParams(String url, List<NameValuePair> params) {
        try {
            URIBuilder builder = new URIBuilder(url);
            for (NameValuePair param : params) {
                builder.setParameter(param.getName(), param.getValue());
            }
            return builder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static ServerResponse sendAppointmentsNonEnclosingRequest(HttpUriRequest request, Optional<String> jwtToken) {
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        if (jwtToken.isPresent()) {
            request.setHeader("Authorization", "Bearer " + jwtToken.get());
        }
        ServerResponse response = doRequest(request);
        if (response.getHeaders().get("error_type")!=null) {
            String errorValue = response.getHeaders().get("error_type");
            String errorMessage = response.getHeaders().get("error_message") != null ?
                    response.getHeaders().get("error_message") : "";
            response.setError(errorValue);
            response.setErrorMessage(errorMessage);
        }
        return response;
    }

    private static ServerResponse sendAppointmentsHttpEnclosingRequest(String data, HttpEntityEnclosingRequestBase httpEnclosingRequest, Optional<String> jwtToken) {
        httpEnclosingRequest.setHeader("Accept", "application/json");
        httpEnclosingRequest.setHeader("Content-type", "application/json");
        if (jwtToken.isPresent()) {
            httpEnclosingRequest.setHeader("Authorization", "Bearer " + jwtToken.get());
        }
        ServerResponse response = doRequest(data, httpEnclosingRequest);
        if (response.getHeaders().get("error_type")!=null) {
            String errorValue = response.getHeaders().get("error_type");
            String errorMessage = response.getHeaders().get("error_message") != null ?
                    response.getHeaders().get("error_message") : "";
            response.setError(errorValue);
            response.setErrorMessage(errorMessage);
        }
        return response;
    }



    private static ServerResponse doRequest(String data, HttpEntityEnclosingRequestBase httpEnclosingRequest) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            if (data != null && !data.isEmpty()) {
                httpEnclosingRequest.setEntity(new StringEntity(data,"UTF-8"));
            }
            CloseableHttpResponse httpResponse = httpClient.execute(httpEnclosingRequest);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpResponse.getEntity().getContent(),"UTF-8"))) {

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                Map<String, String> headers = new HashMap<>();
                for(Header header:httpResponse.getAllHeaders()){
                    headers.put(header.getName(),header.getValue());
                }
                return new ServerResponse(httpResponse.getStatusLine().getStatusCode(), response.toString(), headers);
            }
        } catch (IOException e) {
            return new ServerResponse(HttpStatus.SC_SERVICE_UNAVAILABLE, null, IOException.class.getSimpleName(), e.getMessage(), new HashMap<>());
        }
    }

    private static ServerResponse doRequest(HttpUriRequest httpEnclosingRequest) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse httpResponse = httpClient.execute(httpEnclosingRequest);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpResponse.getEntity().getContent()))) {

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                Map<String, String> headers = new HashMap<>();
                for(Header header:httpResponse.getAllHeaders()){
                    headers.put(header.getName(),header.getValue());
                }
                return new ServerResponse(httpResponse.getStatusLine().getStatusCode(), response.toString(), headers);
            }
        } catch (IOException e) {
            return new ServerResponse(HttpStatus.SC_SERVICE_UNAVAILABLE, null, IOException.class.getSimpleName(), e.getMessage(), new HashMap<>());
        }
    }


}
