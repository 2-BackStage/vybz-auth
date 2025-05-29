package back.vybz.auth_service.busker.dto.in;

import back.vybz.auth_service.busker.dto.SendPurpose;
import back.vybz.auth_service.busker.vo.in.RequestSendEmailCodeVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSendEmailCodeDto {

    private String email;

    private SendPurpose purpose;

    @Builder
    public RequestSendEmailCodeDto(String email, SendPurpose purpose) {
        this.email = email;
        this.purpose = purpose;
    }

    public static RequestSendEmailCodeDto from(RequestSendEmailCodeVo requestSendEmailCodeVo) {
        return RequestSendEmailCodeDto.builder()
                .email(requestSendEmailCodeVo.getEmail())
                .purpose(SendPurpose.valueOf(requestSendEmailCodeVo.getPurpose().toUpperCase()))
                .build();
    }
}
