package back.vybz.auth_service.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSignOutDto {

    private String refreshToken;

    @Builder
    public RequestSignOutDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
