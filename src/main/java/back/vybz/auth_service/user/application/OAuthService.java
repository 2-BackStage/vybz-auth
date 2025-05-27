package back.vybz.auth_service.user.application;

import back.vybz.auth_service.user.dto.in.RequestOAuthSignInDto;
import back.vybz.auth_service.user.dto.in.RequestOAuthSignOutDto;
import back.vybz.auth_service.user.dto.out.ResponseOAuthSignInDto;

public interface OAuthService {

    ResponseOAuthSignInDto signIn(RequestOAuthSignInDto requestOAuthSignUpDto);

    void signOut(RequestOAuthSignOutDto requestOAuthSignOutDto);
}
