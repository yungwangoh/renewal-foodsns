package mubex.renewal_foodsns.domain.dto.response;

import lombok.Builder;

@Builder
public record PostPageResponse(
        long id,
        String thumbnail,
        String title,
        long heart,
        long views,
        String nickName
) {
}
