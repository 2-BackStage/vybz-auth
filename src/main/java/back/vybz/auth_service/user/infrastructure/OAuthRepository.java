package back.vybz.auth_service.user.infrastructure;

import back.vybz.auth_service.common.domain.mysql.SocialType;
import back.vybz.auth_service.common.domain.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialTypeAndProviderId(SocialType socialType, String providerId);
}
