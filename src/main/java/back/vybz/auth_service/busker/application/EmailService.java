package back.vybz.auth_service.busker.application;

import back.vybz.auth_service.busker.dto.in.RequestSendEmailCodeDto;
import back.vybz.auth_service.busker.dto.in.RequestVerificationEmailDto;

public interface EmailService {

    void sendEmailCode(RequestSendEmailCodeDto requestSendEmailCodeDto);

    void verifyEmailCode(RequestVerificationEmailDto requestVerificationEmailDto);
}
