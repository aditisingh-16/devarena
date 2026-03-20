package com.devarena.service;

import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CodeRiskService {

    private static final String API_KEY = "AIzaSyBCymMHA_gV9msn5IZ-05vHX5Ow9oKjLBQ";

    public String analyzeRisks(String code, String language) {
        try {
            String prompt = "Analyze this " + language + " code and identify edge cases that could cause bugs or crashes. Be concise and specific:\\n\\n" + code;

            String requestBody = """
                {
                    "contents": [
                        {"parts": [{"text": "%s"}]}
                    ]
                }
                """.formatted(prompt.replace("\"", "\\\"").replace("\n", "\\n"));

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

            HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }
}