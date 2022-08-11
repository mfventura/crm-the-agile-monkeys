package com.theagilemonkeys.mfventura.crm.configuration;

import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@EnableWebSecurity
@Configuration
public class AuthorizationConfiguration {
  @Autowired
  private UserRepository userRepository;
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception { return authenticationConfiguration.getAuthenticationManager(); }
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests((authz) -> authz
                .antMatchers("/api/v1/users").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/customers").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
            )
            .headers().frameOptions().disable().and()
            .csrf().disable()
            .formLogin().disable()
            .oauth2Login(
                    login -> login
                      .userInfoEndpoint()
                            .userService(oauth2UserService())
            );
    return http.build();
  }
  private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
		final OAuth2UserService userService = new DefaultOAuth2UserService();
    return (u -> {
      OAuth2User user = userService.loadUser(u);
      Set<GrantedAuthority> authorities = new LinkedHashSet<>();
      final String email = (String) user.getAttributes().get("email");
      final var userEntity = userRepository.findByEmailAndRemoveDateIsNull(email);
      Map<String, Object> attributes = new HashMap<>();
      if(userEntity.isPresent()){
        attributes.put("id", userEntity.get().getId());
        attributes.put("email", email);
        authorities.add(new SimpleGrantedAuthority("ROLE_"+userEntity.get().getRole()));
      }
      return new DefaultOAuth2User(authorities, attributes, u.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName());
    });
  }
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().antMatchers("/h2-console**");
  }
}
