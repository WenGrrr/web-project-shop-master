package com.underfit.trpo.configure;

import com.underfit.trpo.security.AuthEntryPointJwt;
import com.underfit.trpo.security.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//Обеспечивает безопасность АОП для методов. Это позволяет @PreAuthorize, @PostAuthorize
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UserDetailsService userDetailsService;// сервис отвечает за поиск информации о юзерах в БД(в где-нибудь).
    private final AuthEntryPointJwt unauthorizedHandler;
    private final AuthTokenFilter authTokenFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();//Можем пойти в LDAP

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();//Что это? Классический. Кодируем пароль, не храним чистоганом
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()//чтобы не передавать csrf-токен(дополнительная защита)
                .disable()
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(unauthorizedHandler)
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//никаких сессий не поддерживаем
                ).authorizeRequests(authRequests ->
                        authRequests
                                .antMatchers("/api/v1/auth/**").permitAll()
                                .antMatchers("*/*.js", "/favicon.ico").permitAll()//позволяем получать js файлы
                                .antMatchers("/","/home","/login","/logout").permitAll()
                                .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider());
        return http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class).build();//в цепочке фильтров выставляем порядок фильтрации
    }
}
