package back.vybz.auth_service.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseSignInDto {

    private String accessToken;

    private String refreshToken;

    private String userUuid;

    @Builder
    public ResponseSignInDto(String accessToken, String refreshToken, String userUuid) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userUuid = userUuid;
    }
}
