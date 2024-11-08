package vip.fairy.provider;

import java.util.Map;
import org.keycloak.representations.idm.UserRepresentation;
import vip.fairy.business.Login;

public interface KeycloakAdminPersister {
  Boolean createRegularUser(UserRepresentation userRepresentation);
  Map<String, String> login( Login login);
}
