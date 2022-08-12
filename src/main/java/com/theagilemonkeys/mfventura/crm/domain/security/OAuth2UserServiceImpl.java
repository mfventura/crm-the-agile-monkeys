package com.theagilemonkeys.mfventura.crm.domain.security;

import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Component
public class OAuth2UserServiceImpl implements OAuth2UserService {
  private OAuth2UserService userService;
  @Autowired
  private UserRepository userRepository;
  
  public OAuth2UserServiceImpl() {
    this.userService = new DefaultOAuth2UserService();
  }
  
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
  
    OAuth2User user = userService.loadUser(userRequest);
    Set<GrantedAuthority> authorities = new LinkedHashSet<>();
    final String email = (String) user.getAttributes().get("email");
    final var userEntity = userRepository.findByEmailAndRemoveDateIsNull(email);
    Map<String, Object> attributes = new HashMap<>();
    if(userEntity.isPresent()){
      attributes.put("id", userEntity.get().getId());
      attributes.put("email", email);
      authorities.add(new SimpleGrantedAuthority("ROLE_"+userEntity.get().getRole()));
      return new DefaultOAuth2User(authorities, attributes, userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName());
    }
    return new DefaultOAuth2User(authorities, user.getAttributes(), userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName());
  
  }
}
