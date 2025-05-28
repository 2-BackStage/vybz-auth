package back.vybz.auth_service.busker.application;

import back.vybz.auth_service.common.domain.mysql.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    UserDetails loadUserByUuid(String userUuid);

    // User loadUserByEmail(String email);
}
