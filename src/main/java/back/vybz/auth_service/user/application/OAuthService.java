package back.vybz.auth_service.user.application;

import back.vybz.auth_service.user.dto.in.RequestOAuthSignInDto;
import back.vybz.auth_service.common.dto.ResponseSignInDto;

public interface OAuthService {

    ResponseSignInDto signIn(RequestOAuthSignInDto requestOAuthSignUpDto);

    void signOut(String refreshToken);
}
