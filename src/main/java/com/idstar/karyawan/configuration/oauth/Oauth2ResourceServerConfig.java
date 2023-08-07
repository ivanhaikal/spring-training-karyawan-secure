package com.idstar.karyawan.configuration.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true) //secure definition
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * Manage resource server.
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }
//    private static final String SECURED_PATTERN = "/api/**";

    /**
     * Manage endpoints.
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
//                .oauth2Client()
                .csrf()
                .disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/showFile/**", "/v1/showFile/**", "/v1/upload", "/user-register/**", "/forget-password/**", "/oauth/authorize**", "/login**", "/error**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/login/**", "/user-login/**",
                        "/register", "/register/**", "/login/", "/dashboard/**", "/resources/**", "/api/static/**", "/assets/**", "/static/**", "/css/**",
                        "/login-user/**",
                        "/v1/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
        ;
    }
}
