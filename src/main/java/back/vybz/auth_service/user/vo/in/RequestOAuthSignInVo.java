package back.vybz.auth_service.user.vo.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestOAuthSignInVo {

    private String provider;

    private String providerId;

    private String email;

    private String nickname;

    @Builder
    public RequestOAuthSignInVo(String provider, String providerId,
                                String email, String nickname) {
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
        this.nickname = nickname;
    }
}
