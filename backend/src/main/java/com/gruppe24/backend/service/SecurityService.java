package com.gruppe24.backend.service;

import com.gruppe24.backend.entity.User;
import com.gruppe24.backend.exception.UserNotFoundException;
import com.gruppe24.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

  private final UserRepository userRepository;

  private static final Logger log = LoggerFactory.getLogger(SecurityService.class);

  public SecurityService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getAuthenticatedUser() throws RuntimeException {
    String email = getAuthenticatedEmail();
    log.info("Email:" + email);

    if (email == null) {
      throw new RuntimeException("User email not found in authentication");
    }

    return userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("Could not find user with email: " + email));
  }

  public String getAuthenticatedEmail()  throws RuntimeException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated() || (authentication instanceof AnonymousAuthenticationToken)) {
      throw new RuntimeException("No authenticated user found");
    }

    log.info("Authentication: " + String.valueOf(authentication));
    log.info("Principal: " + authentication.getPrincipal());
    log.info("Principal class: " + authentication.getPrincipal().getClass());

    String email;
    if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
      email = oAuth2User.getAttribute("email");
    } else if (authentication.getPrincipal() instanceof UserDetails userDetails) {
      email = userDetails.getUsername();
    } else if (authentication.getPrincipal() instanceof String) {
      email = (String) authentication.getPrincipal();
    } else {
      email = null;
    }

    return email;
  }

  public boolean isAdmin() {
    User user = getAuthenticatedUser();
    return user.isAdmin();
  }
}
