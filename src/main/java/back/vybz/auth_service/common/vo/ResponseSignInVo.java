package back.vybz.auth_service.common.vo;

import back.vybz.auth_service.common.dto.ResponseSignInDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseSignInVo {

    private String accessToken;

    private String refreshToken;

    private String userUuid;

    @Builder
    public ResponseSignInVo(String accessToken, String refreshToken,
                            String userUuid) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userUuid = userUuid;
    }

    public static ResponseSignInVo from(ResponseSignInDto response) {
        return ResponseSignInVo.builder()
                .accessToken(response.getAccessToken())
                .refreshToken(response.getRefreshToken())
                .userUuid(response.getUserUuid())
                .build();
    }
}
