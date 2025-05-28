package back.vybz.auth_service.user.infrastructure;

import back.vybz.auth_service.user.domain.mysql.SocialType;
import back.vybz.auth_service.user.domain.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface OAuthRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialTypeAndProviderId(SocialType socialType, String providerId);
}
