package mubex.renewal_foodsns.domain.dto.response;

import lombok.Builder;
import mubex.renewal_foodsns.domain.type.FoodTag;

@Builder
public record PostResponse(
        String title,
        String text,
        int heart,
        int report,
        FoodTag foodTag,
        long views,
        MemberResponse memberResponse
) {
}
