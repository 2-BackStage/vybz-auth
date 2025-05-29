package back.vybz.auth_service.busker.dto.in;

import back.vybz.auth_service.busker.vo.in.RequestSignUpVo;
import back.vybz.auth_service.common.domain.mysql.Role;
import back.vybz.auth_service.common.domain.mysql.Status;
import back.vybz.auth_service.common.domain.mysql.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class RequestSignUpDto {

    private String email;

    private String password;

    private String categoryId;

    private String phoneNumber;

    private String nickname;

    @Builder
    public RequestSignUpDto(String email, String password,
                            String categoryId, String phoneNumber, String nickname) {
        this.email = email;
        this.password = password;
        this.categoryId = categoryId;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
    }

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .userUuid(UUID.randomUUID().toString())
                .email(email)
                .password(passwordEncoder.encode(password))
                .phoneNumber(phoneNumber.replaceAll("-", ""))
                .role(Role.BUSKER)
                .status(Status.ACTIVE)
                .build();
    }

    public static RequestSignUpDto from(RequestSignUpVo requestSignUpVo) {
        return RequestSignUpDto.builder()
                .email(requestSignUpVo.getEmail())
                .password(requestSignUpVo.getPassword())
                .phoneNumber(requestSignUpVo.getPhoneNumber())
                .nickname(requestSignUpVo.getNickname())
                .categoryId(requestSignUpVo.getCategoryId())
                .build();
    }

//    public BuskerCreatedEvent toBuskerCreatedEvent(String userUuid) {
//        return BuskerCreatedEvent.builder()
//                .userUuid(userUuid)
//                .nickname(this.nickname)
//                .categoryId(this.categoryId)
//                .build();
//    }
}
