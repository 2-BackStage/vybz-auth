package back.vybz.auth_service.user.domain.mysql;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
    GOOGLE("구글"),
    KAKAO("카카오");

    private final String type;

    @JsonValue
    public String getLabel() {
        return type;
    }

    @JsonCreator
    public static SocialType fromString(String value) {
        for (SocialType status : SocialType.values()) {
            if (status.type.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown SocialType: " + value);
    }
}
