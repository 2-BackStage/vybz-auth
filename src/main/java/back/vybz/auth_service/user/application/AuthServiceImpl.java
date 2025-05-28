package back.vybz.auth_service.user.application;

import back.vybz.auth_service.user.domain.CustomUserDetails;
import back.vybz.auth_service.user.infrastructure.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUuid(String userUuid) {
        return authRepository.findByUserUuid(userUuid)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("해당 UUID 유저를 찾을 수 없습니다."));
    }
}
