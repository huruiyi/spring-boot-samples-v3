package vip.fairy.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

class KeycloakJwtRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  /**
   * Prefix used for realm level roles.
   */
  public static final String PREFIX_ROLE = "ROLE_";

  /**
   * Name of the claim containing the realm level roles
   */
  private static final String CLAIM_REALM_ACCESS = "realm_access";

  /**
   * Name of the claim containing roles. (Applicable to realm and resource level.)
   */
  private static final String CLAIM_ROLES = "roles";


  /**
   * Extracts the realm-level roles from a JWT token distinguishing between them using prefixes.
   */
  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt) {
    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    Map<String, Collection<String>> realmAccess = jwt.getClaim(CLAIM_REALM_ACCESS);

    if (realmAccess != null && !realmAccess.isEmpty()) {
      Collection<String> roles = realmAccess.get(CLAIM_ROLES);
      if (roles != null && !roles.isEmpty()) {
        Collection<GrantedAuthority> realmRoles = roles.stream()
            .map(role -> new SimpleGrantedAuthority(PREFIX_ROLE + role))
            .collect(Collectors.toList());
        grantedAuthorities.addAll(realmRoles);
      }
    }

    return grantedAuthorities;
  }
}
