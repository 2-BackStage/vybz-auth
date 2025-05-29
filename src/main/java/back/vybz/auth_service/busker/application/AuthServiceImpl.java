package back.vybz.auth_service.busker.application;

import back.vybz.auth_service.busker.dto.in.RequestAuthSignInDto;
import back.vybz.auth_service.busker.dto.in.RequestSignUpDto;
import back.vybz.auth_service.common.application.TokenService;
import back.vybz.auth_service.common.domain.CustomUserDetails;
import back.vybz.auth_service.busker.infrastructure.AuthRepository;
import back.vybz.auth_service.common.domain.mysql.Status;
import back.vybz.auth_service.common.domain.mysql.User;
import back.vybz.auth_service.common.dto.ResponseSignInDto;
import back.vybz.auth_service.common.entity.BaseResponseStatus;
import back.vybz.auth_service.common.exception.BaseException;
import back.vybz.auth_service.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    private final PasswordEncoder passwordEncoder;

    private final RedisUtil<String> redisUtil;

    private final TokenService tokenService;

    @Override
    public UserDetails loadUserByUuid(String userUuid) {
        return authRepository.findByUserUuid(userUuid)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("해당 UUID 유저를 찾을 수 없습니다."));
    }

    @Transactional
    @Override
    public void signUp(RequestSignUpDto requestSignUpDto) {

        String email = requestSignUpDto.getEmail();
        String phone = requestSignUpDto.getPhoneNumber();

        if(authRepository.existsByEmail(email)) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_EMAIL);
        }

        if (authRepository.existsByPhoneNumber(phone)) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_SMS);
        }

        String emailVerifyKey = "sign-up-Verified:" + email;

        String smsVerifyKey = "sign-up-sms-Verified:" + phone;

        if (!"true".equals(redisUtil.get(emailVerifyKey))) {
            throw new BaseException(BaseResponseStatus.SIGN_UP_NOT_VERIFIED);
        }

        if (!"true".equals(redisUtil.get(smsVerifyKey))) {
            throw new BaseException(BaseResponseStatus.SIGN_UP_NOT_SMS_VERIFIED);
        }

        authRepository.save(requestSignUpDto.toEntity(passwordEncoder));

        redisUtil.delete(emailVerifyKey);
        redisUtil.delete(smsVerifyKey);
    }

    @Override
    public boolean existsEmail(String email) {
        return authRepository.existsByEmail(email);
    }

    @Override
    public ResponseSignInDto signIn(RequestAuthSignInDto requestAuthSignInDto) {

        User user = authRepository.findByEmail(requestAuthSignInDto.getEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_LOGIN));

        if (!passwordEncoder.matches(requestAuthSignInDto.getPassword(), user.getPassword())) {
            throw new BaseException(BaseResponseStatus.INVALID_LOGIN);
        } else if (user.getStatus() == Status.WITHDRAWAL) {
            throw new BaseException(BaseResponseStatus.WITHDRAWAL_PENDING);
        }

        return tokenService.issueToken(user);
    }
}
