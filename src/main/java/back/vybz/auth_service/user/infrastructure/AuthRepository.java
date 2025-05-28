package back.vybz.auth_service.user.infrastructure;

import back.vybz.auth_service.user.domain.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserUuid(String uuid);
}
