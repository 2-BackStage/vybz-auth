package back.vybz.auth_service.user.dto.in;

import back.vybz.auth_service.user.vo.in.RequestOAuthSignInVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestOAuthSignInDto {

    private String provider;

    private String providerId;

    private String email;

    @Builder
    public RequestOAuthSignInDto(String provider, String providerId, String email) {
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
    }

    public static RequestOAuthSignInDto from(RequestOAuthSignInVo requestOAuthLoginVo) {
        return RequestOAuthSignInDto.builder()
                .provider(requestOAuthLoginVo.getProvider())
                .providerId(requestOAuthLoginVo.getProviderId())
                .email(requestOAuthLoginVo.getEmail())
                .build();
    }
}
