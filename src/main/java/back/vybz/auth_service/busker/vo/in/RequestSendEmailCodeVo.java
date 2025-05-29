package back.vybz.auth_service.busker.vo.in;

import back.vybz.auth_service.common.pattern.RegexPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSendEmailCodeVo {

    @NotBlank
    @Pattern(
            regexp = RegexPatterns.EMAIL,
            message = "이메일은 10자 이상 30자 이하로 입력해주세요"
    )
    private String email;

    @NotBlank
    private String purpose;
}
