package back.vybz.auth_service.user.domain.mysql;

import back.vybz.auth_service.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fan_info")
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자 uuid
     */
    @Column(name = "user_uuid", nullable = false, unique = true)
    private String userUuid;

    /**
     * 유저 id
     */
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    /**
     * 소셜 타입
     */
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    /**
     * 소셜 id
     */
    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Builder
    public User(Long id, String userUuid, String userId,
                SocialType socialType, String providerId) {
        this.id = id;
        this.userUuid = userUuid;
        this.userId = userId;
        this.socialType = socialType;
        this.providerId = providerId;
    }
}
