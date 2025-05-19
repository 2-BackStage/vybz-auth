package back.vybz.auth_service.domain.mysql;

import back.vybz.auth_service.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fan_info")
@Getter
@NoArgsConstructor
public class FanInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_uuid", nullable = false, unique = true)
    private String userUuid;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

}
