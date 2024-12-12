package com.fallt.oauth2_example.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class Oauth2LogoutHandler implements LogoutHandler {

    private final RestTemplate restTemplate;
    private final OAuth2AuthorizedClientService authorizedClientService;

    private static final String GOOGLE_TOKEN_REVOKE_PATH = "https://oauth2.googleapis.com/revoke?token=";

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                    clientRegistrationId,
                    oauthToken.getName()
            );
            if (authorizedClient != null) {
                String accessToken = authorizedClient.getAccessToken().getTokenValue();
                if (accessToken != null) {
                    revokeToken(accessToken);
                }
            }
        }
    }

    private void revokeToken(String accessToken) {
        String revokePath = GOOGLE_TOKEN_REVOKE_PATH + accessToken;
        restTemplate.postForObject(revokePath, null, String.class);
    }
}
