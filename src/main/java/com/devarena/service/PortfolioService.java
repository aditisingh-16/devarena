package com.devarena.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class PortfolioService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String GITHUB_API = "https://api.github.com/users/";

    public Map<String, Object> analyzePortfolio(String username) {
        Map<String, Object> result = new LinkedHashMap<>();

        try {
            // Fetch user profile
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "DevArena-App");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> userResponse = restTemplate.exchange(
                GITHUB_API + username, HttpMethod.GET, entity, Map.class);
            Map userData = userResponse.getBody();

            // Fetch repositories
            ResponseEntity<List> reposResponse = restTemplate.exchange(
                GITHUB_API + username + "/repos?per_page=100",
                HttpMethod.GET, entity, List.class);
            List<Map> repos = reposResponse.getBody();

            // Basic info
            result.put("username", username);
            result.put("name", userData.get("name"));
            result.put("bio", userData.get("bio"));
            result.put("followers", userData.get("followers"));
            result.put("public_repos", userData.get("public_repos"));
            result.put("avatar_url", userData.get("avatar_url"));

            // Analysis
            List<String> roasts = new ArrayList<>();
            List<String> suggestions = new ArrayList<>();
            int score = 100;

            // Check bio
            if (userData.get("bio") == null || userData.get("bio").toString().isEmpty()) {
                roasts.add("🔥 No bio? Recruiters think you're a ghost!");
                suggestions.add("Add a bio describing what you do");
                score -= 10;
            }

            // Check repos
            int repoCount = repos.size();
            if (repoCount < 5) {
                roasts.add("🔥 Only " + repoCount + " repos? My grandma has more projects!");
                suggestions.add("Aim for at least 5-6 good projects");
                score -= 15;
            }

            // Check READMEs
            long reposWithoutReadme = repos.stream()
                .filter(r -> r.get("description") == null || 
                            r.get("description").toString().isEmpty())
                .count();
            if (reposWithoutReadme > 2) {
                roasts.add("🔥 " + reposWithoutReadme + " repos with no description — are these secret projects?");
                suggestions.add("Add descriptions to all your repositories");
                score -= 10;
            }

            // Check followers
            int followers = Integer.parseInt(userData.get("followers").toString());
            if (followers < 5) {
                roasts.add("🔥 " + followers + " followers? Even your own code doesn't follow you!");
                suggestions.add("Engage with the GitHub community, star and follow other devs");
                score -= 5;
            }

            // Check profile picture
            if (userData.get("avatar_url").toString().contains("identicon")) {
                roasts.add("🔥 Default profile picture? This is not WhatsApp!");
                suggestions.add("Upload a professional profile photo");
                score -= 5;
            }

            // Check website/blog
            if (userData.get("blog") == null || userData.get("blog").toString().isEmpty()) {
                roasts.add("🔥 No portfolio website? How will recruiters find you?");
                suggestions.add("Create a portfolio website and add it to your GitHub profile");
                score -= 5;
            }

            result.put("score", Math.max(score, 0));
            result.put("roasts", roasts);
            result.put("suggestions", suggestions);
            result.put("total_repos", repoCount);

        } catch (Exception e) {
            result.put("error", "GitHub user not found or API limit reached!");
        }

        return result;
    }
}