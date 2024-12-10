package com.fallt.jwt.logging;

import com.fallt.jwt.domain.entity.AuditLog;
import com.fallt.jwt.service.AuditLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;

@Component
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

    private final AuditLogService auditLogService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Principal principal = request.getUserPrincipal();
        String username;
        if (principal != null) {
            username = principal.getName();
        } else {
            username = "unknown";
        }
        String method = request.getMethod();
        String uri = request.getRequestURI();
        AuditLog auditLog = new AuditLog();
        auditLog.setUsername(username);
        auditLog.setAction("User request");
        auditLog.setDescription("Method: " + method + ", uri: " + uri);
        auditLogService.saveLog(auditLog);
        filterChain.doFilter(request, response);
    }
}
