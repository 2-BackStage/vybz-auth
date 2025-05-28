package back.vybz.auth_service.busker.application;

import back.vybz.auth_service.busker.dto.SendEmailPurpose;
import back.vybz.auth_service.busker.dto.in.RequestSendEmailCodeDto;
import back.vybz.auth_service.busker.dto.in.RequestVerificationEmailDto;
import back.vybz.auth_service.busker.infrastructure.email.EmailSender;
import back.vybz.auth_service.busker.infrastructure.email.EmailTemplateBuilder;
import back.vybz.auth_service.common.entity.BaseResponseStatus;
import back.vybz.auth_service.common.exception.BaseException;
import back.vybz.auth_service.common.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final RedisUtil<String> redisUtil;

    private final AuthService authService;

    private final EmailSender emailSender;

    private final EmailTemplateBuilder emailTemplateBuilder;


    @Override
    public void sendEmailCode(RequestSendEmailCodeDto requestSendEmailCodeDto) {

//        if (requestSendEmailCodeDto.getPurpose() == SendEmailPurpose.PASSWORD_RESET &&
//                authService.loadUserByUuid(requestSendEmailCodeDto.getEmail()) == null
//        ) {
//            throw new BaseException(BaseResponseStatus.NOT_FOUND_EMAIL);
//        }
//
//        final String code = RandomStringUtils.random(6, true, true);
//        final String email = requestSendEmailCodeDto.getEmail();
//
//        final String limitKey = "Limit:EmailSend:" + email;
//
//        if (redisUtil.get(limitKey) != null) {
//            throw new BaseException(BaseResponseStatus.EMAIL_CODE_SEND_LIMITED);
//        }
//
//        redisUtil.save(email, code, 5L, TimeUnit.MINUTES);
//        redisUtil.save(limitKey, "3", 3L, TimeUnit.MINUTES);
//
//        if (requestSendEmailCodeDto.getPurpose() == SendEmailPurpose.SIGN_UP |
//                requestSendEmailCodeDto.getPurpose() == SendEmailPurpose.PASSWORD_RESET) {
//            emailSender.send(email, "Starbucks 이메일 인증", emailTemplateBuilder.buildVerificationEmail("이메일 인증을", code));
//        }
//        else {
//            emailSender.send(email, "Starbucks 계정 복구 인증", emailTemplateBuilder.buildVerificationEmail("계정 복구를", code));
//        }
//
//        redisUtil.save("EmailVerify:" + email, "0", 5L, TimeUnit.MINUTES);
    }

    @Override
    public void verifyEmailCode(RequestVerificationEmailDto requestVerificationEmailDto) {
//        final String email = requestVerificationEmailDto.getEmail();
//        final String redisCode = redisUtil.get(email);
//
//        if (redisCode == null) {
//            throw new BaseException(BaseResponseStatus.EXPIRED_EMAIL_CODE);
//        }
//
//        if (!redisCode.equals(requestVerificationEmailDto.getVerificationCode())) {
//            String failKey = "EmailVerify:" + email;
//
//            if (redisUtil.increase(failKey, 5L, TimeUnit.MINUTES) >= 5) {
//                redisUtil.delete(email);
//                redisUtil.delete(failKey);
//                throw new BaseException(BaseResponseStatus.EMAIL_CODE_VERIFICATION_LIMITED);
//            }
//            throw new BaseException(BaseResponseStatus.INVALID_EMAIL_CODE);
//        }
//
//        if (requestVerificationEmailDto.getPurpose() == SendEmailPurpose.PASSWORD_RESET) {
//            redisUtil.save("PwdReset:Verified:" + email, "true", 10, TimeUnit.MINUTES);
//        } else if (requestVerificationEmailDto.getPurpose() == SendEmailPurpose.SIGN_UP) {
//            redisUtil.save("SignUp:Verified:" + email, "true", 20, TimeUnit.MINUTES);
//        } else {
//            redisUtil.save("AccountRecovery:Verified:" + email, "true", 10, TimeUnit.MINUTES);
//        }
//
//        redisUtil.delete(email);
//        redisUtil.delete("EmailVerify:" + email);
    }
}
