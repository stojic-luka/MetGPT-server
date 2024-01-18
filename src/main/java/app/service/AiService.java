package app.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiService {
    private final String baseURL = "http://127.0.0.1:5000";

    /**
     * Retrieves a response from the AI service API.
     *
     * @param  message	the message to send to the API
     * @return         	the response received from the API
     * @throws JSONException	if there is an error parsing or creating JSON
     */
    public String getResponse(String message) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject query = new JSONObject();
        query.put("input", message);

        HttpEntity<String> req = new HttpEntity<>(query.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(baseURL + "/chat", req, String.class);
    }
}
