package app.service;

import org.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class AiService {

    private final String baseURL = "http://127.0.0.1:5000";

    public JSONObject getResponse(String message) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject query = new JSONObject();
        query.put("input", message);
        
        HttpEntity<JSONObject> req = new HttpEntity<>(query, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        JSONObject resp = restTemplate.postForObject(baseURL + "/chat", req, JSONObject.class);
        System.out.println(resp);
        return resp;
    }
}
