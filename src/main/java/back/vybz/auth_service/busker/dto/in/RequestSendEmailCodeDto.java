package back.vybz.auth_service.busker.dto.in;

import back.vybz.auth_service.busker.dto.SendEmailPurpose;
import back.vybz.auth_service.busker.vo.in.RequestSendEmailCodeVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSendEmailCodeDto {

    private String email;

    private SendEmailPurpose purpose;

    @Builder
    public RequestSendEmailCodeDto(String email, SendEmailPurpose purpose) {
        this.email = email;
        this.purpose = purpose;
    }

    public static RequestSendEmailCodeDto from(RequestSendEmailCodeVo requestSendEmailCodeVo) {
        return RequestSendEmailCodeDto.builder()
                .email(requestSendEmailCodeVo.getEmail())
                .purpose(SendEmailPurpose.valueOf(requestSendEmailCodeVo.getPurpose().toUpperCase()))
                .build();
    }
}
