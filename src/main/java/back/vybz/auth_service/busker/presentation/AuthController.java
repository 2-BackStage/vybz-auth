package back.vybz.auth_service.busker.presentation;

import back.vybz.auth_service.busker.infrastructure.email.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final EmailSender emailSender;

    @GetMapping("/send")
    public String testSend(
            @RequestParam String email) {
        emailSender.send(email, "VYBZ 테스트 메일", "<h1>Hello, world!</h1>");
        return "메일 발송 완료!";
    }
}
