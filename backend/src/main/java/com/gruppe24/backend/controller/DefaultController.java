package com.gruppe24.backend.controller;

import com.gruppe24.backend.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <strong>Provides basic navigation and system health information for the application.</strong>
 * <p>
 * This controller serves as the primary entry point for initial interactions with the application,
 * offering a simple greeting at the root path, a health status check to verify server availability,
 * and a secured example endpoint to demonstrate access control for authenticated users. It is designed
 * to give a quick overview of the service's status and to facilitate basic security checks.
 * </p>
 * <p>
 * <ul>
 *   Endpoints:
 *   <li><strong>GET /</strong> - Returns a welcome message or redirects users to a more informative page.</li>
 *   <li><strong>GET /status</strong> - Provides a quick health check of the server, indicating if the service is operational.</li>
 *   <li><strong>GET /secured</strong> - An example secured endpoint, accessible only to authenticated users, demonstrating basic access control.</li>
 * </ul>
 * </p>
 * <p>
 * These endpoints are intended as a starting point for further development and can be expanded or modified
 * to suit the specific needs of the application, including integrating more comprehensive health checks,
 * user-specific greetings, and additional secured functionalities.
 * </p>
 *
 * @version 1.2
 */
@RestController
public class DefaultController {

  private final SecurityService securityService;

  public DefaultController(SecurityService securityService) {
    this.securityService = securityService;
  }

  @GetMapping("/")
  public ResponseEntity<String> index() {
    return new ResponseEntity<>("Welcome!", HttpStatus.OK);
  }

  /**
   * Check the status of the server.
   *
   * @return HTTPS_status_code 200 with string message: "Service is up and running"
   */
  @GetMapping("/status")
  public ResponseEntity<String> getStatus() {
    return new ResponseEntity<>("Service is up and running", HttpStatus.OK);
  }

  @GetMapping("/secured")
  public ResponseEntity<String> secured(@AuthenticationPrincipal OAuth2User principal) {
    String userName = securityService.getAuthenticatedUser().getUserName();
    return new ResponseEntity<>("Hello " + userName + "!", HttpStatus.ACCEPTED);
  }

}
