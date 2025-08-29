package com.example.bajajtest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class BajajQualifierApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BajajQualifierApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // 1: API with my details
        String generateWebhookUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "I Pavan Santhosh");
        requestBody.put("regNo", "22BCE20064");
        requestBody.put("email", "pavansanthoships@gmail.com");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(generateWebhookUrl, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String webhookUrl = (String) response.getBody().get("webhook");
            String accessToken = (String) response.getBody().get("accessToken");

            System.out.println("Webhook URL: " + webhookUrl);
            System.out.println("Access Token: " + accessToken);

            // Step 2: final SQL query
            String finalQuery = "SELECT e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME, " +
                    "COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT " +
                    "FROM EMPLOYEE e1 " +
                    "JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID " +
                    "LEFT JOIN EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT AND e2.DOB > e1.DOB " +
                    "GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME " +
                    "ORDER BY e1.EMP_ID DESC;";

            Map<String, String> finalBody = new HashMap<>();
            finalBody.put("finalQuery", finalQuery);

            HttpHeaders postHeaders = new HttpHeaders();
            postHeaders.setContentType(MediaType.APPLICATION_JSON);
            postHeaders.set("Authorization", accessToken);

            HttpEntity<Map<String, String>> finalEntity = new HttpEntity<>(finalBody, postHeaders);

            // Step 3: Submitting SQL query
            ResponseEntity<String> finalResponse =
                    restTemplate.postForEntity(webhookUrl, finalEntity, String.class);

            System.out.println("Final Submission Response: " + finalResponse.getBody());
        } else {
            System.out.println("Failed to generate webhook");
        }
    }
}

