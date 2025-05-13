package es.miw.tfm.invierte.core.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service class for handling JWT operations.
 * This class provides methods for creating, extracting, and verifying JWT tokens.
 * It uses the Auth0 JWT library for token management.
 *
 * @author denilssonmn
 * @author devcastlecix
 */
@Service
public class JwtService {

  private static final String BEARER = "Bearer ";

  private static final String USER_CLAIM = "user";

  private static final String NAME_CLAIM = "name";

  private static final String ROLE_CLAIM = "role";

  private static final String COMPANY_ROLE_CLAIM = "companyRoles";

  private final String secret;

  private final String issuer;

  private final int expire;

  /**
   * Constructor for JwtService.
   * Initializes the secret, issuer, and expiration time for JWT tokens.
   *
   * @param secret the secret key for signing tokens
   * @param issuer the issuer of the tokens
   * @param expire the expiration time in seconds
   */
  @Autowired
  public JwtService(@Value("${tfm.jwt.secret}") String secret,
      @Value("${tfm.jwt.issuer}") String issuer,
      @Value("${tfm.jwt.expire}") int expire) {
    this.secret = secret;
    this.issuer = issuer;
    this.expire = expire;
  }

  /**
   * Extracts the bearer token from the Authorization header.
   * Validates the format and removes the "Bearer " prefix.
   *
   * @param bearer the Authorization header value
   * @return the extracted token or an empty string if invalid
   */
  public String extractBearerToken(String bearer) {
    if (bearer != null && bearer.startsWith(BEARER) && 3 == bearer.split("\\.").length) {
      return bearer.substring(BEARER.length());
    } else {
      return "";
    }
  }

  /**
   * Creates a JWT token with the specified claims.
   *
   * @param user the user identifier
   * @param name the user's name
   * @param role the user's role
   * @param taxIdentificationNumber the user's tax identification number
   * @return the generated JWT token
   */
  public String createToken(String user, String name, String role, String taxIdentificationNumber) {
    return JWT.create()
        .withIssuer(this.issuer)
        .withIssuedAt(new Date())
        .withNotBefore(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + this.expire * 1000L))
        .withClaim(USER_CLAIM, user)
        .withClaim(NAME_CLAIM, name)
        .withClaim(COMPANY_ROLE_CLAIM, Map.of(taxIdentificationNumber, role))
        .sign(Algorithm.HMAC256(this.secret));
  }

  /**
   * Extracts the user identifier from the JWT token.
   *
   * @param authorization the JWT token
   * @return the user identifier or an empty string if invalid
   */
  public String user(String authorization) {
    return this.verify(authorization)
        .map(jwt -> jwt.getClaim(USER_CLAIM).asString())
        .orElse("");
  }

  /**
   * Extracts the user's name from the JWT token.
   *
   * @param authorization the JWT token
   * @return the user's name or an empty string if invalid
   */
  public String name(String authorization) {
    return this.verify(authorization)
        .map(jwt -> jwt.getClaim(NAME_CLAIM).asString())
        .orElse("");
  }

  /**
   * Extracts the user's role from the JWT token.
   *
   * @param authorization the JWT token
   * @return the user's role or an empty string if invalid
   */
  public String role(String authorization) {
    return this.verify(authorization)
        .map(jwt -> jwt.getClaim(ROLE_CLAIM).asString())
        .orElse("");
  }

  /**
   * Extracts the user's company roles from the JWT token.
   *
   * @param authorization the JWT token
   * @return a map of company roles or an empty map if invalid
   */
  public Map<String, Object> roles(String authorization) {
    return this.verify(authorization)
        .map(jwt -> jwt.getClaim(COMPANY_ROLE_CLAIM).asMap())
        .orElse(Collections.emptyMap());
  }

  /**
   * Verifies the JWT token using the secret and issuer.
   *
   * @param token the JWT token
   * @return an Optional containing the decoded JWT if valid, or empty if invalid
   */
  private Optional<DecodedJWT> verify(String token) {
    try {
      return Optional.of(JWT.require(Algorithm.HMAC256(this.secret))
          .withIssuer(this.issuer).build()
          .verify(token));
    } catch (Exception exception) {
      return Optional.empty();
    }
  }

}
