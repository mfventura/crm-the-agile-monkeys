package com.theagilemonkeys.mfventura.crm.configuration;

import com.theagilemonkeys.mfventura.crm.domain.security.OAuth2UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class AuthorizationConfiguration {
  @Autowired
  private OAuth2UserServiceImpl oAuth2UserService;
  @Bean
  @Profile("default")
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests((authz) -> authz
                .antMatchers("/api/v1/users").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/customers").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .headers().frameOptions().disable().and()
            .csrf().disable()
            .formLogin().disable()
            .oauth2Login(
                    login -> login
                      .userInfoEndpoint()
                            .userService(oAuth2UserService)
            );
    return http.build();
  }
  
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().antMatchers("/h2-console**");
  }
}
