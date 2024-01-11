package app.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiService {

    private final String baseURL = "http://127.0.0.1:5000";

    public String getResponse(String jsonQuery) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonQuery, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        String resp = restTemplate.postForObject(baseURL + "/chat", requestEntity, String.class);
        return resp;
    }
}
