package com.gruppe24.backend.controller.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gruppe24.backend.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;


@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


  private final UserRepository userRepository;

  private static final long EXPIRATION_TIME = 900_000_000;
  private static final String SECRET = "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";

  private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

  public CustomAuthenticationSuccessHandler(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
    String email = oauthToken.getPrincipal().getAttribute("email");

    String token = JWT.create()
            .withSubject(email)
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(SECRET.getBytes()));

    Cookie cookie = new Cookie("token", token);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);


    if (userRepository.findByEmail(email).isPresent()) {
      log.info("User found! Redirect to secured page");

      String redirectURL = "http://localhost:5173/login/success#token=" + token;
      getRedirectStrategy().sendRedirect(request, response, redirectURL);
    } else {
      log.info("User does not exist, redirect to registration page");
      assert email != null;

      String redirectURL = "http://localhost:5173/register/#token=" + token;
      getRedirectStrategy().sendRedirect(request, response, redirectURL);
    }
  }

}
