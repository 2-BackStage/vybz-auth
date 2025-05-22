package back.vybz.auth_service.busker.domain.mysql;

import back.vybz.auth_service.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@Table(name = "busker_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BuskerInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 버스커 uuid
     */
    @Column(name = "user_uuid", nullable = false, unique = true)
    private String userUuid;

    /**
     * 버스커 이메일
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * 버스커 비밀번호
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 버스커 카테고리
     */
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    /**
     * 버스커 닉네임(활동명)
     */
    @Column(name = "nickname", nullable = false)
    private String nickname;

    /**
     * 버스커 생년월일(그룹이면 탄생일)
     */
    @Column(name = "birth_date")
    private Date birthDate;


    @Builder
    public BuskerInfo(Long id, String userUuid, String email, String password, Long categoryId, String nickname, Date birthDate) {
        this.id = id;
        this.userUuid = userUuid;
        this.email = email;
        this.password = password;
        this.categoryId = categoryId;
        this.nickname = nickname;
        this.birthDate = birthDate;
    }

}
