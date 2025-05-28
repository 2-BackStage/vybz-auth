package back.vybz.auth_service.busker.infrastructure.email;

import org.springframework.stereotype.Component;

@Component
public class EmailTemplateBuilder {
    public String buildVerificationEmail(String purpose, String code) {
        return """
                <html>
                    <body style="font-family: 'Helvetica Neue', Arial, sans-serif; background-color: #0C0F1C; padding: 40px;">
                        <div style="max-width: 600px; margin: auto; background-color: #1A1A1A; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);">
                            <div style="background-color: #00D1FF; padding: 24px; text-align: center;">
                                <img src="https://www.vybz.kr/common/img/common/logo.png" alt="Vybz Logo" style="height: 40px;">
                            </div>
                            <div style="padding: 32px 16px;">
                                <h2 style="color: #00D1FF; margin-bottom: 16px;">Vybz 이메일 인증</h2>
                                <p style="font-size: 16px; color: #CCCCCC;">아래 인증번호를 입력하여 %s 완료해주세요.</p>
                                <div style="margin: 24px 0; text-align: center;">
                                    <span style="font-size: 28px; color: #00D1FF; font-weight: bold;">%s</span>
                                </div>
                                <p style="font-size: 14px; color: #AAAAAA;">인증번호는 5분간 유효합니다.</p>
                            </div>
                            <div style="background-color: #0C0F1C; padding: 16px; text-align: center; font-size: 12px; color: #666666;">
                                본 메일은 VYBZ에서 발송되었습니다.
                            </div>
                        </div>
                    </body>
                </html>
            """.formatted(purpose, code);
    }
}
