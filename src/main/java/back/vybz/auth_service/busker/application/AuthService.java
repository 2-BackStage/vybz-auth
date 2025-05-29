package back.vybz.auth_service.busker.application;

import back.vybz.auth_service.busker.dto.in.RequestAuthSignInDto;
import back.vybz.auth_service.busker.dto.in.RequestSignUpDto;
import back.vybz.auth_service.common.dto.ResponseSignInDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    UserDetails loadUserByUuid(String userUuid);

    void signUp(RequestSignUpDto requestSignUpDto);

    boolean existsEmail(String email);

    ResponseSignInDto signIn(RequestAuthSignInDto requestAuthSignInDto);
}
