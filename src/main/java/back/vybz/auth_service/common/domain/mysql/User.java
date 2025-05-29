package back.vybz.auth_service.common.domain.mysql;

import back.vybz.auth_service.common.entity.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자 uuid
     */
    @Column(name = "user_uuid", nullable = false, unique = true)
    private String userUuid;

    /**
     * 소셜 타입
     */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    /**
     * 소셜 id
     */
    @Column(name = "provider_id", unique = true)
    private String providerId;

    /**
     * 이메일
     */
    @Column(name = "email")
    private String email;

    /**
     * 전화번호
     */
    @Column(name = "number", unique = true)
    private String phoneNumber;

    /**
     * 패스워드
     */
    @Column(name = "password")
    private String password;

    /**
     * 역할
     */
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * 사용자 상태
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public User(Long id, String userUuid, SocialType socialType, String providerId,
                String email, String phoneNumber, String password,
                Role role, Status status) {
        this.id = id;
        this.userUuid = userUuid;
        this.socialType = socialType;
        this.providerId = providerId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.status = status;
    }
}
