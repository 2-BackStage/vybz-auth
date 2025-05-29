package back.vybz.auth_service.busker.dto.in;

import back.vybz.auth_service.busker.vo.in.RequestAuthSignInVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestAuthSignInDto {

    private String email;

    private String password;

    @Builder
    public RequestAuthSignInDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static RequestAuthSignInDto from(RequestAuthSignInVo requestAuthSignInVo) {
        return RequestAuthSignInDto.builder()
                .email(requestAuthSignInVo.getEmail())
                .password(requestAuthSignInVo.getPassword())
                .build();
    }
}
