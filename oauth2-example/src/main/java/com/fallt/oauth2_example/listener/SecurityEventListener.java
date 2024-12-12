package com.fallt.oauth2_example.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SecurityEventListener {

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        log.info("User login successfully: {}", event.getAuthentication().getName());
    }

    @EventListener
    public void onLogoutSuccess(LogoutSuccessEvent event) {
        log.info("User logout successfully: {}", event.getAuthentication().getName());
    }
}
