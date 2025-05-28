package back.vybz.auth_service.user.application;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    UserDetails loadUserByUuid(String userUuid);
}
