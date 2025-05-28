package back.vybz.auth_service.user.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestOAuthSignOutDto {

    private String refreshToken;

    @Builder
    public RequestOAuthSignOutDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
