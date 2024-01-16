package app.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class AiService {

    private final String baseURL = "http://127.0.0.1:5000";

    public String getResponse(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject query = new JSONObject();
        query.put("name", "jon doe");
        
        HttpEntity<String> req = new HttpEntity<>(jsonQuery, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        String resp = restTemplate.postForObject(baseURL + "/chat", req, String.class);
        return resp;
    }
}
