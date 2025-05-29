package back.vybz.auth_service.busker.dto.in;

import back.vybz.auth_service.busker.dto.SendPurpose;
import back.vybz.auth_service.busker.vo.in.RequestSendSmsCodeVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSendSmsCodeDto {

    private String phoneNumber;

    private SendPurpose sendPurpose;

    @Builder
    public RequestSendSmsCodeDto(String phoneNumber, SendPurpose sendPurpose) {
        this.phoneNumber = phoneNumber;
        this.sendPurpose = sendPurpose;
    }

    public static RequestSendSmsCodeDto from(RequestSendSmsCodeVo requestSmsVo) {
        return RequestSendSmsCodeDto.builder()
                .phoneNumber(requestSmsVo.getPhoneNumber())
                .sendPurpose(SendPurpose.valueOf(requestSmsVo.getPurpose().toUpperCase()))
                .build();
    }
}
