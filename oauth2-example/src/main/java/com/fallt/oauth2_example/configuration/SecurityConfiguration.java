package com.fallt.oauth2_example.configuration;

import com.fallt.oauth2_example.oauth2.Oauth2LogoutHandler;
import com.fallt.oauth2_example.service.SocialAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final SocialAppService socialAppService;
    private final Oauth2LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/login", "/error", "/webjars/**").permitAll() // Разрешаем доступ к этим маршрутам всем
                        .requestMatchers("/h2-console/*").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Только для администраторов
                        .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                )
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) // Обработка ошибок аутентификации
//                )
                .oauth2Login(oauth2Login -> oauth2Login
//                        .loginPage("/") // Указываем страницу для входа
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint
                                                .userService(socialAppService)
                                )
                                .defaultSuccessUrl("/user")
                )
                .logout(logout -> logout
                                .logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
//                        .logoutSuccessHandler(logoutHandler) // Обработчик для логаута
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                );
//                .headers(headers -> headers.httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable));
        return http.build();
    }
}
