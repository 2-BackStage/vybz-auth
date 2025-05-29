package back.vybz.auth_service.busker.application;

import back.vybz.auth_service.busker.dto.in.RequestSendSmsCodeDto;
import back.vybz.auth_service.busker.dto.in.RequestVerificationSmsDto;

public interface SmsService {

    void sendSms(RequestSendSmsCodeDto requestSmsDto);

    void verifySmsCode(RequestVerificationSmsDto requestVerificationSmsDto);
}
