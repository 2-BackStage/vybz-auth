package back.vybz.auth_service.user.vo.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestReissueVo {

    private String refreshToken;

    @Builder
    public RequestReissueVo(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
