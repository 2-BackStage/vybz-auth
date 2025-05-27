package back.vybz.auth_service.user.domain.mysql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("일반 사용자"),
    BUSKER("버스커");

    private final String roleName;
}
