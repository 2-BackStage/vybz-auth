package back.vybz.auth_service.common.util;

import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsCertificationUtil {

    private final DefaultMessageService defaultMessageService;

    @Value("${cools.api.sender-number}")
    private String senderNumber;

    // 단일 메시지 전송
    public void sendSMS(String to,  String fullMessage){
        Message message = new Message(); // 새 메시지 객체 생성
        message.setFrom(senderNumber); // 발신자 번호 설정
        message.setTo(to); // 수신자 번호 설정
        message.setText(fullMessage); // 메시지 내용 설정

        defaultMessageService.sendOne(new SingleMessageSendingRequest(message)); // 메시지 발송 요청
    }
}
