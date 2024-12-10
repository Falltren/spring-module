package com.fallt.jwt.security;

import com.fallt.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class LoginAttempt {

    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    @Value("${app.security.max-failed-attempt}")
    private Integer maxFailedAttempt;
    private final Map<String, Integer> failedAttempt = new ConcurrentHashMap<>();

    public void loginFailed(String username) {
        int failedCount = failedAttempt.getOrDefault(username, 0);
        failedCount++;
        failedAttempt.put(username, failedCount);
        if (failedCount >= maxFailedAttempt) {
            blockAccount(username);
        }
    }

    public void loginSucceeded(String username) {
        failedAttempt.remove(username);
    }

    private void blockAccount(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails instanceof AppUserDetails ud) {
            userService.block(ud.getUser());
        }
    }
}
