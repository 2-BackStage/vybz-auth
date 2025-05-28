package back.vybz.auth_service.busker.vo.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSendEmailCodeVo {

    @NotBlank
    @Size(min = 10, max = 30, message = "이메일은 10자 이상 30자 이하로 입력하세요")
    @Email(message = "올바른 이메일 형식을 입력하세요")
    private String email;

    @NotBlank
    private String purpose;
}
